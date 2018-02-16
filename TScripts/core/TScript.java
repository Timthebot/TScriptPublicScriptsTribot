package scripts.TScripts.core;

import org.tribot.script.Script;

public abstract class TScript extends Script {
    public void start() {
        // Executes once at the start
    }

    public void stop() {
        // Override once at the start
    }

    public abstract void mainloop();
    // The main loop

    // Don't override these
    @Override
    public void run() {
        start();
        while (!shouldStop) {
            sleep(10);
            mainloop();
        }
        stop();
    }

    protected void endScript() {
        // Script will end once called
        shouldStop = true;
    }

    private boolean shouldStop = false;

}
