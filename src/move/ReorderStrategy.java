package move;

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

    public ReorderStrategy(){
        this.operations = new StapelOperations();
    }

    @Override
    public Yard reorderYard(Yard yard) {

        for (Row row : yard.getRowList()){
            for (Stapel stapel : row.getStapels()){
                if (!checkSafetyConstraints(stapel)){
                    resolutionPool.add(new Stapel(stapel));
                }
            }
        }

        System.out.println(resolutionPool);
        reorderHelper(resolutionPool,0,0,0,0);
        return null;
    }

    public boolean checkSafetyConstraints(Stapel stapel){

       if (!stapel.checkHeightConstraint(Yard.H_SAFE)){
           return false;
       }
       if (!stapel.checkUpperContainerConstraint()){
           return false;
       }
       if (!stapel.checkWeightOrderConstraint()){
           return false;
       }
       return true;
    }

    public boolean reorderHelper(List<Stapel> resolutionPool, int row, int slot, int sIndex, int cIndex){

        Stapel stapel = resolutionPool.get(sIndex);

        if (checkSafetyConstraints(resolutionPool.get(sIndex))){
            resolutionPool.remove(stapel);
            if (resolutionPool.isEmpty()){
                return true;
            }
            //TODO returns false?
            reorderHelper(resolutionPool, row, slot, sIndex, cIndex);
        }

        if (stapel.getUpperContainer().getGc() == 1){

        }

        return false;
    }


}
