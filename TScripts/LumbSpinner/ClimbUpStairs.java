package scripts.TScripts.LumbSpinner;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.util.abc.ABCUtil;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;

public class ClimbUpStairs extends TSpinNode {
    public ClimbUpStairs(ABCUtil abc2) {
        super("Climbing up Lumbridge stairs", abc2);
    }

    @Override
    public boolean validate() {
        return getFloorInLumbCastle() == 0;
    }

    @Override
    public void execute() {
        walkToIfNeeded(stairtile);
        RSObject[] stairs = Objects.findNearest(15, "Staircase");
        if (stairs.length > 0) {
            stairs[0].click("Climb-up");
            idle();
            Timing.waitCondition(isOnSpinFloor, General.random(3000, 7000));
        }
    }

    private final RSTile stairtile = new RSTile(3206, 3208, 0);
}
