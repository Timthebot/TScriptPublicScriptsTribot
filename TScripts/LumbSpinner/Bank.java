package scripts.TScripts.LumbSpinner;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api.util.abc.ABCUtil;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Login;
import org.tribot.api2007.types.RSItem;
import scripts.TScripts.core.TScript;

public class Bank extends TSpinNode {
    public Bank(ABCUtil abc2, TScript parent) {
        super("Banking", abc2);
        this.parent = parent;
    }

    @Override
    public boolean validate() {
        RSItem[] flaxInInv = Inventory.find(1779);
        return getFloorInLumbCastle() == 2 && flaxInInv.length == 0;
    }

    @Override
    public void execute() {
        if (Banking.isBankScreenOpen()) {
            if (Banking.find("Flax").length < 1) {
                System.out.println("Should logout!");
                Login.logout();
                parent.endScript();
            } else {
                if (Inventory.getAll().length > 0) {
                    Banking.depositAll();
                }
                if (Banking.withdraw(0, "Flax")) {
                    Timing.waitCondition(waitTillHasFlax, General.random(1000, 4000));
                } else {
                    System.err.println("Encountered problem banking");
                }
            }
        } else {
            if (Banking.openBank()) {
                Timing.waitCondition(isBankOpen, General.random(1000, 2000));
            }
        }
    }

    static final Condition isBankOpen = new Condition() {
        @Override
        public boolean active() {
            General.sleep(100);
            return Banking.isBankScreenOpen();
        }
    };

    private TScript parent;

}
