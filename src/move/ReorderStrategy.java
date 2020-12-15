package move;

import comparator.SortContainerByIncreasingHeight;
import comparator.SortContainersByIncreasingLengthAndIncreasingWeight;
import main.Container;
import main.Row;
import main.Yard;
import model.Stapel;
import model.StapelOperations;

import java.util.ArrayList;
import java.util.List;

public class ReorderStrategy implements Strategy {

    private StapelOperations operations;
    private List<Stapel> resolutionPool = new ArrayList<>();
    private List<Stapel> feasiblePool = new ArrayList<>();
    private List<Row> rows;

    public ReorderStrategy() {
        this.operations = new StapelOperations();
    }

    @Override
    public Yard reorderYard(Yard yard) {

        for (Row row : yard.getRowList()) {
            for (Stapel stapel : row.getStapels()) {
                if (!checkSafetyConstraints(stapel)) {
                    resolutionPool.add(new Stapel(stapel));
                }
                feasiblePool.add(new Stapel(stapel));
            }
        }
        System.out.println(resolutionPool);
        return null;
    }

    public boolean checkSafetyConstraints(Stapel stapel) {

        if (!stapel.checkHeightConstraint(Yard.H_SAFE)) {
            return false;
        }
        if (!stapel.checkUpperContainerConstraint()) {
            return false;
        }
        if (!stapel.checkWeightOrderConstraint()) {
            return false;
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

    public void doOperation(Stapel stapel) {

        if (stapel.getUpperContainer().getGc() == 1) {
            if (stapel.checkWeightOrderConstraint()) {
                insertContainer(stapel);
            } else {
                placeContainerOnTop(stapel);
            }
        } else {
            insertContainer(stapel);
        }

    }

    public void insertContainer(Stapel stapel) {
        stapel.getContainerList().sort(new SortContainerByIncreasingHeight());
        List<Integer> slotIds = stapel.getUpperContainer().getSlotIds();

        for (Container container : stapel.getContainerList()) {
            placeTemporary(container);
        }
        stapel.getContainerList().sort(new SortContainersByIncreasingLengthAndIncreasingWeight());
        rebuildStapel(stapel, slotIds);
    }

    public void placeContainerOnTop(Stapel stapel) {

    }

    public void placeTemporary(Container container) {
        List<Integer> slotIds = new ArrayList<>();
        List<Integer> tempSlotIds;
        int counter;

        // put all slots with containers on it in list
        for (Row row : rows) {
            for (Stapel stapel : row.getStapels()) {
                slotIds.addAll(stapel.getUpperContainer().getSlotIds());
            }
        }

        // first try to move container on empty place in neighborhood
        Row curRow = getRow(container.getSlotIds().get(0));
        List<Integer> place = new ArrayList<>(curRow.getSlots().keySet());
        place.removeAll(slotIds);

        int teller = 0;
        placeContainerOnEmptySlots(container, teller, place);

        // try to place container on the other rows
        List<Row> tempRows = new ArrayList<>(rows);
        tempRows.remove(curRow);
        for (Row tempRow : tempRows) {
            place = new ArrayList<>(tempRow.getSlots().keySet());
            place.removeAll(slotIds);

            teller = 0;
            placeContainerOnEmptySlots(container, teller, place);
        }

        // try to place container on top of another container
        for (Row row : rows) {
            for (Stapel stapel : row.getStapels()) {
                if (checkPhysicalConstraints(container, stapel.getUpperContainer())) {
                    tempSlotIds = new ArrayList<>(stapel.getUpperContainer().getSlotIds());
                    placeContainer(container, tempSlotIds);
                    return;
                }
            }
        }

    }

    private void placeContainerOnEmptySlots(Container container, int teller, List<Integer> place) {
        List<Integer> tempSlotIds;
        tempSlotIds = new ArrayList<>();

        for (int i = 0; i < place.size() - 1; i++) {
            if (place.get(i) == place.get(i + 1)) {
                teller++;
                tempSlotIds.add(place.get(i));
            } else {
                teller = 0;
                tempSlotIds = new ArrayList<>();
            }
            if (teller == container.getLc()) {
                placeContainer(container, tempSlotIds);
            }
        }
    }

    public void rebuildStapel(Stapel stapel, List<Integer> slotIds) {

    }

    public void placeContainer(Container container, List<Integer> slotIds) {

    }

    public Row getRow(int slotId) {
        int id = (int) Math.floor((slotId) / (Yard.L / Yard.L_S));

        // extra check last slotId
        if (id == rows.size()) {
            id = id - 1;
        }

        return rows.get(id);
    }

    /*public boolean reorderHelper(List<Stapel> resolutionPool, List<Stapel> feasiblePool, int row, int slot, int sIndex) {

        Stapel stapel = resolutionPool.get(sIndex);

        if (sIndex == resolutionPool.size() - 1) {
            reorderHelper(resolutionPool, feasiblePool, row, slot, 0);
        }

        if (row == rows.size() - 1) {
            return false;
        }

        if (slot == rows.get(slot).getSlots().size() - 1) {
            reorderHelper(resolutionPool, feasiblePool, row + 1, 0, sIndex);
        }

        if (checkSafetyConstraints(resolutionPool.get(sIndex))) {
            resolutionPool.remove(stapel);
            feasiblePool.add(stapel);
            if (resolutionPool.isEmpty()) {
                return true;
            }
            //TODO returns false?
            reorderHelper(resolutionPool, feasiblePool, row, slot, sIndex);
        }

        if (stapel.getUpperContainer().getGc() == 1) {

            if (stapel.getContainerList().size() == 1) {
                searchContainerInFeasiblePool(resolutionPool, feasiblePool, row, slot, sIndex, stapel);
            }

            int teller = 0;
            for (Container c : stapel.getContainerList()) {

                if (c.getLc() == stapel.getUpperContainer().getLc() && c.getGc() > 1) {
                    Coordinate2D coordinate = rows.get(row).getSlots().get(slot).getCenter();
                    if (cranes.canMove(stapel.getUpperContainer(), coordinate)) {
                        c.setCenter(coordinate);
                        List<Stapel> stapels = operations.removeUpperContainer(stapel);
                        resolutionPool.remove(stapel);
                        resolutionPool.addAll(stapels);
                        reorderHelper(stapels, feasiblePool, row, slot, sIndex);
                    } else {
                        reorderHelper(resolutionPool, feasiblePool, row, slot + 1, sIndex);
                    }
                } else {
                    teller ++;
                    if (teller == stapel.getContainerList().size() - 1){
                        searchContainerInFeasiblePool(resolutionPool, feasiblePool, row, slot, sIndex, stapel);
                    }
                }
            }

        }

        return false;
    }

    private void searchContainerInFeasiblePool(List<Stapel> resolutionPool, List<Stapel> feasiblePool, int row, int slot, int sIndex, Stapel stapel) {
        for (Stapel feasibleStapel : feasiblePool) {
            if (feasibleStapel.getUpperContainer().getLc() == stapel.getUpperContainer().getLc()) {
                if (cranes.canMove(feasibleStapel.getUpperContainer(), stapel.getUpperContainer().getCenter())) {
                    feasibleStapel.getUpperContainer().setCenter(stapel.getUpperContainer().getCenter());
                    List<Stapel> tempStapels = operations.removeUpperContainer(feasibleStapel);
                    feasiblePool.remove(feasibleStapel);
                    for (Stapel tempStapel : tempStapels) {
                        if (checkSafetyConstraints(tempStapel)) {
                            feasiblePool.add(tempStapel);
                        } else {
                            resolutionPool.add(tempStapel);
                        }
                    }
                    reorderHelper(resolutionPool, feasiblePool, row, slot, sIndex);
                } else {
                    reorderHelper(resolutionPool, feasiblePool, row, slot, sIndex + 1);
                }
            }
        }
    }*/


}
