package main;

public class Test {
    public static int time = 0;

    public static void main(String[] args) {

        /* -------------------- CASE 1 -------------------- */
        q1_rechts_case1(0);
        q1_links_case1(0);
        q2_rechts_case1(0);
        q2_links_case1(0);

         test1();
         test2();
         test3();
    }

    private static void test1() {
        System.out.println("------------------------ TEST1 ------------------------");
        CraneSchedule cs = new CraneSchedule();
        cs.addCrane(new Crane(1, 0, 0, 2));
        cs.addCrane(new Crane(2, 3, 0, 2));

        Container c1 = new Container(1, 1, 1);
        c1.setCenter(new Coordinate2D(3, 2));

        Container c2 = new Container(2, 1, 1);
        c2.setCenter(new Coordinate2D(6, 2));


        Coordinate2D place1 = new Coordinate2D(6, 3);

        Coordinate2D place2 = new Coordinate2D(9, 3);

        boolean b = cs.canMove(c1, place1, time, 1);

        if (b) {
            cs.printTimeLine();

            b = cs.canMove(c2, place2, time, 2);

            if (b) cs.printTimeLine();
        }
    }
    private static void test2() {
        System.out.println("------------------------ TEST2 ------------------------");
        CraneSchedule cs = new CraneSchedule();
        cs.addCrane(new Crane(1, 6, 0, 2));
        cs.addCrane(new Crane(2, 9, 0, 2));

        Container c1 = new Container(1, 1, 1);
        c1.setCenter(new Coordinate2D(3, 3));

        Container c2 = new Container(2, 1, 1);
        c2.setCenter(new Coordinate2D(6, 2));


        Coordinate2D place1 = new Coordinate2D(0, 3);

        Coordinate2D place2 = new Coordinate2D(3, 3);

        boolean b = cs.canMove(c1, place1, time, 1);

        if (b) {
            cs.printTimeLine();

            b = cs.canMove(c2, place2, time, 2);

            if (b) cs.printTimeLine();
        }
    }

    private static void test3() {
        System.out.println("------------------------ TEST3 ------------------------");
        CraneSchedule cs = new CraneSchedule();
        cs.addCrane(new Crane(1, 0, 0, 2));
        cs.addCrane(new Crane(2, 9, 0, 2));

        Container c1 = new Container(1, 1, 1);
        c1.setCenter(new Coordinate2D(3, 2));

        Container c2 = new Container(2, 1, 1);
        c2.setCenter(new Coordinate2D(6, 2));


        Coordinate2D place1 = new Coordinate2D(6, 3);

        Coordinate2D place2 = new Coordinate2D(3, 3);

        boolean b = cs.canMove(c1, place1, time, 1);

        if (b) {
            cs.printTimeLine();

            b = cs.canMove(c2, place2, time, 2);

            if (b) cs.printTimeLine();
        }
    }
    private static void q1_rechts_case1(int t) {
        System.out.println("------------------------ q1_rechts_case1 ------------------------");
        CraneSchedule cs = new CraneSchedule();
        cs.addCrane(new Crane(1, 0, 0, 2));
        cs.addCrane(new Crane(2, 9, 6, 2)); //collision for (8,0)


        Container c = new Container(1, 1, 1);
        c.setCenter(new Coordinate2D(3, 2));

        Coordinate2D place = new Coordinate2D(6, 3);


        boolean b = cs.canMove(c, place, t, 1);

        if (b) cs.printTimeLine();

    }

    private static void q1_links_case1(int t) {
        System.out.println("------------------------ q1_links_case1 ------------------------");

        CraneSchedule cs = new CraneSchedule();
        cs.addCrane(new Crane(1, 6, 0, 2));
        cs.addCrane(new Crane(2, 9, 6, 2));


        Container c = new Container(1, 1, 1);
        c.setCenter(new Coordinate2D(3, 3));

        Coordinate2D place = new Coordinate2D(0, 3);


        boolean b = cs.canMove(c, place, t, 1);

        if (b) cs.printTimeLine();
    }

    private static void q2_rechts_case1(int t) {
        System.out.println("------------------------ q2_rechts_case1 ------------------------");
        CraneSchedule cs = new CraneSchedule();
        cs.addCrane(new Crane(1, 0, 6, 2));
        cs.addCrane(new Crane(2, 3, 0, 2));


        Container c = new Container(1, 1, 1);
        c.setCenter(new Coordinate2D(6, 2));

        Coordinate2D place = new Coordinate2D(9, 3);


        boolean b = cs.canMove(c, place, t, 2);

        if (b) cs.printTimeLine();
    }

    private static void q2_links_case1(int t) {
        System.out.println("------------------------ q2_links_case1 ------------------------");

        CraneSchedule cs = new CraneSchedule();
        cs.addCrane(new Crane(1, 0, 6, 2));//collision for ( 1, 6)
        cs.addCrane(new Crane(2, 9, 0, 2));


        Container c = new Container(1, 1, 1);
        c.setCenter(new Coordinate2D(6, 2));

        Coordinate2D place = new Coordinate2D(3, 3);


        boolean b = cs.canMove(c, place, t, 2);

        if (b) cs.printTimeLine();
    }
}
