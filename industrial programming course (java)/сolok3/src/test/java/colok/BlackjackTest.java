package colok;

import org.junit.Test;

import static org.junit.Assert.*;

public class BlackjackTest {

    @Test
    public void winTest() {
        String bet = "123";
        CardGame cardGame = new Blackjack(bet);
        String expected = "123123";
        String actual = cardGame.win(bet);
        assertEquals(expected, actual);
    }
}