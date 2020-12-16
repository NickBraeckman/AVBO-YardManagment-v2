package model;

import com.google.common.graph.GraphBuilder;
import com.google.common.graph.Graphs;
import com.google.common.graph.MutableGraph;
import comparator.SortContainerByDecreasingHeight;
import lombok.Data;
import main.Container;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Data
public class Stapel {

    private List<Container> containerList;
    private MutableGraph<Container> graph;
    private Container upperContainer;

    public Stapel(List<Container> containers) {
        this.containerList = containers;
        Collections.sort(this.containerList, new SortContainerByDecreasingHeight());
        graph = GraphBuilder.directed().allowsSelfLoops(false).build();
        this.upperContainer = containers.get(0);
    }

    public Stapel(Stapel stapel) {
        this.containerList = new ArrayList<>();
        for (Container container : stapel.getContainerList()) {
            containerList.add(new Container(container));
        }
        Collections.sort(this.containerList, new SortContainerByDecreasingHeight());
        this.upperContainer = new Container(stapel.getUpperContainer());
        this.graph = Graphs.copyOf(stapel.getGraph());
    }

    public boolean addContainer(Container container) {
        if (container.getLc() == upperContainer.getLc()) {
          //  container.setHeight(upperContainer.getHeight() + 1);
            containerList.add(container);
            graph.putEdge(upperContainer, container);
            return true;
        }
        return false;
    }

    public boolean checkUpperContainerConstraint() {
        if (upperContainer.getGc() == 1) {
            return false;
        }
        return true;
    }

    public boolean checkHeightConstraint(int height) {
        if (upperContainer.getHeight() > height) {
            return false;
        }
        return true;
    }

    public boolean checkWeightOrderConstraint() {
        for (Container c : containerList) {
            if (c.getHeight() > 1) {
                Set<Container> containers = graph.predecessors(c);
                for (Container predecessor : containers) {
                    if (predecessor.getGc() > c.getGc()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "\n Stapel{" +
                "containerList=" + containerList +
                ", containerGraph=" + graph +
                ", upperContainer=" + upperContainer +
                '}';
    }
}
