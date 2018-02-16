package scripts.TScripts.LumbSpinner;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api.util.abc.ABCUtil;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSInterfaceChild;

public class SpinAllFlax extends TSpinNode {
    public SpinAllFlax(ABCUtil abc2) {
        super("Spinning flax!", abc2);
    }

    @Override
    public boolean validate() {
        RSInterfaceChild inter = Interfaces.get(459, 1);
        return inter != null && !inter.isHidden() &&
                inter.getChild(1).getText().contains("What would you like to spin");
    }

    @Override
    public void execute() {
        RSInterfaceChild inter = Interfaces.get(459, 4);
        inter.click("Spin-All");
        Timing.waitCondition(new Condition() {
            @Override
            public boolean active() {
                General.sleep(100);
                return Player.getAnimation() != -1;
            }
        }, General.random(1000, 3000));


    }
}
