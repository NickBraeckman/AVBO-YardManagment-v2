package main;

import lombok.Data;


import java.util.*;

@Data
public class Yard {

    public static int L, W, H_MAX;
    public static int L_S, W_S;
    public static int H_SAFE;

    private CraneSchedule craneSchedule;
    private List<Row> rowList;
    private List<Container> containers;

    public Yard() {
        this.rowList = new ArrayList<>();
        this.containers = new ArrayList<>();
        this.craneSchedule = new CraneSchedule();
    }

    public Yard(Yard yard) {
        this.rowList = new ArrayList<>();
        for (Row r : yard.getRowList()) {
            this.getRowList().add(new Row(r));
        }
    }

    public void addRow(Row row) {
        rowList.add(row);
    }

    public Row getRow(int slotId) {
        int id = (int) Math.floor((slotId) / (Yard.L / Yard.L_S));
        if (id == rowList.size()) {
            id = id - 1;
        }
        return rowList.get(id);
    }

    public void initContainer(Container container, List<Integer> slotIds) {
        // Coordinaten van middelpunt van een container
        Row row = getRow(slotIds.get(0));
        Slot firstSlot = row.getSlot(slotIds.get(0));

        Coordinate2D center = new Coordinate2D(firstSlot.getCoordinate());

        container.setOrigin(center);

        center.setX(center.getX() + (container.getLc() / 2));
        center.setY(center.getY() + (W_S / 2));
        container.setCenter(center);
        container.setRow(row);

        row.pushContainer(container, slotIds);
    }

    @Override
    public String toString() {
        String s1 = "--------------------------------------------------------------";
        String header = "<" + s1 + " YARD " + s1 + ">" + "\n";

        String yardParametersString = "Yard parameters: \n \t" + getYardParametersToString() + "\n";
        String slotParametersString = "Slot parameters: \n \t" + getSlotParametersToString() + "\n";

        String rowsString = "Rows:\n" + getRowsToString();
        String containersString = "Containers:\n" + getContainersToString();

        String s2 = s1;
        String footer = "\n</" + s2 + " END " + s2 + ">";

        return header +
                yardParametersString +
                slotParametersString +
                rowsString +
                containersString +
                footer;
    }

    private String getYardParametersToString() {
        return "L(x)=" + L + "\t W(y)=" + W + "\t Hmax=" + H_MAX + "\t Hsafe=" + H_SAFE;

    }

    private String getSlotParametersToString() {
        return "Ls(x)=" + L_S + "\t Ws(y)=" + W_S;

    }

    private String getRowsToString() {
        StringBuilder sb = new StringBuilder();
        for (Row r : rowList) {
            sb.append("\t R").append(r.getId()).append(":").append(" Slots: ").append(r.getSlotsToString()).append("\n");
        }
        return sb.toString();

    }

    private String getContainersToString() {
        StringBuilder sb = new StringBuilder();
        for (Container c : containers) {
            sb.append("\t").append(c.toString()).append("\n");
        }
        return sb.toString();
    }

    public void setCraneSchedule(CraneSchedule craneSchedule) {
        this.craneSchedule = craneSchedule;
    }
}

