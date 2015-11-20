package dss.views.sections.comparison;

import dss.views.State;
import dss.views.Widget;
import javafx.fxml.FXML;

public class Comparison extends Widget {

    @FXML
    public void onBack() {
        State.get().setLocation(
                new State.Location(State.Location.Section.LISTING));
    }
}
