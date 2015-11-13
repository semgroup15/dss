package dss.views;

public class ComparisonRootController {


    // Reference to the main views.
	private MainView mainApp;


    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainView mainApp) {
        this.mainApp = mainApp; //This one first
    }
}
