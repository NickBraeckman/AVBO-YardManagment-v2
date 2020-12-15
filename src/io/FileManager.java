package io;

import comparator.SortContainerByIncreasingHeight;
import comparator.SortStapelsById;
import main.*;
import model.Stapel;
import move.CraneMovement;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileManager {

    private static final Logger logger = Logger.getLogger(FileManager.class.getName());

    public static Yard readFile(File inputFile) throws FileNotFoundException {
        String[] inputFileStringArray = inputFile.getName().split("[_|.]");
        int Q = Integer.parseInt(inputFileStringArray[3].substring(1));
        int C = Integer.parseInt(inputFileStringArray[4].substring(1));

        boolean fileIsLab4 = inputFile.toString().contains("_S");

        String[] strArr;
        int Lc, Gc, id, x, y;

        /*  -------------------------------- PROJECT VARIABLES -------------------------------- */
        Yard yard;
        Map<Integer, Slot> slots = new HashMap<>();
        List<Container> tempContainers = new ArrayList<>();
        List<Crane> craneList = new ArrayList<>();
        List<Container> containers = new ArrayList<>();
        List<Row> rows = new ArrayList<>();
        List<String> strList;
        List<Integer> slotIds;

        /*  -------------------------------- YARD PARAMETERS -------------------------------- */
        Scanner sc = new Scanner(inputFile);
        sc.nextLine();
        strArr = sc.nextLine().split(",");
        Yard.L = Integer.parseInt(strArr[0]);
        Yard.W = Integer.parseInt(strArr[1]);
        Yard.H_MAX = Integer.parseInt(strArr[2]);
        if (fileIsLab4) {
            Yard.H_SAFE = Integer.parseInt(inputFileStringArray[5].substring(1));
        } else {
            Yard.H_SAFE = Yard.H_MAX;
        }

        /*  -------------------------------- SLOT PARAMETERS -------------------------------- */
        sc.nextLine();
        strArr = sc.nextLine().split(",");
        Yard.L_S = Integer.parseInt(strArr[0]);
        Yard.W_S = Integer.parseInt(strArr[1]);
        yard = new Yard();

        /*  -------------------------------- SLOTS -------------------------------- */
        sc.nextLine();
        int amountOfColumns = (Yard.L / Yard.L_S);
        int amountOfRows = (Yard.W / Yard.W_S);
        Row row;
        Slot slot;
        for (int i = 0; i < amountOfRows; i++) {
            row = new Row(i + 1);
            for (int j = 0; j < amountOfColumns; j++) {
                strArr = sc.nextLine().split(",");       // [8/30/12] (for i=7)
                id = Integer.parseInt(strArr[0]);            // 8
                x = Integer.parseInt(strArr[1]);             // 30
                y = Integer.parseInt(strArr[2]);
                slot = new Slot(id, x, y);                    // 12
                slots.put(id, slot);
                row.getSlots().add(slot);
            }
            yard.addRow(row);
        }

        /*  -------------------------------- CRANES -------------------------------- */
        CraneSchedule craneSchedule = new CraneSchedule();
        int d = 0;

        sc.nextLine();
        for (int i = 0; i < Q; i++) {
            strArr = sc.nextLine().split(",");
            id = Integer.parseInt(strArr[0]);
            x = Integer.parseInt(strArr[1]);
            y = Integer.parseInt(strArr[2]);
            d = Integer.parseInt(strArr[3]);  //delta

            craneSchedule.addCrane(new Crane(id, x, y,d));
        }




        /*  -------------------------------- CONTAINERS -------------------------------- */
        sc.nextLine();
        for (int i = 0; i < C; i++) {
            strArr = sc.nextLine().split(",");
            id = Integer.parseInt(strArr[0]);
            Lc = Integer.parseInt(strArr[1]);
            Gc = Integer.parseInt(strArr[2]);
            containers.add(new Container(id, Lc, Gc));
        }

        /*  -------------------------------- CONTAINER TO SLOT MAPPING -------------------------------- */
        sc.nextLine();
        while (sc.hasNextLine()) {

            slotIds = new ArrayList<>();
            strArr = sc.nextLine().split(",");

            strList = Arrays.asList(strArr);
            for (String s : strList) {
                slotIds.add(Integer.parseInt(s));
            }
            id = slotIds.get(0);
            slotIds.remove(0);
            Container container = containers.get(id - 1);
            container.setStartIndex(slotIds.get(0));
            container.setStopIndex(slotIds.get(slotIds.size() - 1));
            tempContainers.add(container);
        }
        yard.addContainers(tempContainers);
        return yard;
    }

    public static void writeFile(Yard yard, File file) {
        try (PrintWriter pwOut = new PrintWriter(file)) {
            logger.log(Level.INFO, "Generating output ...");
            pwOut.println("# container->slots");
            for (String s : printContainers(yard.getRowList())) {
                pwOut.println(s);
            }
            //TODO Romeo
            /*pwOut.println("# kraanbewegingen (t,x,y)");
            for (CraneMovement cm : yard.getCraneList().get(0).getMoves()) {
                pwOut.println(cm);
            }*/
            logger.log(Level.INFO, "Output generated in " + file);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static List<String> printContainers(List<Row> rows) {
        List<String> temp = new ArrayList<>();

        for (Row row : rows) {
            Collections.sort(row.getStapels(),new SortStapelsById());
            for (Stapel stapel : row.getStapels()) {
                Collections.sort(stapel.getContainerList(),new SortContainerByIncreasingHeight());
                for (Container container : stapel.getContainerList()) {
                    StringBuilder strb = new StringBuilder();
                    strb.append(container.getId());
                    for (int id : container.getSlotIds()) {
                        strb.append(",").append(id);
                    }
                    temp.add(strb.toString());
                }
            }
        }
        return temp;
    }
}
