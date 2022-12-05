package colok;

import java.util.Scanner;

public abstract class CardGame {
    String bet;

    CardGame() {
    }

    public String gameProcess() {
        String result;
        startMessage();
        setBet(bet);
        formDeck();
        shuffleDeck();
        drawStartCards();
        playGame();
        System.out.println("Are you lucky today? (Y/N)");
        Scanner in = new Scanner(System.in);
        String answer;
        while (true) {
            answer = in.nextLine();
            if (answer.equals("Y") || answer.equals("N")) {
                break;
            } else {
                System.out.println("Incorrect input. Try again.");
            }
        }
        if (answer.equals("Y")) {
            result = win(bet);
        } else {
            result = lose();
        }
        endGame();
        return result;
    }

    protected abstract void startMessage();

    protected abstract void drawStartCards();

    protected abstract void playGame();

    protected abstract String win(String bet);

    protected abstract void endGame();

    public void formDeck() {
        System.out.println("Deck formed");
    }

    public void setBet(String bet) {
        this.bet = bet;
        System.out.println("Bets accepted");
    }

    public void shuffleDeck() {
        System.out.println("Deck shuffled");
    }

    public String lose() {
        System.out.println("You lost your bet :(");
        return "Nothing";
    }


}
