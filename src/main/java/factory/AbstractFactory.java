package factory;

/**
 * The interface Abstract factory.
 *
 * @param <T> the type parameter
 */
public interface AbstractFactory<T> {
    /**
     * Create t.
     *
     * @param gameType the game type
     * @return the t
     */
    T create(String gameType);
}
