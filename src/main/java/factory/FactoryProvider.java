package factory;

/**
 * The type Factory provider.
 */
public class FactoryProvider {
    /**
     * Get factory abstract factory.
     *
     * @param choice the choice
     * @return the abstract factory
     */
    public static AbstractFactory getFactory(String choice){
        if("AbstractGame".equalsIgnoreCase(choice)){
            return new RulesFactory();
        }
        return null;
    }

}
