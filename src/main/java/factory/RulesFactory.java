package factory;

import checkersRules.AbstractGame;
import checkersRules.EnglishGame;
import checkersRules.RussianGame;
import checkersRules.TurkishGame;

public class RulesFactory implements AbstractFactory {
    public AbstractGame create(String rulesType){
        if ("EnglishGame".equalsIgnoreCase(rulesType)){
            return new EnglishGame();
        }
        else if ("RussianGame".equalsIgnoreCase(rulesType)){
            return new RussianGame();
        }
        else if ("TurkishGame".equalsIgnoreCase(rulesType)){
            return new TurkishGame();
        }
        return null;
    }
}
