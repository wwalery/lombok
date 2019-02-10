import lombok.Builder;
import lombok.Singular;
public @Builder class BuilderDefaultsWarnings {
  public static @java.lang.SuppressWarnings("all") class BuilderDefaultsWarningsBuilder {
    private @java.lang.SuppressWarnings("all") long x;
    private @java.lang.SuppressWarnings("all") int z;
    private @java.lang.SuppressWarnings("all") java.util.ArrayList<String> items;
    @java.lang.SuppressWarnings("all") BuilderDefaultsWarningsBuilder() {
      super();
    }
    public @java.lang.SuppressWarnings("all") BuilderDefaultsWarningsBuilder x(final long x) {
      this.x = x;
      return this;
    }
    public @java.lang.SuppressWarnings("all") BuilderDefaultsWarningsBuilder z(final int z) {
      this.z = z;
      return this;
    }
    public @java.lang.SuppressWarnings("all") BuilderDefaultsWarningsBuilder item(final String item) {
      if ((this.items == null))
          this.items = new java.util.ArrayList<String>();
      this.items.add(item);
      return this;
    }
    public @java.lang.SuppressWarnings("all") BuilderDefaultsWarningsBuilder items(final java.util.Collection<? extends String> items) {
      if ((this.items == null))
          this.items = new java.util.ArrayList<String>();
      this.items.addAll(items);
      return this;
    }
    public @java.lang.SuppressWarnings("all") BuilderDefaultsWarningsBuilder clearItems() {
      if ((this.items != null))
          this.items.clear();
      return this;
    }
    public @java.lang.SuppressWarnings("all") BuilderDefaultsWarnings build() {
      java.util.List<String> items;
      switch (((this.items == null) ? 0 : this.items.size())) {
      case 0 :
          items = java.util.Collections.emptyList();
          break;
      case 1 :
          items = java.util.Collections.singletonList(this.items.get(0));
          break;
      default :
          items = java.util.Collections.unmodifiableList(new java.util.ArrayList<String>(this.items));
      }
      return new BuilderDefaultsWarnings(x, z, items);
    }
    public @java.lang.Override @java.lang.SuppressWarnings("all") java.lang.String toString() {
      return (((((("BuilderDefaultsWarnings.BuilderDefaultsWarningsBuilder(x=" + this.x) + ", z=") + this.z) + ", items=") + this.items) + ")");
    }
  }
  long x = System.currentTimeMillis();
  final int y = 5;
  @Builder.Default int z;
  @Builder.Default @Singular java.util.List<String> items;
  @java.lang.SuppressWarnings("all") BuilderDefaultsWarnings(final long x, final int z, final java.util.List<String> items) {
    super();
    this.x = x;
    this.z = z;
    this.items = items;
  }
  public static @java.lang.SuppressWarnings("all") BuilderDefaultsWarningsBuilder builder() {
    return new BuilderDefaultsWarningsBuilder();
  }
}
class NoBuilderButHasDefaults {
  public static @java.lang.SuppressWarnings("all") class NoBuilderButHasDefaultsBuilder {
    @java.lang.SuppressWarnings("all") NoBuilderButHasDefaultsBuilder() {
      super();
    }
    public @java.lang.SuppressWarnings("all") NoBuilderButHasDefaults build() {
      return new NoBuilderButHasDefaults();
    }
    public @java.lang.Override @java.lang.SuppressWarnings("all") java.lang.String toString() {
      return "NoBuilderButHasDefaults.NoBuilderButHasDefaultsBuilder()";
    }
  }
  private final @Builder.Default long z = 5;
  public @Builder NoBuilderButHasDefaults() {
    super();
  }
  public static @java.lang.SuppressWarnings("all") NoBuilderButHasDefaultsBuilder builder() {
    return new NoBuilderButHasDefaultsBuilder();
  }
}