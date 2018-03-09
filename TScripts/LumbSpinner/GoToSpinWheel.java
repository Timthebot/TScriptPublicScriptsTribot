package scripts.TScripts.LumbSpinner;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api.util.abc.ABCUtil;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;

public class GoToSpinWheel extends TSpinNode {
    public GoToSpinWheel(ABCUtil abc2) {
        super("Going to the spinning wheel", abc2);
    }

    @Override
    public boolean validate() {
        RSItem[] flaxInInv = Inventory.find(1779);
        return flaxInInv.length != 0 && getFloorInLumbCastle() == 2;
    }

    private static final RSTile target = new RSTile(3205, 3209, 2);
    @Override
    public void execute() {
        walkToIfNeeded(target);
        RSObject[] stairs = Objects.findNearest(15, "Staircase");
        if (stairs.length > 0) {
            if (stairs[0].click("Climb-down")) {
                idle();
                Timing.waitCondition(isOnSpinFloor, General.random(1000, 4000));
            }
        }
    }
}
