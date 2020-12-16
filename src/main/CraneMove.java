package main;

import lombok.Data;

@Data
public class CraneMove {
    private int time, craneID, x, y, c;


    public CraneMove(int time, int craneID, int x, int y) {
        this.time = time;
        this.craneID = craneID;
        this.x = x;
        this.y = y;
        this.c = -1;
    }

    public CraneMove(int time, int craneID, int x, int y, int c) {
        this.time = time;
        this.craneID = craneID;
        this.x = x;
        this.y = y;
        this.c = c;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(time).append(",")
                .append(craneID).append(",")
                .append(x).append(",")
                .append(y).append(",");

        if (c != -1) {
            sb.append(c);
        }

        return sb.toString();
    }
}
