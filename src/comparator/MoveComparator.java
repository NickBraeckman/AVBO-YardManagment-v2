package comparator;

import main.CraneMove;

import java.util.Comparator;

public class MoveComparator implements Comparator<CraneMove> {
    @Override
    public int compare(CraneMove o1, CraneMove o2) {
        return Integer.compare(o1.getTime(), o2.getTime());
    }
}
