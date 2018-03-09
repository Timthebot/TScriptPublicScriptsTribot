package scripts.TScripts.LumbSpinner;

import org.tribot.api.Clicking;
import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api.types.generic.Condition;
import org.tribot.api.util.abc.ABCUtil;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;

public class GoToBank extends TSpinNode {
    public GoToBank(ABCUtil abc2) {
        super("Going to the bank", abc2);
    }

    @Override
    public boolean validate() {
        RSItem[] flaxInInv = Inventory.find(1779);
        return flaxInInv.length == 0 && getFloorInLumbCastle() == 1 && !Player.isMoving();
    }

    @Override
    public void execute() {
        openDoorIfNeeded();
        RSObject[] stairs = Objects.findNearest(15, "Staircase");
        if (stairs.length > 0) {
            if (stairs[0].click("Climb-up")) {
                idle();
                Timing.waitCondition(isOnTopFloor, General.random(3000, 7000));
            }
        }
        General.sleep(600);
    }
}
