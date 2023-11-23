package com.example.helllllllooo;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainPanelController implements Initializable {
    public Button page01;
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
    private BorderPane borderPane;

    @FXML
    private AreaChart<?, ?> chartPurchase;

    @FXML
    private AreaChart<?, ?> chartSale;

    @FXML
    private LineChart<?, ?> chartReceipt;
    @FXML
    private ImageView nationality;

    private List<Button> menus;

    public String loggedInUsername;
    private String fullName;
    private String email;
    private String gender;
    private String mobile;

    public MainPanelController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        menus = List.of(/* Add your buttons here */);
    }

    @FXML
    private void changeButtonBackground(Button clickedButton) {
        for (Button otherButton : menus) {
            if (clickedButton.equals(otherButton)) {
                clickedButton.setStyle("-fx-text-fill:#f0f0f0;-fx-background-color:#2b2a26;");
            } else {
                otherButton.setStyle("-fx-text-fill:#f0f0f0;-fx-background-color:#404040;");
            }
        }
    }

    @FXML
    private void clear() {
        borderPane.setCenter(null);
    }

    @FXML
    private Pair<Parent, HomeViewController> loadFXML(String fileName) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fileName + ".fxml"));
        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        HomeViewController controller = loader.getController();

        return new Pair<>(parent, controller);
    }
    @FXML
    private void loadFXML1(String fileName) {
        Parent parent;
        try {
            parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fileName + ".fxml")));
            // Create a FadeTransition with a duration of 1 second
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), parent);

            // Set the starting and ending opacity values
            fadeTransition.setFromValue(0.0);
            fadeTransition.setToValue(1.0);

            // Play the fade transition
            fadeTransition.play();
            borderPane.setCenter(parent);

        } catch (IOException ex) {
            ex.printStackTrace();
            // Handle the exception appropriately
        }
    }

    @FXML
    private void close() throws IOException {
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LoginView.fxml")));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("User Login");

        stage.show();
    }

    @FXML
    private void loadPage01View(ActionEvent e) {
        loadFXML1("Page01View");
        changeButtonBackground((Button) e.getSource());
    }




    @FXML
    private void loadHomeView(ActionEvent e) throws Exception {
        // Set the center of the borderPane to the main view
        clear(); // Clear existing content
        Pair<Parent, HomeViewController> result = loadFXML("HomeView");;
        HomeViewController homeViewController = result.getValue();

        // Set the logged-in username in the HomeViewController
        homeViewController.setLoggedInUsername(loggedInUsername);

        // Set the center of the borderPane to the loaded view
        borderPane.setCenter(result.getKey());
        changeButtonBackground((Button) e.getSource());
    }

    private Button getCurrentMenuButton() {
        return null;
    }

    public void initializeUser(String userName) {
        loggedInUsername = userName;
    }

    public void handleLogin(String userName, User user) {
        greetingLabel.setText("Welcome, " + userName + "!");
        updateLabels(user);
        updateFlag(user);
    }

    private void updateLabels(User user) {
        if (user != null) {
            loggedInUsername=user.getUsername();
            fullName=user.getName();
            nameLabel.setText("Name: " + user.getName());
            email=user.getEmail();
            emailLabel.setText("Email: " + user.getEmail());
            mobile=user.getMobile();
            mobileLabel.setText("Mobile no: " + user.getMobile());
            gender=user.getGender();
            genderLabel.setText("Gender: " + user.getGender());
            System.out.println(loggedInUsername);
        } else {
            nameLabel.setText("Name: N/A");
            emailLabel.setText("Email: N/A");
            mobileLabel.setText("Mobile no: N/A");
            genderLabel.setText("Gender: N/A");
        }

    }
    private void updateFlag(User user) {
        if (user != null) {
            nameLabel.setText("Name: " + user.getName());

            String userFlag = user.getUserFlag();

            if (userFlag != null) {
                String imagePath = "/com/example/helllllllooo/";

                if ("Nepal".equals(userFlag)) {
                    imagePath += "nepalflag.png";
                } else if ("Malaysia".equals(userFlag)) {
                    imagePath += "malaysiaflag.png";
                } else if ("Thailand".equals(userFlag)) {
                    imagePath += "thaiFlag.png";
                } else if ("Singapore".equals(userFlag)) {
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
            } else {
                System.out.println("User flag is null");
            }
        } else {
            System.out.println("User is null");
        }
    }
    public String getLoggedInUsername() {
        return loggedInUsername;
    }

    public void chooseImageClicked(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        // Show open file dialog
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            // Load the selected image into the ImageView
            Image selectedImage = new Image(selectedFile.toURI().toString());
            userImage.setImage(selectedImage);
        }
    }
}
