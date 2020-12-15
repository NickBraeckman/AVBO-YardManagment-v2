package comparator;

import main.Container;

import java.util.Comparator;

public class SortContainerById implements Comparator<Container> {
    @Override
    public int compare(Container o1, Container o2) {
        if (o1.getId() > o2.getId()){
            return -1;
        } else {
            return (o1.getId() == o2.getId()) ? 0 : 1;
        }
    }
}
