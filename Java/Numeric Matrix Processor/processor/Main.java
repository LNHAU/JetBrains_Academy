package processor;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final Scanner input = new Scanner(System.in);
        int choice = -1;
        do {
            System.out.println("1. Add matrices");
            System.out.println("2. Multiply matrix by a constant");
            System.out.println("3. Multiply matrices");
            System.out.println("4. Transpose matrix");
            System.out.println("5. Calculate a determinant");
            System.out.println("6. Inverse matrix");
            System.out.println("0. Exit");
            System.out.print("Your choice: ");
            if (!input.hasNextInt()) {
                processorError();
            }
            else {
                choice = input.nextInt();
                final int[] matrixA_size;
                final int matrixA_n;
                final int matrixA_m;
                double[][] matrixA;
                final int[] matrixB_size;
                final int matrixB_n;
                final int matrixB_m;
                double[][] matrixB;
                final int[] matrix_size;
                final int matrix_n;
                final int matrix_m;
                double[][] matrix;
                switch(choice) {
                    case 1:
                        matrixA_size = readMatrixSize(input, "Enter size of first matrix: ");
                        matrixA_n = matrixA_size[0];
                        matrixA_m = matrixA_size[1];
                        System.out.println("Enter first matrix:");
                        matrixA = readMatrix(input, matrixA_n, matrixA_m);
                        matrixB_size = readMatrixSize(input, "Enter size of second matrix: ");
                        matrixB_n = matrixB_size[0];
                        matrixB_m = matrixB_size[1];
                        System.out.println("Enter second matrix:");
                        matrixB = readMatrix(input, matrixB_n, matrixB_m);
                        if (matrixA_n == 0 || matrixA_m == 0 || matrixB_n != matrixA_n || matrixB_m != matrixA_m) {
                            processorError();
                            break;
                        }
                        System.out.println("The result is:");
                        outputMatrix(addMatrices(matrixA, matrixA_n, matrixA_m, matrixB, matrixB_n, matrixB_m), matrixA_n, matrixA_m);
                        break;
                    case 2:
                        matrix_size = readMatrixSize(input, "Enter size of matrix: ");
                        matrix_n = matrix_size[0];
                        matrix_m = matrix_size[1];
                        System.out.println("Enter matrix:");
                        matrix = readMatrix(input, matrix_n, matrix_m);
                        System.out.print("Enter constant: ");
                        if (matrix_n == 0 || matrix_m == 0 || !input.hasNextDouble()) {
                            processorError();
                            break;
                        }
                        double cForMult = input.nextDouble();
                        System.out.println("The result is:");
                        outputMatrix(multiplyByAConstant(matrix, matrix_n, matrix_m, cForMult), matrix_n, matrix_m);
                        break;
                    case 3:
                        matrixA_size = readMatrixSize(input, "Enter size of first matrix: ");
                        matrixA_n = matrixA_size[0];
                        matrixA_m = matrixA_size[1];
                        System.out.println("Enter first matrix:");
                        matrixA = readMatrix(input, matrixA_n, matrixA_m);
                        matrixB_size = readMatrixSize(input, "Enter size of second matrix: ");
                        matrixB_n = matrixB_size[0];
                        matrixB_m = matrixB_size[1];
                        System.out.println("Enter second matrix:");
                        matrixB = readMatrix(input, matrixB_n, matrixB_m);
                        if (matrixA_n == 0 || matrixA_m == 0 || matrixB_n != matrixA_m || matrixB_m == 0) {
                            processorError();
                            break;
                        }
                        System.out.println("The result is:");
                        outputMatrix(multiplyMatrices(matrixA, matrixA_n, matrixA_m, matrixB, matrixB_n, matrixB_m), matrixA_n, matrixB_m);
                        break;
                    case 4:
                        int subChoice = -1;
                        do {
                            System.out.println("\n1. Main diagonal");
                            System.out.println("2. Side diagonal");
                            System.out.println("3. Vertical line");
                            System.out.println("4. Horizontal line");
                            System.out.print("Your choice: ");
                            if (!input.hasNextInt()) {
                                processorError();
                            } else {
                                subChoice = input.nextInt();
                            }
                        } while (subChoice <= 0);
                        matrix_size = readMatrixSize(input, "Enter matrix size : ");
                        matrix_n = matrix_size[0];
                        matrix_m = matrix_size[1];
                        System.out.println("Enter matrix:");
                        matrix = readMatrix(input, matrix_n, matrix_m);
                        double[][] resultTransposeMatrix = null;
                        switch(subChoice) {
                            case 1:
                                resultTransposeMatrix = transposeMatrixAlongMainDiagonal(matrix, matrix_n, matrix_m);
                                break;
                            case 2:
                                resultTransposeMatrix = transposeMatrixAlongSideDiagonal(matrix, matrix_n, matrix_m);
                                break;
                            case 3:
                                resultTransposeMatrix = transposeMatrixAlongVerticalLine(matrix, matrix_n, matrix_m);
                                break;
                            case 4:
                                resultTransposeMatrix = transposeMatrixAlongHorizontalLine(matrix, matrix_n, matrix_m);
                                break;
                        }
                        System.out.println("The result is:");
                        outputMatrix(resultTransposeMatrix, matrix_n, matrix_m);
                        break;
                    case 5:
                        matrix_size = readMatrixSize(input, "Enter matrix size: ");
                        matrix_n = matrix_size[0];
                        matrix_m = matrix_size[1];
                        System.out.println("Enter matrix:");
                        matrix = readMatrix(input, matrix_n, matrix_m);
                        if (matrix_n == 0 || matrix_m == 0 || matrix_m != matrix_n) {
                            processorError();
                            break;
                        }
                        System.out.println("The result is:");
                        System.out.printf("%.2f%n", getDeterminant(matrix, matrix_n, matrix_m));
                        break;
                    case 6:
                        matrix_size = readMatrixSize(input, "Enter matrix size: ");
                        matrix_n = matrix_size[0];
                        matrix_m = matrix_size[1];
                        System.out.println("Enter matrix:");
                        matrix = readMatrix(input, matrix_n, matrix_m);
                        if (matrix_n == 0 || matrix_m == 0 || matrix_m != matrix_n) {
                            processorError();
                            break;
                        }
                        double determinant = getDeterminant(matrix, matrix_n, matrix_m);
                        if (determinant != 0) {
                            System.out.println("The result is:");
                            outputMatrix(inverseMatrix(matrix, matrix_n, matrix_m, determinant), matrix_n, matrix_m);
                        } else {
                            System.out.println("This matrix doesn't have an inverse.\n");
                        }
                        break;
                    case 0:
                        break;
                }
            }
        } while (choice != 0);
    }
    
    static void processorError() {
        System.out.println("\nThe operation cannot be performed.");
        System.exit(-1);
    }
    
    static int[] readMatrixSize(Scanner input, String text) {
        System.out.print(text);
        int[] matrixSize = null;
        String read_n = input.next();
        //System.out.println(read_n);
        if (read_n == null || read_n.length() == 0) {
            processorError();
        } else {
            String read_m = input.next();
            //System.out.println(read_m);
            if (read_m == null || read_m.length() == 0) {
                processorError();
            }  else {
                    try {
                        int matrix_n = Integer.parseInt(read_n);
                        int matrix_m = Integer.parseInt(read_m);
                        matrixSize = new int[] {matrix_n, matrix_m};
                    } catch (NullPointerException | NumberFormatException e) {
                        processorError();
                    }
            }
        }
        return matrixSize;
    }
    
    static double[][] readMatrix(Scanner input, int matrix_n, int matrix_m) {
        if (matrix_n == 0 || matrix_m == 0) {
            System.out.println("ERROR");
            System.exit(-1);
        }
        double[][] matrix = new double[matrix_n][matrix_m];
        for (int i = 0; i < matrix_n; i++) {
            for (int j = 0; j < matrix_m; j++) {
                if (!input.hasNextDouble()) {
                    processorError();
                } else {
                    try {
                        matrix[i][j] = input.nextDouble();
                    } catch (NullPointerException | NumberFormatException e) {
                        processorError();
                    }
                }
            }
        }
        return matrix;
    }
    
    static double[][] addMatrices(double[][] matrixA, int matrixA_n, int matrixA_m, double[][] matrixB, int matrixB_n, int matrixB_m) {
        if (matrixA_n == 0 || matrixA_m == 0 || matrixB_n != matrixA_n || matrixB_m != matrixA_m) {
            System.out.println("ERROR");
            System.exit(-1);
        }
        double[][] addResultMatrix = new double[matrixA_n][matrixA_m];
        for (int i = 0; i < matrixA_n; i++) {
            for (int j = 0; j < matrixA_m; j++) {
                addResultMatrix[i][j] = matrixA[i][j] + matrixB[i][j];
            }
        }
        return addResultMatrix;
    }
    
    static double[][] multiplyByAConstant(double[][] matrix, int matrix_n, int matrix_m, double cForMult) {
        double[][] multResultMatrix = new double[matrix_n][matrix_m];
        for (int i = 0; i < matrix_n; i++) {
            for (int j = 0; j < matrix_m; j++) {
                multResultMatrix[i][j] = cForMult * matrix[i][j];
                //System.out.printf("%d : %d : %f : %f%n", i, j, cForMult, matrix[i][j]);
            }
        }
        return multResultMatrix;
    }
    
    static double[][] multiplyMatrices(double[][] matrixA, int matrixA_n, int matrixA_m, double[][] matrixB, int matrixB_n, int matrixB_m) {
        if (matrixA_n == 0 || matrixA_m == 0 || matrixB_n != matrixA_m || matrixB_m == 0) {
            System.out.println("ERROR");
            System.exit(-1);
        }
        double[][] multiplyResultMatrix = new double[matrixA_n][matrixB_m];
        for (int k = 0; k < matrixB_m; k++) {
            for (int i = 0; i < matrixA_n; i++) {
                multiplyResultMatrix[i][k] = 0.;
                for (int j = 0; j < matrixA_m; j++) {
                    multiplyResultMatrix[i][k] += matrixA[i][j] * matrixB[j][k];
                }
            }
        }
        return multiplyResultMatrix;
    }
    
    static void outputMatrix(double[][] matrix, int matrix_n, int matrix_m) {
        for (int i = 0; i < matrix_n; i++) {
            for (int j = 0; j < matrix_m; j++) {
                System.out.printf("%6.2f ", matrix[i][j]);
            }
            System.out.println();
        }
    }
    
    static double[][] transposeMatrixAlongMainDiagonal(double[][] matrix, int matrix_n, int matrix_m) {
        if (matrix_n == 0 || matrix_m == 0) {
            System.out.println("ERROR");
            System.exit(-1);
        }
        double[][] resultTransposeMatrix = new double[matrix_n][matrix_m];
        for (int i = 0; i < matrix_n; i++) {
            for (int j = 0; j < matrix_m; j++) {
                resultTransposeMatrix[j][i] = matrix[i][j];
            }
        }
        return resultTransposeMatrix;
    }
    
    static double[][] transposeMatrixAlongSideDiagonal(double[][] matrix, int matrix_n, int matrix_m) {
        if (matrix_n == 0 || matrix_m == 0) {
            System.out.println("ERROR");
            System.exit(-1);
        }
        double[][] resultTransposeMatrix = new double[matrix_n][matrix_m];
        for (int j = matrix_m - 1, k = 0; j >= 0; j--, k++) {
            for (int i = matrix_n - 1, l = 0; i >= 0 ; i--, l++) {
                resultTransposeMatrix[k][l] = matrix[i][j];
            }
        }
        return resultTransposeMatrix;
    }
    
    static double[][] transposeMatrixAlongVerticalLine(double[][] matrix, int matrix_n, int matrix_m) {
        if (matrix_n == 0 || matrix_m == 0) {
            System.out.println("ERROR");
            System.exit(-1);
        }
        double[][] resultTransposeMatrix = new double[matrix_n][matrix_m];
        for (int i = 0; i < matrix_n; i++) {
            for (int j = matrix_m - 1, k = 0; j >= 0; j--, k++) {
                resultTransposeMatrix[i][k] = matrix[i][j];
            }
        }
        return resultTransposeMatrix;
    }
    
    static double[][] transposeMatrixAlongHorizontalLine(double[][] matrix, int matrix_n, int matrix_m) {
        if (matrix_n == 0 || matrix_m == 0) {
            System.out.println("ERROR");
            System.exit(-1);
        }
        double[][] resultTransposeMatrix = new double[matrix_n][matrix_m];
        for (int i = matrix_n - 1, k = 0; i >= 0 ; i--, k++) {
            for (int j = 0; j < matrix_m; j++) {
                resultTransposeMatrix[k][j] = matrix[i][j];
            }
        }
        return resultTransposeMatrix;
    }

    static double[][] subMatrix(double[][] matrix, int matrix_n, int iRow, int jColumn) {
        if (matrix_n == 0) {
            System.out.println("ERROR");
            System.exit(-1);
        }
        double[][] subMatrix = new double[matrix_n - 1][matrix_n - 1];
        for (int k = 0, i = 0; i < matrix_n; i++) {
            if (i != iRow) {
                for (int l = 0, j = 0; j < matrix_n; j++) {
                    if (j != jColumn) {
                        subMatrix[k][l] = matrix[i][j];
                        //System.out.printf("%d : %d : %d : %d : %f%n", i, j, k, l, matrix[i][j]);
                        l++;
                    }
                }
                k++;
            }
        }
        //outputMatrix(subMatrix, matrix_n - 1, matrix_n - 1);
        return subMatrix;
    }

    static double getDeterminant(double[][] matrix, int matrix_n, int matrix_m) {
        if (matrix_n == 0 || matrix_m == 0 || matrix_m != matrix_n) {
            System.out.println("ERROR");
            System.exit(-1);
        }
        double determinant = 0.;
        if (matrix_n == 1) {
            determinant = matrix[0][0];
        } else if (matrix_n == 2) {
            //System.out.printf("%f : %f - %f : %f%n", matrix[0][0], matrix[1][1], matrix[0][1], matrix[1][0]);
            determinant = matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        } else {
            for (int j = 0; j < matrix_m; j++) {
                determinant += (j % 2 == 0 ? 1 : -1) * matrix[0][j] * getDeterminant(subMatrix(matrix, matrix_n, 0, j), matrix_n - 1, matrix_m - 1);
                //System.out.printf("%d : %d : %f%n", j, matrix_m, determinant);
            }
        }
        return determinant;
    }

    static double[][] inverseMatrix(double[][] matrix, int matrix_n, int matrix_m, double determinant) {
        if (matrix_n == 0 || matrix_m == 0 || matrix_m != matrix_n) {
            System.out.println("ERROR");
            System.exit(-1);
        }
        double[][] inversedMatrix = new double[matrix_n][matrix_n];
        if (determinant != 0) {
            double[][] cofactors = new double[matrix_n][matrix_n];
            for (int i = 0; i < matrix_n; i++) {
                for (int j = 0; j < matrix_m; j++) {
                    cofactors[i][j] += ((i + j) % 2 == 0 ? 1 : -1) * getDeterminant(subMatrix(matrix, matrix_n, i, j), matrix_n - 1, matrix_m - 1);
                    //System.out.printf("%d : %d : %f%n", i, j, cofactors[i][j]);
                }
            }
            inversedMatrix = multiplyByAConstant(transposeMatrixAlongMainDiagonal(cofactors, matrix_n, matrix_m), matrix_n, matrix_m, 1 / determinant);
        }
        return inversedMatrix;
    }
}
