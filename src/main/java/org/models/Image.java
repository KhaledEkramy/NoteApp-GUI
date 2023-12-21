package org.models ;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Image {
    private BufferedImage image;
    private User user ;
    private Note note ;
    public Image(File file) throws IOException {
        this.image = ImageIO.read(file);
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setUser(User user){
        this.user = user;
    }
    public User getUser(){
        return this.user;
    }
    public void setNote (Note note){
        this.note = note;
    }
    public Note getNote(){
        return this.note;
    }
}
