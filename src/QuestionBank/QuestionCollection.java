package QuestionBank;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class QuestionCollection {
    private ArrayList<File> questionFiles;
    private ArrayList<String> topics;
    private ArrayList<Integer> years;
    private ArrayList<String> types;
    private ArrayList<String> sessions;
    private ArrayList<Integer> marks;
    private ArrayList<String> difficulties;

    private ArrayList<Question> questionsArray;
    private ArrayList<Question> filteredQuestions;

    QuestionCollection(ArrayList<Question> questions) {
        questionFiles = new ArrayList<>();
        topics = new ArrayList<>();
        years = new ArrayList<>();
        types = new ArrayList<>();
        sessions = new ArrayList<>();
        marks = new ArrayList<>();
        difficulties = new ArrayList<>();
        setQuestionsArray(questions);
        for (Question q : questions) {
            questionFiles.add(q.getQuestionFile());
            topics.add(q.getTopic());
            types.add(q.getType());
            years.add(q.getYear());
            sessions.add(q.getSession());
            marks.add(q.getMarks());
            difficulties.add(q.getDifficulty());
        }
    }

    public void setQuestionsArray(ArrayList<Question> questionsArray) { this.questionsArray = questionsArray; }
    public void setQuestionFiles(ArrayList<File> questionFiles) { this.questionFiles = questionFiles; }
    public void setTopics(ArrayList<String> topics) { this.topics = topics; }
    public void setYears(ArrayList<Integer> years) { this.years = years; }
    public void setTypes(ArrayList<String> types) { this.types = types; }
    public void setSessions(ArrayList<String> sessions) {this.sessions = sessions; }
    public void setMarks(ArrayList<Integer> marks) {this.marks = marks; }
    public void setFilteredQuestions(ArrayList<Question> filteredQuestions) { this.filteredQuestions = filteredQuestions; }
    public void setDifficulties(ArrayList<String> difficulties) { this.difficulties = difficulties; }

    public ArrayList<Question> getQuestionsArray() {
        return questionsArray;
    }
    public ArrayList<File> getQuestionFiles() { return questionFiles; }
    public ArrayList<String> getTopics() { return topics; }
    public ArrayList<Integer> getYears() { return years; }
    public ArrayList<String> getTypes() { return types; }
    public ArrayList<String> getSessions() { return sessions; }
    public ArrayList<Integer> getMarks() { return marks; }
    public ArrayList<String> getDifficulties() { return difficulties; }
    public ArrayList<Question> getFilteredQuestions(Object year, String topic, String type, String session, String difficulty) {
        filteredQuestions = new ArrayList<>(questionsArray);
        try {
            filterYear((Integer)year);
        } catch (ClassCastException e) {

        }
        filterTopic(topic);
        filterType(type);
        filterSession(session);
        filterDifficulty(difficulty);
        return filteredQuestions;
    }

    public ArrayList<Integer> getUniqueYears() {
        ArrayList<Integer> years = getYears();
        for (int i = 0; i < years.size(); i++) {
            int currentYear = years.get(i);
            for (int j = i + 1; j < years.size(); j++) {
                if (currentYear == years.get(j))
                    years.remove(j--);
            }
        }
        return years;
    }
    public ArrayList<String> getUniqueTopics() {
        ArrayList<String> topics = getTopics();
        for (int i = 0; i < topics.size(); i++) {
            String currentTopic = topics.get(i);
            for (int j = i + 1; j < topics.size(); j++) {
                if (currentTopic.equals(topics.get(j)))
                    topics.remove(j--);
            }
        }
        return topics;
    }
    public ArrayList<String> getUniqueTypes() {
        ArrayList<String> types = getTypes();
        for (int i = 0; i < types.size(); i++) {
            String currentType = types.get(i);
            for (int j = i + 1; j < types.size(); j++) {
                if (currentType.equals(types.get(j)))
                    types.remove(j--);
            }
        }
        return types;
    }
    public ArrayList<String> getUniqueSessions() {
        ArrayList<String> sessions = getSessions();
        for (int i = 0; i < sessions.size(); i++) {
            String currentSession = sessions.get(i);
            for (int j = i + 1; j < sessions.size(); j++) {
                if (currentSession.equals(sessions.get(j)))
                    sessions.remove(j--);
            }
        }
        return sessions;
    }
    public ArrayList<String> getUniqueDifficulties() {
        ArrayList<String> difficulties = getDifficulties();
        for (int i = 0; i < difficulties.size(); i++) {
            String currentDifficulty = difficulties.get(i);
            for (int j = i + 1; j < difficulties.size(); j++) {
                if (currentDifficulty.equals(difficulties.get(j))) {
                    difficulties.remove(j--);
                }
            }
        }
        return difficulties;
    }

    public void addQuestion(Question question) {
        questionsArray.add(question);
    }

    private void filterYear(Integer year) {
        for (Iterator<Question> i = filteredQuestions.iterator(); i.hasNext();) {
            Question q = i.next();
            if (q.getYear() != year) {
                i.remove();
            }
        }
    }
    private void filterTopic(String topic) {
        for (Iterator<Question> i = filteredQuestions.iterator(); i.hasNext();) {
            Question q = i.next();
            if (!q.getTopic().equals(topic) && !topic.equals("None")) {
                i.remove();
            }
        }
    }
    private void filterType(String type) {
        for (Iterator<Question> i = filteredQuestions.iterator(); i.hasNext();) {
            Question q = i.next();
            if (!q.getType().equals(type) && !type.equals("None")) {
                i.remove();
            }
        }
    }
    private void filterSession(String session) {
        for (Iterator<Question> i = filteredQuestions.iterator(); i.hasNext();) {
            Question q = i.next();
            if (!q.getSession().equals(session) && !session.equals("None")) {
                i.remove();
            }
        }
    }
    private void filterDifficulty(String difficulty) {
        for (Iterator<Question> i = filteredQuestions.iterator(); i.hasNext();) {
            Question q = i.next();
            if (!q.getDifficulty().equals(difficulty) && !difficulty.equals("None")) {
                i.remove();
            }
        }
    }

    public QuestionCollection search(String searchString) {
        ArrayList<Question> searchResults = new ArrayList<>();
        for (Question q : questionsArray) {
            if (q.getEntireQuestion().toLowerCase().indexOf(searchString.toLowerCase()) != -1)
                searchResults.add(q);
        }
        return new QuestionCollection(searchResults);
    }

    @Override
    public String toString() {
        String s = new String();
        for (Question q : questionsArray)
            s = q.toString() + Main.NEWLINE;
        return s;
    }
}
