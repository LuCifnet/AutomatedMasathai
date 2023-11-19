package com.example.helllllllooo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Question {


    private static final String filePath = "src/main/resources/com/example/helllllllooo/question_list1.txt";
    private List<String> lines;

    private String question;
    private String choiceA;
    private String choiceB;
    private String choiceC;
    private String choiceD;

    public Question() {
        lines = readLinesFromFile();
    }

    public Question(String question, String choiceA, String choiceB, String choiceC, String choiceD) {
        this.question = question;
        this.choiceA = choiceA;
        this.choiceB = choiceB;
        this.choiceC = choiceC;
        this.choiceD = choiceD;
    }

    public Question(Object question, String choiceA, String choiceB, String choiceC, String choiceD) {
    }

    public List<String> readLinesFromFile() {
        List<String> fileLines = new ArrayList<>();

        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                fileLines.add(line);
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileLines;
    }


    public List<String> getChoices() {
        List<String> choices = new ArrayList<>();
        choices.add(choiceA);
        choices.add(choiceB);
        choices.add(choiceC);
        choices.add(choiceD);
        return choices;
    }

    public String getCorrectAnswer() {
        return "Implement logic to get correct answer based on question";
    }

    public int getTotalNumberOfLinesInFile() {
        return 0;
    }

    public List<String> getChoices(int choiceStartIndex, int choiceEndIndex) {
        return null;
    }

    public String getQuestion(int questionIndex) {
        return question;
    }

    public String getQuestion() {
        return null;
    }
}
