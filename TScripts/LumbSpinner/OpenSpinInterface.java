package scripts.TScripts.LumbSpinner;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Keyboard;
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
        return getFloorInLumbCastle() == 1 && items.length > 0 && !isSpinInterfaceOpen();
    }

    private static final RSTile walkToWheel = new RSTile(3209, 3213, 1);

    @Override
    public void execute() {
        openDoorIfNeeded();
        walkToIfNeeded(walkToWheel);
        RSObject[] wheels = Objects.findNearest(10, "Spinning wheel");
        if (wheels.length > 0) {
            if (wheels[0].click("Spin")) {
                Timing.waitCondition(waitForInterface, General.random(600, 3300));
                Keyboard.holdKey('3', 51, isAnimating);
                Timing.waitCondition(isAnimating, General.random(1000, 3000));

            }
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