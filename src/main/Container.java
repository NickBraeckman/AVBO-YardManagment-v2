package main;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Container{

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
        this.center = new Coordinate2D(-1,-1);
        this.origin = new Coordinate2D(-1,-1);
    }

    public Container(Container container) {
        this.id = container.getId();
        this.Lc = container.getLc();
        this.Gc = container.getGc();
        this.height = container.getHeight();
        this.center = new Coordinate2D(container.getCenter());
        this.origin = new Coordinate2D(container.getOrigin());
        this.startIndex = container.getStartIndex();
        this.stopIndex = container.getStopIndex();
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

    public void setCenter(Coordinate2D coordinate){
        this.startIndex = coordinate.x - getWc()/2;
        this.stopIndex = coordinate.x + getWc()/2;
        this.center = coordinate;
    }

    @Override
    public String toString() {
        return String.format("C%d: [%d, %d] height: %d, weight: %d", getId(), getStartIndex(), getStopIndex(), getHeight(), getGc());
    }
}
