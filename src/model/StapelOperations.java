package model;

import com.google.common.graph.Graph;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.Graphs;
import com.google.common.graph.MutableGraph;
import comparator.SortContainersByIncreasingLengthAndIncreasingWeight;
import lombok.Data;
import main.Container;

import java.util.*;

@Data
public class StapelOperations {

    private List<Stapel> stapels;

    public StapelOperations(List<Container> containers) {
        buildAllStapels(containers);
    }

    public StapelOperations() {
    }

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
                            System.out.println(container1 + " " + container2);
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
        List<Stapel> containerStapelList = new ArrayList<>();

        // implementeer een graaf-doorloop methode (niet-recursief) die de intervalGraph splitst in een lijst van
        // (niet-gerichte) sub-grafen, waartussen er geen onderlinge verbindingen zijn.
        MutableGraph<Integer> subGraph;
        List<Container> containerList;
        Container currentContainer;
        Set<Container> visited = new HashSet<>();

        // Stack van nodes om kinderen van huidige node bij te houden
        Stack<Container> stack = new Stack<>();

        for (Container container : graph.nodes()) {
            if (!visited.contains(container)) {

                subGraph = GraphBuilder.directed().allowsSelfLoops(false).build();
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
                        subGraph.addNode(currentContainer.getId());
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
                            subGraph.putEdge(currentContainer.getId(), c.getId());
                        }
                    }
                }
                if (!subGraph.nodes().isEmpty()) {
                    Stapel stapel = new Stapel(containerList);
                    stapel.setContainerGraph(subGraph);
                    containerStapelList.add(stapel);
                    System.out.println(stapel);
                }
            }
        }
        return containerStapelList;
    }

    public List<Stapel> removeUpperContainer(Stapel stapel) {
        MutableGraph<Integer> graph = stapel.getContainerGraph();
        Container upperContainer = stapel.getUpperContainer();
        List<Stapel> stapels = new ArrayList<>();
        List<Container> tempContainers;

        Set<Integer> ids = graph.successors(upperContainer.getId());

        graph.removeNode(upperContainer.getId());
        stapel.getContainerList().remove(0);

        if (ids.size() > 1) {
            for (int id : ids) {
                Set<Integer> nodes = Graphs.reachableNodes(graph, id);
                for (int node : nodes) {
                    tempContainers = new ArrayList<>();

                    for (Container container : stapel.getContainerList()) {
                        if (container.getId() == node) {
                            tempContainers.add(container);
                        }
                    }

                    if (!tempContainers.isEmpty()) {
                        Stapel tempStapel = new Stapel(tempContainers);
                        tempStapel.setContainerGraph(Graphs.inducedSubgraph(graph,nodes));
                        stapels.add(stapel);
                    }
                }
            }
        }
        return stapels;
    }

    public Graph<Container> buildContainerGraph(List<Container> containers) {
        MutableGraph<Container> graph = GraphBuilder.directed().allowsSelfLoops(false).build();
        return null;
    }

    public List<Stapel> mergeStapel(Stapel stapel1, Stapel stapel2) {
        List<Container> containers = new ArrayList<>();
        containers.addAll(stapel1.getContainerList());
        containers.addAll(stapel2.getContainerList());
        Collections.sort(containers, new SortContainersByIncreasingLengthAndIncreasingWeight());
        return null;
    }

}
