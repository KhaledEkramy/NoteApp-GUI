module org.main.note_gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens org.main to javafx.fxml;
    exports org.main;
}