package org.models ;
import java.io.File;

public class Note {

    private String title;
    private String body;
    private User user;
    private File path;

    public Note(User user) {
        this.user = user;
        this.path = new File("Notes\\" + this.user.getName() + "\\Note");
        path.mkdirs();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public User getUser() {
        return this.user ;
    }

    @Override
    public String toString() {
        return "Title = " + title + "\nBody\n" + body;
    }
}
