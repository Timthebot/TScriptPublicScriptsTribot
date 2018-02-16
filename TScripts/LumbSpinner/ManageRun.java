package scripts.TScripts.LumbSpinner;

import org.tribot.api.util.abc.ABCUtil;
import org.tribot.api2007.*;

public class ManageRun extends TSpinNode {
    public ManageRun(ABCUtil abc2) {
        super("Turning on run", abc2);
        runTarget = abc2.generateRunActivation();
    }

    @Override
    public boolean validate() {
        return Game.getRunEnergy() > runTarget && !Game.isRunOn();
    }

    @Override
    public void execute() {
        Options.setRunOn(true);
        runTarget = abc_util.generateRunActivation();

    }

    private int runTarget;
}
