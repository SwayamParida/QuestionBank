package QuestionBank;

import java.sql.*;
import java.util.ArrayList;

public class DataBaseReader {

    private static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static String DATABASE_URL = "jdbc:mysql://localhost/Questionbank?useSSL=false";
    private static String USER = "root";
    private static String PASSWORD = "swayam";

    private QuestionCollection questions;

    public DataBaseReader() {
        Connection c = null;
        Statement statement = null;
        ResultSet resultSet = null;
        ArrayList<Question> questions = new ArrayList<>();

        try {
            System.out.println("Registering for JDBC driver...");
            Class.forName(JDBC_DRIVER);
            System.out.println("Registration successful");
        } catch (ClassNotFoundException e) {
            System.out.println("Registration unsuccessful");
            e.printStackTrace();
            System.exit(0);
        }
        try {
            System.out.println("Connection to database...");
            c = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            System.out.println("Connection successful");
        } catch (SQLException e) {
            System.out.println("Connection unsuccessful");
            e.printStackTrace();
            System.exit(0);
        }
        try {
            System.out.println("Executing query...");
            statement = c.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Physics");
            System.out.println("Query execution successful");
        } catch (SQLException e) {
            System.out.println("Query execution unsuccessful");
            e.printStackTrace();
            System.exit(0);
        } /**
        try {
            while (resultSet.next()) {
                System.out.println("Question #" + resultSet.getInt("QNumber"));
                System.out.print("Topic: " + resultSet.getString("topic") + TAB);
                System.out.print("Type: " + resultSet.getString("type") + TAB);
                System.out.println("Year: " + resultSet.getInt("year"));
            }
        } catch (SQLException e) {
            System.out.println("Some columns not found");
            e.printStackTrace();
            System.exit(0);
        } */
        try {
            while (resultSet.next()) {
                String file = resultSet.getString("QFile");
                String topic = resultSet.getString("Topic");
                int year = resultSet.getInt("Year");
                String type = resultSet.getString("Type");
                String session = resultSet.getString("Session");
                int marks = resultSet.getInt("Marks");
                String difficulty = resultSet.getString("Difficulty");

                Question q = new Question(file, year, topic, type, session, marks, difficulty);
                questions.add(q);
            }
        } catch (SQLException e) {
            System.out.println("Some columns not found");
            e.printStackTrace();
            System.exit(0);
        }
        try {
            System.out.println("Cleaning up...");
            resultSet.close();
            statement.close();
            c.close();
            System.out.println("Clean up successful");
        } catch (SQLException e) {
            System.out.println("Clean up unsuccessful");
            e.printStackTrace();
            System.exit(0);
        }
        this.questions = new QuestionCollection(questions);
    }
    public QuestionCollection getQuestions() { return questions; }
}
