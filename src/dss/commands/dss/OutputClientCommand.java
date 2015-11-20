package dss.commands.dss;

import java.util.List;

import dss.models.device.Device;
import dss.views.MainView;

public class OutputClientCommand implements Runnable {
    @Override
    public void run() {
        System.out.println("DSS Output Client");


        MainView.launchApp();
    }
}
