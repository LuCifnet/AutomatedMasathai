package com.example.helllllllooo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.util.*;

public class QuizController {

    public static int correct;
    public static int wrong;
    public Label question;


    @FXML
    public Button opt1, opt2, opt3, opt4, submitButton;

    private static final int TIME_LIMIT_SECONDS = 600; // 10 minutes
    private Timeline timer;
    private int counter = 0;

    @FXML
    private Label errorMessage;

    @FXML
    private Label errorMessage_next;
    private void setupTimer() {
        timer = new Timeline(new KeyFrame(Duration.seconds(TIME_LIMIT_SECONDS), event -> showResults()));
        timer.setCycleCount(1); // Run once
        timer.play();
    }


    @FXML
    private void initialize() {
        loadQuestions();
        setupTimer();
    }

    private boolean[] questionAnswered = new boolean[10];

    private void loadQuestions() {

        if (!questionAnswered[counter]) {
            if (counter == 0) { // Question 1
                question.setText("1. How many consonants are there in the English alphabet?");
                opt1.setText("19");
                opt2.setText("20");
                opt3.setText("21");
                opt4.setText("22");
            }
            if (counter == 1) { // Question 2
                question.setText("2. Who invented the Light bulb?");
                opt1.setText("Thomas Alva Edison");
                opt2.setText("Alexander Fleming");
                opt3.setText("Charles Babbage");
                opt4.setText("Albert Einstein");
            }
            if (counter == 2) { //Question 3
                question.setText("3. In the Solar System, farthest planet from the Sun is");
                opt1.setText("Jupiter");
                opt2.setText("Saturn");
                opt3.setText("Uranus");
                opt4.setText("Neptune");
            }
            if (counter == 3) { //Question 4
                question.setText("4. Largest moon in the Solar System?");
                opt1.setText("Titan");
                opt2.setText("Ganymede");
                opt3.setText("Moon");
                opt4.setText("Europa");
            }
            if (counter == 4) {//Question 5
                question.setText("5. Which of these is 'not' a property of metal?");
                opt1.setText("Good Conduction");
                opt2.setText("Malleable");
                opt3.setText("Non Ductile");
                opt4.setText("Sonourous");
            }
            if (counter == 5) { //Question 6
                question.setText("6. Who discovered Pasteurisation?");
                opt1.setText("Alexander Fleming");
                opt2.setText("Louis Pasteur");
                opt3.setText("Simon Pasteur");
                opt4.setText("William Pasteur");
            }
            if (counter == 6) { //Question 7
                question.setText("7. Hydrochloric acid (HCl) is produced by -?");
                opt1.setText("Small Intestine");
                opt2.setText("Liver");
                opt3.setText("Oesophagus");
                opt4.setText("Stomach");
            }
            if (counter == 7) { //Question 8
                question.setText("8. The fastest animal in the world is -");
                opt1.setText("Lion");
                opt2.setText("Blackbuck");
                opt3.setText("Cheetah");
                opt4.setText("Quarter Horse");
            }
            if (counter == 8) { //Question 9
                question.setText("9. Complementary colour of Red is -");
                opt1.setText("Blue");
                opt2.setText("Green");
                opt3.setText("Yellow");
                opt4.setText("Pink");
            }
            if (counter == 9) { //Question 10
                question.setText("10. World Environment Day is on -");
                opt1.setText("5th June");
                opt2.setText("5th July");
                opt3.setText("15th June");
                opt4.setText("25th June");
            }

        } else {
            // If the question is already answered, show an appropriate message or handle it as needed
            errorMessage.setVisible(true); // Show an error message
        }


    }

