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

    private final Connection con;

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private Button loginButton;

    private Window window;

    public LoginController() {
        DbConnection dbc = DbConnection.getDatabaseConnection();
        con = DbConnection.getConnection();
    }

    @FXML
    private void login() throws Exception {
        if (isValidated()) {
            PreparedStatement ps;
            ResultSet rs;

            String query = "select * from users WHERE username = ?";
            try {
                ps = con.prepareStatement(query);
                ps.setString(1, username.getText());
                rs = ps.executeQuery();

                if (rs.next()) {
                    String storedHashedPassword = rs.getString("hashed_password");

                    // Check if the provided password matches the stored hashed password
                    if (BCrypt.checkpw(password.getText(), storedHashedPassword)) {
                        // Call handleLogin to update the greeting label in MainPanelController
                        handleLogin(username.getText());

                        // Now, close the login window and open the main panel view
                        Stage stage = (Stage) loginButton.getScene().getWindow();
                        stage.close();

                        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainPanelView.fxml")));

                        Scene scene = new Scene(root);

                        stage.setScene(scene);
                        stage.setTitle("Admin Panel");

                        stage.show();
                    } else {
                        AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                                "Invalid username and password.");
                        username.requestFocus();
                    }
                } else {
                    AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                            "Invalid username and password.");
                    username.requestFocus();
                }
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
    }

    private boolean isValidated() {
        window = loginButton.getScene().getWindow();
        if (username.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "Username text field cannot be blank.");
            username.requestFocus();
        } else if (username.getText().length() < 5 || username.getText().length() > 25) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "Username text field cannot be less than 5 and greater than 25 characters.");
            username.requestFocus();
        } else if (password.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "Password text field cannot be blank.");
            password.requestFocus();
        } else if (password.getText().length() < 5 || password.getText().length() > 25) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "Password text field cannot be less than 5 and greater than 25 characters.");
            password.requestFocus();
        } else {
            return true;
        }
        return false;
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


    // Method to handle the login and update greeting label in MainPanelController
    private void handleLogin(String userName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPanelView.fxml"));
            Parent root = loader.load();

            // Get the controller instance from the loader
            MainPanelController mainPanelController = loader.getController();

            // Call the handleLogin method in MainPanelController
            mainPanelController.handleLogin(userName);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
