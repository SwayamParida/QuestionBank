package QuestionBank;

import java.io.*;

public class Question {

    private File questionFile;
    private int year, marks;
    private String topic, type, session, difficulty;

    Question(File question, int year, String topic, String type, String session, int marks, String difficulty) {
        setQuestionFile(question);
        setYear(year);
        setTopic(topic);
        setType(type);
        setSession(session);
        setMarks(marks);
        setDifficulty(difficulty);
    }
    Question(String question, int year, String topic, String type, String session, int marks, String difficulty) {
        setQuestionFile(question);
        setYear(year);
        setTopic(topic);
        setType(type);
        setSession(session);
        setMarks(marks);
        setDifficulty(difficulty);
    }

    public File getQuestionFile() { return questionFile; }
    public int getYear() { return year; }
    public String getTopic() { return topic; }
    public String getType() { return type; }
    public String getSession() { return session; }
    public int getMarks() { return marks; }
    public String getDifficulty() { return difficulty; }

    public void setQuestionFile(File questionFile) { this.questionFile = questionFile; }
    public void setQuestionFile(String question) { this.questionFile = new File(question); }
    public void setYear(int year) { this.year = year; }
    public void setTopic(String topic) {this.topic = topic; }
    public void setType(String type) {this.type = type; }
    public void setSession(String session) { this.session = session; }
    public void setMarks(int marks) { this.marks = marks; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

    @Override
    public String toString() {
        return getQuestionPreview(Main.PREVIEW_LENGTH);
    }
    public String getEntireQuestion() {
        FileReader inputStream = null;
        BufferedReader inputBuffer;
        String s = new String();
        try {
            inputStream = new FileReader(questionFile);
            inputBuffer = new BufferedReader(inputStream);
            String line;
            while ((line = inputBuffer.readLine()) != null)
                s = s + line + Main.NEWLINE;
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }
    public String getQuestionPreview(int length) {
        return getEntireQuestion().substring(0, length) + Main.ELLIPSIS + "(" + marks + ")";
    }
}
