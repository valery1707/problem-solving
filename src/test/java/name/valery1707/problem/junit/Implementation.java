package name.valery1707.problem.junit;

import java.util.Map;

public class Implementation<T> {

    private final String name;
    private final T impl;

    private Implementation(String name, T impl) {
        this.name = name;
        this.impl = impl;
    }

    public static <T> Implementation<T> of(String name, T impl) {
        return new Implementation<>(name, impl);
    }

    public static <T> Implementation<T> of(Map.Entry<String, T> entry) {
        return of(entry.getKey(), entry.getValue());
    }

    public String name() {
        return name;
    }

    public T get() {
        return impl;
    }

    @Override
    public String toString() {
        return name();
    }

}
