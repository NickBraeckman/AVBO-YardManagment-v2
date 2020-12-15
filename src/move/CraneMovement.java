package move;

import lombok.Data;
import main.Coordinate2D;

@Data
public class CraneMovement {
    private final int time;
    private final Coordinate2D coordinate;
    private int containerId;

}
