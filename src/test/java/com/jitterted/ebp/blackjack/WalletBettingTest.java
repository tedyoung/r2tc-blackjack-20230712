package com.jitterted.ebp.blackjack;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class WalletBettingTest {

    @Test
    void walletWithBalance12WhenBet8ThenBalanceIs4() {
        Wallet wallet = createWalletWithInitialBalanceOf(12);

        wallet.bet(8);

        assertThat(wallet.balance())
                .isEqualTo(12 - 8);
    }

    @Test
    void walletWithBalance27Bet7And9ThenBalanceIs11() {
        Wallet wallet = createWalletWithInitialBalanceOf(27);

        wallet.bet(7);
        wallet.bet(9);

        assertThat(wallet.balance())
                .isEqualTo(27 - 7 - 9);
    }

    @Test
    void betFullBalanceThenWalletIsEmpty() {
        Wallet wallet = createWalletWithInitialBalanceOf(73);

        wallet.bet(73);

        assertThat(wallet.isEmpty())
                .isTrue();
    }

    @Test
    void betMoreThanBalanceThrowsException() {
        Wallet wallet = createWalletWithInitialBalanceOf(17);

        assertThatIllegalStateException()
                .isThrownBy(() -> wallet.bet(18));
    }

    private Wallet createWalletWithInitialBalanceOf(int initialAmount) {
        Wallet wallet = new Wallet();
        wallet.addMoney(initialAmount);
        return wallet;
    }
}
