package dss;

import java.util.HashMap;
import java.util.Map;

import dss.commands.InputClientCommand;
import dss.commands.MigrateCommand;
import dss.commands.OutputClientCommand;

public class Main {

    public static void main(String[] args) {
        Map<String, Runnable> commands = new HashMap<>();

        commands.put("migrate", new MigrateCommand());
        commands.put("input", new InputClientCommand());
        commands.put("output", new OutputClientCommand());

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
