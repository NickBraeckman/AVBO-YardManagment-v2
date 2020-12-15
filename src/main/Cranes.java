package main;

import lombok.Data;
import move.CraneMovement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

@Data
public class Cranes implements Iterable<Crane> {
    private List<Crane> cranes;

    public Cranes(){
        this.cranes = new ArrayList<>();
    }

    @Override
    public Iterator<Crane> iterator() {
        return null;
    }

    @Override
    public void forEach(Consumer<? super Crane> action) {

    }

    @Override
    public Spliterator<Crane> spliterator() {
        return null;
    }

    /**
     *
     * @param c
     * @param coordinate
     * @return true if move could be planned -> state yard changes
     */
    public boolean canMove(Container c, Coordinate2D coordinate){
        return true;
    }

    public void addCrane(Crane crane){
        cranes.add(crane);
    }
}
