package boardTests;

import checkersRules.AbstractGame;
import checkersRules.EnglishGame;
import checkersRules.RussianGame;
import checkersRules.TurkishGame;
import factory.RulesFactory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;



public class RulesFactoryTest {
    @Test
    public void rulesFactoryTest(){
        RulesFactory factory = new RulesFactory();

        AbstractGame englishGame = factory.create("EnglishGame");
        assertEquals(englishGame.getClass(), EnglishGame.class);

        AbstractGame russianGame = factory.create("RussianGame");
        assertEquals(russianGame.getClass(), RussianGame.class);

        AbstractGame turkishGame = factory.create("TurkishGame");
        assertEquals(turkishGame.getClass(), TurkishGame.class);
    }
}
