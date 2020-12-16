package comparator;

import java.util.Comparator;

public class MoveComparator implements Comparator<CraneMovement> {
    @Override
    public int compare(CraneMovement o1, CraneMovent o2) {
        return Integer.compare(o1.getTime(),o2.getTime());
    }
}
