import javax.crypto.interfaces.PBEKey;
import java.util.Comparator;

public class LastRowComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        int k1 = (int)o1;
        int k2 = (int)o2;
        return (k1 > k2 ? - 1 : (k1 == k2 ? 0 : 1));
    }
}
