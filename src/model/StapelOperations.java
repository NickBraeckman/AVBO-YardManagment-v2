package model;

import com.google.common.graph.Graph;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import lombok.Data;
import main.Container;

import java.util.*;

@Data
public class StapelOperations {

    private List<Stapel> stapels;

    public StapelOperations(List<Container> containers) {
        buildAllStapels(containers);
    }

    public StapelOperations(){}

    public List<Stapel> getStapels() {
        return stapels;
    }


    // enkel voor main
    private void buildAllStapels(List<Container> containers) {
        MutableGraph<Container> containerStack = GraphBuilder.undirected().allowsSelfLoops(false).build();
        for (int i = 0; i < containers.size(); i++) {
            containerStack.addNode(containers.get(i));

            for (int j = 0; j < containers.size(); j++) {
                Container container1 = containers.get(i);
                Container container2 = containers.get(j);


                if (!container1.equals(container2)) {
                    if (container1.hasOverlap(container2)) {
                        if (!containerStack.hasEdgeConnecting(container1, container2)) {
                            container2.setHeight(container1.getHeight() + 1);
                            containerStack.putEdge(container1, container2);
                            break;
                        }
                    }
                }
            }
        }
        this.stapels = buildAllStapelsHelper(containerStack);
    }

    // disconnected component graph to list of connected subgraphs
    private List<Stapel> buildAllStapelsHelper(Graph<Container> graph) {

        int teller = 0;
        List<Stapel> containerStapelList = new ArrayList<>();

        // implementeer een graaf-doorloop methode (niet-recursief) die de intervalGraph splitst in een lijst van
        // (niet-gerichte) sub-grafen, waartussen er geen onderlinge verbindingen zijn.
        MutableGraph<Container> subGraph;
        List<Container> containerList;
        Container currentContainer;
        Set<Container> visited = new HashSet<>();

        // Stack van nodes om kinderen van huidige node bij te houden
        Stack<Container> stack = new Stack<>();

        for (Container container : graph.nodes()) {
            teller++;
            if (!visited.contains(container)) {

                subGraph = GraphBuilder.undirected().allowsSelfLoops(false).build();
                containerList = new ArrayList<>();
                stack.push(container);

                while (!stack.isEmpty()) {

                    currentContainer = stack.pop();
                    Set<Container> successors = graph.successors(currentContainer);

                    if (visited.contains(currentContainer)) {
                        continue;
                    }

                    visited.add(currentContainer);

                    if (successors.isEmpty()) {
                        subGraph.addNode(currentContainer);
                        containerList.add(currentContainer);
                        continue;
                    }

                    for (Container c : successors) {
                        if (!visited.contains(c)) {
                            stack.push(c);
                            if (!containerList.contains(c)) {
                                containerList.add(c);
                            }
                            if (!containerList.contains(currentContainer)) {
                                containerList.add(currentContainer);
                            }
                            subGraph.putEdge(currentContainer, c);
                        }
                    }
                }
                if (!subGraph.nodes().isEmpty()) {
                    Stapel stapel = new Stapel(teller,containerList);
                    stapel.setContainerGraph(subGraph);
                    containerStapelList.add(stapel);
                    System.out.println(stapel);
                }
            }
        }
        return containerStapelList;
    }

    public List<Stapel> removeUpperContainer(Stapel stapel){
        MutableGraph<Container> graph = stapel.getContainerGraph();
        Container upperContainer = stapel.getUpperContainer();
        List<Stapel> stapels = new ArrayList<>();

        graph.removeNode(upperContainer);
        stapel.getContainerList().remove(0);

        stapels = buildAllStapelsHelper(graph);
        return stapels;
    }

}
