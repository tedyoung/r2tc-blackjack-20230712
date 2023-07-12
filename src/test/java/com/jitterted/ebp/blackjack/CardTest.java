package com.jitterted.ebp.blackjack;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.fusesource.jansi.Ansi.ansi;

class CardTest {

    private static final Suit IRRELEVANT_SUIT = Suit.HEARTS;
    private static final String IRRELEVANT_RANK = "10";

    @Test
    public void withNumberCardHasNumericValueOfTheNumber() throws Exception {
        Card card = new Card(IRRELEVANT_SUIT, "7");

        assertThat(card.rankValue())
                .isEqualTo(7);
    }

    @Test
    public void withValueOfQueenHasNumericValueOf10() throws Exception {
        Card card = new Card(IRRELEVANT_SUIT, "Q");

        assertThat(card.rankValue())
                .isEqualTo(10);
    }

    @Test
    public void withAceHasNumericValueOf1() throws Exception {
        Card card = new Card(IRRELEVANT_SUIT, "A");

        assertThat(card.rankValue())
                .isEqualTo(1);
    }

    @Test
    public void suitOfHeartsOrDiamondsIsDisplayedInRed() throws Exception {
        // given a card with Hearts or Diamonds
        Card heartsCard = new Card(Suit.HEARTS, IRRELEVANT_RANK);
        Card diamondsCard = new Card(Suit.DIAMONDS, IRRELEVANT_RANK);

        // when we ask for its display representation
        String ansiRedString = ansi().fgRed().toString();

        // then we expect a red color ansi sequence
        assertThat(heartsCard.display())
                .contains(ansiRedString);
        assertThat(diamondsCard.display())
                .contains(ansiRedString);
    }

    @Test
    public void cardDisplaysSuitAsSymbol() throws Exception {
        Card spadesCard = new Card(Suit.SPADES, IRRELEVANT_RANK);

        assertThat(spadesCard.display())
                .contains("│    ♠    │");
    }

}