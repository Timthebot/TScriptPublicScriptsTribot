package scripts.TScripts.LumbSpinner;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api.util.abc.ABCUtil;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.*;

public class OpenSpinInterface extends TSpinNode {
    public OpenSpinInterface(ABCUtil abc2) {
        super("Opening the spin interface", abc2);
    }

    @Override
    public boolean validate() {
        RSItem[] items = Inventory.find("Flax");
        RSInterfaceChild inter = Interfaces.get(459, 1);
        return getFloorInLumbCastle() == 1 && items.length > 0 && (inter == null || inter.isHidden()) && Player.getAnimation() == -1;
    }

    private static final RSTile walkToWheel = new RSTile(3209, 3213, 1);

    @Override
    public void execute() {
        openDoorIfNeeded();
        walkToIfNeeded(walkToWheel);
        RSObject[] wheels = Objects.findNearest(10, "Spinning wheel");
        if (wheels.length > 0) {
            wheels[0].click("Spin");
            Timing.waitCondition(waitForInterface, General.random(2000, 3300));
        }
    }

    private final Condition waitForInterface = new Condition() {
        @Override
        public boolean active() {
            General.sleep(100); // Sleep to reduce CPU usage.
            RSInterfaceChild inter = Interfaces.get(459, 1);
            if (inter != null && !inter.isHidden()) {
                RSInterfaceComponent child = inter.getChild(1);
                return (child != null &&
                        inter.getChild(1).getText().contains("What would you like to spin"));
            } else {
                return false;
            }
        }
    };
}