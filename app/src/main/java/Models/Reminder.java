package Models;

public class Reminder {

    String text;
    Integer important;
    int id; // Adding it because it's used in the database.

    public Reminder() {
    }

    public Reminder(String text, Integer important) {
        this.text = text;
        this.important = important;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getImportant() {
        return important;
    }

    public void setImportant(Integer important) {
        this.important = important;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
