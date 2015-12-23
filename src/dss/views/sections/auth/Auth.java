package dss.views.sections.auth;

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

public class Auth extends Widget implements Initializable {

    @FXML
    TextField username;

    @FXML
    PasswordField password;

    @FXML
    public void initialize(URL location, ResourceBundle resourceBundle) {
    }

    @FXML
    public void onLogin() {

        // Get username and password
        String username = this.username.getText();
        String password = this.password.getText();

        List<User> users = User.manager.select("auth", username, password);

        if (!users.isEmpty()) {
            State state = State.get();

            state.setLevel(State.Level.AUTHORIZED);
            state.setLocation(
                    new State.Location(State.Location.Section.LISTING));
        }
    }
}