    boolean checkAnswer(String answer) {

        if (counter == 0) {
            if (answer.equals("21")) {
                return true;
            } else {
                return false;
            }
        }
        if (counter == 1) {
            if (answer.equals("Thomas Alva Edison")) {
                return true;
            } else {
                return false;
            }
        }
        if (counter == 2) {
            if (answer.equals("Neptune")) {
                return true;
            } else {
                return false;
            }
        }
        if (counter == 3) {
            if (answer.equals("Ganymede")) {
                return true;
            } else {
                return false;
            }
        }
        if (counter == 4) {
            if (answer.equals("Non Ductile")) {
                return true;
            } else {
                return false;
            }
        }
        if (counter == 5) {
            if (answer.equals("Louis Pasteur")) {
                return true;
            } else {
                return false;
            }
        }
        if (counter == 6) {
            if (answer.equals("Stomach")) {
                return true;
            } else {
                return false;
            }
        }
        if (counter == 7) {
            if (answer.equals("Cheetah")) {
                return true;
            } else {
                return false;
            }
        }
        if (counter == 8) {
            if (answer.equals("Green")) {
                return true;
            } else {
                return false;
            }
        }
        if (counter == 9) {
            if (answer.equals("5th June")) {
                return true;
            } else {
                return false;
            }
        }
        return false;


    }

    public void opt1clicked(ActionEvent event) {
        handleAnswer(opt1.getText());
    }

    @FXML
    public void opt2clicked(ActionEvent event) {
        handleAnswer(opt2.getText());
    }

    @FXML
    public void opt3clicked(ActionEvent event) {
        handleAnswer(opt3.getText());
    }

    @FXML
    public void opt4clicked(ActionEvent event) {
        handleAnswer(opt4.getText());
    }

    private void handleAnswer(String answer) {
        if (!questionAnswered[counter]) {
            questionAnswered[counter] = true; // Mark the question as answered
            checkAnswer(answer);
            if (checkAnswer(answer)) {
                correct++;
            } else {
                wrong++;
            }
            if (counter == 9) {
                showResults();
            } else {
                counter++;
                loadQuestions();
            }
        } else {
            // If the question is already answered, show an appropriate message or handle it as needed
            errorMessage.setVisible(true); // Show an error message
        }
    }


    @FXML
    public void submitButtonClicked(ActionEvent event) {
        if (allQuestionsAnswered()) {
            errorMessage.setVisible(false); // Hide the error message
            showResults(); // Call showResults directly
        } else {
            errorMessage.setVisible(true); // Show the error message
        }
    }

    private boolean allQuestionsAnswered() {
        for (boolean answered : questionAnswered) {
            if (!answered) {
                return false;
            }
        }
        return true;
    }

    private void showResults() {
        timer.stop();


        try {
            Stage thisStage = (Stage) submitButton.getScene().getWindow();
            thisStage.close();

            // The rest of your code remains the same
            FXMLLoader resultLoader = new FXMLLoader(getClass().getResource("result.fxml"));
            Scene resultScene = new Scene(resultLoader.load());

            ResultController resultController = resultLoader.getController();
            resultController.setResults(correct, wrong);

            Stage resultStage = new Stage();
            resultStage.setScene(resultScene);
            resultStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void nextButtonClicked(ActionEvent event) {
        // Hide both error messages first
        errorMessage.setVisible(false);
        errorMessage_next.setVisible(false);

        // Handle logic for the 'Next' button
        if (allQuestionsAnswered()) {
            // If all questions are answered, you can proceed
            if (counter < 9) {
                counter++;
                loadQuestions();
            }
        } else {
            // If the current question is not answered, show an error message
            errorMessage_next.setVisible(true);
        }
    }

    @FXML
    public void prevButtonClicked(ActionEvent event) {
        errorMessage.setVisible(false);

        // Handle logic for the 'Prev' button
        if (counter > 0) {
            counter--;
            loadQuestions();
        } else {
            // If the user is trying to go back from the first question, show a warning message
            errorMessage.setVisible(true);

            // Use a Timeline to hide the error message after a delay (e.g., 2 seconds)
            Timeline timeline = new Timeline(new KeyFrame(
                    Duration.seconds(2),
                    e -> errorMessage.setVisible(false)
            ));
            timeline.setCycleCount(1); // Run once
            timeline.play();
        }

    }}


