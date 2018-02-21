package scripts.TScripts.LumbSpinner;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.util.abc.ABCUtil;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSInterfaceChild;
import org.tribot.api2007.types.RSInterfaceComponent;
import org.tribot.api2007.types.RSItem;

public class SpinAllFlax extends TSpinNode {
    public SpinAllFlax(ABCUtil abc2) {
        super("Spinning flax!", abc2);
    }

    @Override
    public boolean validate() {
        RSItem[] items = Inventory.find("Flax");
        return items.length > 0 && isSpinInterfaceOpen();
    }


    @Override
    public void execute() {
        RSInterfaceChild inter = Interfaces.get(459, 4);
        if (inter != null) {
            inter.click("Spin-All");
            Timing.waitCondition(isAnimating, General.random(1000, 3000));
        }
    }
}
