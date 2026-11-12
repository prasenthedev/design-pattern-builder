package com.designpatterns.builder;

/** Places a few orders so you can see the builder in action. */
public class Main {

    public static void main(String[] args) {
        Pizza custom = Pizza.builder(Size.LARGE)
                .crust(Crust.THIN)
                .sauce(Sauce.PESTO)
                .extraCheese()
                .topping("artichokes")
                .topping("sun-dried tomatoes")
                .note("Please cut into squares")
                .build();
        System.out.println("Custom order : " + custom);

        System.out.println("House special: " + PizzaMenu.margherita(Size.MEDIUM));
        System.out.println("House special: " + PizzaMenu.meatLovers(Size.LARGE));

        try {
            Pizza.builder(Size.SMALL).crust(Crust.DEEP_DISH).build();
        } catch (IllegalStateException e) {
            System.out.println("Kitchen says no: " + e.getMessage());
        }
    }
}
