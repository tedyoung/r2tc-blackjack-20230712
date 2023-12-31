package com.jitterted.ebp.blackjack;

import org.fusesource.jansi.Ansi;

import java.util.Objects;

import static org.fusesource.jansi.Ansi.ansi;

public class Card {
    private final Suit suit;
    private final String rank;

    public Card(Suit suit, String rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public int rankValue() {
        if ("JQK".contains(rank)) {
            return 10;
        } else if (rank.equals("A")) {
            return 1;
        } else {
            return Integer.parseInt(rank);
        }
    }

    public String display() {
        String[] lines = new String[7];
        lines[0] = "┌─────────┐";
        lines[1] = String.format("│%s%s       │", rank, rank.equals("10") ? "" : " ");
        lines[2] = "│         │";
        lines[3] = String.format("│    %s    │", suit.symbol());
        lines[4] = "│         │";
        lines[5] = String.format("│       %s%s│", rank.equals("10") ? "" : " ", rank);
        lines[6] = "└─────────┘";

        Ansi.Color cardColor = suit.isRed() ? Ansi.Color.RED : Ansi.Color.BLACK;
        return ansi()
                .fg(cardColor).toString()
                + String.join(ansi().cursorDown(1)
                                    .cursorLeft(11)
                                    .toString(), lines);
    }

    @Override
    public String toString() {
        return "Card {" +
                "suit=" + suit +
                ", rank='" + rank + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Card card = (Card) o;
        return suit == card.suit && Objects.equals(rank, card.rank);
    }

    @Override
    public int hashCode() {
        return Objects.hash(suit, rank);
    }
}
