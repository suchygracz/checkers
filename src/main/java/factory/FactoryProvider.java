package factory;

public class FactoryProvider {
    public static AbstractFactory getFactory(String choice){
        if("AbstractGame".equalsIgnoreCase(choice)){
            return new RulesFactory();
        }
        return null;
    }

}
