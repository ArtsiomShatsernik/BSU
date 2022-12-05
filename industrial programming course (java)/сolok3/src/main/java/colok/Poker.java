package colok;

public class Poker extends CardGame {
    Poker(String bet) {
        this.bet = bet;
    }

    @Override
    protected void startMessage() {
        System.out.println("You decided to play poker");
    }

    @Override
    protected void drawStartCards() {
        System.out.println("Dealer and players draw cards for poker");
    }

    @Override
    protected void playGame() {
        System.out.println("Playing poker");
        for (int i = 0; i < 10; i++) {
            System.out.print(".");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println();
    }


    @Override
    protected String win(String bet) {
        System.out.println("You win poker game :)");
        return bet + bet + bet;
    }

    @Override
    protected void endGame() {
        System.out.println("Poker game ended");
    }
}
