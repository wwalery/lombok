# Project Lombok - fork with logging method entry/exit

This fork adds annotation **LogEntry** to Lombok framework.
With this annotation you may log entry/exit for any method. Also you may log method execution time.

## Installation

See Project Lombok installation instructions.
The fork is based on Lombok version 1.16.16 - because the latest version (1.16.18) doesn't work in my main IDE - NetBeans

### Usage

An annotation **LogEntry** can be applied to single method or to entire class. When a class is annotated, annotation and its settings
are applied to all methods (if method doesn't have own annotation). Also one of Lombok logging annotation is necessary.

Parameters for annotation:

* **isEntry** - generates log of method entry with all method arguments (default - *true*)
* **isExit** - generates log of method exit, with method result logging (default - *true*)
* **method** - method for logging (default - *trace*, in JUL - *finest*)
* **withTimer** - generates timing of method execution (default - *true*). It's valid only if *isExit* is *true*.


Example:

```java
@Slf4j
@LogEntry
public class Test {
  public boolean copyFileOne(String in, String out) throws IOException {
    ...
    return !internalVar.isEmpty();
  }
}
```

produce:
```java
public class Test {
...
  public boolean copyFile(String in, String out) throws IOException {
    long $timer = System.currentTimeMillis();
    if (log.isTraceEnabled()) log.trace("entry: Test.copyFile(in={},out={})",in, out);
    ...
    boolean $result = !internalVar.isEmpty();
    if (log.isTraceEnabled()) log.trace("exit: Test.copyFile({}) = {} ms", $result, System.currentTimeMillis() - $timer);
    return $result;
  }
}
```


But when one method is annotated, all annotation settings are applied to this method, without taking into account the class annotation:
```java
@Slf4j
@LogEntry
public class Test {
  public boolean copyFileOne(String in, String out) throws IOException {
    ...
    return !internalVar.isEmpty();
  }
      
  @LogEntry(method = "info", isExit = false, withTimer = false)
  public void copyFileTwo(String in, String out) throws IOException {
    ...
  }

  @LogEntry(method = "debug", withTimer = false)
  public void copyFileThree(String in, String out) throws IOException {
    ...
  }

}
```

produce:
```java
public class Test {
...
  public boolean copyFileOne(String in, String out) throws IOException {
    long $timer = System.currentTimeMillis();
    if (log.isTraceEnabled()) log.trace("entry: Test.copyFileOne(in={},out={})",in, out);
    ...
    boolean $result = !internalVar.isEmpty();
    if (log.isTraceEnabled()) log.trace("exit: Test.copyFileOne({}) = {} ms", $result, System.currentTimeMillis() - $timer);
    return $result;
  }

  public void copyFileTwo(String in, String out) throws IOException {
    if (log.isInfoEnabled()) log.info("entry: Test.copyFileTwo(in={},out={})",in, out);
    ...
  }

  public void copyFileThree(String in, String out) throws IOException {
    if (log.isDebugEnabled()) log.debug("entry: Test.copyFileThree(in={},out={})",in, out);
    ...
    if (log.isDebugEnabled()) log.debug("exit: Test.copyFileThree()");
  }

}
```


WARINIG: Annonation implemented only for standard javac compiler, not for Eclipse.


