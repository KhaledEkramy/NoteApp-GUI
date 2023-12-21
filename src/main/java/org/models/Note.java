package org.models ;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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

    // Method to insert an image into the note
    public void insertImage(String imageName) {
        try {
            // Append a link to the image in the note
            File noteFile = new File(path, getTitle() + ".txt");
            FileWriter writer = new FileWriter(noteFile, true);
            writer.write("\nImage Link: Notes\\" + user.getName() + "\\images\\" + imageName + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Title = " + title + "\nBody\n" + body;
    }
}
