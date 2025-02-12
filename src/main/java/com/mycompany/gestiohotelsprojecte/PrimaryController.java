package com.mycompany.gestiohotelsprojecte;

import com.mycompany.gestiohotelsprojecte.model.Model;
import java.io.IOException;
import java.sql.Date;
import java.time.ZoneId;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TabPane;
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
    @FXML
    TabPane ClientEmpleat;
    @FXML
    CheckBox isCliente;
    @FXML
    CheckBox isEmpleado;

    // Funcion para crear una alerta, pasandole el mensaje por un parametro
    private void alterMos(String misgg, boolean error) {
        Alert alerta;
        if (error) {
            alerta = new Alert(Alert.AlertType.ERROR);
        } else {
            alerta = new Alert(Alert.AlertType.INFORMATION);
        }
        alerta.setContentText(misgg);
        alerta.show();
    }

    @FXML
    private void crearCliente() {
        if (!textNom.getText().isEmpty() && !textCognom.getText().isEmpty() && !textAdreça.getText().isEmpty() && !textDNI.getText().isEmpty() && !textTelefon.getText().isEmpty() && !textEmail.getText().isEmpty()) {
            try {
                Date date = new java.sql.Date(Date.from(textData.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()).getTime());
                // String alta = model.altaPersona(new Client(textNom.getText(), textCognom.getText(), textAdreça.getText(), textDNI.getText(), date, textTelefon.getText(), textEmail.getText()));
                String alta = "a";
                if (alta == null) {
                    alterMos("Se ha completado la creacion de la persona", false);
                } else {
                    alterMos(alta, true);
                }
            } catch (Exception e) {
                alterMos("Seleccione una fecha antes de continuar.", true);
            }

        } else {
            alterMos("Rellene todos los campos antes de seguir." , true);
        }

    }

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
    
    public void initialize(){
        ClientEmpleat.setVisible(false);
    }
    
    public void changeCheckboxes() {
        if (ClientEmpleat.visibleProperty().get() == false) {
            ClientEmpleat.setVisible(true);
        }
        if (isCliente.selectedProperty().get()) {
            ClientEmpleat.getSelectionModel().select(1);
            ClientEmpleat.getTabs().get(1).setDisable(false);
        } else {
            ClientEmpleat.getTabs().get(1).setDisable(true);
        }
        if (isEmpleado.selectedProperty().get()) {
            ClientEmpleat.getSelectionModel().select(0);
            ClientEmpleat.getTabs().get(0).setDisable(false);
        } else {
            ClientEmpleat.getTabs().get(0).setDisable(true);
        }
    }
    
    public void injecta(Model obj) {
        model = obj;
    }

}
