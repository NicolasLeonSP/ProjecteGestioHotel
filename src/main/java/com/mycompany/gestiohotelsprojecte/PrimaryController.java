package com.mycompany.gestiohotelsprojecte;

import com.mycompany.gestiohotelsprojecte.model.Client;
import com.mycompany.gestiohotelsprojecte.model.Empleat;
import com.mycompany.gestiohotelsprojecte.model.Estat_Laboral;
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
    TextField textTargetaCredit;
    // Empleado
    @FXML
    DatePicker dataContractacio;
    @FXML
    TextField textLugarTrabajo;
    @FXML
    TextField textSalarioBruto;
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
                if (check.equals("")) {
                    check = "";
                    // Check cliente
                    Date dateClient = null;
                    String targetaCreditASubir = "";
                    if (isCliente.isSelected()) {
                        if (TipusClient.getValue() != null && dataRegistre.getValue() != null) {
                            dateClient = new java.sql.Date(Date.from(dataRegistre.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()).getTime());
                            
                            if (!textTargetaCredit.getText().isEmpty()) {
                                targetaCreditASubir = textTargetaCredit.getText();
                            } else {
                                targetaCreditASubir = null;
                            }
                            String checkCliente = model.checkCliente(dateClient, targetaCreditASubir);
                            if (!checkCliente.equals("")) {
                                check += checkCliente;
                            }
                        } else {
                            check += "- Rellene todos los campos de cliente antes de continuar.\n";
                        }
                    }
                    // Check Empleado
                    Date dateEmpleado = null;
                    if (isEmpleado.isSelected()) {
                        if (estadoLaboral.getValue() != null && dataContractacio.getValue() != null && textLugarTrabajo.getText().isEmpty() == false && textSalarioBruto.getText().isEmpty() == false) {
                            dateEmpleado = new java.sql.Date(Date.from(dataContractacio.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()).getTime());
                            String checkEmpleado = model.checkEmpleado(dateEmpleado, textSalarioBruto.getText());
                            if (!checkEmpleado.equals("")) {
                                check += checkEmpleado;
                            }
                        } else {
                            check += "- Rellene todos los campos de empleado antes de continuar.\n";
                        }
                    }
                    if (check.equals("")) {
                        Boolean personaSubida = model.altaPersona(personaASubir);
                        if (personaSubida) {
                            String textSubido = "Se ha creado la persona con exito \n";
                            int IDPersona = model.getIdPersona(textDNI.getText());
                            if (isCliente.isSelected()) {
                                Client clienteASubir;
                                clienteASubir = new Client(textNom.getText(), textCognom.getText(), textAdreça.getText(), textDNI.getText(), date, textTelefon.getText(), textEmail.getText(), dateClient, (Tipus_Client) TipusClient.getValue(), targetaCreditASubir);
                                clienteASubir.setID_Persona(IDPersona);
                                Boolean clienteSubido = model.altaCliente(clienteASubir);
                                if (personaSubida) {
                                    textSubido += "Se ha creado el cliente con exito \n";
                                }
                            }
                            if (isEmpleado.isSelected()) {
                                Empleat empleadoASubir;
                                empleadoASubir = new Empleat(textNom.getText(), textCognom.getText(), textAdreça.getText(), textDNI.getText(), date, textTelefon.getText(), textEmail.getText(), textLugarTrabajo.getText(), dateEmpleado, Integer.parseInt(textSalarioBruto.getText()), (Estat_Laboral) estadoLaboral.getValue());
                                empleadoASubir.setID_Persona(IDPersona);
                                Boolean empleadoSubido = model.altaEmpleado(empleadoASubir);
                                if (empleadoSubido) {
                                    textSubido += "Se ha creado el empleado con exito \n";
                                }
                            }
                            alterMos(textSubido, false);
                        } else {
                            alterMos("Error. El documento de identitad dado esta en uso, o algo ha fallado en el proceso de subida.", true);
                        }

                    } else {
                        alterMos(check, true);
                    }
                } else {
                    alterMos(check, true);
                }
            } catch (NullPointerException e) {
                System.out.println(e);
                alterMos("Error fatal, contacte con el administrador de la aplicacion.", true);
            } catch (Exception e) {
                System.out.println(e);
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
        model.initModel();
        TipusClient.getItems().addAll(model.getTipoCliente());
        estadoLaboral.getItems().addAll(model.getEstatLaboral());
    }

    public void changeCheckboxes() {
        if (ClientEmpleat.visibleProperty().get() == false) {
            ClientEmpleat.setVisible(true);
        }
        if (isCliente.selectedProperty().get()) {
            ClientEmpleat.getSelectionModel().select(0);
            ClientEmpleat.getTabs().get(0).setDisable(false);
        } else {
            ClientEmpleat.getTabs().get(0).setDisable(true);
        }
        if (isEmpleado.selectedProperty().get()) {
            ClientEmpleat.getSelectionModel().select(1);
            ClientEmpleat.getTabs().get(1).setDisable(false);
        } else {
            ClientEmpleat.getTabs().get(1).setDisable(true);
        }
    }

    public void injecta(Model obj) {
        model = obj;
    }

}
