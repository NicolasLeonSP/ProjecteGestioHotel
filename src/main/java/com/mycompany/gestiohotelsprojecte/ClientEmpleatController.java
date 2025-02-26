package com.mycompany.gestiohotelsprojecte;

import com.mycompany.gestiohotelsprojecte.model.Client;
import com.mycompany.gestiohotelsprojecte.model.Empleat;
import com.mycompany.gestiohotelsprojecte.model.Estat_Laboral;
import com.mycompany.gestiohotelsprojecte.model.Model;
import com.mycompany.gestiohotelsprojecte.model.Persona;
import com.mycompany.gestiohotelsprojecte.model.Tipus_Client;
import java.sql.Date;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;

public class ClientEmpleatController {

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
    // Funcion para crear una persona, cliente y empleado.
    private void crearPersona() {
        if (!textNom.getText().isEmpty() && !textCognom.getText().isEmpty() && !textAdreça.getText().isEmpty() && !textDNI.getText().isEmpty() && !textTelefon.getText().isEmpty() && !textEmail.getText().isEmpty() && (isCliente.isSelected() || isEmpleado.isSelected())) {
            try {
                Date date = model.LocalDateToSqlDate(textData.getValue());
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
                            dateClient = model.LocalDateToSqlDate(dataRegistre.getValue());
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
                            check += "- Ompliu tots els camps de client abans de continuar.\n";
                        }
                    }
                    // Check Empleado
                    Date dateEmpleado = null;
                    if (isEmpleado.isSelected()) {
                        if (estadoLaboral.getValue() != null && dataContractacio.getValue() != null && textLugarTrabajo.getText().isEmpty() == false && textSalarioBruto.getText().isEmpty() == false) {
                            dateEmpleado = model.LocalDateToSqlDate(dataContractacio.getValue());
                            String checkEmpleado = model.checkEmpleado(dateEmpleado, textSalarioBruto.getText());
                            if (!checkEmpleado.equals("")) {
                                check += checkEmpleado;
                            }
                        } else {
                            check += "- Ompliu tots els camps d'empleat abans de continuar.\n";
                        }
                    }
                    if (check.equals("")) {
                        Boolean personaSubida = personaASubir.altaPersona();
                        if (personaSubida) {
                            String textSubido = "S'ha creat la persona amb èxit. \n";
                            int IDPersona = model.getIdPersona(textDNI.getText());
                            if (isCliente.isSelected()) {
                                Client clienteASubir;
                                clienteASubir = new Client(textNom.getText(), textCognom.getText(), textAdreça.getText(), textDNI.getText(), date, textTelefon.getText(), textEmail.getText(), dateClient, (Tipus_Client) TipusClient.getValue(), targetaCreditASubir);
                                clienteASubir.setID_Persona(IDPersona);
                                Boolean clienteSubido = clienteASubir.altaCliente();
                                if (personaSubida) {
                                    textSubido += "El client s'ha creat amb èxit. \n";
                                }
                            }
                            if (isEmpleado.isSelected()) {
                                Empleat empleadoASubir;
                                empleadoASubir = new Empleat(textNom.getText(), textCognom.getText(), textAdreça.getText(), textDNI.getText(), date, textTelefon.getText(), textEmail.getText(), textLugarTrabajo.getText(), dateEmpleado, Integer.parseInt(textSalarioBruto.getText()), (Estat_Laboral) estadoLaboral.getValue());
                                empleadoASubir.setID_Persona(IDPersona);
                                Boolean empleadoSubido = empleadoASubir.altaEmpleado();
                                if (empleadoSubido) {
                                    textSubido += "L'empleat s'ha creat amb èxit. \n";
                                }
                            }
                            alterMos(textSubido, false);
                            restartCampos();
                        } else {
                            alterMos("Error. El document d'identitat que s'ha donat en ús, o alguna cosa ha fallat en el procés de pujada.", true);
                        }

                    } else {
                        alterMos(check, true);
                    }
                } else {
                    alterMos(check, true);
                }
            } catch (NullPointerException e) {
                System.out.println(e);
                alterMos("Error fatal, contacteu amb l'administrador de l'aplicació.", true);
            } catch (Exception e) {
                System.out.println(e);
                alterMos("Verifiqueu que totes les dates necessàries han estat seleccionades abans de continuar.", true);
            }

        } else {
            alterMos("Ompliu tots els camps abans de seguir.", true);
        }

    }

    @FXML
    private void restartCampos() {
        textNom.clear();
        textCognom.clear();
        textAdreça.clear();
        textDNI.clear();
        textData.getEditor().clear();
        textTelefon.clear();
        textEmail.clear();
        isCliente.selectedProperty().set(false);
        isEmpleado.selectedProperty().set(false);
        dataRegistre.getEditor().clear();
        TipusClient.valueProperty().set(null);
        textTargetaCredit.clear();
        dataContractacio.getEditor().clear();
        textLugarTrabajo.clear();
        textSalarioBruto.clear();
        estadoLaboral.valueProperty().set(null);
        ClientEmpleat.setVisible(false);
    }

    public void initialize() {
        ClientEmpleat.setVisible(false);
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
        if (!isEmpleado.selectedProperty().get() && !isCliente.selectedProperty().get()) {
            ClientEmpleat.setVisible(false);
        }
    }

    public void injecta(Model obj) {
        model = obj;
    }
}
