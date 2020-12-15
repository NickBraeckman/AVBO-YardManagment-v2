package main;

import com.google.common.graph.Graph;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import lombok.Data;
import model.Stapel;
import model.StapelOperations;

import java.util.*;

@Data
public class Row {

    private StapelOperations stapelOperations;
    private int id;
    private List<Slot> slots;
    List<Stapel> stapels = new ArrayList();


    public Row(int id){
        this.id = id;
        this.slots = new ArrayList<>();
        this.stapels = new ArrayList<>();
    }

    public Row(Row row){
        this.id = row.getId();
        this.slots = new ArrayList<>();
        for (Slot slot : row.getSlots()){
            this.getSlots().add(new Slot(slot));
        }
    }

    public void addStapel(Stapel stapel){
        stapels.add(stapel);
    }


    @Override
    public String toString() {
        return "\n Row{" +
                "id=" + id +
                ", slots=" + slots +
                ", stapels=" + stapels +
                '}';
    }
}
