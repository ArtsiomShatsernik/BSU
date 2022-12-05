package colok;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String yourBet = "bet";
        Scanner in = new Scanner(System.in);
        CardGame cardGame = null;
        System.out.println("You got into an illegal casino in the depths of BSU\n What game will you play?" +
                "\n Enter P (poker) or B (blackjack)");
        String game;
        while (true) {
            game = in.nextLine();
            if (game.equals("P") || game.equals("B")) {
                break;
            } else {
                System.out.println("No such game. Try again.");
            }
        }
        if (game.equals("P")) {
            cardGame = new Poker(yourBet);
        } else if (game.equals("B")) {
            cardGame = new Blackjack(yourBet);
        }
        yourBet = cardGame.gameProcess();
        System.out.println("Now you have: " + yourBet);
    }
}