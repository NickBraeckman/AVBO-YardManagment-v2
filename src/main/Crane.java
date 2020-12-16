package main;

import lombok.Data;


@Data
public class Crane {

    private int id;
    private Coordinate2D startCoordinate/*, curCoordinate*/;
//    private List<CraneMovement> moves;
//    private int time;
    public static int PICKUP_TIME = 2;
    public static int DROP_TIME = 2;
    public static int vx = 1, vy = 1;
//    private int X_Q_MIN, X_Q_MAX;
//    private boolean occupied;
    private int delta = 0;

    public Crane(int id, int x0, int y0, int delta) {
        this.id = id;
        this.startCoordinate = new Coordinate2D(x0, y0);
//        this.curCoordinate = startCoordinate;
//        this.X_Q_MIN = 0;
//        this.X_Q_MAX = Yard.L;
//        this.time = 0;
        this.delta = delta;
//        moves = new ArrayList<>();
//        moves.add(new CraneMovement(time, startCoordinate));
    }
}
