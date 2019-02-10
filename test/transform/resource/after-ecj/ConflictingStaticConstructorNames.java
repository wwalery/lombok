@lombok.Data(staticConstructor = "of") @lombok.NoArgsConstructor class ConflictingStaticConstructorNames {
  public @java.lang.Override @java.lang.SuppressWarnings("all") boolean equals(final java.lang.Object o) {
    if ((o == this))
        return true;
    if ((! (o instanceof ConflictingStaticConstructorNames)))
        return false;
    final ConflictingStaticConstructorNames other = (ConflictingStaticConstructorNames) o;
    if ((! other.canEqual((java.lang.Object) this)))
        return false;
    return true;
  }
  protected @java.lang.SuppressWarnings("all") boolean canEqual(final java.lang.Object other) {
    return (other instanceof ConflictingStaticConstructorNames);
  }
  public @java.lang.Override @java.lang.SuppressWarnings("all") int hashCode() {
    final int result = 1;
    return result;
  }
  public @java.lang.Override @java.lang.SuppressWarnings("all") java.lang.String toString() {
    return "ConflictingStaticConstructorNames()";
  }
  public @java.lang.SuppressWarnings("all") ConflictingStaticConstructorNames() {
    super();
  }
}