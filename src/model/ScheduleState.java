package model;

import lombok.Data;
import main.Coordinate2D;
import main.Crane;

@Data
public class ScheduleState {
    private int t;
    private Coordinate2D cooQ2;
    private Coordinate2D cooQ1;


    public ScheduleState(int t, Crane crane) {
        this.t=t;

        //TODO romeo add coordinate of good crane
    }
}
