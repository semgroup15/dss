package dss.commands.dss;

import dss.views.input.InputClientMain;

public class InputClientCommand implements Runnable {
    @Override
    public void run() {
        InputClientMain.launchApp();
    }
}
