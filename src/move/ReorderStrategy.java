package move;

import comparator.SortContainerByIncreasingHeight;
import comparator.SortContainerByIncreasingWeight;
import main.*;

import java.util.*;

public class ReorderStrategy implements Strategy {

    private List<Container> tempContainerQueue;
    private Stack<Container> lifoContainerStack;
    private boolean isSafe = false;
    private CraneSchedule craneSchedule;
    private List<Row> rows;
    private List<Container> containers;

    public ReorderStrategy() {
        this.lifoContainerStack = new Stack<>();
        this.tempContainerQueue = new ArrayList<>();
        this.rows = new ArrayList<>();

    }

    @Override
    public Yard reorderYard(Yard yard) {
        this.rows = yard.getRowList();
        this.craneSchedule = yard.getCraneSchedule();
        this.containers = yard.getContainers();
        Container containerToMove;

        while (!isSafe)
            if (lifoContainerStack.isEmpty()) {
                containerToMove = searchYardForContainerToMove();

                if (containerToMove == null) {
                    isSafe = true;
                }

                if (!lifoContainerStack.contains(containerToMove)) {
                    lifoContainerStack.push(containerToMove);
                }
            } else {
                containerToMove = lifoContainerStack.pop();
                doOperation(containerToMove);
            }
        craneSchedule.printTimeLine();
        return yard;
    }

    private Container searchYardForContainerToMove() {

        for (Row row : rows) {
            for (Slot slot : row.getSlots().values()) {
                Container containerToMove = null;
                if (!checkSafetyConstraints(slot)) {
                    containerToMove = slot.getUpperContainer();
                }

                if (containerToMove != null) {
                    return containerToMove;
                }
            }
        }
        return null;
    }

    public boolean checkSafetyConstraints(Slot slot) {

        if (!slot.checkHeightConstraint(Yard.H_SAFE)) {
            return false;
        }
        if (!slot.checkUpperContainerConstraint()) {
            return false;
        }
        if (!slot.checkWeightOrderConstraint()) {
            return false;
        }
        return true;
    }

    public boolean checkSafetyConstraints(Container container) {
        List<Slot> slots = container.getSlots();
        for (Slot slot : slots) {
            if (!checkSafetyConstraints(slot)) {
                return false;
            }
        }
        return true;
    }

    public boolean checkPhysicalConstraints(Container container, Container topContainer) {
        if (topContainer.getLc() != container.getLc()) {
            return false;
        }

        if (topContainer.getGc() <= container.getGc()) {
            return false;
        }

        if (topContainer.getHeight() + 1 > Yard.H_MAX) {
            return false;
        }
        return true;
    }

    public void doOperation(Container container) {
        if (container.getGc() == 1) {
            if (container.isUpper()) {
                if (containsHeavierContainer(container)) {
                    insertContainer(container);
                } else {
                    placeContainerOnTop(container);
                }
            } else {
                insertContainer(container);
            }
        } else {
            insertContainer(container);
        }
    }

    public void insertContainer(Container unsafeContainer) {

        Set<Container> containerSet = new HashSet<>();
        Set<Integer> curSlots = new HashSet<>();
        Set<Container> allContainersSet = new HashSet<>();
        int rowIndex = unsafeContainer.getRow().getId() - 1;
        List<Slot> previousSlots = rows.get(rowIndex).getSlots(unsafeContainer.getSlotIds());

        for (Slot slot : previousSlots) {
            if (slot.getContainerStack().size() > 0) {
                for (int i = slot.getContainerStack().size() - 1; i >= 0; i--) {
                    Container container = slot.getContainerStack().get(i);
                    if (!tempContainerQueue.contains(container)) {
                        allContainersSet.add(container);
                        if (slot.getContainerStack().get(i).getLc() == unsafeContainer.getLc()) {
                            containerSet.add(slot.getContainerStack().get(i));
                        }
                    }
                }
            }

        }

        if (containerSet.size() > 1) {
            tempContainerQueue.addAll(containerSet);
        } else {
            tempContainerQueue.addAll(allContainersSet);
        }

        tempContainerQueue.sort(new SortContainerByIncreasingHeight());
        for (Container container : tempContainerQueue) {
            if (container.isUpper()) {
                if (!placeTemporary(container, curSlots)) {
                    if (!lifoContainerStack.contains(container)) {
                        lifoContainerStack.add(container);
                    }
                    return;
                } else {
                    curSlots.addAll(container.getSlotIds());
                }
            }
        }

        tempContainerQueue.sort(new SortContainerByIncreasingWeight());
        rebuildStack(tempContainerQueue, previousSlots);
    }

