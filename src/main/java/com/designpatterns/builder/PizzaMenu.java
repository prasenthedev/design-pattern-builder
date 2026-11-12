package com.designpatterns.builder;

/**
 * The house specials. Each one is just a pre-written recipe.
 */
public final class PizzaMenu {

    private PizzaMenu() {
    }

    public static Pizza margherita(Size size) {
        return Pizza.builder(size)
                .topping("fresh mozzarella")
                .topping("basil")
                .build();
    }

    public static Pizza meatLovers(Size size) {
        return Pizza.builder(size)
                .sauce(Sauce.BBQ)
                .extraCheese()
                .topping("pepperoni")
                .topping("sausage")
                .topping("bacon")
                .build();
    }
}
