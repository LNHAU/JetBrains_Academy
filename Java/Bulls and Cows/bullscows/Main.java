package bullscows;
import java.util.Locale;
import java.util.Scanner;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        final Scanner input = new Scanner(System.in).useLocale(Locale.US);
        System.out.println("Input the length of the secret code:");
        final String sLen = input.nextLine().trim();
        int len = 0;
        try {
            len = Integer.parseInt(sLen);
        } catch (NullPointerException | NumberFormatException e) {
            System.out.printf("Error: \"%s\" isn't a valid number.", sLen);
            System.exit(-1);
        }
        if (len == 0) {
            System.out.printf("Error: it's not possible to generate a code with a length of %d.", len);
            System.exit(-1);
        } else if (len > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            System.exit(-1);
        } else {
            System.out.println("Input the number of possible symbols in the code:");
            final String sLenCharacters = input.nextLine().trim();
            int lenCharacters = 0;
            try {
                lenCharacters = Integer.parseInt(sLenCharacters);
            } catch (NullPointerException | NumberFormatException e) {
                System.out.printf("Error: \"%s\" isn't a valid number.", sLenCharacters);
                System.exit(-1);
            }
            if (lenCharacters == 0 || lenCharacters < len) {
                System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.", len, lenCharacters);
                System.exit(-1);
            } else if (lenCharacters > 36) {
                System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
                System.exit(-1);
            } else {
                final char[] symbols = new char[lenCharacters];
                final String secretCode = generateSecretCode(len, lenCharacters, symbols);
                System.out.println("Okay, let's start a game!");
                int i = 1;
                String sRead;
                char[] read;
                do {
                    System.out.printf("Turn %d:%n", i);
                    sRead = input.nextLine();
                    if (sRead.length() != len) {
                        System.out.printf("Error: it's not possible to find a code with a length of %d with \"%s\".", len, sRead);
                        System.exit(-1);
                    }
                    read = sRead.toCharArray();
                    i++;
                } while (!evaluate(secretCode, len, symbols, read));
            }
        }
    }

    private static String generateSecretCode(int len, int lenCharacters, char[] symbols) {
        // generate a secret code of the given length.
        // It should consist of unique numbers and characters.
        String secretCode;
        Random random = new Random();
        char[] secretCodeArray = new char[len];
        char[] latinChars = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
                                'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        for (int i = 0; i < len; i++) {
            secretCodeArray[i] = ' ';
        }
        int i;
        for (i = 0; i < Math.min(10, lenCharacters); i++) {
            symbols[i] = Character.forDigit(i, 10);
        }
        for (int j = 0; i < lenCharacters; i++, j++) {
            symbols[i] = latinChars[j];
        }
        //System.out.println(String.valueOf(symbols));
        int k = 0;
        while (k < len) {
            secretCodeArray[k] = symbols[random.nextInt(lenCharacters)];
            boolean found = false;
            for (int l = 0; l < k; l++) {
                if (secretCodeArray[k] == secretCodeArray[l]) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                k++;
            }
        }
        secretCode = String.valueOf(secretCodeArray);
        StringBuilder hiddenSecretCode = new StringBuilder(len);
        for (int l = 0; l < len; l++) {
            hiddenSecretCode.append("*");
        }
        System.out.printf("The secret is prepared: %s (0-%d",
                            hiddenSecretCode.toString(), lenCharacters >= 10 ? 9 : lenCharacters - 1);
        if (lenCharacters > 10) {
            System.out.printf(", a-%c", symbols[lenCharacters - 1]);
        }
        System.out.println(")");
        return secretCode;
    }

    private static boolean evaluate(String secretCode, int len, char[] symbols, char[] read) {
        boolean result = false;
        int nbBulls = 0;
        int nbCows = 0;
        int readIndex = 0;
        char[] secretCodeArray = secretCode.toCharArray();
        for (char symbol: read) {
            boolean found = false;
            for (int i = 0; i < symbols.length; i++) {
                if (symbol == symbols[i]) {
                    found = true;
                    break;
                }
            }
            //System.out.println(symbol + ":" + found + ":" + String.valueOf(symbols));
            if (! found) {
                System.out.printf("Error: \"%c\" isn't a valid symbol (0-%d",
                                    symbol, symbols.length >= 10 ? 9 : symbols.length - 1);
                if (symbols.length > 10) {
                    System.out.printf(", a-%c", symbols[symbols.length - 1]);
                }
                System.out.println(").");
                System.exit(-1);
            }
            for (int i = 0; i < len; i++) {
                if (symbol == secretCodeArray[i]) {
                    if (i == readIndex) {
                        nbBulls++;
                    } else {
                        nbCows++;
                    }
                }
            }
            readIndex++;
        }
        StringBuilder sb = new StringBuilder("Grade: ");
        if (nbBulls > 0 || nbCows > 0) {
            if (nbBulls > 0) {
                sb.append(nbBulls).append(" ").append(nbBulls > 1 ? "bulls" : "bull");
                sb.append(nbCows > 0 ? " and " : ".");
            }
            if (nbCows > 0) {
                sb.append(nbCows).append(" ").append(nbCows > 1 ? "cow(s)." : "cow.");
            }
        }
        else {
            sb.append("None.");
        }
        if (nbBulls == len) {
            sb.append("%nCongratulations! You guessed the secret code.");
            result = true;
        }
        System.out.println(sb.toString());
        return result;
    }
}
