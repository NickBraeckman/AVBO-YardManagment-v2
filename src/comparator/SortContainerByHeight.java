package comparator;

import main.Container;

import java.util.Comparator;

public class SortContainerByHeight implements Comparator<Container> {
    @Override
    public int compare(Container o1, Container o2) {
        return Integer.compare(o2.getHeight(), o1.getHeight());
    }
}
