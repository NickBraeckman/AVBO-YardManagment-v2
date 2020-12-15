package main;

import lombok.Data;
import move.CraneMovement;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

@Data
public class Cranes implements Iterable<Crane> {
    private List<Crane> cranes;

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
}
