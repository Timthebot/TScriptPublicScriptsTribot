package scripts.TScripts.core;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.interfaces.Positionable;
import org.tribot.api.types.generic.Condition;
import org.tribot.api.util.abc.ABCUtil;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;

public abstract class TNode extends TTask {

    public TNode(String status, ABCUtil abc2) {
        super(status, abc2);
    }

    public abstract boolean validate();

    public static void walkToIfNeeded(Positionable target) {
        if (Player.getPosition().distanceTo(target) > 3) {
            Walking.walkTo(target);
            Timing.waitCondition(new Condition() {
                @Override
                public boolean active() {
                    General.sleep(100);
                    return Player.getPosition().distanceTo(target) < 3;
                }
            }, General.random(1000, 4000));
        }
    }


}
