import java.math.BigDecimal;
import java.math.RoundingMode;

public class Taylor {
    private static BigDecimal getAccuracy(int k) {
        BigDecimal e = new BigDecimal("0.1");
        BigDecimal coeff = new BigDecimal("0.1");
        if (k < 4) {
            for (int i = 1; i < k; i++)
               e = e.multiply(coeff);
        } else {
            e = e.pow(k);
        }
        return e;
    }
    public static BigDecimal getTaylor(int k, BigDecimal x) {
        BigDecimal sum = new BigDecimal("0");
        BigDecimal next = new BigDecimal("1");
        double i = 0;
        BigDecimal accuracy = getAccuracy(k);
        while (next.abs().compareTo(accuracy) != -1) {
            sum = sum.add(next);
            BigDecimal temp = x.multiply(x);
            BigDecimal dividend = next.multiply(BigDecimal.valueOf(-1));
            dividend = dividend.multiply(temp);
            BigDecimal divisor = new BigDecimal((2 * i + 3) * (2 * i + 2));
            next = dividend.divide(divisor, k + 1, RoundingMode.DOWN);
            i++;
        }
        return sum;
    }

}