package com.mycompany.gestiohotelsprojecte;

import com.mycompany.gestiohotelsprojecte.model.Client;
import com.mycompany.gestiohotelsprojecte.model.Model;
import com.mycompany.gestiohotelsprojecte.model.Persona;
import com.mycompany.gestiohotelsprojecte.model.Tipus_Client;
import java.io.IOException;
import java.sql.Date;
import java.time.ZoneId;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
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
    // Cliente
    @FXML
    DatePicker dataRegistre;
    @FXML
    ComboBox TipusClient;
    @FXML
    TextField targetaCredit;
    // Empleado
    @FXML
    DatePicker dataContractacio;
    @FXML
    TextField lugarTrabajo;
    @FXML
    TextField salarioBruto;
    @FXML
    ComboBox estadoLaboral;

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
    private void crearPersona() {
        if (!textNom.getText().isEmpty() && !textCognom.getText().isEmpty() && !textAdreça.getText().isEmpty() && !textDNI.getText().isEmpty() && !textTelefon.getText().isEmpty() && !textEmail.getText().isEmpty() && (isCliente.isSelected() || isEmpleado.isSelected())) {
            try {
                Date date = new java.sql.Date(Date.from(textData.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()).getTime());
                // Check Persona
                Persona personaASubir = new Persona(textNom.getText(), textCognom.getText(), textAdreça.getText(), textDNI.getText(), date, textTelefon.getText(), textEmail.getText());
                String check = model.checkPersona(personaASubir);
                if (check == null) {
                    check = "";
                    // Check cliente
                    Date dateClient;
                    if (isCliente.isSelected()) {
                        if (TipusClient.getValue().toString() != null) {
                            dateClient = new java.sql.Date(Date.from(dataRegistre.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()).getTime());
                            String targetaCreditASubir;
                            if (targetaCredit.getText().isEmpty()) {
                                targetaCreditASubir = targetaCredit.getText();
                            } else {
                                targetaCreditASubir = null;
                            }
                            String checkCliente = model.checkCliente(dateClient, targetaCreditASubir);
                            if (!checkCliente.isEmpty()) {
                                check += checkCliente;
                            }
                        }
                    }
                    // Check Empleado
                    Date dateEmpleado;
                    if (isEmpleado.isSelected()) {
                        if (estadoLaboral.getValue().toString() != null) {
                            dateEmpleado = new java.sql.Date(Date.from(dataContractacio.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()).getTime());
                            String checkEmpleado = model.checkEmpleado(date, check);
                            if (!checkEmpleado.isEmpty()) {
                                check += checkEmpleado;
                            }
                        }
                    }
                    if (check == "") {
                        // Client clienteASubir = new Client(textNom.getText(), textCognom.getText(), textAdreça.getText(), textDNI.getText(), date, textTelefon.getText(), textEmail.getText(), date, (Client.Tipus_Client)TipusClient.getValue(), targetaCreditASubir);
                    } else {
                        alterMos(check, true);
                    }
                } else {
                    alterMos(check, true);
                }
            } catch (Exception e) {
                alterMos("Verifique que todas las fechas necesarias han sido seleccionadas antes de continuar.", true);
            }

        } else {
            alterMos("Rellene todos los campos antes de seguir.", true);
        }

    }

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    public void initialize() {
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
