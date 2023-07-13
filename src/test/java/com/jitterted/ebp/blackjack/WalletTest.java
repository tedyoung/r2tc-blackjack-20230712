package com.jitterted.ebp.blackjack;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class WalletTest {

    @Test
    void newWalletIsEmpty() {
        Wallet wallet = new Wallet();

        assertThat(wallet.isEmpty())
                .isTrue();
    }

    @Test
    void addMoneyToNewWalletIsNotEmpty() {
        Wallet wallet = new Wallet();

        wallet.addMoney(1);

        assertThat(wallet.isEmpty())
                .isFalse();
    }

    @Test
    void newWalletBalanceIsZero() {
        Wallet wallet = new Wallet();

        assertThat(wallet.balance())
                .isZero();
    }

    @Test
    void newWalletAdd11ThenBalanceIs11() {
        Wallet wallet = new Wallet();

        wallet.addMoney(11);

        assertThat(wallet.balance())
                .isEqualTo(11);
    }

    @Test
    void newWalletAdd7And8ThenBalanceIs15() {
        Wallet wallet = new Wallet();

        wallet.addMoney(7);
        wallet.addMoney(8);

        assertThat(wallet.balance())
                .isEqualTo(7 + 8);
    }

    @Test
    void addNegativeMoneyThrowsException() {
        Wallet wallet = new Wallet();

        assertThatIllegalArgumentException()
                .isThrownBy(() -> wallet.addMoney(-1));
    }
}
