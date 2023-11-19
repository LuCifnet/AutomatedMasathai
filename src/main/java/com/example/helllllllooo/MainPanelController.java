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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Ramesh Godara
 */
public class MainPanelController implements Initializable {


    @FXML
    private BorderPane borderPane;

    @FXML
    private AreaChart<?, ?> chartPurchase;

    @FXML
    private AreaChart<?, ?> chartSale;

    @FXML
    private LineChart<?, ?> chartReceipt;

    @FXML
    private Label greetingLabel;

    private List<Button> menus;

    public MainPanelController() {
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize the menus list with your actual buttons
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

    public void handleLogin(String userName) {
        greetingLabel.setText("Welcome, " + userName + "!");
    }

    @FXML
    private void clear() {
        borderPane.setCenter(null);
    }

    @FXML
    private void loadFXML(String fileName) {
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
        loadFXML("Page01View");
        changeButtonBackground((Button) e.getSource());
    }

    @FXML
    private void loadPage02View(ActionEvent e) {
        loadFXML("Page02View");
        changeButtonBackground((Button) e.getSource());
    }

    @FXML
    private void loadPage03View(ActionEvent e) {
        loadFXML("Page03View");
        changeButtonBackground((Button) e.getSource());
    }

    @FXML
    private void loadPage04View(ActionEvent e) {
        loadFXML("Page04View");
        changeButtonBackground((Button) e.getSource());
    }

    @FXML
    private void loadHomeView(ActionEvent e) {
        loadFXML("HomeView");
        changeButtonBackground((Button) e.getSource());
    }
}
