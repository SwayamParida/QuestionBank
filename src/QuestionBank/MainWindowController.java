package QuestionBank;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {
    @FXML private ComboBox yearComboBox, topicComboBox, typesComboBox, sessionComboBox, difficultyComboBox;
    @FXML private ListView<Question> questionsPreview;
    @FXML private ListView<String> worksheetPreview;
    @FXML private TextArea questionDisplay;
    @FXML private TextField searchBar;
    @FXML private HBox attributesDisplay;
    @FXML private Button year, session, type, difficulty, topic;

    private QuestionCollection questions;
    private QuestionCollection worksheetQuestions;

    @Override
    public void initialize(URL FXML_file, ResourceBundle resources) {
        questions = new DataBaseReader().getQuestions();
        worksheetQuestions = new QuestionCollection(new ArrayList<>());
        yearComboBox.getItems().addAll(questions.getUniqueYears());
        yearComboBox.setValue("None");
        topicComboBox.getItems().addAll(questions.getUniqueTopics());
        topicComboBox.setValue("None");
        typesComboBox.getItems().addAll(questions.getUniqueTypes());
        typesComboBox.setValue("None");
        sessionComboBox.getItems().addAll(questions.getUniqueSessions());
        sessionComboBox.setValue("None");
        difficultyComboBox.getItems().addAll(questions.getUniqueDifficulties());
        difficultyComboBox.setValue("None");

        addQuestionsToListView(questions.getQuestionsArray());
    }
    private void addQuestionsToListView(ArrayList<Question> questions) {
        questionsPreview.getItems().clear();
        ObservableList questionPreviewList = FXCollections.observableArrayList();
        questionPreviewList.addAll(questions);
        questionsPreview.setItems(questionPreviewList);
    }
    @FXML public void loadQuestion(MouseEvent event) throws NullPointerException {
        Question q = questionsPreview.getSelectionModel().getSelectedItem();
        questionDisplay.setText(q.getEntireQuestion());

        attributesDisplay.setVisible(true);
        year.setText(new Integer(q.getYear()).toString());
        type.setText(q.getType());
        difficulty.setText(q.getDifficulty());
        topic.setText(q.getTopic());
        session.setText(q.getSession());
    }
    @FXML public void filter(ActionEvent event) {
        Object year = yearComboBox.getSelectionModel().getSelectedItem();
        String topic = (String) topicComboBox.getSelectionModel().getSelectedItem();
        String type = (String) typesComboBox.getSelectionModel().getSelectedItem();
        String session = (String) sessionComboBox.getSelectionModel().getSelectedItem();
        String difficulty = (String) difficultyComboBox.getSelectionModel().getSelectedItem();

        addQuestionsToListView(questions.getFilteredQuestions(year, topic, type, session, difficulty));
    }
    @FXML public void search(KeyEvent event) {
        String searchString = searchBar.getText();
        if (event.getCode().equals(KeyCode.ENTER)) {
            ObservableList<Question> questionsInView_OL = questionsPreview.getItems();
            ArrayList<Question> questionsInView_AL = new ArrayList<>();
            for (Question q : questionsInView_OL)
                questionsInView_AL.add(q);
            QuestionCollection searchResults = new QuestionCollection(questionsInView_AL).search(searchString);
            addQuestionsToListView(searchResults.getQuestionsArray());
        } else if (event.getCode().equals(KeyCode.BACK_SPACE)) {
            if (searchBar.getText().isEmpty())
                filter(null);
        }
    }
    @FXML public void addToWorksheet(MouseEvent event) {
        Question selectedQuestion = questionsPreview.getSelectionModel().getSelectedItem();
        try {
            worksheetPreview.getItems().add(selectedQuestion.getQuestionPreview(Main.WORKSHEET_PREVIEW_LENGTH));
        } catch (StringIndexOutOfBoundsException e) {
            worksheetPreview.getItems().add(selectedQuestion.getQuestionPreview(selectedQuestion.getEntireQuestion().length()));
        }
        worksheetQuestions.addQuestion(selectedQuestion);
    }
    @FXML public void popupMenu(MouseEvent event) {
        if (event.getButton() == MouseButton.SECONDARY) {
            ContextMenu popupMenu = new ContextMenu();
            MenuItem delete = new MenuItem("Delete");
            delete.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    int selectedQuestionIndex = worksheetPreview.getSelectionModel().getSelectedIndex();
                    worksheetPreview.getItems().remove(selectedQuestionIndex);
                    worksheetQuestions.removeQuestion(selectedQuestionIndex);
                }
            });
            popupMenu.getItems().addAll(delete);
            popupMenu.show(worksheetPreview, event.getScreenX(), event.getScreenY());
        }
    }
    @FXML public void setYearComboBox(MouseEvent event) {
        yearComboBox.setValue(Integer.parseInt(year.getText()));
        topicComboBox.setValue("None");
        typesComboBox.setValue("None");
        sessionComboBox.setValue("None");
        difficultyComboBox.setValue("None");
    }
    @FXML public void setSessionComboBox(MouseEvent event) {
        sessionComboBox.setValue(session.getText());
        topicComboBox.setValue("None");
        typesComboBox.setValue("None");
        yearComboBox.setValue("None");
        difficultyComboBox.setValue("None");
    }
    @FXML public void setDifficultyComboBox(MouseEvent event) {
        difficultyComboBox.setValue(difficulty.getText());
        topicComboBox.setValue("None");
        typesComboBox.setValue("None");
        sessionComboBox.setValue("None");
        yearComboBox.setValue("None");
    }
    @FXML public void setTypesComboBox(MouseEvent event) {
        typesComboBox.setValue(type.getText());
        topicComboBox.setValue("None");
        yearComboBox.setValue("None");
        sessionComboBox.setValue("None");
        difficultyComboBox.setValue("None");
    }
    @FXML public void setTopicComboBox(MouseEvent event) {
        topicComboBox.setValue(topic.getText());
        yearComboBox.setValue("None");
        typesComboBox.setValue("None");
        sessionComboBox.setValue("None");
        difficultyComboBox.setValue("None");
    }
    @FXML public void newWorksheet(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Worksheet");
        File worksheet = fileChooser.showSaveDialog(Main.stage);
        try {
            PrintWriter writer = new PrintWriter(worksheet, "UTF-8");
            int questionNumber = 1;
            for (Question q : worksheetQuestions.getQuestionsArray())
                writer.println(questionNumber++ + " " + q.getEntireQuestion() + Main.NEWLINE);
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (UnsupportedEncodingException e) {
            System.out.println("Encoding option unavailable on platform");
        }
    }
}
