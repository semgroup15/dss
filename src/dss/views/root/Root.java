package dss.views.root;

import dss.models.device.Device;
import dss.views.base.State;
import dss.views.base.Widget;
import dss.views.search.Search;
import dss.views.sections.auth.Auth;
import dss.views.sections.comparison.Comparison;
import dss.views.sections.detail.Detail;
import dss.views.sections.listing.Listing;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Root layout.
 */
public class Root extends Widget
        implements Initializable, State.LocationListener {

    @FXML
    Search search;

    @FXML
    Listing listing;

    @FXML
    Detail detail;

    @FXML
    Comparison comparison;

    @FXML
    Auth auth;

    @FXML
    public void initialize(URL location, ResourceBundle resourceBundle) {
        State.get().addLocationListener(this);
    }

    @Override
    public void onLocationChange(State.Location location) {

        // Switch visible section depending on the current location
        switch (location.getSection()) {
            case LISTING:
                auth.setVisible(false);
                detail.setVisible(false);
                comparison.setVisible(false);
                listing.setVisible(true);
                break;

            case DETAIL:
                auth.setVisible(false);
                listing.setVisible(false);
                comparison.setVisible(false);
                detail.setDevice((Device) location.getData()[0]);
                detail.setVisible(true);
                break;

            case COMPARISON:
                auth.setVisible(false);
                listing.setVisible(false);
                detail.setVisible(false);
                comparison.setVisible(true);
                break;

            case AUTH:
                auth.setVisible(true);
                break;
        }
    }
}
