package main;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Container implements Comparable<Container>{

    private int id;
    private int Lc, Gc, Wc;
    private int startIndex;
    private int stopIndex;
    private int height;
    private Coordinate2D center, origin;

    public Container(int id, int Lc, int Gc) {
        this.id = id;
        this.Lc = Lc;
        this.Gc = Gc;
        this.height = 1;
    }

    public Container(Container container) {
        this.id = container.getId();
        this.Lc = container.getLc();
        this.Gc = container.getGc();
        this.height = container.getHeight();
        this.center = new Coordinate2D(container.getCenter());
        this.origin = new Coordinate2D(container.getOrigin());
    }

    public List<Integer> getSlotIds(){
        List<Integer> slotIds = new ArrayList<>();
        for (int i = startIndex; i <= stopIndex; i++){
            slotIds.add(i);
        }
        return slotIds;
    }


    public boolean hasOverlap(Container c) {
        if ((this.getStartIndex() >= c.getStartIndex() && this.getStopIndex() <= c.getStopIndex())) {
            return true;
        }
            return false;

    }

    @Override
    public int compareTo(Container o) {
        if (this.getHeight() > o.getHeight()){
            return -1;
        } else {
            return (this.getHeight() == o.getHeight()) ? 0 : 1;
        }
    }

    @Override
    public String toString() {
        return String.format("C%d: [%d, %d] height: %d, weight: %d", getId(), getStartIndex(), getStopIndex(), getHeight(), getGc());
    }
}
