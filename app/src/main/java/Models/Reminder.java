package Models;

public class Reminder {
    String text;
    Boolean important;

    public Reminder() {
    }

    public Reminder(String text, Boolean important) {
        this.text = text;
        this.important = important;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getImportant() {
        return important;
    }

    public void setImportant(Boolean important) {
        this.important = important;
    }
}
