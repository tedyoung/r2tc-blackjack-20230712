package com.jitterted.ebp.blackjack;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class HandTest {

    @Test
    void handWithValueOf22isBusted() {
        Hand hand = createHand("3", "9", "J");

        assertThat(hand.isBusted())
                .isTrue();
    }

    @Test
    void handWithValueOf21isNotBusted() {
        Hand hand = createHand("2", "9", "J");

        assertThat(hand.isBusted())
                .isFalse();
    }

    @Test
    void handWithValueOf20isNotBusted() {
        Hand hand = createHand("J", "Q");

        assertThat(hand.isBusted())
                .isFalse();
    }

    private static Hand createHand(String... ranks) {
        List<Card> cards = new ArrayList<>();

        for (String rank : ranks) {
            cards.add(new Card(Suit.SPADES, rank));
        }

        return new Hand(cards);
    }
}