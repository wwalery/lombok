import lombok.extern.*;
import lombok.extern.slf4j.*;

@Slf4j
public class LogEntry_Slf4j_method {

  @LogEntry
  private String test1(String param1, String param2) {
    return "";
  }

  public String test2() {
    return "";
  }


}