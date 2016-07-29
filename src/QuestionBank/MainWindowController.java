package QuestionBank;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {
    @FXML private ComboBox yearComboBox, topicComboBox, typesComboBox, sessionComboBox, difficultyComboBox;
    @FXML private ListView<Question> questionsPreview, worksheetPreview;
    @FXML private TextArea questionDisplay;
    @FXML private ListView<String> attributesDisplay;
    @FXML private TextField searchBar;

    private QuestionCollection questions;

    @Override
    public void initialize(URL FXML_file, ResourceBundle resources) {
        DataBaseReader db = new DataBaseReader();
        questions = db.getQuestions();
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

        /**
        attributesDisplay.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> attributesDisplay) {
                return new Attribute();
            }
        });
         */
    }
    private void addQuestionsToListView(ArrayList<Question> questions) {
        questionsPreview.getItems().clear();
        ObservableList questionPreviewsList = FXCollections.observableArrayList();
        questionPreviewsList.addAll(questions);
        questionsPreview.setItems(questionPreviewsList);
    }
    @FXML public void loadQuestion(MouseEvent event) throws NullPointerException {
        Question q = questionsPreview.getSelectionModel().getSelectedItem();
        questionDisplay.setText(q.getEntireQuestion());

        attributesDisplay.getItems().clear();
        attributesDisplay.getItems().add(new Integer(q.getYear()).toString());
        attributesDisplay.getItems().add(q.getTopic());
        attributesDisplay.getItems().add(q.getType());
        attributesDisplay.getItems().add(q.getSession());
        attributesDisplay.getItems().add(q.getDifficulty());
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
    @FXML public void addToWorksheet(ActionEvent event) {
        Question selectedQuestion = questionsPreview.getSelectionModel().getSelectedItem();
        worksheetPreview.getItems().add(selectedQuestion);
    }

    /**
    private static class Attribute extends ListCell<String> {
        private ImageView icon;
        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                switch (item) {
                    case "Easy": icon = new ImageView("file:res/easy-icon.png"); break;
                    case "Medium": icon = new ImageView("file:res/medium-icon.png"); break;
                    case "Hard ": icon = new ImageView("file:res/hard-icon.png"); break;
                }
            }
            if (icon != null) {
                icon.setFitHeight(16);
                icon.setFitWidth(16);
            }
            setText(item);
            setGraphic(icon);
        }
    } */
}
