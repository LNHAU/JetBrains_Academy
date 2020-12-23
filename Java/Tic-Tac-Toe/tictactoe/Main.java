package tictactoe;
import java.util.Scanner;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.print("Enter cells: ");
        char[][] game = new char[3][3];
        newGame(game);
        /* readAndPrint(game);
        for (char[] vector: game) {
            System.out.println(vector);
        }*/
        //analyze(game);
        char val1 = 'X';
        char val2 = 'O';
        char val = val1;
        do {
            System.out.println("Enter the coordinates:");
            readAndPlay(game, val);
            if (val == val1) { // other player's turn
                val = val2;
            } else {
                val = val1;
            }
        } while (!analyze(game));
    }

    static void newGame(char[][] game) {
        outputsLineOfDashes();
        for (int i = 0; i < 3; i ++) {
            System.out.println("|       |");
            for (int j = 0; j < 3; j++) {
                game[i][j] = ' ';
            }
        }
        outputsLineOfDashes();
    }

    static void outputsLineOfDashes() {
        for (int i = 1; i <= 9; i++) {
            System.out.print("-");
        }
        System.out.println();
    }
    
    static void readAndPrint(char[][] game) {
        Scanner input = new Scanner(System.in);
        String read = input.next();
        outputsLineOfDashes();
        for (int i = 0, j = 0; i < 9; i += 3, j++) {
            System.out.printf("| %c %c %c |%n", read.charAt(i), read.charAt(i + 1), read.charAt(i + 2));
            for (int k = 0; k < 3; k++) {
                game[j][k] = read.charAt(i + k);
            }
        }
        outputsLineOfDashes();
    }
    
    static boolean analyze(char[][] game) {
        int nbEmptyCells = 0;
        int nbRowsOfX = 0;
        int nbRowsOfO = 0;
        int nbX = 0;
        int nbO = 0;
        for (char[] vector: game) {
            if (Arrays.equals(new char[]{'X', 'X', 'X'}, vector)) {
                nbRowsOfX++;
                nbX += 3;
            } else if (Arrays.equals(new char[]{'0', '0', '0'}, vector)) {
                nbRowsOfO++;
                nbO += 3;
            } else {
                for (char c: vector) {
                    if (c == 'X') {
                        nbX++;
                    } else if (c == 'O') {
                        nbO++;
                    }else {
                        nbEmptyCells++;
                    }
                }
            }
        }
        if ((game[0][0] == 'X' && game[1][1] == 'X' && game[2][2] == 'X')
            || (game[0][2] == 'X' && game[1][1] == 'X' && game[2][0] == 'X')) {
                nbRowsOfX++;
        }
        if ((game[0][0] == 'O' && game[1][1] == 'O' && game[2][2] == 'O')
            || (game[0][2] == 'O' && game[1][1] == 'O' && game[2][0] == 'O')) {
                nbRowsOfO++;
        }
        for (int j = 0; j < 3; j++) {
            if (game[0][j] == 'X' && game[1][j] == 'X' && game[2][j] == 'X') {
                nbRowsOfX++;
            }
            if (game[0][j] == 'O' && game[1][j] == 'O' && game[2][j] == 'O') {
                nbRowsOfO++;
            }
        }
        // Game not finished when neither side has three in a row but the grid still has empty cells.
        if (nbRowsOfX == 0 && nbRowsOfO == 0 && nbEmptyCells > 0) {
            //System.out.println("Game not finished");
            return false;
        }
        // Draw when no side has a three in a row and the grid has no empty cells.
        if (nbRowsOfX == 0 && nbRowsOfO == 0 && nbEmptyCells == 0) {
            System.out.println("Draw");
            return true;
        }
        // Impossible when the grid has three X’s in a row as well as three O’s in a row,
        // or there are a lot more X's than O's or vice versa (the difference should be 1 or 0;
        // if the difference is 2 or more, then the game state is impossible).
        if ((nbRowsOfX > 0 && nbRowsOfO > 0) || (Math.abs(nbX - nbO) >= 2)) {
            System.out.println("Impossible");
            return true;
        } else if (nbRowsOfX > 0) { // X wins when the grid has three X’s in a row.
            System.out.println("X wins");
            return true;
        } else if (nbRowsOfO > 0) { // O wins when the grid has three O’s in a row.
            System.out.println("O wins");
            return true;
        }
        return false;
    }

    public static boolean isNumeric(final String str) {
        if (str == null || str.length() == 0) { // null or empty
            return false;
        }
        for (char c : str.toCharArray()) {
            if (!(Character.isDigit(c) || c == ' ')) {
                return false;
            }
        }
        return true;
    }

    public static boolean isCoordinates(final String str) {
        if (str == null || str.length() == 0 || str.length() < 3) { // null or empty or incomplete
            return false;
        }
        if (!((str.charAt(0) == '1' || str.charAt(0) == '2' || str.charAt(0) == '3')
            && (str.charAt(2) == '1' || str.charAt(2) == '2' || str.charAt(2) == '3'))){
            return false;
        }
        return true;
    }

    static void readAndPlay(char[][] game, char val) {
        Scanner input = new Scanner(System.in);
        String read = input.nextLine().strip();
        int XCoordinate = -1;
        int YCoordinate = -1;
        do {
            if (!isNumeric(read)) {
                System.out.println("You should enter numbers!");
                read = input.nextLine().strip();
            } else if (!isCoordinates(read)) {
                System.out.println("Coordinates should be from 1 to 3!");
                read = input.nextLine().strip();
            } else {
                XCoordinate = Integer.parseInt("" + read.charAt(0)) - 1;
                YCoordinate = Integer.parseInt("" + read.charAt(2)) - 1;
                if (game[XCoordinate][YCoordinate] == 'X' || game[XCoordinate][YCoordinate] == 'O') {
                    System.out.println("This cell is occupied! Choose another one!");
                    XCoordinate = -1;
                    YCoordinate = -1;
                    read = input.nextLine().strip();
                }
            }
        } while (XCoordinate == -1 || YCoordinate == -1);
        update(game, XCoordinate, YCoordinate, val);
        print(game);
    }

    static void update(char[][] game, int XCoordinate, int YCoordinate, char val) {
        game[XCoordinate][YCoordinate] = val;
    }

    static void print(char[][] game) {
        outputsLineOfDashes();
        for (char[] vector: game) {
            System.out.print("| ");
            for (char val: vector) {
                System.out.printf("%c ", val);
            }
            System.out.println("|");
        }
        outputsLineOfDashes();
    }
}
