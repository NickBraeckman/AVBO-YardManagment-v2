package model;

import com.google.common.graph.Graph;
import com.google.common.graph.GraphBuilder;
import comparator.SortContainerByDecreasingHeight;
import lombok.Data;
import main.Container;

import java.util.Collections;
import java.util.List;

@Data
public class Stapel {

    private int id;
    private List<Container> containerList;
    private Graph<Container> containerGraph;
    private Container upperContainer;

    public Stapel(int id, List<Container> containers) {
        this.id = id;
        this.containerList = containers;
        Collections.sort(this.containerList, new SortContainerByDecreasingHeight());
        containerGraph = GraphBuilder.undirected().allowsSelfLoops(false).build();
        this.upperContainer = containers.get(0);
    }

    @Override
    public String toString() {
        return "\n Stapel{" +
                "containerList=" + containerList +
                ", containerGraph=" + containerGraph +
                ", upperContainer=" + upperContainer +
                '}';
    }
}
