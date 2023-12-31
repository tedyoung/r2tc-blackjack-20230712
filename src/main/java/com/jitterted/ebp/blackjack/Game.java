package com.jitterted.ebp.blackjack;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class Game {

    private final Deck deck;

    private final Hand playerHand = new Hand();
    private final Hand dealerHand = new Hand();
    private int playerBalance = 0;
    private int playerBetAmount = 0;

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

        GameOutcome gameOutcome = determineOutcome(playerBusted);
        playerBalance += gameOutcome.payoffAmount(playerBetAmount);

    }

    private GameOutcome determineOutcome(boolean playerBusted) {
        if (playerBusted) {
            System.out.println("You Busted, so you lose.  💸");
            return GameOutcome.PLAYER_LOSES;
        } else if (dealerHand.isBusted()) {
            System.out.println("Dealer went BUST, Player wins! Yay for you!! 💵");
            return GameOutcome.PLAYER_WINS;
        } else if (playerHand.beats(dealerHand)) { // playerHand.beats(dealerHand)
            System.out.println("You beat the Dealer! 💵");
            return GameOutcome.PLAYER_WINS;
        } else if (dealerHand.pushes(playerHand)) { // dealerHand.pushes(playerHand)
            System.out.println("Push: You tie with the Dealer. 💸");
            return GameOutcome.PLAYER_PUSHES;
        } else {
            System.out.println("You lost to the Dealer. 💸");
            return GameOutcome.PLAYER_LOSES;
        }
    }

    private void dealerTurn(boolean playerBusted) {
        // Dealer makes its choice automatically based on a simple heuristic (<=16, hit, 17>=stand)
        if (!playerBusted) {
            while (dealerHand.shouldDealerHit()) {
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
                if (playerHand.isBusted()) {
                    playerBusted = true;
                }
            } else {
                System.out.println("You need to [H]it or [S]tand");
            }
        }
        return playerBusted;
    }

    private boolean isPlayerHits(String playerChoice) {
        return playerChoice.startsWith("h");
    }

    private boolean isPlayerStands(String playerChoice) {
        return playerChoice.startsWith("s");
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


    public int playerBalance() {
        return playerBalance;
    }

    public void playerDeposits(int depositAmount) {
        playerBalance += depositAmount;
    }

    public void playerBets(int betAmount) {
        playerBalance -= betAmount;
        playerBetAmount = betAmount;
    }


    public void playerWins() {
        playerBalance += playerBetAmount * 2;
    }

    public void playerLoses() {
        playerBalance += playerBetAmount * 0;
    }

    public void playerPushes() {
        playerBalance += playerBetAmount * 1;
    }

    public void playerWinsBlackjack() {
        playerBalance += playerBetAmount * 2.5;
    }
}
