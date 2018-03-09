package scripts.TScripts.LumbSpinner;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api.util.abc.ABCUtil;
import org.tribot.api2007.Magic;
import org.tribot.api2007.MessageListener;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.interfaces.MessageListening07;


public class RunToStairs extends TSpinNode {
    public RunToStairs(ABCUtil abc2) {
        super("We are lost. Returning to Lumbridge...", abc2);
    }

    @Override
    public boolean validate() {
        return getFloorInLumbCastle() == -1;
    }

    @Override
    public void execute() {
        if (stairtile.distanceTo(Player.getPosition()) < 15) {
            walkToIfNeeded(stairtile);
        } else if (lumbtile.distanceTo(Player.getPosition()) < 10) {
            walkToIfNeeded(insideCastle);
        } else {
            if (Magic.selectSpell("Lumbridge Home Teleport")) {
                Timing.waitCondition(isAnimating, General.random(1000, 3000));
                if (isAnimating.active()) {
                    Timing.waitCondition(waitTillInLumbridge, General.random(31000, 35000));
                    System.out.println("Home port!");
                } else {
                    System.out.println("Home port failed!");
                }


            }
        }
    }

    private final RSTile stairtile = new RSTile(3206, 3208, 0);
    private final RSTile lumbtile = new RSTile(3223, 3219, 0);
    private final RSTile insideCastle = new RSTile(3214, 3211, 0);

    private final Condition waitTillInLumbridge = new Condition() {
        @Override
        public boolean active() {
            return lumbtile.distanceTo(Player.getPosition()) < 10;
        }
    };

}
