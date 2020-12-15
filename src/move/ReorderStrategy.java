package move;

import main.Row;
import main.Yard;
import model.Stapel;

import java.util.ArrayList;
import java.util.List;

public class ReorderStrategy implements Strategy {

    private List<Stapel> resolutionPool = new ArrayList<>();

    @Override
    public Yard reorderYard(Yard yard) {

        for (Row row : yard.getRowList()){
            for (Stapel stapel : row.getStapels()){
                if (!checkSafetyConstraints(stapel)){
                    resolutionPool.add(stapel);
                }
            }
        }
        System.out.println(resolutionPool);
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
}
