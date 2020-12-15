package comparator;

import main.Container;

import java.util.Comparator;

public class SortContainersByIncreasingLengthAndIncreasingWeight implements Comparator<Container> {
    @Override
    public int compare(Container o1, Container o2) {
        if (o1.getLc() < o2.getLc()) {
            return -1;
        } else if (o1.getLc() == o2.getLc()) {
            if (o1.getGc() < o2.getGc()) {
                return -1;
            } else {
                return (o1.getGc() == o2.getGc()) ? 0 : 1;
            }
        } else {
            return 1;
        }
    }
}
