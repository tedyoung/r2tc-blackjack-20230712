package com.jitterted.ebp.blackjack;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class HandTest {

    @Test
    void handWithValueOf22isBusted() {
        Hand hand = HandFactory.createHand("3", "9", "J");

        assertThat(hand.isBusted())
                .isTrue();
    }

    @Test
    void handWithValueOf21isNotBusted() {
        Hand hand = HandFactory.createHand("2", "9", "J");

        assertThat(hand.isBusted())
                .isFalse();
    }

    @Test
    void handWithValueOf20isNotBusted() {
        Hand hand = HandFactory.createHand("J", "Q");

        assertThat(hand.isBusted())
                .isFalse();
    }

}