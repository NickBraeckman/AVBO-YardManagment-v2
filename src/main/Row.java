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
    private Map<Integer,Slot> slots;
    List<Stapel> stapels = new ArrayList();


    public Row(int id){
        this.id = id;
        this.slots = new HashMap<>();
        this.stapels = new ArrayList<>();
    }

    public Row(Row row){
        this.id = row.getId();
        this.slots = new HashMap<>();
        for (Slot slot : row.getSlots().values()){
            this.getSlots().put(slot.getId(),new Slot(slot));
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
