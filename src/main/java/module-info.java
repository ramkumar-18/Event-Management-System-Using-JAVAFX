module org.example.ems {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;

    opens org.example.ems to javafx.fxml;
    exports org.example.ems;
}