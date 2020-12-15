package main;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Coordinate2D {
    int x, y;

    public Coordinate2D(){
        this.x = -1;
        this.y = -1;
    }

    public Coordinate2D(Coordinate2D p) {
        this.x = p.getX();
        this.y = p.getY();
    }

    public int getManhattanDistance(Coordinate2D c) {
        return Math.max(Math.abs(c.x - x), Math.abs(c.y - y));
    }

    public int getManhattanDistance(int x) {
        return Math.abs(this.x - x);
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }

}
