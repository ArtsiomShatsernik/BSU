import java.math.BigDecimal;
import java.util.Formatter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in  = new Scanner(System.in);
        System.out.print("Введите точность: ");
        int k = in.nextInt();
        System.out.print("Введите x: ");
        BigDecimal x = in.nextBigDecimal();
        System.out.printf("Сумма по Тейлору: %." + (k + 1) + "f\n", Taylor.getTaylor(k, x));
        System.out.printf("Значение стандартой функцией: %." + (k + 1) + "f\n", Math.sin(x.doubleValue()) / x.doubleValue());
    }
}
