package model;

import lombok.Data;
import main.Container;

@Data
public class Node {

    public int id;
    public boolean visited;

    Node(int id){
        this.id = id;
        this.visited = false;
    }

    public void visit(){
        this.visited = true;
    }

    public void unvisit(){
        this.visited = false;
    }

}
