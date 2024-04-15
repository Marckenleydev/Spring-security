package marc.dev.documentmanagementsystem.function;
@FunctionalInterface
public interface TriFunction<T, U, V> {
    void accept(T t, U u, V v);
}
