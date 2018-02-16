package scripts.TScripts.LumbSpinner;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api.util.abc.ABCUtil;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSItem;

public class Bank extends TSpinNode {
    public Bank(ABCUtil abc2) {
        super("Banking", abc2);
    }

    @Override
    public boolean validate() {
        RSItem[] flaxInInv = Inventory.find(1779);
        return getLocation() == 2 && flaxInInv.length == 0;
    }

    @Override
    public void execute() {
        if (Banking.isBankScreenOpen()) {
            if (Inventory.getAll().length > 0) {
                Banking.depositAll();
            }
            if (Banking.withdraw(50, "Flax")) {
                Timing.waitCondition(new Condition() {
                    @Override
                    public boolean active() {
                        General.sleep(100);
                        RSItem[] flaxInInv = Inventory.find("Flax");
                        return flaxInInv.length > 0;
                    }
                }, General.random(1000, 4000));
            } else {
                System.out.println("Encountered problem banking");
            }
        } else {
            Banking.openBank();
            Timing.waitCondition(new Condition() {
                @Override
                public boolean active() {
                    General.sleep(100);
                    return Banking.isBankScreenOpen();
                }
            }, General.random(1000, 2000));
        }

    }
}
