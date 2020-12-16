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
    private int time, craneID, otherCraneID, deltaX, deltaY, deltaDif, deltaHelper, xFactor, yFactor, timeToBeTravelled;
    private Crane crane;
    //    private Coordinate2D start, stop;
    private boolean xPositive, yPositive;
    private int x, y;

    private int startTime = 0;


    public CraneSchedule() {
        cranes = new ArrayList<>(2);
        timeline = new HashMap<>();

    }

    public CraneSchedule(int craneID) {
        cranes = new ArrayList<>(2);
        timeline = new HashMap<>();
        this.craneID = craneID;

    }

    private void setOtherCrane() {
        if (this.crane.getId() == 2) otherCraneID = 1;
        else otherCraneID = 2;
    }

    /**
     * @param container container which has to be moved
     * @param place     coordinate of the place where the container needs to be placed
     * @return true if move could be planned -> state yard changes
     */
    public boolean canMove(Container container, Coordinate2D place) {

        Coordinate2D cCoo = container.getCenter();

        this.time = 0;//TODO set time on which crane is free
        this.crane = cranes.get(craneID - 1); //TODO get crane
        this.craneID = crane.getId();
        this.setOtherCrane();
        Coordinate2D craneCoo = timeline.get(time).get(craneID - 1); //TODO get coordinate on current time
        startTime = time;

        if (move(craneCoo, cCoo)) {
            return move(cCoo, place);
        } else return false;
    }

    private boolean move(Coordinate2D start, Coordinate2D stop) {
        int temp_t = -1;
        deltaX = Math.abs(start.getX() - stop.getX());
        deltaY = Math.abs(start.getY() - stop.getY());
        deltaDif = Math.abs(deltaX - deltaY);
        deltaHelper = deltaDif + 1;


        determineDirection(start, stop); // left or right

        switch (determineRouteType()) {
            case 1:
                System.out.println("Route type 1");

                timeToBeTravelled = deltaX;
                temp_t = case1(start, stop);
                break;
            case 2:
                System.out.println("Route type 2");

                timeToBeTravelled = deltaY;
                temp_t = case2(start, stop);
                break;
            case 3:
                System.out.println("Route type 3");

                timeToBeTravelled = deltaY;
                temp_t = case3(start, stop);
                break;
            default:
                logger.severe("Took crane that's not possible: craneid=" + craneID);
                break;
        }

        if (temp_t <= time + timeToBeTravelled) {
            System.out.println("Error on time:" + temp_t);
            Coordinate2D coo = timeline.get(startTime).get(craneID - 1);

            while (temp_t > startTime) {


                timeline.get(temp_t).set(craneID - 1, coo);

                temp_t--;
            }

            System.out.println("Move undone");
            printTimeLine();
            return false;
        }

        time += timeToBeTravelled;
        return true;

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

    private void determineDirection(Coordinate2D start, Coordinate2D stop) {
        xPositive = start.getX() - stop.getX() < 0;
        yPositive = start.getY() - stop.getY() < 0;

        if (xPositive) xFactor = 1;
        else xFactor = -1;

        if (yPositive) yFactor = 1;
        else yFactor = -1;
    }

    /**
     * Distance to be travelled over x-axis is larger then or equals distance to be travelled over y-axis
     * <p>
     * time
     * craneCoo coordinate of crane on time move is tried
     * start    start coordinate of move
     * stop     stop coordinate of move
     * true if move could be planned
     *
     * @param start
     * @param stop
     */
    private int case1(Coordinate2D start, Coordinate2D stop) {

        boolean planned;
        x = start.getX();
        y = start.getY();


        int t = time;

        while (t <= time + timeToBeTravelled) {
            planned = tryMove(t, new Coordinate2D(x, y));
            // MOVE COULD NOT BE DONE !!! RETURN
            if (!planned) return t;
            // INCREASE

            x = x + (xFactor);

            if (y < start.getY() + (deltaY * yFactor)) y = y + (yFactor);

            t++;
        }

        // All moves could be done
        return t;
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
     *
     * @param start
     * @param stop
     * @return
     */
    private int case2(Coordinate2D start, Coordinate2D stop) {

        return -2;
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
     *
     * @param start
     * @param stop
     * @return
     */
    private int case3(Coordinate2D start, Coordinate2D stop) {

        return -3;
    }

    private boolean tryMove(int t, Coordinate2D coo) {

        List<Coordinate2D> coordinate2DS1 = timeline.get(t);

        if (timeline.containsKey(t)) {
            //TODO check if crane does not already have a coo on this time (standard -1,-1
            //TODO check for collisions !!!


            if (coordinate2DS1.get(craneID - 1) != null) {
                if (!(coordinate2DS1.get(craneID - 1).equals(coo))) {
                    System.out.println("Error t:" + t + " currCoo:" + coo + " timeline:" + coordinate2DS1.get(craneID - 1));
                }
            }


        } else {
            //Add new
            List<Coordinate2D> coordinate2DS = new ArrayList<>(2);
            coordinate2DS.add(cranes.get(0).getStartCoordinate());
            coordinate2DS.add(cranes.get(1).getStartCoordinate());
            timeline.put(t, coordinate2DS);
            coordinate2DS1 = coordinate2DS;
        }

        //TODO Romeo

        // collision detection
        boolean collision = false;

        if (xPositive && craneID == 1) {
            if (x + deltaHelper > timeline.get(t).get(otherCraneID-1).getX()) collision = true;
        }
        if (!xPositive && craneID == 2) {
            if (x - deltaHelper < timeline.get(t).get(otherCraneID-1).getX()) collision = true;
        }

        if (collision) {
            printTimeLine();
            System.out.println("Collisions !!! t:" + t + "current crane" + coordinate2DS1.get(craneID - 1) + " other crane" + coordinate2DS1.get(otherCraneID - 1));
            return false;
        }

        timeline.get(t).set(craneID - 1, coo);
        return true;
    }


    private void addState(int t, Crane q, Coordinate2D coo) {

        if (timeline.get(t) == null) {
            timeline.put(t, new ArrayList<>());
        }

        List<Coordinate2D> temp = timeline.get(t);


        timeline.get(t).add(q.getId() - 1, coo);
    }


    public void addCrane(Crane crane) {
        this.cranes.add(crane);
        addState(0, crane, crane.getStartCoordinate());
    }

    public void printTimeLine() {
        int size = timeline.size();

        List<List<Coordinate2D>> temp = new ArrayList<>(size);

        for (int t = 0; t < size; t++) temp.add(timeline.get(t));

        int t = 0;
        for (List<Coordinate2D> couple : temp) {

            StringBuilder sb = new StringBuilder();
            sb.append("t").append(t);
            sb.append("\t q1:").append(couple.get(0));
            sb.append("\t q2:").append(couple.get(1));
            System.out.println(sb.toString());
            t++;
        }
    }
}
