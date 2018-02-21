package scripts.TScripts.LumbSpinner;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api.util.abc.ABCUtil;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;

public class GoToBank extends TSpinNode {
    public GoToBank(ABCUtil abc2) {
        super("Going to the bank", abc2);
    }

    @Override
    public boolean validate() {
        RSItem[] flaxInInv = Inventory.find(1779);
        return flaxInInv.length == 0 && getFloorInLumbCastle() == 1;
    }

    @Override
    public void execute() {
        openDoorIfNeeded();

        if (false ){//&& hovering) {
            // click climb-up

        } else {
            RSObject[] stairs = Objects.findNearest(15, "Staircase");
            if (stairs.length > 0) {
                stairs[0].click("Climb-up");
                idle();
                Timing.waitCondition(isOnTopFloor, General.random(3000, 7000));
            }
        }
    }
}
