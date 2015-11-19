package dss.views.root;

import dss.views.State;
import dss.views.Widget;
import dss.views.search.Search;
import dss.views.sections.comparison.Comparison;
import dss.views.sections.detail.Detail;
import dss.views.sections.listing.Listing;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

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
    public void initialize(URL location, ResourceBundle resourceBundle) {
        State.get().addLocationListener(this);
    }

    @Override
    public void onLocationChange(State.Location location) {
        listing.setVisible(false);
        detail.setVisible(false);
        comparison.setVisible(false);

        switch (location.getSection()) {
            case LISTING: listing.setVisible(true); break;
            case DETAIL: detail.setVisible(true); break;
            case COMPARISON: comparison.setVisible(true); break;
        }
    }
}