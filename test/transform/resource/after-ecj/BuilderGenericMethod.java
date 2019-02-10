import java.util.List;
import lombok.Builder;
import java.util.*;
class BuilderGenericMethod<T> {
  public @java.lang.SuppressWarnings("all") class MapBuilder<N extends Number> {
    private @java.lang.SuppressWarnings("all") int a;
    private @java.lang.SuppressWarnings("all") long b;
    @java.lang.SuppressWarnings("all") MapBuilder() {
      super();
    }
    public @java.lang.SuppressWarnings("all") MapBuilder<N> a(final int a) {
      this.a = a;
      return this;
    }
    public @java.lang.SuppressWarnings("all") MapBuilder<N> b(final long b) {
      this.b = b;
      return this;
    }
    public @java.lang.SuppressWarnings("all") Map<N, T> build() {
      return BuilderGenericMethod.this.<N>foo(a, b);
    }
    public @java.lang.Override @java.lang.SuppressWarnings("all") java.lang.String toString() {
      return (((("BuilderGenericMethod.MapBuilder(a=" + this.a) + ", b=") + this.b) + ")");
    }
  }
  BuilderGenericMethod() {
    super();
  }
  public @Builder <N extends Number>Map<N, T> foo(int a, long b) {
    return null;
  }
  public @java.lang.SuppressWarnings("all") <N extends Number>MapBuilder<N> builder() {
    return new MapBuilder<N>();
  }
}
