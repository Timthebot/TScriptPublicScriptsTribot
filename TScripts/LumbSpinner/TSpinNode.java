package scripts.TScripts.LumbSpinner;

import org.tribot.api.General;
import org.tribot.api.util.abc.ABCUtil;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.ext.Doors;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import scripts.TScripts.core.TNode;

import java.awt.*;

public abstract class TSpinNode extends TNode {
    public TSpinNode(String status, ABCUtil abc2) {
        super(status, abc2);
    }

    public static void openDoorIfNeeded() {
        RSObject[] openableDoors = Objects.find(10, "Door");
        for (RSObject door : openableDoors) {
            RSTile pos = door.getPosition();
            if (pos.getX() == 3207 && pos.getY() == 3214) {
                System.out.println("Opening the doors!");
                Doors.handleDoor(door, true);
                General.sleep(600);
            }
        }
    }

    public int getLocation() {
        // Returns 2 if on the top floor, 1 if on the spinning floor
        // 0 if on the ground floor or -1 if elsewhere.
        RSTile pos = Player.getPosition();
        if (lumbTopFloors.contains(pos.getX(), pos.getY())) {
            return pos.getPlane();
        } else {
            return -1;
        }
    }

    private Polygon lumbTopFloors = new Polygon(
            new int[]{3205, 3214, 3214, 3205},
            new int[]{3229, 3229, 3208, 3208},
            4);
}
