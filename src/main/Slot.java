package main;

import lombok.Data;

import java.util.Stack;

@Data
public class Slot {

    private int id;
    private Coordinate2D coordinate;
    private Coordinate2D center;
    private Stack<Container> containerStack;
    private boolean isSafe;

    public Slot(int id, int x, int y) {
        this.id = id;
        this.coordinate = new Coordinate2D(x, y);
        this.center = new Coordinate2D(x + Yard.L_S / 2, y + Yard.W_S / 2);
        this.containerStack = new Stack<>();
        this.isSafe = true;
    }

    public Slot(Slot slot) {
        this.id = slot.getId();
        this.center = new Coordinate2D(slot.getCenter());
        this.coordinate = new Coordinate2D(slot.getCoordinate());
        this.containerStack = new Stack<>();
        for (Container container : slot.getContainerStack()) {
            this.containerStack.push(new Container(container));
        }
    }

    public void pushContainer(Container container) {
        if (!containerStack.isEmpty()) {
            containerStack.peek().setUpper(false);
        }
        container.setUpper(true);
        container.setHeight(containerStack.size() + 1);
        containerStack.push(container);
    }

    public Container popContainer(Container container) {
        container.setUpper(false);
        container.setHeight(container.getHeight() - 1);
        if (containerStack.peek().getId() == container.getId()) {
            containerStack.pop();
        } else {
            return null;
        }
        if (!containerStack.isEmpty()) {
            containerStack.peek().setUpper(true);
        }
        return container;
    }

    public boolean checkHeightConstraint(int height) {
        if (containerStack.size() > height) {
            return false;
        }
        return true;
    }

    public boolean checkUpperContainerConstraint() {
        if (!containerStack.isEmpty()) {
            if (containerStack.peek().getGc() == 1) {
                return false;
            }
        }
        return true;
    }

    public boolean checkWeightOrderConstraint() {
        if (containerStack.size() >= 2) {
            for (int i = 0; i < containerStack.size() - 1; i++) {
                Container currentContainer = containerStack.get(i);
                Container nextContainer = containerStack.get(i + 1);

                if (currentContainer.getGc() > nextContainer.getGc()) {
                    return false;
                }
            }
        }
        return true;
    }

    public Container getUpperContainer() {
        if (!containerStack.isEmpty()) {
            return containerStack.peek();
        }
        return null;
    }

    public Container getSecondLastUpperContainer() {
        if (containerStack.size() >= 2) {
            return containerStack.get(containerStack.size() - 2);
        } else return null;
    }

    @Override
    public String toString() {
        return "S" + id + " " + coordinate + ", center:" + center;
    }

}
