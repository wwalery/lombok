import lombok.extern.*;
import lombok.extern.java.*;

@Log
@LogEntry
public class LogEntry_JUL_all {

  private String test1(String param1, String param2) {
    if (param1 == null) {
      return "";
    } else {
      return param1.toString();
    }
  }

  public String test2() {
    return "".toString();
  }

  public void test3(int[] param, String[] arg) {
    return;
  }


}