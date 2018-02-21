package scripts.TScripts.LumbSpinner;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api.util.abc.ABCUtil;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;

public class IsSpinning extends TSpinNode {
    public IsSpinning(ABCUtil abc2) {
        super("Idling whilst spinning flax", abc2);
    }

    @Override
    public boolean validate() {
        return Player.getAnimation() != -1;
    }

    @Override
    public void execute() {
        idle();
        RSItem[] flaxLeft = Inventory.find("Flax");
        if (flaxLeft.length > 1) {
            // We still have move flax to go, so wait for animation to go -1
            Timing.waitCondition(isNotAnimating, General.random(1000, 3000));
            // Sleep until spinning again
            Timing.waitCondition(isAnimating, General.random(1000, 3000));
        } else {
            // Hover the stairs to go-up quickly
            RSObject[] stairs = Objects.findNearest(20, "Staircase");
            if (stairs.length > 0) {
                RSObject staircase = stairs[0];
                if (staircase.isOnScreen()) {
                    staircase.hover();
                } else {
                    Camera.turnToTile(staircase);
                }

                Timing.waitCondition(waitTillNoFlax, General.random(1000, 3000));
                staircase.click("Climb-up");
            }
        }

    }
}
