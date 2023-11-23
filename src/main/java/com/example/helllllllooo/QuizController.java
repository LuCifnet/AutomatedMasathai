package com.example.helllllllooo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;

public class QuizController {

    public static int correct;
    public static int wrong;
    public Label question;

    private static int TIME_LIMIT_SECONDS = 300; // 5 minutes
    @FXML
    public Button opt1, opt2, opt3, opt4, submitButton;
    
    public Button nextButton;
    public Button prevButton;
    public Label timerLabel;
    public ImageView nationality;
    public Label nameLabel;
    public Label genderLabel;
    private Timeline timer;
    private int counter =0;

    private String[] userAnswers = new String[20];


    @FXML
    private Label errorMessage;

    private Alert currentAlert; // Global variable to store the currently displayed alert

    private String line;


    private void setupTimer() {
        timer = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    updateTimerLabel();
                    TIME_LIMIT_SECONDS--;

                    if (TIME_LIMIT_SECONDS <= 0) {
                        timer.stop();
                        handleTimerExpiration();
                    }
                })
        );
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    private void updateTimerLabel() {
        int minutes = TIME_LIMIT_SECONDS / 60;
        int seconds = TIME_LIMIT_SECONDS % 60;
        timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
    }

    private void handleTimerExpiration() {
        if (!allQuestionsAnswered()) {
            // If not all questions are answered, show a failure alert
            showAlert("Time's Up!", "You did not submit in time. You have failed the quiz.", Alert.AlertType.ERROR);

            // Show results with 0 correct answers and all questions marked as wrong
            correct = 0;
            wrong = 17;
            showResults();
        }
    }


    @FXML
    private void initialize() {
        loadQuestions();
        setupTimer();
    }
    private final boolean[] questionAnswered = new boolean[20];


    private void loadQuestions() {
        try {
            // Read the question from a text file
            BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/com/example/helllllllooo/question_list1.txt"));

            // Read the question for the current counter value
            for (int i = 0; i <= counter; i++) {
                line = reader.readLine();
                if (line == null) {
                    // End of file reached, reset counter and return
                    counter = 0;
                    reader.close();
                    return;
                }
            }

            // Assuming each question has four options separated by commas
            String[] parts = line.split(",");
            if (parts.length == 5) {
                String questionText = "Question " + (counter + 1) + ": " + parts[0];
                question.setText(questionText);  // Concatenate "Question1" with the actual question

                opt1.setText(parts[1]);      // Option 1
                opt2.setText(parts[2]);      // Option 2
                opt3.setText(parts[3]);      // Option 3
                opt4.setText(parts[4]);      // Option 4
            }

            // Close the reader
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    boolean checkAnswer(String answer, int counter) {
        String fileName = "src/main/resources/com/example/helllllllooo/answer.txt";
        String correctAnswer = "";

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            int lineCount = 0;

            // Read the file line by line until reaching the desired counter value
            while (lineCount <= counter) {
                correctAnswer = br.readLine();
                lineCount++;
            }

            // Compare the correct answer with the provided answer
            return answer.equals(correctAnswer.trim());  // Use trim to remove leading/trailing whitespaces

        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception, e.g., log the error or return a default value
            return false;
        }
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

            // Store the user's selected answer
            userAnswers[counter] = answer;

            // Compare the user's selected option with the correct answer for the current question
            if (checkAnswer(answer, counter)) {
                correct++;
            } else {
                wrong++;
            }

            if (allQuestionsAnswered()) {
                // Enable the Next button when all questions are answered
                nextButton.setDisable(false);
            } else {
                counter++;
                loadQuestions();
            }
        } else {
            // If the question is already answered, close the current alert and show a new one
            closeCurrentAlert();
            showAlert("Error", "Please click Submit button to show the Result", Alert.AlertType.ERROR);
        }
    }

    private void closeCurrentAlert() {
        if (currentAlert != null) {
            currentAlert.close();
        }

    }


    @FXML
    public void submitButtonClicked(ActionEvent event) {
        if (allQuestionsAnswered()) {
            // Hide the error message
            if (errorMessage != null) {
                errorMessage.setVisible(false);
            }
            showResults(); // Call showResults only when all questions are answered
            saveResultsToFile();
        } else {
            // Show an alert instead of the error message
            showAlert("Error", "Please answer all questions before submitting", Alert.AlertType.ERROR);
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
        // Hide both alerts first
        hideAlerts();

        // Handle logic for the 'Next' button
        if (allQuestionsAnswered()) {
            // If all questions are answered, you can proceed
            if (counter < 9) {
                counter++;
                loadQuestions();
            }
        } else {
            // If the current question is not answered, show an Alert
            showAlert("Error", "Please answer the current question first", Alert.AlertType.ERROR);
        }
    }

    private void hideAlerts() {
        closeCurrentAlert();
    }

    @FXML
    public void prevButtonClicked(ActionEvent event) {
        // Hide both alerts first
        hideAlerts();

        // Handle logic for the 'Prev' button
        if (counter > 0) {
            counter--;
            loadQuestions();

            // Enable the Next button since we are going back to a previous question
            nextButton.setDisable(false);

            // Disable the Prev button if we are on the first question
            prevButton.setDisable(counter == 0);

            // Disable the Next button if the current question is already answered
            nextButton.setDisable(questionAnswered[counter]);
        } else {
            // If the user is trying to go back from the first question, show a warning Alert
            showAlert("Warning", "Cannot go back from the first question", Alert.AlertType.WARNING);
            prevButton.setDisable(true);
        }
    }


    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private void saveResultsToFile() {
        String fileName = "src/main/resources/com/example/helllllllooo/test_results.txt";

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("Results:\n");

            for (int i = 0; i < questionAnswered.length; i++) {
                writer.write("Question " + (i + 1) + ":\n");

                // Write the user's selected answer
                writer.write("User's Answer: " + getSelectedAnswer(i) + "\n");

                // Write the correct answer
                writer.write("Correct Answer: " + getCorrectAnswer(i) + "\n\n");
            }

            writer.write("Total Correct Answers: " + correct + "\n");
            writer.write("Total Wrong Answers: " + wrong + "\n");
            writer.write("Total Questions: " + questionAnswered.length + "\n");
            writer.write("Total Score: " + correct + "/" + questionAnswered.length + "\n");
            writer.write("------------------------------------------------------------------");

            // Optionally, you can write more information to the file if needed
            // For example, you can write the user's name, time taken, etc.
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception, e.g., log the error or show an alert
        }
    }

    // Helper method to get the user's selected answer for a given question
    private String getSelectedAnswer(int questionIndex) {
        return userAnswers[questionIndex];
    }

    // Helper method to get the correct answer for a given question
    private String getCorrectAnswer(int questionIndex) {
        String fileName = "src/main/resources/com/example/helllllllooo/answer.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            int lineCount = 0;

            // Read the file line by line until reaching the desired counter value
            while (lineCount <= questionIndex) {
                String correctAnswer = br.readLine();
                lineCount++;

                if (lineCount == questionIndex + 1) {
                    return correctAnswer.trim();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception, e.g., log the error or return a default value
        }

        return "Correct answer for Question " + (questionIndex + 1);
    }


    }