@lombok.Data class GetterSetterJavadoc1 {
  private int fieldName;
  public @java.lang.SuppressWarnings("all") int getFieldName() {
    return this.fieldName;
  }
  public @java.lang.SuppressWarnings("all") void setFieldName(final int fieldName) {
    this.fieldName = fieldName;
  }
  public @java.lang.Override @java.lang.SuppressWarnings("all") boolean equals(final java.lang.Object o) {
    if ((o == this))
        return true;
    if ((! (o instanceof GetterSetterJavadoc1)))
        return false;
    final GetterSetterJavadoc1 other = (GetterSetterJavadoc1) o;
    if ((! other.canEqual((java.lang.Object) this)))
        return false;
    if ((this.getFieldName() != other.getFieldName()))
        return false;
    return true;
  }
  protected @java.lang.SuppressWarnings("all") boolean canEqual(final java.lang.Object other) {
    return (other instanceof GetterSetterJavadoc1);
  }
  public @java.lang.Override @java.lang.SuppressWarnings("all") int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = ((result * PRIME) + this.getFieldName());
    return result;
  }
  public @java.lang.Override @java.lang.SuppressWarnings("all") java.lang.String toString() {
    return (("GetterSetterJavadoc1(fieldName=" + this.getFieldName()) + ")");
  }
  public @java.lang.SuppressWarnings("all") GetterSetterJavadoc1() {
    super();
  }
}
class GetterSetterJavadoc2 {
  private @lombok.Getter @lombok.Setter int fieldName;
  GetterSetterJavadoc2() {
    super();
  }
  public @java.lang.SuppressWarnings("all") int getFieldName() {
    return this.fieldName;
  }
  public @java.lang.SuppressWarnings("all") void setFieldName(final int fieldName) {
    this.fieldName = fieldName;
  }
}
class GetterSetterJavadoc3 {
  private @lombok.Getter @lombok.Setter int fieldName;
  GetterSetterJavadoc3() {
    super();
  }
  public @java.lang.SuppressWarnings("all") int getFieldName() {
    return this.fieldName;
  }
  public @java.lang.SuppressWarnings("all") void setFieldName(final int fieldName) {
    this.fieldName = fieldName;
  }
}
@lombok.experimental.Accessors(chain = true,fluent = true) class GetterSetterJavadoc4 {
  private @lombok.Getter @lombok.Setter int fieldName;
  GetterSetterJavadoc4() {
    super();
  }
  public @java.lang.SuppressWarnings("all") int fieldName() {
    return this.fieldName;
  }
  public @java.lang.SuppressWarnings("all") GetterSetterJavadoc4 fieldName(final int fieldName) {
    this.fieldName = fieldName;
    return this;
  }
}
@lombok.experimental.Accessors(chain = true,fluent = true) class GetterSetterJavadoc5 {
  private @lombok.Getter @lombok.Setter int fieldName;
  GetterSetterJavadoc5() {
    super();
  }
  public @java.lang.SuppressWarnings("all") int fieldName() {
    return this.fieldName;
  }
  public @java.lang.SuppressWarnings("all") GetterSetterJavadoc5 fieldName(final int fieldName) {
    this.fieldName = fieldName;
    return this;
  }
}