package scripts.TScripts.LumbSpinner;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api.util.abc.ABCUtil;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.ext.Doors;
import org.tribot.api2007.types.*;
import scripts.TScripts.core.TNode;

import java.awt.*;

abstract class TSpinNode extends TNode {
    TSpinNode(String status, ABCUtil abc2) {
        super(status, abc2);
    }


    private Polygon lumbTopFloors = new Polygon(
            new int[]{3205, 3214, 3214, 3205},
            new int[]{3229, 3229, 3208, 3208},
            4);


    void openDoorIfNeeded() {
        RSObject[] openableDoors = Objects.find(10, "Door");
        for (RSObject door : openableDoors) {
            RSTile pos = door.getPosition();
            if (pos.getX() == 3207 && pos.getY() == 3214) {
                System.out.println("Opening the doors!");
                Doors.handleDoor(door, true);
                Timing.waitCondition(isDoorOpen, General.random(2000, 5000));
            }
        }
    }

    int getFloorInLumbCastle() {
        // Returns 2 if on the top floor, 1 if on the spinning floor
        // 0 if on the ground floor or -1 if elsewhere.
        RSTile pos = Player.getPosition();
        if (lumbTopFloors.contains(pos.getX(), pos.getY())) {
            return pos.getPlane();
        } else {
            return -1;
        }
    }

    boolean isSpinInterfaceOpen() {
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


    // Wait conditions

    final Condition waitTillNoFlax = new Condition() {
        @Override
        public boolean active() {
            General.sleep(100);
            RSItem[] flaxInInv = Inventory.find("Flax");
            return flaxInInv.length == 0;
        }
    };


    final Condition waitTillHasFlax = new Condition() {
        @Override
        public boolean active() {
            General.sleep(100);
            RSItem[] flaxInInv = Inventory.find("Flax");
            return flaxInInv.length > 0;
        }
    };

    final Condition isOnTopFloor = new Condition() {
        @Override
        public boolean active() {
            General.sleep(100);
            return getFloorInLumbCastle() == 2;
        }
    };

    final Condition isDoorOpen = new Condition() {
        @Override
        public boolean active() {
            General.sleep(100);
            RSObject[] openableDoors = Objects.find(10, "Door");
            for (RSObject door : openableDoors) {
                RSTile pos = door.getPosition();
                if (pos.getX() == 3207 && pos.getY() == 3214) {
                    return false;
                }
            }
            return true;
        }
    };

    final Condition isOnSpinFloor = new Condition() {
        @Override
        public boolean active() {
            General.sleep(100);
            return getFloorInLumbCastle() == 1;
        }
    };

    final Condition isAnimating = new Condition() {
        @Override
        public boolean active() {
            General.sleep(100);
            return Player.getAnimation() != -1;
        }
    };

    final Condition isNotAnimating = new Condition() {
        @Override
        public boolean active() {
            General.sleep(100);
            return Player.getAnimation() == -1;
        }
    };
}
