package com.jitterted.ebp.blackjack;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.util.List;
import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class Game {

    private final Deck deck;

    private final Hand playerHand = new Hand();
    private final Hand dealerHand = new Hand();

    public static void main(String[] args) {
        installAnsiDisplay();

        welcomeUser();
        waitForUserToContinue();

        playGame();

        resetScreen();
    }

    private static void playGame() {
        Game game = new Game();
        game.initialDeal();
        game.play();
    }

    private static void installAnsiDisplay() {
        AnsiConsole.systemInstall();
    }

    private static void resetScreen() {
        System.out.println(ansi().reset());
    }

    private static void waitForUserToContinue() {
        System.out.println(ansi()
                                   .cursor(3, 1)
                                   .fgBrightBlack().a("Hit [ENTER] to start..."));

        System.console().readLine();
    }

    private static void welcomeUser() {
        System.out.println(ansi()
                                   .bgBright(Ansi.Color.WHITE)
                                   .eraseScreen()
                                   .cursor(1, 1)
                                   .fgGreen().a("Welcome to")
                                   .fgRed().a(" JitterTed's")
                                   .fgBlack().a(" BlackJack game"));
    }

    public Game() {
        deck = new Deck();
    }

    public void initialDeal() {
        dealRoundOfCards();
        dealRoundOfCards();
    }

    private void dealRoundOfCards() {
        // players first, because that's the rule of Blackjack
        playerHand.drawCardFrom(deck);
        dealerHand.drawCardFrom(deck);
    }

    public void play() {
        boolean playerBusted = playerTurn();

        dealerTurn(playerBusted);

        displayFinalGameState();

        displayOutcome(playerBusted);
    }

    private void displayOutcome(boolean playerBusted) {
        if (playerBusted) {
            System.out.println("You Busted, so you lose.  💸");
        } else if (isDealerBusted()) {
            System.out.println("Dealer went BUST, Player wins! Yay for you!! 💵");
        } else if (dealerHand.value() < playerHand.value()) {
            System.out.println("You beat the Dealer! 💵");
        } else if (dealerHand.value() == playerHand.value()) {
            System.out.println("Push: You tie with the Dealer. 💸");
        } else {
            System.out.println("You lost to the Dealer. 💸");
        }
    }

    private boolean isDealerBusted() {
        return dealerHand.value() > 21;
    }

    private void dealerTurn(boolean playerBusted) {
        // Dealer makes its choice automatically based on a simple heuristic (<=16, hit, 17>=stand)
        if (!playerBusted) {
            while (dealerHand.value() <= 16) {
                dealerHand.drawCardFrom(deck);
            }
        }
    }

    private boolean playerTurn() {
        boolean playerBusted = false;
        while (!playerBusted) {
            displayGameState();
            String playerChoice = inputFromPlayer().toLowerCase();
            if (isPlayerStands(playerChoice)) {
                break;
            }
            if (isPlayerHits(playerChoice)) {
                playerHand.drawCardFrom(deck);
                if (isPlayerBusted()) {
                    playerBusted = true;
                }
            } else {
                System.out.println("You need to [H]it or [S]tand");
            }
        }
        return playerBusted;
    }

    private boolean isPlayerBusted() {
        return playerHand.value() > 21;
    }

    private boolean isPlayerHits(String playerChoice) {
        return playerChoice.startsWith("h");
    }

    private boolean isPlayerStands(String playerChoice) {
        return playerChoice.startsWith("s");
    }

    @Deprecated
    public int handValueOf(List<Card> cards) {
        int handValue = cards
                .stream()
                .mapToInt(Card::rankValue)
                .sum();

        // if the total hand value <= 11, then count the Ace as 11 by adding 10
        if (cards
                .stream()
                .anyMatch(card -> card.rankValue() == 1) && handValue < 11) {
            handValue += 10;
        }
        return handValue;
    }

    private String inputFromPlayer() {
        System.out.println("[H]it or [S]tand?");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private void displayGameState() {
        clearScreen();

        displayDealerHandDuringGame();

        displayPlayerHand();
    }

    private void displayFinalGameState() {
        clearScreen();

        displayFinalDealerHand();

        displayPlayerHand();
    }

    private void displayDealerHandDuringGame() {
        System.out.println("Dealer has: ");
        System.out.println(dealerHand.faceUpCard().display()); // first card is Face Up

        // second card is the hole card, which is hidden
        displayBackOfCard();
    }

    private void displayPlayerHand() {
        System.out.println();
        System.out.println("Player has: ");
        playerHand.display();
        System.out.println(" (" + playerHand.value() + ")");
    }

    private void clearScreen() {
        System.out.print(ansi().eraseScreen().cursor(1, 1));
    }

    private void displayFinalDealerHand() {
        System.out.println("Dealer has: ");
        dealerHand.display();
        System.out.println(" (" + dealerHand.value() + ")");
    }

    private void displayBackOfCard() {
        System.out.print(
                ansi()
                        .cursorUp(7)
                        .cursorRight(12)
                        .a("┌─────────┐").cursorDown(1).cursorLeft(11)
                        .a("│░░░░░░░░░│").cursorDown(1).cursorLeft(11)
                        .a("│░ J I T ░│").cursorDown(1).cursorLeft(11)
                        .a("│░ T E R ░│").cursorDown(1).cursorLeft(11)
                        .a("│░ T E D ░│").cursorDown(1).cursorLeft(11)
                        .a("│░░░░░░░░░│").cursorDown(1).cursorLeft(11)
                        .a("└─────────┘"));
    }

}
