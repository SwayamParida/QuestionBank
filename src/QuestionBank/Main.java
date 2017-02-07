package QuestionBank;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    public static final String TAB = "\t";
    public static final String NEWLINE = "\n";
    public static final String ELLIPSIS = "...";
    public static final int PREVIEW_LENGTH = 15;
    public static final int WORKSHEET_PREVIEW_LENGTH = 60;
    public static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        Scene mainWindow = new Scene(root, 720, 720);
        primaryStage.setTitle("QuestionBank");
        primaryStage.setScene(mainWindow);
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("file:res/icons/school.png"));
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
