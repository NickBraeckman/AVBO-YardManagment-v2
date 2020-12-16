package model;

import com.google.common.graph.*;
import comparator.SortContainersByIncreasingLengthAndIncreasingWeight;
import lombok.Data;
import main.Container;

import java.util.*;

@Data
public class StapelOperations {


    public StapelOperations() {

    }

    public List<Stapel> getStapels(List<Container> containers){

        MutableGraph<Container> graph = buildGraph(containers);
        Set<MutableGraph<Container>> visited = new HashSet<>();
        List<Stapel> stapels = new ArrayList<>();

        for (Container c : graph.nodes()){
            Set<Container> containerSet = Graphs.reachableNodes(graph,c);
            MutableGraph<Container> subGraph = Graphs.inducedSubgraph(graph,containerSet);
            visited.add(subGraph);
        }

        for (MutableGraph<Container> subgraph : visited){
            System.out.println(subgraph);
            List<Container> tempContainerList = new ArrayList<>();
            tempContainerList.addAll(subgraph.nodes());
            stapels.add(new Stapel(tempContainerList));
        }

        return stapels;
    }

    private MutableGraph<Container> buildGraph(List<Container> containers) {
        MutableGraph<Container> graph = GraphBuilder.undirected().allowsSelfLoops(false).build();

        for (int i = 0; i < containers.size(); i++) {
            graph.addNode(containers.get(i));

            for (int j = 0; j < containers.size(); j++) {
                Container container1 = containers.get(i);
                Container container2 = containers.get(j);


                if (!container1.equals(container2)) {
                    if (container1.hasOverlap(container2)) {
                        if (!graph.hasEdgeConnecting(container1, container2)) {
                            container2.setHeight(container1.getHeight() + 1);
                            graph.putEdge(container1, container2);
                            break;
                        }
                    }
                }
            }
        }
        return graph;
    }

    public List<Stapel> removeUpperContainer(Stapel stapel) {
        MutableGraph<Integer> graph = stapel.getGraph();
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
                        tempStapel.setGraph(Graphs.inducedSubgraph(graph,nodes));
                        stapels.add(stapel);
                    }
                }
            }
        }
        return stapels;
    }

    // TODO
    public Graph<Container> buildContainerGraph(List<Container> containers) {
        MutableGraph<Container> graph = GraphBuilder.directed().allowsSelfLoops(false).build();
        return null;
    }

    // TODO
    public List<Stapel> mergeStapel(Stapel stapel1, Stapel stapel2) {
        List<Container> containers = new ArrayList<>();
        containers.addAll(stapel1.getContainerList());
        containers.addAll(stapel2.getContainerList());
        Collections.sort(containers, new SortContainersByIncreasingLengthAndIncreasingWeight());
        return null;
    }

}
