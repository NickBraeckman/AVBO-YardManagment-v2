package main;

import lombok.Data;
import model.ScheduleState;
import move.CraneMovement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

@Data
public class CraneSchedule {
    private List<Crane> cranes;


    private List<ScheduleState> timeline;


    public CraneSchedule() {
        cranes = new ArrayList<>(2);
    }

    /**
     * @param c
     * @param coordinate
     * @return true if move could be planned -> state yard changes
     */
    public static boolean canMove(Container c, Coordinate2D coordinate) {


        return true;
    }


    public void addCrane(Crane crane) {
        this.cranes.add(crane);
        addState(0, crane);
    }


    private void addState(int t, Crane crane) {

        timeline.add(new ScheduleState(t, crane));
    }

}
