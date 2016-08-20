import QuestionBank.DataBaseReader;
import QuestionBank.Question;
import QuestionBank.QuestionCollection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {
    @FXML private Accordion questionsPreview;

    private QuestionCollection questions;

    @Override
    public void initialize(URL FXML_file, ResourceBundle resources) {
        questions = new DataBaseReader().getQuestions();
        populateQuestionsPreview(questions.getQuestionsArray());
    }
    private void populateQuestionsPreview(ArrayList<Question> questions) {
        ObservableList<TitledPane> questionPreviewList = FXCollections.observableArrayList();
        for (Question q : questions) {
            Label entireQuestion = new Label(q.getEntireQuestion());
            entireQuestion.setWrapText(true);
            Button addToWorksheet = new Button("ADD");
            // Attributes
            Button year = new Button(new Integer(q.getYear()).toString());
            year.setDisable(true);
            Button session = new Button(q.getSession());
            session.setDisable(true);
            Button type = new Button(q.getType());
            type.setDisable(true);
            Button topic = new Button(q.getTopic());
            topic.setDisable(true);
            //
            HBox attributes = new HBox(2.0, year, session, type, topic);
            GridPane gridPane = new GridPane();
            gridPane.add(entireQuestion, 0, 0, 2, 1);
            gridPane.add(attributes, 0, 1);
            gridPane.add(addToWorksheet, 1, 1);
            TitledPane questionPreview = new TitledPane(q.toString(), gridPane);
            questionPreviewList.add(questionPreview);
        }
        questionsPreview.getPanes().addAll(questionPreviewList);
    }
}
