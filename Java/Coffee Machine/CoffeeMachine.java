package machine;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

enum State {
    BACK("Ready to choose an action"),
    BUY("Choosing to buy a coffee"),
    FILL("Choosing to fill the machine"),
    TAKE("Choosing to take the money"),
    REMAINING("Choosing to see the remaining"),
    VARIANT("Choosing a variant of coffee"),
    FILL_WATER("Choosing quantity of water to fill"),
    FILL_MILK("Choosing quantity of milk to fill"),
    FILL_COFFEE_BEANS("Choosing quantity of coffee beans to fill"),
    FILL_NB_CUPS("Choosing number of cups to fill"),
    EXIT("Choosing to exit");

    private final String libelle;

    State(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}

enum Coffee {
    ESPRESSO("Espresso", 250, 0, 16, 4),
    LATTE("Latte", 350, 75, 20, 7),
    CAPPUCCINO("Cappuccino", 200, 100, 12, 6);

    private final String libelle;
    private final int water;
    private final int milk;
    private final int coffeeBeans;
    private final int price;

    Coffee(String libelle, int water, int milk, int coffeeBeans, int price) {
        this.libelle = libelle;
        this.water = water;
        this.milk = milk;
        this.coffeeBeans = coffeeBeans;
        this.price = price;
    }

    public String getLibelle() {
        return libelle;
    }

    public int getWater() {
        return water;
    }

    public int getMilk() {
        return milk;
    }

    public int getCoffeeBeans() {
        return coffeeBeans;
    }

    public int getPrice() {
        return price;
    }
}

public class CoffeeMachine {
    int water = 400;
    int milk = 540;
    int coffeeBeans = 120;
    int nbCups = 9;
    int money = 550;
    State state = State.BACK;

    private void execute(String input) {
        State state = null;
        if (this.state != State.VARIANT
                && this.state != State.FILL_WATER
                && this.state != State.FILL_MILK
                && this.state != State.FILL_COFFEE_BEANS
                && this.state != State.FILL_NB_CUPS) {
            state = Enum.valueOf(State.class, input.toUpperCase(Locale.ROOT));
        }
        else {
            state = this.state;
        }
        switch (state) {
            case BACK:
                System.out.println("Write action (buy, fill, take, remaining, exit):");
                break;
            case BUY:
                System.out.println("\nWhat do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
                this.state = State.VARIANT;
                break;
            case VARIANT:
                if (!"back".equals(input)) {
                    this.buy(input);
                }
                System.out.println();
                this.state = State.BACK;
                execute("BACK");
                break;
            case FILL:
                System.out.println("\nWrite how many ml of water do you want to add:");
                this.state = State.FILL_WATER;
                break;
            case FILL_WATER:
                this.fill_water(input);
                System.out.println("Write how many ml of milk do you want to add:");
                this.state = State.FILL_MILK;
                break;
            case FILL_MILK:
                this.fill_milk(input);
                System.out.println("Write how many grams of coffee beans do you want to add:");
                this.state = State.FILL_COFFEE_BEANS;
                break;
            case FILL_COFFEE_BEANS:
                this.fill_coffeeBeans(input);
                System.out.println("Write how many disposable cups of coffee do you want to add:");
                this.state = State.FILL_NB_CUPS;
                break;
            case FILL_NB_CUPS:
                this.fill_nbCups(input);
                System.out.println();
                this.state = State.BACK;
                execute("BACK");
                break;
            case TAKE:
                this.take();
                this.state = State.BACK;
                System.out.println();
                this.state = State.BACK;
                execute("BACK");
                break;
            case REMAINING:
                this.remaining();
                this.state = State.BACK;
                System.out.println();
                execute("BACK");
                break;
            case EXIT:
                System.exit(0);
        }
    }

    private void remaining() {
        System.out.println("\nThe coffee machine has:");
        System.out.println(water + " of water");
        System.out.println(milk + " of milk");
        System.out.println(coffeeBeans + " of coffee beans");
        System.out.println(nbCups + " of disposable cups");
        System.out.println("$" + money + " of money");
    }

    private void fill_nbCups(String quantity) {
        this.nbCups += Integer.parseInt(quantity);
    }

    private void fill_coffeeBeans(String quantity) {
        this.coffeeBeans += Integer.parseInt(quantity);
    }

    private void fill_milk(String quantity) {
        this.milk += Integer.parseInt(quantity);
    }

    private void fill_water(String quantity) {
        this.water += Integer.parseInt(quantity);
    }

    private boolean buy(String choice) {
        if (this.nbCups <= 0) {
            System.out.println("Sorry, not enough cups of coffee!");
            return false;
        }
        Coffee coffee = null;
        if (Arrays.asList('1', '2', '3').contains(choice.charAt(0))) {
            coffee = Coffee.values()[Integer.parseInt(choice) - 1];
        }
        else {
            coffee = Enum.valueOf(Coffee.class, choice.toUpperCase(Locale.ROOT));
        }
        if (this.water < coffee.getWater()) {
            System.out.println("Sorry, not enough water!");
            return false;
        }
        if (this.milk < coffee.getMilk()) {
            System.out.println("Sorry, not enough milk!");
            return false;
        }
        if (this.coffeeBeans < coffee.getCoffeeBeans()) {
            System.out.println("Sorry, not enough coffee beans!");
            return false;
        }
        this.water -= coffee.getWater();
        this.milk -= coffee.getMilk();
        this.coffeeBeans -= coffee.getCoffeeBeans();
        this.money += coffee.getPrice();
        System.out.println("I have enough resources, making you a coffee!");
        this.nbCups--;
        return true;
    }

    private void take() {
        System.out.println("\nI gave you $" + money);
        money = 0;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        CoffeeMachine machine = new CoffeeMachine();
        String action = machine.state.name();
        while (machine.state != State.EXIT) {
            machine.execute(action);
            action = input.next();
        }
    }
}

