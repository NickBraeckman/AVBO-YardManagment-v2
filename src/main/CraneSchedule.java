package main;

import lombok.Data;

import java.util.*;
import java.util.logging.Logger;

@Data
public class CraneSchedule {
    private List<Crane> cranes;
    private Map<Integer, List<Coordinate2D>> timeline;
    private Map<Integer, List<Coordinate2D>> temp_timeline;
    private static final Logger logger = Logger.getLogger(CraneSchedule.class.getName());


    // stuff needed for case1(), case2(), case3()
    private int time, craneID, deltaX, deltaY, deltaDif, deltaHelper, xFactor, yFactor, timeToBeTravalled;
    private Crane q;
    private Coordinate2D start, stop;
    private boolean xPositive, yPositive;
    private int x, y;


    public CraneSchedule() {
        cranes = new ArrayList<>(2);
        timeline = new HashMap<>();
    }


    private void deepCopy() {


    }


    /**
     * @param container container which has to be moved
     * @param place     coordinate of the place where the container needs to be placed
     * @return true if move could be planned -> state yard changes
     */
    public boolean canMove(Container container, Coordinate2D place) {
        this.start = container.getCenter();
        this.stop = place;

        this.q = null; //TODO
        this.time = -1;//TODO set time on which crane is free
        Coordinate2D craneCoo = null; //TODO get coordinate on current time


        move(craneCoo, start);
        move(start, stop);

        return false;
    }

    private int determineRouteType() {
        //DETERMINE THE CASE
        if (deltaX - deltaY >= 0) {
            return 1;
        } else {
            if (craneID == 1) {
                if (xPositive) return 2;
                else return 3;
            } else if (craneID == 2) {
                if (xPositive) return 3;
                else return 2;
            }
        }
        return -1;
    }

    private boolean move(Coordinate2D start, Coordinate2D stop) {
        deltaX = Math.abs(start.getX() - stop.getX());
        deltaY = Math.abs(start.getY() - stop.getY());
        deltaDif = Math.abs(deltaX - deltaY);
        deltaHelper = deltaDif + 1;

        xPositive = start.getX() - stop.getX() < 0;
        yPositive = start.getY() - stop.getY() < 0;

        if (xPositive) xFactor = 1;
        else xFactor = -1;

        if (yPositive) yFactor = 1;
        else yFactor = -1;

        switch (determineRouteType()) {
            case 1:
                timeToBeTravalled = deltaX;
                return case1();
            case 2:
                return case2();
            case 3:
                return case3();
            default:
                logger.severe("Took crane that's not possible: craneid=" + craneID);
                break;
        }

        return false;

    }

    private boolean tryMove(int t, int x, int y) {

        if (timeline.containsKey(t)) {
            //TODO check for collisions !!!


            return false;

        } else {
            timeline.put(t, new ArrayList<>());
            timeline.get(t).set(craneID, new Coordinate2D(x, y));
            return true;
        }


    }


    /**
     * Distance to be travelled over x-axis is larger then or equals distance to be travelled over y-axis
     * <p>
     * time
     * craneCoo coordinate of crane on time move is tried
     * start    start coordinate of move
     * stop     stop coordinate of move
     * true if move could be planned
     */
    private boolean case1() {
        boolean planned;
        x = start.getX();
        y = start.getY();

        for (int t = time; t < time + timeToBeTravalled; t++) {
            planned = tryMove(t, x, y);
            // MOVE COULD NOT BE DONE !!! RETURN
            if (!planned) return false;
            // INCREASE
            x++;
            y++;
        }
        // All moves could be done
        return true;
    }

    /**
     * Distance to be travelled over y-axis is larger then or equals distance to be travelled over x-axis
     * Moves that will happen: first y++ until deltaDif is reached, followed by x++ and y++ simultaneously
     * <p>
     * time
     * craneCoo
     * start    start coordinate of move
     * stop     stop coordinate of move
     * deltaDif difference between distance over x-axis and y-axis
     */
    private boolean case2() {

        return false;
    }

    /**
     * Distance to be travelled over y-axis is larger then or equals distance to be travelled over x-axis
     * Moves that will happen: first x++ and y++ simultaneously until deltaDif is reached, followed by y++
     * <p>
     * time
     * craneCoo
     * start    start coordinate of move
     * stop     stop coordinate of move
     * deltaDif difference between distance over x-axis and y-axis
     */
    private boolean case3() {

        return false;
    }


    private void addState(int t, Crane q, Coordinate2D coo) {
        timeline.computeIfAbsent(t, k -> new ArrayList<>(2));

        timeline.get(t).set(q.getId(), coo);
    }


    public void addCrane(Crane crane) {
        this.cranes.add(crane);
        addState(0, crane, crane.getStartCoordinate());
    }


}
