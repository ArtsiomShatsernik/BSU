import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Locale;


public class Main {
    public static void main(String[] args) {
        Scanner in  = new Scanner(System.in);
        System.out.println("Введите размер матрицы:");
        int size = in.nextInt();
        System.out.println("Введите первую матрицу:");
        Matrix m1 = new Matrix(size);
        m1.inputMatrix();
        System.out.println("Введите вторую матрицу");
        Matrix m2 = new Matrix(size);
        m2.inputMatrix();
        m1.transposeEqual(m2);
        System.out.println();
        if (size >= 3) {
            System.out.println("Вывод строк матрицы в разных форматах: ");
            NumberFormat form1 = NumberFormat.getCurrencyInstance();
            System.out.println("Первая строка в текущих денежных еденицах: ");
            for (int i = 0; i < size; i++) {
                System.out.print(form1.format(m1.matrix[0][i]) + " ");
            }
            System.out.println();
            NumberFormat form2 = NumberFormat.getCurrencyInstance(Locale.FRANCE);
            System.out.println("Вторая строка в Французских денежных еденицах: ");
            for (int i = 0; i < size; i++) {
                System.out.print(form2.format(m1.matrix[1][i]) + " ");
            }
            System.out.println();
            NumberFormat form3 = NumberFormat.getPercentInstance(Locale.US);
            System.out.println("Треться строка в процентах: ");
            for (int i = 0; i < size; i++) {
                System.out.print(form3.format(m1.matrix[1][i]) + " ");
            }
            System.out.println();
        }
        System.out.println();

        System.out.println("Первый массив с отсортированной последней строкой:");
        Arrays.sort(m1.matrix[size - 1], new LastRowComparator());
        m1.show();
        Arrays.sort(m1.matrix[size - 1]);
        System.out.println("Введите элемент для бинарного поиска в последней строке:");
        int bFind = in.nextInt();
        int result = (Arrays.binarySearch(m1.matrix[size - 1], bFind));
        if (result < 0) {
            System.out.println("Элемент не существует");
        } else {
            System.out.println("Элемент существует по индексу: " + result);
        }
    }
}