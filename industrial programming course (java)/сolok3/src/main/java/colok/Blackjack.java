package colok;

public class Blackjack extends CardGame {
    Blackjack(String bet) {
        this.bet = bet;
    }

    @Override
    protected void startMessage() {
        System.out.println("You decided to play blackjack");
    }

    @Override
    protected void drawStartCards() {
        System.out.println("Dealer and players draw cards for blackjack");
    }

    @Override
    protected void playGame() {
        System.out.println("Playing blackjack");
        for (int i = 0; i < 5; i++) {
            System.out.print(".");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println();
    }

    @Override
    protected String win(String bet) {
        System.out.println("You win blackjack game :)");
        return bet + bet;
    }

    @Override
    protected void endGame() {
        System.out.println("Blackjack game ended");
    }

}
