package scripts.TScripts.core;

import org.tribot.api.util.abc.ABCUtil;

public abstract class TTask {
    // A Task simply has an execute method and is somewhat rarely used, only for a set of linear tasks that don't
    // require validation. TNode should be used more frequently.

    public TTask(String status, ABCUtil abc2) {
        this.status = status;
        this.abc_util = abc2;
    }

    public abstract void execute();

    public void idle() {
        // Any method that can idle should call the idle() method, which implements abc2
        // see https://tribot.org/forums/topic/60720-guide-to-implementing-abc2/

        if (this.abc_util.shouldCheckTabs())
            this.abc_util.checkTabs();

        if (this.abc_util.shouldCheckXP())
            this.abc_util.checkXP();

        if (this.abc_util.shouldExamineEntity())
            this.abc_util.examineEntity();

        if (this.abc_util.shouldMoveMouse())
            this.abc_util.moveMouse();

        if (this.abc_util.shouldPickupMouse())
            this.abc_util.pickupMouse();

        if (this.abc_util.shouldRightClick())
            this.abc_util.rightClick();

        if (this.abc_util.shouldRotateCamera())
            this.abc_util.rotateCamera();

        if (this.abc_util.shouldLeaveGame())
            this.abc_util.leaveGame();
    }

    public String getStatus() {
        return status;
    }
    private String status;
    protected ABCUtil abc_util;
}
