package org.main;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.models.FileManage;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;


import javafx.stage.FileChooser;

public class Edit implements Initializable {


    @FXML
    private Label myLabel= new Label();
    @FXML
    private ListView<String> myListView= new ListView<>();
    @FXML
    private TextField textTittle= new TextField();
    @FXML
    private TextArea textArea= new TextArea();

    @FXML
    private Button bEdit= new Button();

    @FXML
    private Label titleLabel;

    private Parent root;
    private Stage stage;
    private Scene scene;

    private String currentNote;
    private FileManage manage= new FileManage();


    // Add File Chooser Initialization in initialize method
    private FileChooser fileChooser = new FileChooser() ;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        myListView.getItems().addAll(manage.getAllNotes());
        myListView.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, t1) -> {
            currentNote = myListView.getSelectionModel().getSelectedItem();
            myLabel.setText(currentNote);
            try {
                manage.activeNote(currentNote);
                textArea.setText(manage.getNoteToEdit());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // Set the content of the note in the text area when initializing
//            try {
//
//            } catch (IOException e) {
//                System.out.println(e);
//            }
        });


    }

    public void buttonBackManage(ActionEvent event){
        pageSCene("ManageNote.fxml",event);
    }
    public void buttonEditNote(ActionEvent event){
        if(!myLabel.getText().equals("Select Note")) {
            pageSCene("EditNote.fxml", event);
        }
        else{
            pageSCene("Edit.fxml", event);
        }
    }
    public void buttonSaveEditNote(ActionEvent event){
        textTittle.setText(manage.getActiveNote());
        manage.setNote(textTittle.getText(),textArea.getText());
        manage.EditNotes(manage.getActiveUser());
        pageSCene("Note.fxml",event);
    }

    public void buttonDelete(ActionEvent event){
        manage.deleteNote(manage.getActiveNote());
        pageSCene("Edit.fxml",event);
    }
    
     // Add this method for choosing and inserting an image in EditNote.fxml
     @FXML
     private void chooseAndInsertImage(ActionEvent event) throws FileNotFoundException {
         // Create the file chooser dialog
         FileChooser fileChooser = new FileChooser();
         fileChooser.setTitle("Select an Image");
         fileChooser.getExtensionFilters().addAll(
                 new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
         );

         // Show the file chooser dialog
         File file = fileChooser.showOpenDialog(stage);
         if (file != null) {
             try {
                 // Create the output directory
                 String noteFolderName = manage.getActiveNote();
                 File outputDir = new File("Notes\\" + manage.getActiveUser() + "\\images\\" + noteFolderName);
                 String extension = file.getName().substring(file.getName().lastIndexOf(".") + 1);
                 outputDir.mkdirs();

                 // Copy the image to the output directory
                 File outputFile = new File(outputDir, file.getName());
                 BufferedImage image = ImageIO.read(file);
                 ImageIO.write(image, extension, outputFile);

                 // Insert the image path into the text area
                 String insertedText = "\nImage Link: Notes\\" + manage.getActiveUser() + "\\images\\" + noteFolderName + "\\" + file.getName() + "\n";
                 textArea.insertText(textArea.getCaretPosition(), insertedText);
                 image.flush();
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }
     }



    //move between Scene
    public void pageSCene(String pageScene, ActionEvent event)  {
        try{
            Pane loader =  FXMLLoader.load(getClass().getResource(pageScene));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(loader);
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public void exitButton(){
        System.exit(0) ;
    }



}

