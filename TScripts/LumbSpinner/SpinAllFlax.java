package scripts.TScripts.LumbSpinner;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api.util.abc.ABCUtil;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSInterfaceChild;
import org.tribot.api2007.types.RSInterfaceComponent;

public class SpinAllFlax extends TSpinNode {
    public SpinAllFlax(ABCUtil abc2) {
        super("Spinning flax!", abc2);
    }

    @Override
    public boolean validate() {
        RSInterfaceChild inter = Interfaces.get(459, 1);
        if (inter != null && !inter.isHidden()) {
            RSInterfaceComponent child = inter.getChild(1);
            if (child != null) {
                String text = child.getText();
                return text != null && text.contains("What would you like to spin");
            }
        }
        return false;
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
