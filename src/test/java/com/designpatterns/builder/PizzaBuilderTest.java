package com.designpatterns.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

class PizzaBuilderTest {

    @Test
    void unspecifiedOptionsGetSensibleDefaults() {
        Pizza pizza = Pizza.builder(Size.MEDIUM).build();

        assertEquals(Size.MEDIUM, pizza.size());
        assertEquals(Crust.HAND_TOSSED, pizza.crust());
        assertEquals(Sauce.TOMATO, pizza.sauce());
        assertFalse(pizza.hasExtraCheese());
        assertTrue(pizza.toppings().isEmpty());
        assertTrue(pizza.note().isEmpty());
    }

    @Test
    void chosenOptionsOverrideDefaultsAndToppingsKeepTheirOrder() {
        Pizza pizza = Pizza.builder(Size.LARGE)
                .crust(Crust.THIN)
                .sauce(Sauce.PESTO)
                .extraCheese()
                .topping("artichokes")
                .topping("  olives ")
                .note("Squares, please")
                .build();

        assertEquals(Crust.THIN, pizza.crust());
        assertEquals(Sauce.PESTO, pizza.sauce());
        assertTrue(pizza.hasExtraCheese());
        assertEquals(List.of("artichokes", "olives"), pizza.toppings());
        assertEquals("Squares, please", pizza.note().orElseThrow());
    }

    @Test
    void finishedPizzaIsUnaffectedByLaterBuilderChanges() {
        Pizza.Builder builder = Pizza.builder(Size.MEDIUM).topping("basil");
        Pizza first = builder.build();

        builder.topping("anchovies");
        Pizza second = builder.build();

        assertEquals(List.of("basil"), first.toppings());
        assertEquals(List.of("basil", "anchovies"), second.toppings());
        assertThrows(UnsupportedOperationException.class, () -> first.toppings().add("pineapple"));
    }

    @Test
    void rejectsMoreThanSixToppings() {
        Pizza.Builder builder = Pizza.builder(Size.LARGE);
        for (int i = 1; i <= Pizza.MAX_TOPPINGS + 1; i++) {
            builder.topping("topping " + i);
        }

        assertThrows(IllegalStateException.class, builder::build);
    }

    @Test
    void rejectsSmallDeepDish() {
        Pizza.Builder builder = Pizza.builder(Size.SMALL).crust(Crust.DEEP_DISH);

        IllegalStateException e = assertThrows(IllegalStateException.class, builder::build);
        assertTrue(e.getMessage().contains("Deep dish"));
    }

    @Test
    void rejectsBlankToppingsAndMissingSize() {
        assertThrows(IllegalArgumentException.class, () -> Pizza.builder(Size.SMALL).topping("   "));
        assertThrows(NullPointerException.class, () -> Pizza.builder(null));
    }
}
