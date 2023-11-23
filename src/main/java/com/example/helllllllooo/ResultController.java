package com.example.helllllllooo;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.ActionEvent;


import java.io.IOException;
import java.util.EventObject;

public class ResultController {


    public ResultController() {
        // Default (no-argument) constructor
    }

    @FXML
    public Label remark, marks, markstext, correcttext, wrongtext;

    @FXML
    public ProgressIndicator correct_progress, wrong_progress;

    private int correct;
    private int wrong;

    private HomeViewController homeViewController;

    public void setHomeViewController(HomeViewController homeViewController) {
        this.homeViewController = homeViewController;
    }




    @FXML
    private void initialize() {
        // Initialization code (unchanged)
    }

    public void setResults(int correct, int wrong) {
        this.correct = correct;
        this.wrong = wrong;

        correcttext.setText("Correct Answers : " + correct);
        wrongtext.setText("Incorrect Answers : " + wrong);

        marks.setText(correct + "/20");
        float correctf = (float) correct / 20;
        correct_progress.setProgress(correctf);

        float wrongf = (float) wrong / 20;
        wrong_progress.setProgress(wrongf);

        markstext.setText(correct + " Marks Scored");

        updateRemarkText();
    }

    private void updateRemarkText() {
        if (correct < 2) {
            remark.setText("Oh no..! You have failed the quiz. It seems that you need to improve your general knowledge. Practice daily! Check your results here.");
        } else if (correct >= 2 && correct < 9) {
            remark.setText("Oops..! You have scored fewer marks. It seems like you need to improve your general knowledge. Check your results here.");
        } else if (correct == 10 || correct <= 16) {
            remark.setText("Congratulations! Its your hard work and determination which helped you to score good marks. Check your results here.");
        } else if (correct == 20 || correct == 17) {
            remark.setText("Congratulations! You have passed the quiz with full marks because of your hard work and dedication towards studies. Keep it up! Check your results here.");
        }
    }

    public void handleBackButton(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPanelView.fxml"));
        Parent homeView = loader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(homeView));
        stage.show();
    }

}
