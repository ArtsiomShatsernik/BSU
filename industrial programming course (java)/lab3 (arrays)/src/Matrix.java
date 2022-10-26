import java.util.*;

public class Matrix {
    private
    int size;
    public
    Integer[][] matrix;
    public
    Matrix(int nSize) {
        size = nSize;
        matrix = new Integer[size][size];
    }
    void rMatrix() {
        Random r = new Random();
        matrix = new Integer[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = r.nextInt(10);
            }
        }
       show();
    }
    void inputMatrix() {
        Scanner in = new Scanner(System.in);
        System.out.println("Введите матрицу по строкам:");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = in.nextInt();
            }
        }
    }
    void show() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
    void transpose(boolean diag) {
        if (diag) {
            for (int i = 0; i < size; i++) {
                for (int j = i; j < size; j++) {
                    int temp = matrix[i][j];
                    matrix[i][j] = matrix[j][i];
                    matrix[j][i] = temp;
                }
            }
        } else if (!diag) {
            for (int i = 0; i < size; i++) {
                for (int j = size - 1 - i; j >= 0; j--) {
                    int temp = matrix[i][j];
                    matrix[i][j] = matrix[size - j - 1][size - i - 1];
                    matrix[size - j - 1][size - i - 1] = temp;
                }
            }
        }
    }
    void transposeEqual(Matrix second) {
        boolean diag = true;
        for (int i = 0; i < 3; i++) {
            if (Arrays.deepEquals(this.matrix, second.matrix)) {
                System.out.println("Equal");
                return;
            } else {
                second.transpose(diag);
                diag = !diag;
            }
        }
        diag = false;
        for (int i = 0; i < 3; i++) {
            if (Arrays.deepEquals(this.matrix, second.matrix)) {
                System.out.println("Equal");
                return;
            } else {
                second.transpose(diag);
                diag = !diag;
            }
        }
        System.out.println("Not equal");
    }
}
