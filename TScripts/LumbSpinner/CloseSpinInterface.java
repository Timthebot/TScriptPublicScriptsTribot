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

public class CloseSpinInterface extends TSpinNode {
    public CloseSpinInterface(ABCUtil abc2) {
        super("Closing the spin interface", abc2);
    }

    @Override
    public boolean validate() {
        RSItem[] items = Inventory.find("Flax");
        return items.length == 0 && isSpinInterfaceOpen();
    }

    @Override
    public void execute() {
        RSInterfaceChild inter = Interfaces.get(459, 1);
        if (inter != null) {
            RSInterfaceComponent comp = inter.getChild(11);
            if (comp != null) {
                comp.click("Close");
            }
        }

    }
}