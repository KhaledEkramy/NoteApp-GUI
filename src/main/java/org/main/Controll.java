package org.main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.models.FileManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

import java.io.File;
import java.io.IOException;

public class Controll {

    private final String CREATION = "Creation.fxml";
    private final String LOGIN = "Login.fxml";
    private final String NOTE = "Note.fxml";
    private final String MANAGE="ManageNote.fxml";
//    private final String SECRETNote = "SecretNote.fxml";
    private FileManager manage = new FileManager();

    //user
    @FXML
    private TextField userName;

    @FXML
    private TextField password;
    //note
    @FXML
    private TextField textTittle;
    @FXML
    private TextArea textArea;

    //login
    @FXML
    private TextField textUser;
    @FXML
    private TextField textPassword;
    @FXML
    private Label myLabel;
    @FXML
    private Button btnInsertImage;

    private FileChooser fileChooser = new FileChooser();
    private Stage stage;


    public void pageSCene(String pageScene, ActionEvent event) throws IOException {
         Parent root;
         Scene scene;
        Pane loader =  FXMLLoader.load(getClass().getResource(pageScene));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(loader);
        stage.setScene(scene);
        stage.show();
    }

    //login user
    public void buttonLogin(ActionEvent event) throws IOException {
        manage.setUser(textUser.getText(), textPassword.getText());
        try{
        if (manage.checkPass(manage.getUser().getName(), manage.getUser().getPassword()) == true) {
            manage.activeUser(textUser.getText());
            pageSCene(MANAGE, event);
        } else {
            pageSCene(LOGIN, event);

        }
        }catch (IOException e){
            System.out.println(e);
        }
    }

    public void buttonCreatAccount(ActionEvent event)throws IOException{
        pageSCene(CREATION,event);
    }

    //create user
    public void buttonCreat(ActionEvent event) throws IOException {
        manage.setUser(userName.getText(), password.getText());
        manage.createUser();
        manage.activeUser(userName.getText());
        pageSCene(MANAGE, event);
    }


    //manage note
    public void buttonSaveNote(ActionEvent event) throws IOException {
        manage.setNote(textTittle.getText(), textArea.getText());
        manage.createNotes(manage.getActiveUser());
        pageSCene(NOTE, event);
    }

    public void buttonCreateNote(ActionEvent event)throws IOException{
        pageSCene(NOTE, event);
    }
    public void exitButton(){
        System.exit(0);
    }
    public void buttonEditNote(ActionEvent event)throws IOException{
        pageSCene("Edit.fxml",event);

    }

    public void buttouBackLogin(ActionEvent event)throws IOException{
        pageSCene(LOGIN,event);
    }
    public void buttonPageManage(ActionEvent event)throws IOException{
        pageSCene(MANAGE,event);
    }

    @FXML
    private void chooseAndInsertImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );

        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            manage.chooseAndInsertImage(file);
        }
        // Insert the image path into the text area
        String insertedText = "\nImage Link: Notes\\" + manage.getActiveUser() + "\\images\\" + manage.getActiveNote() + "\\" + file.getName() + "\n";
        textArea.insertText(textArea.getCaretPosition(), insertedText);
    }
    public void openImage(String path){
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        JLabel label = new JLabel();

        // Set the path of the image
        String imagePath = "path/to/image.jpg";

        try {
            // Read the image from the file
            Image image = ImageIO.read(new File(imagePath));

            // Set the image to the label
            label.setIcon(new ImageIcon(image));

            // Add the label to the panel
            panel.add(label);

            // Add the panel to the frame
            frame.add(panel);

            // Set the frame properties
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
