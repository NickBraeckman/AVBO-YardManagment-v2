package move;

import main.*;
import model.Stapel;
import model.StapelOperations;

import java.util.ArrayList;
import java.util.List;

public class ReorderStrategy implements Strategy {

    private StapelOperations operations;
    private List<Stapel> resolutionPool = new ArrayList<>();
    private List<Stapel> feasiblePool = new ArrayList<>();
    private List<Row> rows;
    private Cranes cranes;

    public ReorderStrategy() {
        this.operations = new StapelOperations();
    }

    @Override
    public Yard reorderYard(Yard yard) {
        this.cranes = yard.getCranes();
        this.rows = yard.getRowList();

        for (Row row : yard.getRowList()) {
            for (Stapel stapel : row.getStapels()) {
                if (!checkSafetyConstraints(stapel)) {
                    resolutionPool.add(new Stapel(stapel));
                }
                feasiblePool.add(new Stapel(stapel));
            }
        }

        System.out.println(resolutionPool);
        reorderHelper(resolutionPool, feasiblePool, 0, 0, 0);
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

    public boolean reorderHelper(List<Stapel> resolutionPool, List<Stapel> feasiblePool, int row, int slot, int sIndex) {

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
    }


}
