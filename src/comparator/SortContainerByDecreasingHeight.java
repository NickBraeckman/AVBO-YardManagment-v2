package comparator;

import main.Container;

import java.util.Comparator;

public class SortContainerByDecreasingHeight implements Comparator<Container> {
    @Override
    public int compare(Container o1, Container o2) {
        if (o1.getHeight() > o2.getHeight()){
            return -1;
        } else {
            return (o1.getHeight() == o2.getHeight()) ? 0 : 1;
        }
    }
}
