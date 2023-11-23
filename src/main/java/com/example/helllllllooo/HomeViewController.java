package com.example.helllllllooo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HomeViewController implements Initializable {

    public ImageView userImage;
    @FXML
    private Label greetingLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label genderLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label mobileLabel;
    @FXML
    private ImageView nationality;

    private String fullName;
    private String email;
    private String gender;
    private String mobile;
    private String loggedInUsername;
    private String nationalityText;
    private ResultController resultController;



    public HomeViewController() {
    }

    @FXML
    private void chooseImageClicked(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        // Show open file dialog
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            // Load the selected image into the ImageView
            Image selectedImage = new Image(selectedFile.toURI().toString());
            userImage.setImage(selectedImage);
        }
    }

    public void setLoggedInUsername(String loggedInUsername) {
        try {
            this.loggedInUsername = loggedInUsername;
            greetingLabel.setText("Welcome, " + loggedInUsername + "!");
            refreshUserDetails();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception, show an alert, or log it as needed
        }
    }

    public void refreshUserDetails() {
        try {
            getDetails(loggedInUsername);
            nameLabel.setText("Name: " + fullName);
            mobileLabel.setText("Mobile no: " + mobile);
            genderLabel.setText("Gender: " + gender);
            emailLabel.setText("Email: " + email);

            String imagePath = "/com/example/helllllllooo/";

            if ("Nepal".equals(nationalityText)) {
                imagePath += "nepalflag.png";
            } else if ("Malaysia".equals(nationalityText)) {
                imagePath += "malaysiaflag.png";
            } else if ("Thailand".equals(nationalityText)) {
                imagePath += "thaiFlag.png";
            } else if ("Singapore".equals(nationalityText)) {
                imagePath += "singaporeFlag.png";
            }

            InputStream stream = getClass().getResourceAsStream(imagePath);

            if (stream != null) {
                Image image = new Image(stream);
                nationality.setFitHeight(130);
                nationality.setFitWidth(190);
                nationality.setImage(image);
            } else {
                System.out.println("Image not found: " + imagePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception, show an alert, or log it as needed
        }
    }

    private void getDetails(String username) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "select * from users WHERE username = ?";
        try {
            Connection con = DbConnection.getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, username);
            rs = ps.executeQuery();

            if (rs.next()) {
                fullName = rs.getString("full_name");
                email = rs.getString("email");
                mobile = rs.getString("mobile_number");
                gender = rs.getString("gender");
                nationalityText = rs.getString("Nationality");
            } else {
                System.out.println("No user found for username: " + username);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialization code, if needed
    }

}
