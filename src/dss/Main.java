package dss;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Map<String, Runnable> commands = new HashMap<>();

        commands.put("migrate", new Runnable() {
            @Override
            public void run() {
                new dss.models.PackageMigration().doForward();
            }
        });

        commands.put("input", new Runnable() {
            @Override
            public void run() {
                System.out.println("DSS Input Client");
            }
        });

        commands.put("output", new Runnable() {
            @Override
            public void run() {
                System.out.println("DSS Output Client");
            }
        });

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