    private boolean placeTemporary(Container container, Set<Integer> curSlots) {
        List<Slot> slotList;
        List<Slot> containerSlots = container.getSlots();
        int counter = 0;

        for (Row row : rows) {
            slotList = new ArrayList<>();
            for (Slot slot : row.getSlots().values()) {

                // place not on the same slots
                if (!containerSlots.contains(slot)) {

                    // slot is empty
                    if (slot.getContainerStack().isEmpty() && !curSlots.contains(slot.getId())) {
                        slotList.add(slot);
                        counter++;

                        // upper container is not on same slots of parent container of previous place
                        // upper container from slot has same length and Hmax is not exceeded and is ordered on weight
                    } else if (!curSlots.contains(slot.getId()) && slot.getUpperContainer().getLc() == container.getLc() && slot.getContainerStack().size() + 1 <= Yard.H_MAX && slot.getUpperContainer().getGc() <= container.getGc()) {

                        counter = 0;
                        slotList = new ArrayList<>();
                        Container tempContainer = slot.getContainerStack().peek();
                        List<Slot> tempSlots;
                        tempSlots = tempContainer.getSlots();
                        slotList.addAll(tempSlots);
                        if (placeContainer(container, slotList)) {
                            return true;
                        }

                    } else {
                        counter = 0;
                        slotList = new ArrayList<>();
                    }

                    // found empty slots that have a matching length
                    if (counter == (container.getLc() / Yard.L_S)) {
                        placeContainer(container, slotList);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void rebuildStack(List<Container> tempContainerQueue, List<Slot> previousSlots) {
        List<Slot> possibleSlots;
        List<Slot> possibleEmptySlots;
        List<Container> placedContainers = new ArrayList<>();

        for (Container container : tempContainerQueue) {

            if (container.isUpper()) {
                int counter = 0;
                int emptyCounter = 0;
                possibleSlots = new ArrayList<>();
                possibleEmptySlots = new ArrayList<>();

                for (Slot slot : previousSlots) {

                    if (slot.getContainerStack().size() + 1 <= Yard.H_SAFE) {

                        if (slot.getContainerStack().isEmpty()) {
                            emptyCounter++;
                            possibleEmptySlots.add(slot);

                        } else if (slot.getContainerStack().peek().getLc() == container.getLc() && slot.getContainerStack().peek().getGc() <= container.getGc()) {
                            counter = 0;
                            possibleSlots = new ArrayList<>();
                            Container tempContainer = slot.getUpperContainer();
                            List<Slot> tempSlots = tempContainer.getSlots();
                            if (placeContainer(container, tempSlots)) {
                                placedContainers.add(container);
                                break;
                            }

                        } else if (slot.getContainerStack().peek().getGc() <= container.getGc() && container.getLc() > slot.getContainerStack().peek().getLc()) {
                            counter++;
                            possibleSlots.add(slot);
                        } else {
                            counter = 0;
                            emptyCounter = 0;
                            possibleSlots = new ArrayList<>();
                            possibleEmptySlots = new ArrayList<>();
                        }
                    }
                    if (counter == (container.getLc() / Yard.L_S)) {
                        if (placeContainer(container, possibleSlots)) {
                            placedContainers.add(container);
                            break;
                        }
                    }

                    if (emptyCounter == (container.getLc() / Yard.L_S)) {
                        if (placeContainer(container, possibleEmptySlots)) {
                            placedContainers.add(container);
                            break;
                        }
                    }
                }
            }
        }
        for (Container placedContainer : placedContainers) {
            tempContainerQueue.removeIf(container -> container.getId() == placedContainer.getId());
        }
        for (Container tempContainer : tempContainerQueue) {
            if (checkSafetyConstraints(tempContainer)) {
                lifoContainerStack.removeIf(c -> c.getId() == tempContainer.getId());
                if (!lifoContainerStack.contains(tempContainer)) {
                    lifoContainerStack.add(tempContainer);
                }
            }
        }
        tempContainerQueue = new ArrayList<>();
    }


    public void placeContainerOnTop(Container unsafeContainer) {
        if (unsafeContainer.getHeight() == Yard.H_MAX) {
            placeTemporary(unsafeContainer, new HashSet<>(unsafeContainer.getSlotIds()));
        }
        List<Container> containers = new ArrayList<>();
        containers.add(unsafeContainer);
        placeContainerOnTopHelper(containers, unsafeContainer);
    }

    private boolean placeContainerOnTopHelper(List<Container> connectingContainers, Container unsafeContainer) {

        if (unsafeContainer.getSlotIds().isEmpty()) {
            System.out.println(unsafeContainer);
        }
        List<Integer> slotIds = unsafeContainer.getSlotIds();
        int firstSlotId = slotIds.get(0);
        int lastSlotId = slotIds.get(slotIds.size() - 1);
        Row row = unsafeContainer.getRow();

        if (isPlaced(connectingContainers)) {
            return true;
        }

        if (firstSlotId != row.getFirstSlotId()) {
            Container prevContainer = row.getSlots().get(firstSlotId - 1).getUpperContainer();
            if (getNeighborContainers(connectingContainers, unsafeContainer, prevContainer)) {
                return true;
            }
        }
        if (lastSlotId != row.getFirstSlotId() + row.getSlots().size() - 1) {
            Container nextContainer = row.getSlots().get(lastSlotId + 1).getUpperContainer();
            return getNeighborContainers(connectingContainers, unsafeContainer, nextContainer);
        }

        return false;
    }

    private boolean getNeighborContainers(List<Container> connectingContainers, Container unsafeContainer, Container connectingContainer) {
        if (!connectingContainers.contains(connectingContainer)) {
            if (connectingContainer.getId() != unsafeContainer.getId() && connectingContainer.getLc() == unsafeContainer.getLc() && connectingContainer.getHeight() == unsafeContainer.getHeight() && connectingContainer.getGc() <= unsafeContainer.getGc()) {
                if (isPlaced(connectingContainers)) {
                    return true;
                }
                connectingContainers.add(connectingContainer);
                placeContainerOnTopHelper(connectingContainers, connectingContainer);
            }
        }
        return false;
    }

    public boolean containsHeavierContainer(Container unsafeContainer) {
        List<Slot> slots = unsafeContainer.getSlots();
        for (Container c : slots.get(0).getContainerStack()) {
            if (c.getLc() == unsafeContainer.getLc() && c.getGc() > unsafeContainer.getGc()) {
                return true;
            }
        }
        return false;

    }

    private boolean isPlaced(List<Container> containers) {
        int length = 0;
        int weight = 0;
        for (Container c : containers) {
            length += c.getLc();
            weight = Math.max(weight, c.getGc());
        }
        Container container = getCounterWeightContainer(containers, length, weight);
        if (container != null) {
            List<Slot> slots = new ArrayList<>();
            for (Container c : containers) {
                for (Slot slot : c.getSlots()) {
                    if (!slots.contains(slot)) {
                        slots.add(slot);
                    }
                }
            }
            placeContainer(container, slots);
            return true;
        }
        return false;
    }

    private Container getCounterWeightContainer(List<Container> containers, int Lc, int Gc) {

        for (Container container : this.containers) {
            if (container.isUpper() && !containers.contains(container)) {
                if (!containers.contains(container) && container.getGc() > 1 && container.getGc() >= Gc && container.getLc() == Lc) {
                    for (Slot slot : container.getSlots()) {
                        if (slot.getSecondLastUpperContainer() != null) {
                            if (!(slot.getSecondLastUpperContainer().getGc() == 1)) {
                                return slot.getUpperContainer();
                            }
                        } else {
                            return slot.getUpperContainer();
                        }
                    }
                }
            }
        }
        return null;
    }

    public Row getRow(Slot slot) {
        for (Row row : rows) {
            if (row.getSlot(slot.getId()) != null) {
                return row;
            }
        }
        return null;
    }

    public boolean placeContainer(Container container, List<Slot> slots) {

        Coordinate2D newCenter = new Coordinate2D(slots.get(0).getCoordinate());
        int x = newCenter.getX();
        int y = newCenter.getY();
        x += container.getLc() / 2;
        y += Yard.W_S / 2;
        newCenter.setX(x);
        newCenter.setY(y);

        Random random = new Random();
        int i = random.nextInt();
        int q = 0;
        if (i < 0.5) {
            q = 1;
        } else {
            q = 2;
        }
        if (craneSchedule.canMove(container, newCenter, q)) {
            applyChangesToContainer(container, slots);
            //System.out.println("t:" + CraneSchedule.time + " c:" + container);
            return true;
        }

        return false;
    }


    private void applyChangesToContainer(Container container, List<Slot> place) {
        Row oldRow = container.getRow();
        oldRow.popContainer(container);

        List<Integer> slotIds = new ArrayList<>();
        for (Slot slot : place) {
            slotIds.add(slot.getId());
        }

        Row newRow = getRow(place.get(0));

        newRow.pushContainer(container, slotIds);
        container.setRow(newRow);
    }
}
