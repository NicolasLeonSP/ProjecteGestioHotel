module com.mycompany.gestiohotelsprojecte {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql; 
    requires javafx.swing;
    requires java.desktop; 
    requires javafx.base;
    requires javafx.graphics;
    requires java.base;

    opens com.mycompany.gestiohotelsprojecte to javafx.fxml;
    exports com.mycompany.gestiohotelsprojecte;
}
