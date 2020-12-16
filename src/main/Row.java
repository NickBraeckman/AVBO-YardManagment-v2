package main;


import lombok.Data;
import model.StapelOperations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class Row {

    private int id;
    private Map<Integer, Slot> slots;
    private int firstSlotId;

    public Row(int id) {
        this.id = id;
        this.slots = new HashMap<>();
    }

    public Row(Row row) {
        this.id = row.getId();
        this.slots = new HashMap<>();
        for (Slot slot : row.getSlots().values()) {
            this.getSlots().put(slot.getId(), new Slot(slot));
        }
    }

    public void addSlot(Slot slot) {
        slots.put(slot.getId(), slot);
    }

    public void popContainer(Container container) {
        List<Integer> slotIds = container.getSlotIds();
        for (int slotId : slotIds) {
            slots.get(slotId).popContainer(container);
        }
        container.setSlotIds(new ArrayList<>());
    }

    public void pushContainer(Container container, List<Integer> slotIds) {
        for (int slotId : slotIds) {
            slots.get(slotId).pushContainer(container);
        }
        container.setSlotIds(new ArrayList<>(slotIds));
    }

    public Slot getSlot(int slotId) {
        return slots.get(slotId);
    }

    public List<Slot> getSlots(List<Integer> slotIds) {
        List<Slot> tempSlots = new ArrayList<>();
        slots.forEach((key, value) -> {
            if (slotIds.contains(key)) {
                tempSlots.add(value);
            }
        });
        return tempSlots;
    }

    public List<Slot> getSlots(Container container){
        List<Slot> tempSlots = new ArrayList<>();
        slots.forEach((key, value) -> {
            if (container.getSlotIds().contains(key)) {
                tempSlots.add(value);
            }
        });
        return tempSlots;
    }

    @Override
    public String toString() {
        return "\n---Row{" +
                "id=" + id +
                ", slots=" + slots +
                '}';
    }

    public String getSlotsToString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (Slot s : slots.values()) {
            sb.append(s.getId()).append(",");
        }
        int l = sb.length();
        sb.delete(l - 1, l);
        sb.append(")");
        return sb.toString();
    }

    public int getFirstSlotId() {
       return firstSlotId;
    }
}
