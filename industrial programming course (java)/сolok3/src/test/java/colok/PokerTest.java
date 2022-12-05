package colok;

import org.junit.Test;

import static org.junit.Assert.*;

public class PokerTest {

    @Test
    public void winTest() {
        String bet = "123";
        CardGame cardGame = new Poker(bet);
        String expected = "123123123";
        String actual = cardGame.win(bet);
        assertEquals(expected, actual);
    }
}