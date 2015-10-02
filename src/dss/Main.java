package dss;

import java.util.HashMap;
import java.util.Map;

import dss.commands.db.MigrateCommand;
import dss.commands.dss.InputClientCommand;
import dss.commands.dss.OutputClientCommand;
import dss.commands.interop.ExpandCommand;
import dss.commands.interop.InitCommand;
import dss.commands.media.FetchCommand;

public class Main {

    public static void main(String[] args) {
        Map<String, Runnable> commands = new HashMap<>();

        commands.put("db.migrate", new MigrateCommand());

        commands.put("dss.input", new InputClientCommand());
        commands.put("dss.output", new OutputClientCommand());

        commands.put("interop.init", new InitCommand());
        commands.put("interop.expand", new ExpandCommand());

        commands.put("media.fetch", new FetchCommand());

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
