package QuestionBank;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    // Declaring constants to be used throughout the program
    public static final String NEWLINE = "\n";

    public static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        // Setting the application window size dimensions
        Scene mainWindow = new Scene(root, 720, 720);
        primaryStage.setTitle("QuestionBank");
        primaryStage.setScene(mainWindow);
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("file:res/icons/school.png"));
        primaryStage.show();
    }
    // Application start point
    public static void main(String[] args) {
        launch(args);
    }
}
