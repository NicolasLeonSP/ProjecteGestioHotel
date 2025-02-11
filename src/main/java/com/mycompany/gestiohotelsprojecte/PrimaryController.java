package com.mycompany.gestiohotelsprojecte;

import com.mycompany.gestiohotelsprojecte.model.Model;
import com.mycompany.gestiohotelsprojecte.model.Persona;
import java.io.IOException;
import java.sql.Date;
import java.time.ZoneId;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class PrimaryController {
    private Model model;
    @FXML
    TextField textNom;
    @FXML
    TextField textCognom;
    @FXML
    TextField textAdreça;
    @FXML
    TextField textDNI;
    @FXML
    DatePicker textData;
    @FXML
    TextField textTelefon;
    @FXML
    TextField textEmail;
    // Funcion para crear una alerta, pasandole el mensaje por un parametro
    private void alterMos(String misgg) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setContentText(misgg);
        alerta.show();
    }
    @FXML
    private void crearCliente() {
        Date date = new java.sql.Date(Date.from(textData.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()).getTime());
        boolean alta = model.altaPersona(new Persona(textNom.getText(),textCognom.getText(),textAdreça.getText(),textDNI.getText(),date,textTelefon.getText(),textEmail.getText()));
        if (alta) {
            alterMos("Se ha completado la creacion de la persona");
        } else {
            alterMos("Error");}
        
    }
    
    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
    
    public void injecta(Model obj) {
        model = obj;
    }
 
}
