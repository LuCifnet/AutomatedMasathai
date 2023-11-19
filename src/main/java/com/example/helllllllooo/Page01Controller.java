package com.example.helllllllooo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Page01Controller {

    @FXML
    private Button playquizbtn;

    @FXML
    private void initialize() {
        playquizbtn.setOnAction(event -> openQuiz());
    }

    private void openQuiz() {
        try {
            Stage thisStage = (Stage) playquizbtn.getScene().getWindow();
            thisStage.close();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("quiz.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
