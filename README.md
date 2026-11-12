# Builder Design Pattern in Java

A runnable example of the **Builder** pattern, built around ordering a pizza.
Accompanies the Medium article: _link coming soon_ (source in [`article/builder-medium.md`](article/builder-medium.md)).

## Requirements
- Java 17+
- Maven 3.8+

## Run
```
mvn compile exec:java
```

## Test
```
mvn test
```

## Project structure
```
src/main/java/com/designpatterns/builder/
├── Pizza.java        # immutable product + nested fluent Builder (validation lives in build())
├── PizzaMenu.java    # house specials: ready-made recipes for the builder (the "Director")
├── Size.java         # enums used by Pizza
├── Crust.java
├── Sauce.java
└── Main.java         # runnable demo
src/test/java/com/designpatterns/builder/
└── PizzaBuilderTest.java

```

## What this demonstrates
- A required value (`Size`) taken up front, with optional values as chained calls and sensible defaults.
- A single `build()` checkpoint for rules that span several fields (max six toppings, no small deep dish).
- An immutable product: toppings are copied at build time, so later builder changes never affect a finished pizza.
- A tiny "Director" (`PizzaMenu`) reusing the same builder for house specials.
