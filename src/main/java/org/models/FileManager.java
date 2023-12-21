package org.models;

import javax.imageio.ImageIO;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner ;


public class FileManager {

    private User user= new User();
    private Note note=new Note(user);

    public void setUser(String name,String pass){
        this.user.setName(name);
        this.user.setPassword(pass);

    }

    public void setNote(String tittle,String body){
        this.note.setTitle(tittle);
        this.note.setBody(body);
    }

    public User getUser(){
        return this.user;
    }
    public Note getNote(){
        return this.note;
    }


    // user
    public void createUser(){
        try{
            File path1= new File("Notes");
            path1.mkdirs();

            File path2= new File("Notes\\"+getUser().getName());
            path2.mkdirs();

            File path3= new File("Notes\\" + getUser().getName() +"\\Note");
            path3.mkdirs();

            File file= new File("Notes\\"+getUser().getName()+"\\details.txt");

            FileWriter writer= new FileWriter(file);

            String details= getUser().toString();
            writer.write(details);
            writer.close();

        }catch (IOException e){
            System.out.println(e);
        }
    }
    public static String searchUser(String nameFolder) throws IOException {
        File path = new File("Notes");
        String[] childern = path.list();
        if (childern == null) {
            System.out.println("There 's not direct folder");
        } else {
            for (int i = 0; i < childern.length; i++) {
                if (childern[i].equals(nameFolder)) {
                    return childern[i];
                }
            }
        }
        return null;
    }

    public boolean checkPass(String u, String pass) throws IOException {

        try {
            if (searchUser(u) != null) {
                Scanner word = new Scanner(new File("Notes\\" + u + "\\details.txt"));
                while (word.hasNext()) {
                    String str = word.nextLine().substring(11);
                    if (str.indexOf(pass) != -1) {
                        if (pass.compareTo(str) == 0) {
                            return true;
                        }
                    }
                }
            }

        } catch (IOException e) {
            System.out.println(e);
        }
        return false;
    }
    public void activeUser(String user) throws IOException {
        try {
            File path = new File("Notes\\active.txt");

            FileWriter writer = new FileWriter(path);
            String details = "Active User : " + user;
            writer.write(details);
            writer.close();

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public String getActiveUser()  {
        String activeUser = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader("Notes\\active.txt"));
            while ((activeUser = br.readLine().substring(14)) != null) {
                System.out.println(activeUser);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return activeUser;
    }


    //Note
    public void createNotes(String activeUser) {

        try {
            File file = new File("Notes\\" + activeUser +"\\Note\\"+ getNote().getTitle()+".txt" );
            FileWriter writer = new FileWriter(file);

            String setDetails =  getNote().getBody();
            writer.write(setDetails);
            writer.close();
        }catch (IOException e){
            System.out.println(e);
        }
    }

    public void deleteNote(String activeNote){
        try{
            File path= new File("Notes\\"+getActiveUser()+"\\Note\\"+activeNote+".txt");
            path.delete();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public String[] getAllNotes(){
        File directoryPath = new File("Notes\\"+getActiveUser()+"\\Note");

        String[] contents = directoryPath.list();
        return contents;
    }

    public void activeNote(String note) throws IOException {
        try {
            File path = new File("Notes\\"+getActiveUser()+"\\activeNote.txt");

            FileWriter writer = new FileWriter(path);
            String details = "Active Note : " + getTitle(note);
            writer.write(details);
            writer.close();

        } catch (IOException e) {
            System.out.println(e);
        }
    }
    public String getActiveNote()  {
        String activeUser = "";
        try {
            BufferedReader br= new BufferedReader(new FileReader("Notes\\"+getActiveUser()+"\\activeNote.txt"));
            while ((activeUser = br.readLine().substring(14)) != null ) {
                System.out.println(activeUser);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return activeUser;
    }
    public  String getTitle(String title){
        String thename="";
        for(int i=0;i<title.indexOf(".txt");i++) {
            char c = title.charAt(i);
            thename += c;
        }
        return thename;
    }
    public void EditNotes(String activeUser) {

        try {


            File file = new File("Notes\\" + activeUser +"\\Note\\"+ getNote().getTitle()+".txt");

            FileWriter writer = new FileWriter(file);

            String setDetails =  getNote().getBody();
            writer.write(setDetails);
            writer.close();


        }catch (IOException e){
            System.out.println(e);
        }
    }

    public String getNoteToEdit() {
        String content = "";
        try {
            // Construct the file path
            String filePath = "Notes\\" + getActiveUser() + "\\Note\\" + getActiveNote() + ".txt";

            // Read all lines from the file and join them into a single string
            content = String.join("\n", Files.readAllLines(Paths.get(filePath)));
        } catch (IOException e) {
            System.out.println(e);
        }
        return content;
    }

    public void chooseAndInsertImage(File file) {
        try {
            String noteFolderName = getActiveNote();
            File outputDir = new File("Notes\\" + getActiveUser() + "\\images\\" + noteFolderName);
            outputDir.mkdirs();
            String extension = file.getName().substring(file.getName().lastIndexOf(".") + 1);
            outputDir.mkdirs();

            File outputFile = new File(outputDir, file.getName());
            Image image = new Image(file);
            image.setNote(getNote());
            image.setUser(getUser());
            // Save the image to the output directory
            ImageIO.write(image.getImage(), extension, outputFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
