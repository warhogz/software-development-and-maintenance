package variant13;

/** Обобщённый функциональный интерфейс */
public interface Converter<T, R> {
    R convert(T value);
}
