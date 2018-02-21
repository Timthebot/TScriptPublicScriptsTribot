package scripts.TScripts.core;

import org.tribot.script.Script;
import org.tribot.script.interfaces.Ending;
import org.tribot.script.interfaces.Starting;

public abstract class TScript extends Script {
    public abstract void mainloop();
    // The main loop

    // Don't override these
    @Override
    public void run() {
        while (!shouldStop) {
            sleep(10);
            mainloop();
        }
    }

    protected void endScript() {
        // Script will end once called
        shouldStop = true;
    }

    private boolean shouldStop = false;

}
