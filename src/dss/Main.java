package dss;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Map<String, Runnable> commands = new HashMap<>();

        // DB
        commands.put("db.init", new dss.commands.db.InitCommand());

        // GSMArena
        commands.put("ga.init", new dss.commands.ga.InitCommand());
        commands.put("ga.expand", new dss.commands.ga.ExpandCommand());

        // Media
        commands.put("media.fetch", new dss.commands.media.FetchCommand());

        // Random
        commands.put("rand.prices", new dss.commands.rand.PricesCommand());
        commands.put("rand.reviews", new dss.commands.rand.ReviewsCommand());

        // UI
        commands.put("ui", () -> dss.views.Main.main(args));

        if (args.length == 1) {
            Runnable command = commands.get(args[0]);
            if (command == null) {
                System.err.println("Command not found");
            } else {
                command.run();
            }
        }
    }
}
