package comparator;

import main.Container;
import model.Stapel;

import java.util.Comparator;

public class SortStapelsById implements Comparator<Stapel> {
    @Override
    public int compare(Stapel o1, Stapel o2) {
        if (o1.getId() > o2.getId()){
            return -1;
        } else {
            return (o1.getId() == o2.getId()) ? 0 : 1;
        }
    }
}
