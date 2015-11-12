package dss;

import dss.views.MainView;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Map<String, Runnable> commands = new HashMap<>();

        commands.put("db.init", new dss.commands.db.InitCommand());

        commands.put("dss.input", new dss.commands.dss.InputClientCommand());
        commands.put("dss.output", new dss.commands.dss.OutputClientCommand());

        commands.put("ga.init", new dss.commands.ga.InitCommand());
        commands.put("ga.expand", new dss.commands.ga.ExpandCommand());

        commands.put("media.fetch", new dss.commands.media.FetchCommand());

        if (args.length == 1) {
            Runnable command = commands.get(args[0]);
            if (command == null) {
                System.err.println("Command not found");
            } else {
                command.run();
            }
        }

        MainView.launchApp();
    }
}
