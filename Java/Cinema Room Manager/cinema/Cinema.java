package cinema;
import java.util.Scanner;

public class Cinema {

    public static void main(String[] args) {
        final Scanner input = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        final int nbRows = input.nextInt();
        System.out.println("Enter the number of seats in each row:");
        final int nbColumns = input.nextInt();
        // ticketPrice, middleSize, ticketPriceForFront, ticketPriceForBack,
        // nbRows, nbColumns, totalSeats, nbPurchasedTickets, currentIncome, maxTotalIncome
        final int[] statistics = {10, 60, 10, 8, nbRows, nbColumns, nbRows * nbColumns, 0, 0, 0};
        final char[][] theatre = initialize(statistics);

        int choice = -1;
        while (choice != 0) {
            System.out.println("\n1. Show the seats");
            System.out.println("2. Buy a ticket");
            System.out.println("3. Statistics");
            System.out.println("0. Exit");
            choice = input.nextInt();
            execute(input, choice, theatre, statistics);
        }
    }

    private static void calculateMaxTotalIncome(int[] statistics) {
        int maxTotalIncome = statistics[9];
        int totalSeats = statistics[6];
        if (totalSeats <= statistics[1]) {
            int ticketPrice = statistics[0];
            maxTotalIncome = ticketPrice * totalSeats;
        } else {
            int ticketPriceForFront = statistics[2];
            int ticketPriceForBack = statistics[3];
            int nbRows = statistics[4];
            int nbColumns = statistics[5];
            maxTotalIncome = (ticketPriceForFront * ((nbRows / 2) * nbColumns))
                    + (ticketPriceForBack * ((nbRows - (nbRows / 2)) * nbColumns));
        }
        statistics[9] = maxTotalIncome;
    }

    static char[][] initialize(int[] statistics) {
        int nbRows = statistics[4];
        int nbColumns = statistics[5];
        char[][] theatre = new char[nbRows][nbColumns];
        for (int i = 0; i < nbRows; i++) {
            for (int j = 0; j < nbColumns; j++) {
                theatre[i][j] = 'S';
            }
        }
        calculateMaxTotalIncome(statistics);
        return theatre;
    }

    static void execute(Scanner input, int choice, char[][] theatre, int[] statistics) {
        switch (choice) {
            case 1:
                printScheme(theatre, statistics);
                break;
            case 2:
                int row = -1;
                int column = -1;
                do {
                    System.out.println("\nEnter a row number:");
                    row = input.nextInt();
                    System.out.println("Enter a seat number in that row:");
                    column = input.nextInt();
                } while (!seatIsBooked(theatre, row, column, statistics));
                break;
            case 3:
                printStatistics(statistics);
                break;
            case 0:
                //System.exit(0);
                return;
        }
    }

    static void printScheme(char[][] theatre, int[] statistics) {
        int nbRows = statistics[4];
        int nbColumns = statistics[5];
        System.out.println("\nCinema:");
        StringBuilder title = new StringBuilder(" ");
        for (int j = 1; j <= nbColumns; j++) {
            title.append(" ").append(j);
        }
        System.out.println(title.toString());
        StringBuilder body = new StringBuilder();
        for (int i = 0; i < nbRows; i++) {
            for (int j = 0; j < nbColumns; j++) {
                if (j == 0) {
                    body.append(i + 1);
                }
                body.append(" ").append(theatre[i][j]);
            }
            System.out.println(body.toString());
            body.delete(0, body.length());
        }
    }
    
    static boolean seatIsBooked(char[][] theatre, int row, int column, int[] statistics) {
        int ticketPriceForSelectedSeat = 0;
        int totalSeats = statistics[6];
        if (totalSeats <= statistics[1]) {
            ticketPriceForSelectedSeat = statistics[0];
        } else {
            int ticketPriceForFront = statistics[2];
            int ticketPriceForBack = statistics[3];
            int nbRows = statistics[4];
            if (row <= nbRows / 2) {
                ticketPriceForSelectedSeat = ticketPriceForFront;
            }
            else {
                ticketPriceForSelectedSeat = ticketPriceForBack;
            }
        }
        if (isUpdated(theatre, row, column, statistics)) {
            System.out.printf("%nTicket price: $%d%n", ticketPriceForSelectedSeat);
            statistics[7] = statistics[7] + 1; // nbPurchasedTickets
            statistics[8] = statistics[8] + ticketPriceForSelectedSeat; // currentIncome
            return true;
        }
        return false;
    }
    
    static boolean isUpdated(char[][] theatre, int row, int column, int[] statistics) {
        int nbRows = statistics[4];
        int nbColumns = statistics[5];
        if (row - 1 < 0 || row - 1 > nbRows - 1 || column - 1 < 0 || column - 1 > nbColumns - 1) {
            System.out.println("Wrong input!");
            return false;
        } else if (theatre[row - 1][column - 1] == 'B') {
            System.out.println("That ticket has already been purchased!");
            return false;
        }
        theatre[row - 1][column - 1] = 'B';
        return true;
    }

    static void printStatistics(int[] statistics) {
        int totalSeats = statistics[6];
        int nbPurchasedTickets = statistics[7];
        int currentIncome = statistics[8];
        int maxTotalIncome = statistics[9];
        float percentage = nbPurchasedTickets * 100f / totalSeats;
        System.out.printf("Number of purchased tickets: %d%n", nbPurchasedTickets);
        System.out.printf("Percentage: %.2f%%%n", percentage);
        System.out.printf("Current income: $%d%n", currentIncome);
        System.out.printf("Total income: $%d", maxTotalIncome);
    }
}