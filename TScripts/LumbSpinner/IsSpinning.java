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
            Timing.waitCondition(new Condition() {
                @Override
                public boolean active() {
                    General.sleep(100);
                    return Player.getAnimation() == -1;
                }
            }, General.random(1000, 3000));
            // Sleep until spinning again
            Timing.waitCondition(new Condition() {
                @Override
                public boolean active() {
                    General.sleep(100);
                    return Player.getAnimation() != -1;
                }
            }, General.random(1000, 3000));
        } else {
            // Hover the stairs to go-up quickly
            RSObject[] stairs = Objects.findNearest(20, "Staircase");
            System.out.println("Hovering stairs");
            if (stairs.length > 0) {
                RSObject staircase = stairs[0];
                if (staircase.isOnScreen()) {
                    staircase.hover();
                } else {
                    Camera.turnToTile(staircase);
                }
            }
            Timing.waitCondition(new Condition() {
                @Override
                public boolean active() {
                    General.sleep(100);
                    RSItem[] flax = Inventory.find("Flax");
                    return flax.length == 0;
                }
            }, General.random(1000, 3000));
            stairs[0].click("Climb-up");
        }

    }
}
