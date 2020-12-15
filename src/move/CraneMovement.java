package move;

import lombok.Data;
import main.Coordinate2D;

@Data
public class CraneMovement {
    private final int time;
    private final Coordinate2D coordinate;
    private int containerId;

    @Override
    public String toString() {
        String part1 = time + "," + coordinate.getX() + "," + coordinate.getY();
        String part2 = "";
        if (containerId != -1) {
            part2 = "," + containerId;
        }
        return part1 + part2;
    }
}
