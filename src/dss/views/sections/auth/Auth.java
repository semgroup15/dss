package dss.views.sections.auth;

import dss.Developer;
import dss.models.user.User;
import dss.views.base.State;
import dss.views.base.Widget;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Developer({
    Developer.Value.AHMED,
    Developer.Value.TRIXIE,
    Developer.Value.JIM,
    Developer.Value.SEBASTIAN,
})
public class Auth extends Widget implements Initializable {

    @FXML
    TextField username;

    @FXML
    PasswordField password;

    @FXML
    public void initialize(URL location, ResourceBundle resourceBundle) {
        visibleProperty().addListener((observable) -> username.requestFocus());
    }

    @FXML
    public void onLogin() {

        // Get username and password
        String username = this.username.getText();
        String password = this.password.getText();

        // Clear username and password
        this.username.clear();
        this.password.clear();

        List<User> users = User.manager.select("auth", username, password);

        if (!users.isEmpty()) {
            State.get().setLevel(State.Level.AUTHORIZED);
            setVisible(false);
        }
    }

    @FXML
    public void onCancel() {
        setVisible(false);
    }
}
