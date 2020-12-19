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


    public StapelOperations() {

    }

    public List<Stapel> getStapels(List<Container> containers) {

        MutableGraph<Container> graph = buildGraph(containers);
        Set<Container> visited = new HashSet<>();
        MutableGraph<Container> subGraph;
        List<Stapel> stapels = new ArrayList<>();

        /*for (Container c : graph.nodes()) {
            Set<Container> containerSet = Graphs.reachableNodes(graph, c);
            MutableGraph<Container> subGraph = Graphs.inducedSubgraph(graph, containerSet);
            visited.add(subGraph);
        }

        for (MutableGraph<Container> subgraph : visited) {
            List<Container> tempContainerList = new ArrayList<>();
            tempContainerList.addAll(subgraph.nodes());
            Stapel stapel = new Stapel(tempContainerList);
            stapel.setGraph(subgraph);
            stapels.add(stapel);
            System.out.println(stapel);
        }*/

        return stapels;
    }

    private void dfs(Container c, MutableGraph<Container> graph) {
        if (graph.predecessors(c).isEmpty()) {
            System.out.println(c);
            return;
        } else {
            System.out.println(c);
            for (Container pred : graph.predecessors(c)) {
                dfs(pred, graph);
            }
        }
    }

    private MutableGraph<Container> buildGraph(List<Container> containers) {
        MutableGraph<Container> graph = GraphBuilder.directed().allowsSelfLoops(false).build();
        Set<Container> visited = new HashSet<>();
        for (int i = 0; i < containers.size(); i++) {
            graph.addNode(containers.get(i));

            for (int j = 0; j < containers.size(); j++) {
                Container container1 = containers.get(i);
                Container container2 = containers.get(j);

                if (!container1.equals(container2)) {
                    if (container1.hasOverlap(container2) && !visited.contains(container2)) {
                        graph.putEdge(container1, container2);
                        visited.add(container1);
                        break;
                    } else if (container2.hasOverlap(container1) && !visited.contains(container2)) {
                        graph.putEdge(container2, container1);
                        visited.add(container1);
                        break;
                    }
                }
            }
        }
        return graph;
    }

    public List<Stapel> removeUpperContainer(Stapel stapel) {
        MutableGraph<Container> graph = stapel.getGraph();
        Container upperContainer = stapel.getUpperContainer();

        List<Stapel> stapels = new ArrayList<>();
        List<Container> tempContainers;

        Set<Container> predecessors = graph.predecessors(upperContainer);

        graph.removeNode(upperContainer);
        stapel.getContainerList().remove(0);

        if (predecessors.size() > 1) {

            for (Container predecessor : predecessors) {

                Set<Container> nodes = Graphs.reachableNodes(graph, predecessor);

                for (Container node : nodes) {
                    tempContainers = new ArrayList<>();

                    for (Container container : stapel.getContainerList()) {

                        if (container.getId() == node.getId()) {
                            tempContainers.add(container);
                        }
                    }

                    if (!tempContainers.isEmpty()) {
                        Stapel tempStapel = new Stapel(tempContainers);
                        tempStapel.setGraph(Graphs.inducedSubgraph(graph, nodes));
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
