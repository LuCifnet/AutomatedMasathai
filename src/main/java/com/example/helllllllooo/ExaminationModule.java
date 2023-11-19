//package com.example.helllllllooo;// ExaminationModule.java
//// The class with corrections and assuming you have a Question class
//import com.example.helllllllooo.Question;
//import javafx.animation.Animation;
//import javafx.animation.KeyFrame;
//import javafx.animation.Timeline;
//import javafx.application.Platform;
//import javafx.geometry.Insets;
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//import javafx.util.Duration;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.util.ArrayList;
//import java.util.List;
//
//public class ExaminationModule {
//
//    private final String currentUserName;
//    private List<Question> questions;
//    private int currentQuestionIndex = 0;
//    private int totalQuestions;
//    private int timeSeconds = 300; // 5 minutes
//    private Timeline timeline;
//    private Label timerLabel;
//    private Label questionLabel;
//    private ToggleGroup answerGroup;
//    private Button nextButton;
//    private Button prevButton;
//    private Button submitButton;  // Added Submit button
//    private Runnable onExamCompletion;
//
//    public ExaminationModule(String currentUserName) {
//        this.currentUserName = currentUserName;
//        loadQuestionsFromFile();
//
//        timerLabel = new Label();
//        questionLabel = new Label();
//        answerGroup = new ToggleGroup();
//        nextButton = new Button("Next");
//        prevButton = new Button("Previous");
//        submitButton = new Button("Submit");  // Added Submit button
//
//        VBox optionsBox = new VBox(5);
//        for (int i = 0; i < 4; i++) {
//            RadioButton radioButton = new RadioButton();
//            radioButton.setToggleGroup(answerGroup);
//            optionsBox.getChildren().add(radioButton);
//        }
//
//        nextButton.setOnAction(e -> handleNavigation(1));
//        prevButton.setOnAction(e -> handleNavigation(-1));
//        submitButton.setOnAction(e -> handleSubmit());  // Added handler for Submit button
//
//        BorderPane questionArea = new BorderPane();
//        questionArea.setTop(questionLabel);
//        questionArea.setCenter(optionsBox);
//
//        initializeTimer();
//
//        updateQuestion();
//
//        BorderPane layout = new BorderPane();
//        layout.setTop(createHeader());
//        layout.setCenter(questionArea);
//        layout.setBottom(createNavigationButtons());
//        layout.setRight(submitButton);  // Added Submit button
//
//        Scene scene = new Scene(layout, 600, 400);
//        Stage stage = new Stage();
//        stage.setScene(scene);
//        stage.setTitle("Online Exam");
//        stage.show();
//    }
//
//    private HBox createHeader() {
//        Label nameLabel = new Label("Candidate: " + currentUserName);
//        nameLabel.setStyle("-fx-font-weight: bold;");
//        HBox headerBox = new HBox(10, nameLabel, timerLabel);
//        headerBox.setPadding(new Insets(10));
//        return headerBox;
//    }
//
//    private VBox createNavigationButtons() {
//        HBox buttonBox = new HBox(10, prevButton, nextButton);
//        buttonBox.setPadding(new Insets(10));
//
//        VBox vbox = new VBox(10, createHeader(), buttonBox);
//        vbox.setPadding(new Insets(10));
//        return vbox;
//    }
//
//    private void initializeTimer() {
//        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
//            if (timeSeconds > 0) {
//                timeSeconds--;
//                updateTimerLabel();
//            } else {
//                timeline.stop();
//                showAlert("Time's up! Exam completed.");
//                handleSubmit();  // Automatically submit when time is up
//            }
//        }));
//        timeline.setCycleCount(Animation.INDEFINITE);
//        timeline.play();
//    }
//
//    private void updateTimerLabel() {
//        int minutes = timeSeconds / 60;
//        int seconds = timeSeconds % 60;
//        timerLabel.setText(String.format("Time: %02d:%02d", minutes, seconds));
//    }
//
//    private void updateQuestion() {
//        if (currentQuestionIndex >= 0 && currentQuestionIndex < totalQuestions) {
//            Question currentQuestion = questions.get(currentQuestionIndex);
//            questionLabel.setText((currentQuestionIndex + 1) + ". " + currentQuestion.getQuestionText());
//
//            for (int i = 0; i < 4; i++) {
//                RadioButton radioButton = (RadioButton) answerGroup.getToggles().get(i);
//                radioButton.setText(currentQuestion.getOptions()[i]);
//            }
//        }
//    }
//
//    private void handleNavigation(int direction) {
//        if (answerGroup.getSelectedToggle() != null) {
//            recordAnswer();
//            currentQuestionIndex += direction;
//            updateQuestion();
//            answerGroup.selectToggle(null);
//        } else {
//            showAlert("Please select an answer before moving to the next question.");
//        }
//    }
//
//    private void recordAnswer() {
//        if (currentQuestionIndex >= 0 && currentQuestionIndex < totalQuestions) {
//            Question currentQuestion = questions.get(currentQuestionIndex);
//            int selectedAnswerIndex = answerGroup.getToggles().indexOf(answerGroup.getSelectedToggle());
//            currentQuestion.setCandidateAnswer(selectedAnswerIndex);
//        }
//    }
//
//    private void handleSubmit() {
//        if (answerGroup.getSelectedToggle() != null) {
//            recordAnswer();
//            saveResultsToFile("test_result.txt");
//            showAlert("Exam submitted. Results saved.");
//            if (onExamCompletion != null) {
//                onExamCompletion.run();
//            }
//        } else {
//            showAlert("Please select an answer before submitting the exam.");
//        }
//    }
//
//    private void saveResultsToFile(String file) {
//    }
//
//    private void showAlert(String message) {
//        Platform.runLater(() -> {
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("Information");
//            alert.setHeaderText(null);
//            alert.setContentText(message);
//            alert.showAndWait();
//        });
//    }
//
//    private void loadQuestionsFromFile() {
//        questions = new ArrayList<>();
//        try (BufferedReader reader = Files.newBufferedReader(Path.of("question_list.txt"))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                String[] parts = line.split(";");
//                String questionText = parts[0];
//                String[] options = new String[parts.length - 1];
//                System.arraycopy(parts, 1, options, 0, options.length);
//                questions.add(new Question(questionText, options));
//            }
//            totalQuestions = questions.size();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void setOnExamCompletion(Runnable onExamCompletion) {
//        this.onExamCompletion = onExamCompletion;
//    }
//}
