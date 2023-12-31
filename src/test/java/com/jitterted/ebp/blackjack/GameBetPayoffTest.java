package com.jitterted.ebp.blackjack;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class GameBetPayoffTest {

    @Test
    public void newGamePlayerBalanceIsZero() throws Exception {
        Game game = new Game();

        assertThat(game.playerBalance())
                .isZero();
    }


    @Test
    public void playerDeposits25ThenBalanceIs25() throws Exception {
        Game game = new Game();

        game.playerDeposits(25);

        assertThat(game.playerBalance())
                .isEqualTo(25);
    }


    @Test
    public void playerWith100Bets50ThenBalanceIs50() throws Exception {
        Game game = new Game();
        game.playerDeposits(100);

        game.playerBets(50);

        assertThat(game.playerBalance())
                .isEqualTo(100 - 50);
    }

    @Test
    public void playerWith100BalanceBets50AndWinsThenBalanceIs150() throws Exception {
        Game game = new Game();
        game.playerDeposits(100);
        game.playerBets(50);

        game.playerWins();

        assertThat(game.playerBalance())
                .isEqualTo(100 - 50 + (50 * 2));
    }

    @Test
    void playerWith60Bets20AndLosesThenBalanceIs40() {
        Game game = new Game();
        game.playerDeposits(60);
        game.playerBets(20);

        game.playerLoses();

        assertThat(game.playerBalance())
                .isEqualTo(60 - 20);
    }

    @Test
    void playerWith75Bets40AndPushesThenBalanceIs75() {
        Game game = new Game();
        game.playerDeposits(75);
        game.playerBets(40);

        game.playerPushes();

        assertThat(game.playerBalance())
                .isEqualTo(75 - 40 + 40);
    }

    @Test
    void playerWith400Bets400WinsBlackjackThenBalanceIs1_000() {
        Game game = new Game();
        game.playerDeposits(400);
        game.playerBets(400);

        game.playerWinsBlackjack();

        assertThat(game.playerBalance())
                .isEqualTo(1_000);
    }
}
