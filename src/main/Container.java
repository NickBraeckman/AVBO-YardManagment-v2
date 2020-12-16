package main;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
public class Container {

    private int id;
    private int Lc, Gc, Wc;
    private int startSlotIndex;
    private int stopSlotIndex;
    private int height;
    private Row row;
    private Coordinate2D center, origin;
    private boolean isUpper;
    private List<Integer> slotIds;

    public Container(int id, int Lc, int Gc) {
        this.id = id;
        this.Lc = Lc;
        this.Gc = Gc;
        this.height = 0;
        this.center = new Coordinate2D(-1, -1);
        this.origin = new Coordinate2D(-1, -1);
        this.isUpper = false;
        this.slotIds = new ArrayList<>();
    }

    public Container(Container container) {
        this.id = container.getId();
        this.Lc = container.getLc();
        this.Gc = container.getGc();
        this.center = new Coordinate2D(container.getCenter());
        this.origin = new Coordinate2D(container.getOrigin());
        this.startSlotIndex = container.getStartSlotIndex();
        this.stopSlotIndex = container.getStopSlotIndex();
        this.isUpper = container.isUpper();
        this.slotIds = new ArrayList<>();
        for (int slotId : container.getSlotIds()) {
            this.slotIds.add(slotId);
        }
        this.row = new Row(container.getRow());
    }

    public boolean hasOverlap(Container c) {
        if ((this.getStartSlotIndex() >= c.getStartSlotIndex() && this.getStopSlotIndex() <= c.getStopSlotIndex())) {
            return true;
        }
        return false;

    }

    public Slot getSlot(int slotId) {
        if (slotIds.contains(slotId)) {
            return row.getSlot(slotId);
        } else {
            return null;
        }
    }

    public void setSlotIds(List<Integer> slotIds) {
        this.slotIds = slotIds;
        if (!slotIds.isEmpty()) {
            this.startSlotIndex = slotIds.get(0);
            this.stopSlotIndex = slotIds.get(slotIds.size() - 1);
        }
    }

    public List<Slot> getSlots() {
        List<Slot> slots = new ArrayList<>();
        slots = row.getSlots(slotIds);
        return slots;
    }

    public void addSlotId(int slotId) {
        slotIds.add(slotId);
    }

    @Override
    public String toString() {
        return String.format("C%d: [%d, %d], weight: %d, height: %d", getId(), getStartSlotIndex(), getStopSlotIndex(), getGc(), getHeight());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Container container = (Container) o;
        return id == container.id && Lc == container.Lc && Gc == container.Gc && Wc == container.Wc && startSlotIndex == container.startSlotIndex && stopSlotIndex == container.stopSlotIndex && height == container.height && isUpper == container.isUpper && Objects.equals(row, container.row) && Objects.equals(center, container.center) && Objects.equals(origin, container.origin) && Objects.equals(slotIds, container.slotIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, Lc, Gc, Wc, startSlotIndex, stopSlotIndex, height);
    }
}
