package main;

public class Test {
    public static void main(String[] args) {


        /* -------------------- CASE 1 -------------------- */
        q1_rechts_case1();
        q1_links_case1();
        q2_rechts_case1();
        q2_links_case1();

    }

    private static void q1_rechts_case1() {
        System.out.println("------------------------ q1_rechts_case1 ------------------------");
        CraneSchedule cs = new CraneSchedule(1);
        cs.addCrane(new Crane(1, 0, 0, 2));
        cs.addCrane(new Crane(2, 9, 6, 2)); //collision for (8,0)


        Container c = new Container(1, 1, 1);
        c.setCenter(new Coordinate2D(3, 2));

        Coordinate2D place = new Coordinate2D(6, 3);


        boolean b = cs.canMove(c, place);

        if (b) cs.printTimeLine();
    }

    private static void q1_links_case1() {
        System.out.println("------------------------ q1_links_case1 ------------------------");

        CraneSchedule cs = new CraneSchedule(1);
        cs.addCrane(new Crane(1, 6, 0, 2));
        cs.addCrane(new Crane(2, 9, 6, 2));


        Container c = new Container(1, 1, 1);
        c.setCenter(new Coordinate2D(3, 3));

        Coordinate2D place = new Coordinate2D(0, 3);


        boolean b = cs.canMove(c, place);

        if (b) cs.printTimeLine();
    }

    private static void q2_rechts_case1() {
        System.out.println("------------------------ q2_rechts_case1 ------------------------");
        CraneSchedule cs = new CraneSchedule(2);
        cs.addCrane(new Crane(1, 0, 6, 2));
        cs.addCrane(new Crane(2, 3, 0, 2));


        Container c = new Container(1, 1, 1);
        c.setCenter(new Coordinate2D(6, 2));

        Coordinate2D place = new Coordinate2D(9, 3);


        boolean b = cs.canMove(c, place);

        if (b) cs.printTimeLine();
    }

    private static void q2_links_case1() {
        System.out.println("------------------------ q2_links_case1 ------------------------");

        CraneSchedule cs = new CraneSchedule(2);
        cs.addCrane(new Crane(1, 0, 6, 2));//collision for ( 1, 6)
        cs.addCrane(new Crane(2, 9, 0, 2));


        Container c = new Container(1, 1, 1);
        c.setCenter(new Coordinate2D(6, 2));

        Coordinate2D place = new Coordinate2D(3, 3);


        boolean b = cs.canMove(c, place);

        if (b) cs.printTimeLine();
    }
}
