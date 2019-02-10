import lombok.extern.flogger.Flogger;
@lombok.extern.flogger.Flogger class LoggerFlogger {
  private static final com.google.common.flogger.FluentLogger log = com.google.common.flogger.FluentLogger.forEnclosingClass();
  <clinit>() {
  }
  LoggerFlogger() {
    super();
  }
}
@Flogger class LoggerFloggerWithImport {
  private static final com.google.common.flogger.FluentLogger log = com.google.common.flogger.FluentLogger.forEnclosingClass();
  <clinit>() {
  }
  LoggerFloggerWithImport() {
    super();
  }
}
class LoggerFloggerOuter {
  static @lombok.extern.flogger.Flogger class Inner {
    private static final com.google.common.flogger.FluentLogger log = com.google.common.flogger.FluentLogger.forEnclosingClass();
    <clinit>() {
    }
    Inner() {
      super();
    }
  }
  LoggerFloggerOuter() {
    super();
  }
}
