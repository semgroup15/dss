package dss;

import java.util.HashMap;
import java.util.Map;

import dss.commands.db.MigrateCommand;
import dss.commands.dss.InputClientCommand;
import dss.commands.dss.OutputClientCommand;
import dss.commands.gsmarena.DeviceCommand;
import dss.commands.gsmarena.QuickSearchCommand;

public class Main {

    public static void main(String[] args) {
        Map<String, Runnable> commands = new HashMap<>();

        commands.put("db.migrate", new MigrateCommand());

        commands.put("dss.input", new InputClientCommand());
        commands.put("dss.output", new OutputClientCommand());

        commands.put("gsmarena.quicksearch", new QuickSearchCommand());
        commands.put("gsmarena.device", new DeviceCommand());

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
