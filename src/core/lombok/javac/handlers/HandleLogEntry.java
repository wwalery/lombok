/*
 * Copyright (C) 2013-2014 The Project Lombok Authors.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package lombok.javac.handlers;

import com.sun.source.tree.Tree;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.tree.JCTree;
import org.mangosdk.spi.ProviderFor;

import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.Name;
import com.sun.tools.javac.util.List;
import javax.lang.model.type.TypeKind;

import lombok.ConfigurationKeys;
import lombok.extern.LogEntry;
import lombok.core.AnnotationValues;
import lombok.core.AST.Kind;
import lombok.javac.Javac;
import lombok.javac.JavacAnnotationHandler;
import lombok.javac.JavacNode;
import lombok.javac.JavacTreeMaker;
import lombok.javac.handlers.HandleLog.LoggingFramework;
import static lombok.javac.handlers.JavacHandlerUtil.chainDots;

@ProviderFor(JavacAnnotationHandler.class)
public class HandleLogEntry extends JavacAnnotationHandler<LogEntry> {

  private final static String DEFAULT_LOG_METHOD = "trace";
  private final static String TIMER_VARIABLE = "$timer";
  private final static String RESULT_VARIABLE = "$result";

  private Name logMethod;
  private boolean useParamsInLog;
  private JCTree.JCMethodInvocation checkLoggable;
  private JCTree.JCExpression logField;

  @Override
  public void handle(AnnotationValues<LogEntry> annotation, JCAnnotation ast, JavacNode annotationNode) {

    JavacNode node = annotationNode.up();
//    System.out.println(node.getName() + ": " + node.getKind());
    JavacHandlerUtil.deleteAnnotationIfNeccessary(annotationNode, LogEntry.class);
    String logFieldName = annotationNode.getAst().readConfiguration(ConfigurationKeys.LOG_ANY_FIELD_NAME);
    if (logFieldName == null) {
      logFieldName = "log";
    }
    JavacTreeMaker maker = annotationNode.getTreeMaker();
    logField = maker.Ident(node.toName(logFieldName));

    switch (node.getKind()) {
      case TYPE:
        if (!fillFrameworkDependance(annotationNode, node, logFieldName, annotation.getInstance().method())) {
          return;
        }
        for (JavacNode child : node.down()) {
          if (child.getKind() != Kind.METHOD) {
            continue;
          }
//          System.out.println(child.getName() + ": " + JavacHandlerUtil.hasAnnotation(LogEntry.class, child));
          if (!JavacHandlerUtil.hasAnnotation(LogEntry.class, child)) {
            fillNode(annotation.getInstance(), child);
          }
        }
        node.getAst().setChanged();
        break;
      case METHOD:
        if (!fillFrameworkDependance(annotationNode, node.up(), logFieldName, annotation.getInstance().method())) {
          return;
        }
        fillNode(annotation.getInstance(), node);
        node.getAst().setChanged();
    }
  }

  private void fillNode(LogEntry logEntry, JavacNode node) {
    if (logEntry.isEntry()) {
      createLogEntry(node, false, false);
    }
    if (logEntry.withTimer()) {
      createTimer(node);
    }
    if (logEntry.isExit()) {
      createLogExit(node, logEntry.withTimer());
    }
  }

  private boolean fillFrameworkDependance(JavacNode annotationNode, JavacNode classNode, String logfieldName, String logMethodName) {
    LoggingFramework framework = null;
    for (JavacNode child : classNode.down()) {
      if (child.getKind() != Kind.FIELD) {
        continue;
      }
      JCTree.JCVariableDecl log = (JCTree.JCVariableDecl) child.get();
      if (!log.name.contentEquals(logfieldName)) {
        continue;
      }
      for (LoggingFramework frm : LoggingFramework.values()) {
        if (frm.getLoggerTypeName().equals(log.vartype.toString())) {
          framework = frm;
          break;
        }
      }
    }
    if (framework == null) {
      annotationNode.addError("Logger annotation not defined in class.");
      return false;
    }
    String checkLoggableMethodName = "is" + logMethodName.substring(0, 1).toUpperCase() + logMethodName.substring(1) + "Enabled";
    List<JCTree.JCExpression> checkLoggableArgs;
    switch (framework) {
      case LOG4J2:
      case SLF4J:
      case XSLF4J:
        useParamsInLog = true;
        checkLoggableArgs = List.<JCTree.JCExpression>nil();
        break;
      case LOG4J:
      case COMMONS:
        useParamsInLog = false;
        checkLoggableArgs = List.<JCTree.JCExpression>nil();
        break;
      case JBOSSLOG:
      case JUL:
        if (DEFAULT_LOG_METHOD.equals(logMethodName)) {
          logMethodName = "finest";
        }
        useParamsInLog = false;
        checkLoggableMethodName = "isLoggable";
        checkLoggableArgs = List.<JCTree.JCExpression>of(JavacHandlerUtil.chainDots(classNode, "java", "util", "logging", "Level", logMethodName.toUpperCase()));
        break;
      default:
        useParamsInLog = false;
        checkLoggableArgs = List.<JCTree.JCExpression>nil();
    }
    logMethod = classNode.toName(logMethodName);
    Name checkLoggableMethod = classNode.toName(checkLoggableMethodName);
    JavacTreeMaker maker = classNode.getTreeMaker();
    checkLoggable = maker.Apply(List.<JCTree.JCExpression>nil(), maker.Select(logField, checkLoggableMethod), checkLoggableArgs);

    return true;
  }

  private JCExpression frameType(JavacNode node, JCExpression varType, JCExpression param) {
    boolean fieldIsPrimitive = varType instanceof JCTree.JCPrimitiveTypeTree;
    boolean fieldIsPrimitiveArray = varType instanceof JCTree.JCArrayTypeTree && ((JCTree.JCArrayTypeTree) varType).elemtype instanceof JCTree.JCPrimitiveTypeTree;
    boolean fieldIsObjectArray = !fieldIsPrimitiveArray && varType instanceof JCTree.JCArrayTypeTree;
    JavacTreeMaker maker = node.getTreeMaker();
    if (fieldIsPrimitiveArray || fieldIsObjectArray) {
      JCTree.JCExpression tsMethod = chainDots(node, "java", "util", "Arrays", fieldIsObjectArray ? "deepToString" : "toString");
      return maker.Apply(List.<JCTree.JCExpression>nil(), tsMethod, List.<JCTree.JCExpression>of(param));
    } else {
      return param;
    }
  }

  private void createTimer(JavacNode node) {
    JCMethodDecl method = (JCMethodDecl) node.get();
    JCTree.JCBlock nodeToAdd = method.body;
    JavacTreeMaker maker = node.getTreeMaker();
    JCVariableDecl timerDecl = maker.VarDef(
            maker.Modifiers(Flags.FINAL),
            node.toName(TIMER_VARIABLE), maker.Ident(node.toName("long")),
            JavacHandlerUtil.chainDots(node, "System", "currentTimeMillis()")
    );
    nodeToAdd.stats = nodeToAdd.stats.prepend(timerDecl);
  }

  private JCTree.JCStatement generateLogCall(JavacNode node, List<JCTree.JCExpression> logArgs) {
    JavacTreeMaker maker = node.getTreeMaker();
    JCTree.JCMethodInvocation log;
    if (useParamsInLog) {
      log = maker.Apply(List.<JCTree.JCExpression>nil(), maker.Select(logField, logMethod), logArgs);
    } else {
      if (logArgs.size() > 1) {
        JCTree.JCMethodInvocation formatter = maker.Apply(List.<JCTree.JCExpression>nil(),
                maker.Select(maker.Ident(node.toName("String")), node.toName("format")), logArgs);
        log = maker.Apply(List.<JCTree.JCExpression>nil(), maker.Select(logField, logMethod), List.<JCTree.JCExpression>of(formatter));
      } else {
        log = maker.Apply(List.<JCTree.JCExpression>nil(), maker.Select(logField, logMethod), logArgs);
      }
    }
    JCTree.JCStatement result = maker.If(checkLoggable, maker.Exec(log), null);
    return result;
  }

  private void createLogEntry(JavacNode node, boolean inTry, boolean inSynchronized) {
    JCMethodDecl method = (JCMethodDecl) node.get();
    List<JCStatement> statements = method.body.stats;

    JCTree.JCBlock nodeToAdd = method.body;
    /* delving into try and synchronized statements */
    List<JCStatement> stats = statements;
    int idx = 0;
    while (stats.size() > idx) {
      JCStatement stat = stats.get(idx++);
      if (JavacHandlerUtil.isConstructorCall(stat)) {
        continue;
      }
      if (inTry && (stat instanceof JCTry)) {
        stats = ((JCTry) stat).body.stats;
        idx = 0;
        nodeToAdd = ((JCTry) stat).body;
        continue;
      }
      if (inSynchronized && (stat instanceof JCSynchronized)) {
        stats = ((JCSynchronized) stat).body.stats;
        nodeToAdd = ((JCSynchronized) stat).body;
        idx = 0;
        continue;
      }
      break;
    }
    JavacTreeMaker maker = node.getTreeMaker();

    String templateStr = "entry: " + node.up().getName() + "." + node.getName() + "(";
    String delim = "";
    for (JCTree.JCVariableDecl param : method.params) {
      templateStr += delim + param.name.toString() + "=" + (useParamsInLog ? "{}" : "%s");
      delim = ",";
    }
    templateStr += ")";
    List<JCTree.JCExpression> args = List.<JCTree.JCExpression>of(maker.Literal(templateStr));
    for (JCTree.JCVariableDecl param : method.params) {
      args = args.append(frameType(node, param.vartype, maker.Ident(param)));
    }

    JCTree.JCStatement result = generateLogCall(node, args);
    if (JavacHandlerUtil.isConstructorCall(nodeToAdd.stats.head)) {
      nodeToAdd.stats.tail = nodeToAdd.stats.tail.prepend(result);
    } else {
      nodeToAdd.stats = nodeToAdd.stats.prepend(result);
    }
  }

  private void processBlock(JavacNode node, List<JCStatement> statements, boolean withTimer) {
    List<JCStatement> stats = statements;
    while (stats.head != null) {
      JCStatement stat = stats.head;
      List<JCStatement> result = processStatement(node, stat, withTimer);
      if (result != null) {
        JavacTreeMaker maker = node.getTreeMaker();
//        JCBlock returnBlock = maker.Block(0, result);
        stats.head = result.size() == 1 ? result.head : maker.Block(0, result);
      }
      stats = stats.tail;
    }
  }

  private List<JCStatement> processStatement(JavacNode node, JCStatement stat, boolean withTimer) {
    JavacTreeMaker maker = node.getTreeMaker();
    if (stat instanceof JCBlock) {
      processBlock(node, ((JCBlock) stat).stats, withTimer);
    } else if (stat instanceof JCCase) {
      processBlock(node, ((JCCase) stat).stats, withTimer);
    } else if (stat instanceof JCIf) {
      List<JCStatement> result = processStatement(node, ((JCIf) stat).thenpart, withTimer);
      if (result != null) {
        ((JCIf) stat).thenpart = result.size() == 1 ? result.head : maker.Block(0, result);
      }
      result = processStatement(node, ((JCIf) stat).elsepart, withTimer);
      if (result != null) {
        ((JCIf) stat).elsepart = result.size() == 1 ? result.head : maker.Block(0, result);
      }
    } else if (stat instanceof JCCase) {
      processBlock(node, ((JCCase) stat).stats, withTimer);
    } else if (stat instanceof JCSynchronized) {
      processBlock(node, ((JCSynchronized) stat).body.stats, withTimer);
    } else if (stat instanceof JCTry) {
      processBlock(node, ((JCTry) stat).body.stats, withTimer);
    } else if (stat instanceof JCTry) {
      processBlock(node, ((JCTry) stat).body.stats, withTimer);
    } else if (stat instanceof JCReturn) {
      return processReturn(node, (JCReturn) stat, withTimer);
    }
    return null;
  }

  private List<JCStatement> processReturn(JavacNode node, JCReturn stat, boolean withTimer) {
    JCMethodDecl method = (JCMethodDecl) node.get();
    boolean hasReturnAny = !((method.restype == null) || ((method.restype instanceof JCPrimitiveTypeTree) && (((JCPrimitiveTypeTree) method.restype).getPrimitiveTypeKind() == TypeKind.VOID)));
    JavacTreeMaker maker = node.getTreeMaker();
    String templateStr = "exit: " + node.up().getName() + "." + node.getName() + "("
            + (hasReturnAny ? (useParamsInLog ? "{}" : "%s") : "")
            + (withTimer ? (useParamsInLog ? ") = {} ms" : "%s") : ")")
            ;
    List<JCExpression> returnResult = List.<JCExpression>of(maker.Literal(templateStr));
    List<JCStatement> returnData = List.<JCStatement>nil();
    JCStatement result = stat;
    if (hasReturnAny) {
//      System.out.println(method.name + " = " + method.restype.getClass() + ": " + stat.expr.getKind());
//      if ((stat.expr instanceof JCMethodInvocation)
      JCExpression returnExpr;
      if (stat.expr.getKind() == Tree.Kind.NULL_LITERAL) {
        returnExpr = maker.Literal("NULL");
      } else if ((stat.expr.getKind() == Tree.Kind.BOOLEAN_LITERAL)
              || (stat.expr.getKind() == Tree.Kind.CHAR_LITERAL)
              || (stat.expr.getKind() == Tree.Kind.DOUBLE_LITERAL)
              || (stat.expr.getKind() == Tree.Kind.FLOAT_LITERAL)
              || (stat.expr.getKind() == Tree.Kind.IDENTIFIER)
              || (stat.expr.getKind() == Tree.Kind.INT_LITERAL)
              || (stat.expr.getKind() == Tree.Kind.LONG_LITERAL)
              || (stat.expr.getKind() == Tree.Kind.MEMBER_SELECT)
              || (stat.expr.getKind() == Tree.Kind.STRING_LITERAL)
              || (stat.expr.getKind() == Tree.Kind.VARIABLE)) {
        returnExpr = stat.expr;
      } else {
        JCVariableDecl resultDecl = maker.VarDef(
                maker.Modifiers(Flags.FINAL), node.toName(RESULT_VARIABLE), method.restype, stat.expr);
        returnData = returnData.append(resultDecl);
        result = maker.Return(maker.Ident(resultDecl.name));
        returnExpr = maker.Ident(resultDecl.name);
      }
      if (method.restype instanceof JCArrayTypeTree) {
        JCTree.JCExpression asListMethod = chainDots(node, "java", "util", "Arrays", "asList");
        returnExpr = maker.Apply(List.<JCTree.JCExpression>nil(), asListMethod, List.<JCTree.JCExpression>of(returnExpr));
      }
      returnResult = returnResult.append(returnExpr);
    }
    if (withTimer) {
      JCExpression timer = maker.Binary(Javac.CTC_MINUS,
              JavacHandlerUtil.chainDots(node, "System", "currentTimeMillis()"),
              maker.Ident(node.toName(TIMER_VARIABLE)));
      returnResult = returnResult.append(timer);
    }
    JCTree.JCStatement logResult = generateLogCall(node, returnResult);
    return returnData.append(logResult).append(result);
  }

  private void createLogExit(JavacNode node, boolean withTimer) {
    JCMethodDecl method = (JCMethodDecl) node.get();
    processBlock(node, method.body.stats, withTimer);
  }

}
