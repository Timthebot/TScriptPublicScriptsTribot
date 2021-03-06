package scripts.TScripts.LumbSpinner;

import org.tribot.api.Timing;
import org.tribot.api.util.abc.ABCUtil;
import org.tribot.api2007.Login;
import org.tribot.api2007.Skills;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.Ending;
import org.tribot.script.interfaces.MessageListening07;
import org.tribot.script.interfaces.Painting;
import org.tribot.script.interfaces.Starting;
import scripts.TScripts.core.TScript;

import java.awt.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;


@ScriptManifest(authors = {"TScripts"}, name = "TLumbSpinner", description = "" +
        "<p>Spins flax into bowstring in Lumbridge.</p>" +
        "<p>Requirements:</p>" +
        "<ul>" +
        "<li>10 crafting</li>" +
        "<li>Plenty of Flax in bank</li>" +
        "</ul>" +
        "<p>Features:</p>" +
        "<ul>" +
        "<li>Updated to work with new interfaces released on 08/03/2018!</li>" +
        "<li>The fastest flax spinner, uses keyboard for new-style interfaces.</li>" +
        "<li>Start anywhere.</li>" +
        "<li>Attempts to return to Lumbridge when lost</li>" +
        "<li>Opens door when closed</li>" +
        "<li>Implements TriBot Anti-Ban Compliance</li>" +
        "</ul>" +
        "<p>Author: TScripts</p>\n\r" +
        "<p>Discuss the script at https://tribot.org/forums/topic/76135-tlumbspinner</p>" +
        "<p>Report bugs/suggestions at https://github.com/Timthebot/TScriptPublicScriptsTribot/issues/new?title=[TLumbSpinner]</a></p>" +
        "", version = 1.2, category = "Crafting")
public class LumbSpinner extends TScript implements Painting, Starting, Ending, MessageListening07 {
    @Override
    public void onStart() {
        // Executes once at the start

        ABCUtil abc2 = new ABCUtil();

        nodes.add(new ManageRun(abc2));
        nodes.add(new RunToStairs(abc2));
        nodes.add(new ClimbUpStairs(abc2));
        nodes.add(new CloseSpinInterface(abc2));
        nodes.add(new IsSpinning(abc2));
        nodes.add(new OpenSpinInterface(abc2));
        nodes.add(new SpinAllFlax(abc2));
        nodes.add(new GoToBank(abc2));
        nodes.add(new Bank(abc2, this));
        nodes.add(new GoToSpinWheel(abc2));

        startCraftXp = Skills.getXP(Skills.SKILLS.CRAFTING);
        startTime = System.currentTimeMillis();
    }

    @Override
    public void mainloop() {
        // The main loop
        for (TSpinNode node : nodes) {
            if (node.validate()) {
                status = node.getStatus();
                node.execute();
                break; // Ensure priority set by list order
            }
        }
    }

    List<TSpinNode> nodes = new ArrayList<>();

    private long startTime;
    private int startCraftXp;
    private final Font font = new Font("Verdana", Font.BOLD, 14);

    @Override
    public void onPaint(Graphics g) {
        long timeRan = System.currentTimeMillis() - startTime;
        int xpGained = (Skills.getXP(Skills.SKILLS.CRAFTING) - startCraftXp);

        double xpGainedPerMs = (float) xpGained / timeRan;
        double xpPerHr = xpGainedPerMs * (1000 * 60 * 60.);
        g.setColor(new Color(200, 0, 200, 150));
        g.fillRect(15, 15, 300, 150);

        g.setFont(font);
        g.setColor(Color.WHITE);
        g.drawString("TLumbSpinner v1.2", 20, 30);
        g.drawString("Status: " + status, 20, 50);
        g.drawString("Runtime: " + Timing.msToString(timeRan), 20, 70);
        g.drawString("Xp Gained: " + xpGained, 20, 90);
        g.drawString("Flax spun: " + (xpGained / 15), 20, 110);
        g.drawString(MessageFormat.format("k Xp/hr: {0}", xpPerHr / 1000), 20, 130);
        g.drawString(MessageFormat.format("Bowstrings/hr: {0}", (int) (xpPerHr / 15)), 20, 150);

    }

    private String status = "Initialising";

    @Override
    public void onEnd() {
        System.out.println("Thank you for using TLumbSpinner!");
    }


    @Override
    public void playerMessageReceived(String s, String s1) {

    }

    @Override
    public void duelRequestReceived(String s, String s1) {

    }

    @Override
    public void tradeRequestReceived(String s) {

    }

    @Override
    public void serverMessageReceived(String s) {
        if (s.contains("You need to wait another ")) {
            System.err.println("ERROR: Unable to home port and lost, shutting down script!");
            Login.logout();
            endScript();
        }

    }

    @Override
    public void clanMessageReceived(String s, String s1) {

    }

    @Override
    public void personalMessageReceived(String s, String s1) {

    }
}
