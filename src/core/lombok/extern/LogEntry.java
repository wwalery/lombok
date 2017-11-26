/*
 * Copyright (C) 2009-2017 The Project Lombok Authors.
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
package lombok.extern;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Insert method entry logging.
 * <p>
 * Applied to all class method, when class annotated
 * Also need any of @Log annotation on class.
 * <p>
 * Parameters:<p>
 * <b>isEntry</b> - generate log for method entry with all arguments
 * <b>isExit</b> - generate log for method exit (any return)
 * <b>method</b> - method for logging, by default - "trace" (in JUL - "finest")
 * <b>withTimer</b> - generate timing for method execution. Valid on;y when isExit defined.
 * <p>
 * Example:
 * <pre>
 * &#64;LogEntry
 * public boolean copyFile(String in, String out) throws IOException {
 *     ...
 *    return true;
 * }
 * </pre>
 * 
 * Will generate (for @Slf4j):
 * <pre>
 * public boolean copyFile(String in, String out) throws IOException {
 *    long $timer = System.currentTimeMillis();
 *    log.trace("entry: copyFile(in={},out={})",in, out));
 *     ...
 *    boolean $result = true;
 *    log.trace("exit: copyFile({}) = {} ms", $result, System.currentTimeMillis() - $timer));
 *    return $result
 * }
 * </pre>
 * 
 
 */
@Target({ ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.SOURCE)
public @interface LogEntry {
//  boolean inTry() default false;
//  boolean inSynchronized() default false;
  boolean isEntry() default true;
  boolean isExit() default true;
  String method() default "trace";
  boolean withTimer() default true;
}
