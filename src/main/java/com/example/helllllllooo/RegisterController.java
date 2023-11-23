/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.helllllllooo;

import javafx.event.ActionEvent;
import org.mindrot.jbcrypt.BCrypt;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.EventObject;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.stage.Stage;

public class RegisterController implements Initializable {

    public Hyperlink loginHyperlink;
    @FXML
    private TextField fullNameField;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField mobileNumberField;

    @FXML
    private DatePicker birthdatePicker;

    @FXML
    private RadioButton maleRadioButton;

    @FXML
    private RadioButton femaleRadioButton;

    @FXML
    private RadioButton othersRadioButton;

    @FXML
    private ToggleGroup genderToggleGroup;

    @FXML
    private Button submitButton;

    @FXML
    private MenuButton nationalityField;
    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Label messageLabel;
    private EventObject event;

    @FXML
    private void onSubmitButtonClick() {
        String fullName = fullNameField.getText();
        String username = usernameField.getText();
        String email = emailField.getText();
        String mobileNumber = mobileNumberField.getText();
        String birthdate = (birthdatePicker.getValue() != null) ? birthdatePicker.getValue().toString() : "";
        String gender = getSelectedGender();
        String nationality = nationalityField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (validateInput(fullName, username, email, mobileNumber, birthdate, gender, nationality, password, confirmPassword)) {
            String hashedPassword = hashPassword(password);

            // Insert user data into the database
            if (saveUserToDatabase(fullName, username, email, mobileNumber, birthdate, gender, nationality, hashedPassword)) {
                showWelcomeMessageAndNavigateToDashboard(fullName);
                clearFormFields();
            } else {
                showErrorAlert();
            }
        }
    }

    private void showWelcomeMessageAndNavigateToDashboard(String fullName) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Welcome");
        alert.setHeaderText("Welcome to Masathai");
        alert.setContentText("Hello, " + fullName + "! You have successfully registered.");
        alert.showAndWait();

        // Navigate to the dashboard
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPanelView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) submitButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearFormFields() {
        fullNameField.clear();
        usernameField.clear();
        emailField.clear();
        mobileNumberField.clear();
        birthdatePicker.setValue(null);
        maleRadioButton.setSelected(false);
        femaleRadioButton.setSelected(false);
        othersRadioButton.setSelected(false);
        nationalityField.setText("Nationality");
        passwordField.clear();
        confirmPasswordField.clear();
    }

    public void initialize(URL location, ResourceBundle resources) {
        initializeGenderToggleGroup();
        clearFormFields();
    }

    private void initializeGenderToggleGroup() {
        this.genderToggleGroup = new ToggleGroup();
        maleRadioButton.setToggleGroup(this.genderToggleGroup);
        femaleRadioButton.setToggleGroup(this.genderToggleGroup);
        othersRadioButton.setToggleGroup(this.genderToggleGroup);
    }


    public RegisterController() {
        // Default constructor
    }


    private String getSelectedGender() {
        RadioButton selectedRadioButton = (RadioButton) genderToggleGroup.getSelectedToggle();

        if (selectedRadioButton != null) {
            return selectedRadioButton.getText();
        }

        return "Not Specified";
    }

    private boolean validateInput(String fullName, String username, String email, String mobileNumber, String birthdate, String gender, String nationality, String password, String confirmPassword) {
        if (fullName.isEmpty() || username.isEmpty() || email.isEmpty() || mobileNumber.isEmpty() || birthdate.isEmpty() || nationality.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showError("Please fill in all the fields.");
            return false;
        }

        if (!isValidEmail(email)) {
            showError("Invalid email format.");
            return false;
        }

        if (!isValidPhoneNumber(mobileNumber)) {
            showError("Invalid mobile number format.");
            return false;
        }

        if (!isValidPassword(password)) {
            showError("Invalid password. It should be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one digit, and one special symbol.");
            return false;
        }

        if (!password.equals(confirmPassword)) {
            showError("Passwords do not match.");
            return false;
        }

        return true;
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("^[0-9]{10}$");
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 8 && // Minimum length of 8 characters
                password.matches(".*[a-z].*") && // Contains at least one lowercase letter
                password.matches(".*[A-Z].*") && // Contains at least one uppercase letter
                password.matches(".*\\d.*") && // Contains at least one digit
                password.matches(".*[!@#$%^&*()].*"); // Contains at least one special symbol
    }

    private String hashPassword(String password) {
        String salt = BCrypt.gensalt(12);
        return BCrypt.hashpw(password, salt);
    }

    private boolean saveUserToDatabase(String fullName, String username, String email, String mobileNumber, String birthdate, String gender, String nationality, String hashedPassword) {
        try {
            Connection connection = DbConnection.getConnection();
            String insertQuery = "INSERT INTO users (full_name, username, email, mobile_number, hashed_password, birthdate, gender, nationality) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, fullName);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, mobileNumber);
            preparedStatement.setString(5, hashedPassword);
            preparedStatement.setString(6, birthdate);
            preparedStatement.setString(7, gender);
            preparedStatement.setString(8, nationality);

            int rowsAffected = preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Registration Error");
        alert.setContentText("User data insertion failed.");
        alert.showAndWait();
    }


    @FXML
    private void onBackToLoginButtonClick() throws IOException {
        Stage stage = (Stage) submitButton.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LoginView.fxml")));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("User Login");
        stage.show();
    }

    public void nationality(ActionEvent actionEvent) {
        String selectedNationality = ((MenuItem) actionEvent.getSource()).getText();
        nationalityField.setText(selectedNationality);
    }

    @FXML
    private void onGenderSelected(ActionEvent event) {
        RadioButton selectedRadioButton = (RadioButton) event.getSource();

        // Unselect other radio buttons
        maleRadioButton.setSelected(false);
        femaleRadioButton.setSelected(false);
        othersRadioButton.setSelected(false);

        // Select the clicked radio button
        selectedRadioButton.setSelected(true);
    }

}

