package converter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        if (!input.hasNextLine()) {
            System.out.println("ERROR - Run out of user input");
            System.exit(-1);
        }
        String sSourceBase = input.nextLine().trim();
        if (sSourceBase == null || sSourceBase.length() == 0
            || !Character.isDigit(sSourceBase.charAt(0))
                || !Character.isDigit(sSourceBase.charAt(sSourceBase.length() - 1))) {
            System.out.printf("ERROR - Invalid user input: %s%n", sSourceBase);
            System.exit(-1);
        }
        int sourceBase = Integer.parseInt(sSourceBase);
        if (sourceBase < 1 || sourceBase > 36) {
            System.out.printf("ERROR - Invalid user input: %d%n", sourceBase);
            System.exit(-1);
        }
        if (!input.hasNextLine()) {
            System.out.println("ERROR - Run out of user input");
            System.exit(-1);
        }
        String number = input.nextLine().trim();
        if (number == null || number.length() == 0) {
            System.out.printf("ERROR - Invalid user input: %s%n", number);
            System.exit(-1);
        }
        String integerPartFromNumber = number.indexOf('.') != -1 ? number.substring(0, number.indexOf('.')) : number;
        String fractionalPartFromNumber = number.indexOf('.') != -1 ? number.substring(number.indexOf('.'), number.length()) : "";
        if (!input.hasNextLine()) {
            System.out.println("ERROR - Run out of user input");
            System.exit(-1);
        }
        String sNewBase = input.nextLine().trim();
        if (sNewBase == null || sNewBase.length() == 0
                || !Character.isDigit(sNewBase.charAt(0))
                || !Character.isDigit(sNewBase.charAt(sNewBase.length() - 1))) {
            System.out.printf("ERROR - Invalid user input: %s%n", sNewBase);
            System.exit(-1);
        }
        int newBase = Integer.parseInt(sNewBase);
        if (newBase < 1 || newBase > 36) {
            System.out.printf("ERROR - Invalid user input: %d%n", newBase);
            System.exit(-1);
        }
        String integerPartInSourceBase;
        double fractionalPartInSourceBase;
        StringBuilder numberInNewBase;
        if (number != null && number.length() > 0 && sourceBase <= 36 && newBase <= 36) {
            //System.out.printf("%d : %s : %s : %s : %d%n", sourceBase, number, integerPartFromNumber, fractionalPartFromNumber, newBase);
            double numberInSourceBase;
            if (sourceBase == 1) {
                numberInSourceBase = 0.;
                if (!number.contains(".")) {
                    for (char c: number.toCharArray()) {
                        if (c == '1') {
                            numberInSourceBase++;
                        }
                    }
                }
                integerPartInSourceBase = String.valueOf((long) numberInSourceBase);
                fractionalPartInSourceBase = 0.;
            } else if (sourceBase == 10) {
                numberInSourceBase = Double.parseDouble(number);
                integerPartInSourceBase = integerPartFromNumber;
                fractionalPartInSourceBase = Double.parseDouble(fractionalPartFromNumber.length() > 0 ? fractionalPartFromNumber : "0.");
                //System.out.printf("%f : %s : %f%n", numberInSourceBase, integerPartInSourceBase, fractionalPartInSourceBase);
            } else {
                long integerPart = Long.parseLong(integerPartFromNumber, sourceBase);
                double fractionalPart = 0.;
                for (int i = 1; i <= Math.min(5, fractionalPartFromNumber.length() - 1); i++) {
                    int digit = Character.isDigit(fractionalPartFromNumber.charAt(i)) ?
                                    Integer.parseInt(String.valueOf(fractionalPartFromNumber.charAt(i))) :
                                    Character.digit(fractionalPartFromNumber.charAt(i), sourceBase);
                    fractionalPart += digit / Math.pow(sourceBase, i);
                }
                numberInSourceBase = integerPart + fractionalPart;
                integerPartInSourceBase = Long.toString(integerPart);
                fractionalPartInSourceBase = fractionalPart;
                //System.out.printf("%d : %f : %f : %s : %f%n", integerPart, fractionalPart, numberInSourceBase, integerPartInSourceBase, fractionalPartInSourceBase);
            }
            if (newBase == 1) {
                numberInNewBase = new StringBuilder("");
                if (integerPartInSourceBase.length() > 0) {
                    for (long i = 1; i <= Long.valueOf(integerPartInSourceBase); i++) {
                        numberInNewBase.append("1");
                    }
                } else {
                    numberInNewBase.append("0");
                }
            } else if (newBase == 10) {
                numberInNewBase = new StringBuilder(Double.toString(numberInSourceBase));
            } else {
                StringBuilder integerPartInNewBase = new StringBuilder(Long.toString(Long.parseLong(integerPartInSourceBase), newBase));
                StringBuilder fractionalPartInNewBase = new StringBuilder(".");
                if (fractionalPartInSourceBase > 0.) {
                    double fractionalPart = fractionalPartInSourceBase;
                    for (int i = 1; fractionalPart >= 0. && i <= 5; i++) {
                        fractionalPart *= newBase;
                        int digit = (int) fractionalPart;
                        fractionalPart -= digit;
                        if (digit <= 9) {
                            fractionalPartInNewBase.append(digit);
                        } else {
                            fractionalPartInNewBase.append(Character.forDigit(digit, newBase));
                        }
                    }
                }
                //System.out.printf("%d : %s : %s%n", newBase, integerPartInNewBase, fractionalPartInNewBase);
                if (fractionalPartInNewBase.length() == 1) {
                    numberInNewBase = integerPartInNewBase;
                }
                else {
                    numberInNewBase = integerPartInNewBase.append(fractionalPartInNewBase);
                }
            }
            System.out.printf("%s%n", numberInNewBase.toString());
        } else {
            System.out.println();
        }
    }
}

