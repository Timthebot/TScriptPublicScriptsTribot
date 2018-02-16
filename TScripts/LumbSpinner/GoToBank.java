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

public class GoToBank extends TSpinNode {
    public GoToBank(ABCUtil abc2) {
        super("Going to the bank", abc2);
    }

    @Override
    public boolean validate() {
        RSItem[] flaxInInv = Inventory.find(1779);
        return flaxInInv.length == 0 && getLocation() == 1;
    }

    @Override
    public void execute() {
        RSTile target = new RSTile(3205, 3209, 1);
        openDoorIfNeeded();
//        walkToIfNeeded(target);
        RSObject[] stairs = Objects.findNearest(30, "Staircase");
        stairs[0].click("Climb-up");
        idle();
        Timing.waitCondition(new Condition() {
            @Override
            public boolean active() {
                General.sleep(100);
                return getLocation() == 2;
            }
        }, General.random(3000, 7000));
    }
}
