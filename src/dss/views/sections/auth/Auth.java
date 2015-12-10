package dss.views.sections.auth;

import dss.models.user.User;
import dss.views.base.State;
import dss.views.base.Widget;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Auth extends Widget implements Initializable {

    @FXML
    public void initialize(URL location, ResourceBundle resourceBundle) {
    }
    @FXML
    TextField username;

    @FXML
    PasswordField password;

    @FXML
    public void onLogin() {
        String username = this.username.getText();
        String password = this.password.getText();

        if (User.manager.select(
                "auth", username, password).isEmpty()) {
            State.get().setLevel(State.Level.USER);
        } else {
            State.get().setLevel(State.Level.ADMIN);

        }

    }
}