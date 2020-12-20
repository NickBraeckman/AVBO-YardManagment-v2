package comparator;

import main.Container;

import java.util.Comparator;

public class SortContainerByWeight implements Comparator<Container> {
    @Override
    public int compare(Container o1, Container o2) {
        return Integer.compare(o1.getGc(), o2.getGc());
    }
}
