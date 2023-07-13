package com.jitterted.ebp.blackjack;

import java.util.ArrayList;
import java.util.List;

public class HandFactory {
    private static final Suit IRRELEVANT_SUIT = Suit.SPADES;

    static Hand createHand(String... ranks) {
        List<Card> cards = new ArrayList<>();

        for (String rank : ranks) {
            cards.add(new Card(IRRELEVANT_SUIT, rank));
        }

        return new Hand(cards);
    }
}
