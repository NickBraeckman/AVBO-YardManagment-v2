package main;

import com.google.common.graph.EndpointPair;
import com.google.common.graph.Graph;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import lombok.Data;
import model.Stapel;
import model.StapelOperations;

import java.util.*;

@Data
public class Yard {

    public static int L, W, H_MAX;
    public static int L_S, W_S;
    public static int H_SAFE;

    private CraneSchedule craneSchedule;
    private List<Row> rowList;
//    private List<Crane> craneList;

    public Yard() {
        this.rowList = new ArrayList<>();
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

    public void addContainers(List<Container> containers) {
        StapelOperations operations = new StapelOperations();
        for (Stapel stapel : operations.getStapels(containers)) {
            int id = stapel.getUpperContainer().getStartIndex();
            Row row = getRow(id);
            row.addStapel(stapel);
        }
    }

    public Row getRow(int slotId) {
        int id = (int) Math.floor((slotId) / (Yard.L / Yard.L_S));

        // extra check last slotId
        if (id == rowList.size()) {
            id = id - 1;
        }

        return rowList.get(id);
    }

    @Override
    public String toString() {
        return "Yard{" +
                "\n rowList=" + rowList +
                '}';
    }

    public void setCraneSchedule(CraneSchedule craneSchedule) {
        this.craneSchedule = craneSchedule;
    }
}

