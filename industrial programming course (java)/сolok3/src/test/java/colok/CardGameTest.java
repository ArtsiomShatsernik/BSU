package colok;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CardGameTest {
    CardGame game;
    @Before
    public void setUp() {
        game = new Blackjack("100");
    }

    @Test
    public void lose() {
       String actual = game.lose();
       String expected = "Nothing";
       assertEquals(expected, actual);
    }
}