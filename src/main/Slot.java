package main;

import lombok.Data;

@Data
public class Slot {

    private int id;
    private Coordinate2D coordinate;
    private Coordinate2D center;

    public Slot(int id, int x, int y){
        this.id = id;
        this.coordinate = new Coordinate2D(x,y);
        this.center = new Coordinate2D(x + Yard.L_S /2, y + Yard.W_S /2);
    }

    public Slot(Slot slot){
        this.id = slot.getId();
        this.center = new Coordinate2D(slot.getCenter());
        this.coordinate = new Coordinate2D(slot.getCoordinate());
    }

}
