package com.designpatterns.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * An immutable pizza, assembled with the Builder pattern.
 *
 * <p>Only {@link Size} is required. Everything else has a sensible default, so
 * callers mention only what they care about:
 *
 * <pre>{@code
 * Pizza pizza = Pizza.builder(Size.LARGE)
 *         .crust(Crust.THIN)
 *         .topping("mushrooms")
 *         .build();
 * }</pre>
 */
public final class Pizza {

    /** A pizza, not a salad bar. */
    public static final int MAX_TOPPINGS = 6;

    private final Size size;
    private final Crust crust;
    private final Sauce sauce;
    private final boolean extraCheese;
    private final List<String> toppings;
    private final String note;

    private Pizza(Builder builder) {
        this.size = builder.size;
        this.crust = builder.crust;
        this.sauce = builder.sauce;
        this.extraCheese = builder.extraCheese;
        this.toppings = List.copyOf(builder.toppings); // snapshot: later builder changes can't leak in
        this.note = builder.note;
    }

    /** Starts a new pizza order. Size is the only thing we insist on. */
    public static Builder builder(Size size) {
        return new Builder(size);
    }

    public Size size() {
        return size;
    }

    public Crust crust() {
        return crust;
    }

    public Sauce sauce() {
        return sauce;
    }

    public boolean hasExtraCheese() {
        return extraCheese;
    }

    /** An unmodifiable list, in the order the toppings were added. */
    public List<String> toppings() {
        return toppings;
    }

    public Optional<String> note() {
        return Optional.ofNullable(note);
    }

    @Override
    public String toString() {
        return size + " (" + size.inches() + "\") " + crust + " crust, " + sauce + " sauce"
                + (extraCheese ? ", extra cheese" : "")
                + ", toppings=" + toppings
                + (note == null ? "" : ", note=\"" + note + "\"");
    }

    /** Collects the choices step by step, then checks them all at once in {@link #build()}. */
    public static final class Builder {
        private final Size size;
        private Crust crust = Crust.HAND_TOSSED;
        private Sauce sauce = Sauce.TOMATO;
        private boolean extraCheese;
        private final List<String> toppings = new ArrayList<>();
        private String note;

        private Builder(Size size) {
            this.size = Objects.requireNonNull(size, "size");
        }

        public Builder crust(Crust crust) {
            this.crust = Objects.requireNonNull(crust, "crust");
            return this;
        }

        public Builder sauce(Sauce sauce) {
            this.sauce = Objects.requireNonNull(sauce, "sauce");
            return this;
        }

        public Builder extraCheese() {
            this.extraCheese = true;
            return this;
        }

        /** Adds one topping. Call it as many times as you like (up to {@link Pizza#MAX_TOPPINGS}). */
        public Builder topping(String topping) {
            if (topping == null || topping.isBlank()) {
                throw new IllegalArgumentException("A topping needs a name");
            }
            toppings.add(topping.strip());
            return this;
        }

        public Builder note(String note) {
            this.note = Objects.requireNonNull(note, "note").strip();
            return this;
        }

        /**
         * Validates the whole order, then hands over a finished, immutable pizza.
         *
         * @throws IllegalStateException if the combination of choices is not allowed
         */
        public Pizza build() {
            if (toppings.size() > MAX_TOPPINGS) {
                throw new IllegalStateException(
                        "Too many toppings: " + toppings.size() + " (max " + MAX_TOPPINGS + ")");
            }
            if (size == Size.SMALL && crust == Crust.DEEP_DISH) {
                throw new IllegalStateException("Deep dish is not available in SMALL");
            }
            return new Pizza(this);
        }
    }
}
