package com.example.helllllllooo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private Button loginButton;

    private Window window;

    public LoginController() {
    }

    @FXML
    private void login() throws Exception {
        if (isValidated()) {
            PreparedStatement ps;
            ResultSet rs;
            String query = "select * from users WHERE username = ?";
            try {
                Connection con = DbConnection.getConnection();
                ps = con.prepareStatement(query);
                ps.setString(1, username.getText());
                rs = ps.executeQuery();

                if (rs.next()) {
                    String storedHashedPassword = rs.getString("hashed_password");

                    if (BCrypt.checkpw(password.getText(), storedHashedPassword)) {
                        User user = new User(
                                rs.getString("full_name"),
                                rs.getString("email"),
                                rs.getString("mobile_number"),
                                rs.getString("gender"),
                                rs.getString("Nationality"),
                                rs.getString("username")
                        );
                        // Load the MainPanel.fxml file
                        loadMainPanel(user);
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Invalid username and password.");
                        username.requestFocus();
                    }
                } else {
                    showAlert(Alert.AlertType.ERROR, "Invalid username and password.");
                    username.requestFocus();
                }
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
    }
    @FXML
    private void showRegisterStage() throws IOException {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("RegisterView.fxml")));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("User Registration");
        stage.show();
    }
    private boolean isValidated() {
        window = loginButton.getScene().getWindow();
        if (username.getText().isEmpty() || username.getText().length() < 5 || username.getText().length() > 25) {
            showAlert(Alert.AlertType.ERROR, "Invalid username.");
            username.requestFocus();
        } else if (password.getText().isEmpty() || password.getText().length() < 5 || password.getText().length() > 25) {
            showAlert(Alert.AlertType.ERROR, "Invalid password.");
            password.requestFocus();
        } else {
            return true;
        }
        return false;
    }
    private void showAlert(Alert.AlertType type, String content) {
        Alert alert = new Alert(type);
        alert.initOwner(window);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void loadMainPanel(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPanelView.fxml"));
            Parent root = loader.load();

            // Get the controller instance from the loader
            MainPanelController mainPanelController = loader.getController();

            // Call the handleLogin method in MainPanelController
            mainPanelController.handleLogin(username.getText(), user);

            Stage stage = (Stage) loginButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Main Panel");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // You can perform initialization tasks here
    }
}
