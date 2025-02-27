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

    // Variables del controlador
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
    // Variables del cliente
    @FXML
    DatePicker dataRegistre;
    @FXML
    ComboBox TipusClient;
    @FXML
    TextField textTargetaCredit;
    // Variables del empleado
    @FXML
    DatePicker dataContractacio;
    @FXML
    TextField textLugarTrabajo;
    @FXML
    TextField textSalarioBruto;
    @FXML
    ComboBox estadoLaboral;

    // Funcion para crear una alerta, pasandole el mensaje por un parametro y tambien si es un mensaje de error o no
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
                // Esta parte se encargara de hacer un check a persona, con la funcion del modelo checkPersona.
                Persona personaASubir = new Persona(textNom.getText(), textCognom.getText(), textAdreça.getText(), textDNI.getText(), date, textTelefon.getText(), textEmail.getText());
                String check = model.checkPersona(personaASubir);
                if (check.equals("")) {
                    check = "";
                    // Esta parte se encarga de mirar que cliente esta puesto de forma correcta, si se ha decidido que sea un cliente.
                    Date dateClient = null;
                    String targetaCreditASubir = "";
                    if (isCliente.isSelected()) {
                        // Aqui se hacen checks como los campos si se han rellenado o fechas tal que aqui.
                        if (TipusClient.getValue() != null && dataRegistre.getValue() != null) {
                            dateClient = model.LocalDateToSqlDate(dataRegistre.getValue());
                            if (!textTargetaCredit.getText().isEmpty()) {
                                targetaCreditASubir = textTargetaCredit.getText();
                            } else {
                                targetaCreditASubir = null;
                            }
                            // Y aqui se hace el check como tal, con la funcion de checkCliente en modelo.
                            String checkCliente = model.checkCliente(dateClient, targetaCreditASubir);
                            if (!checkCliente.equals("")) {
                                check += checkCliente;
                            }
                        } else {
                            check += "- Ompliu tots els camps de client abans de continuar.\n";
                        }
                    }
                    // Esta parte se encarga de mirar que el empleado esta puesto de forma correcta, si se ha decidido que sea un empleado.
                    Date dateEmpleado = null;
                    if (isEmpleado.isSelected()) {
                        // Aqui se hacen checks con la fecha o los campos que no se han rellenado.
                        if (estadoLaboral.getValue() != null && dataContractacio.getValue() != null && textLugarTrabajo.getText().isEmpty() == false && textSalarioBruto.getText().isEmpty() == false) {
                            dateEmpleado = model.LocalDateToSqlDate(dataContractacio.getValue());
                            // El check como tal, se haria por aqui, con la funcion checkEmpleado.
                            String checkEmpleado = model.checkEmpleado(dateEmpleado, textSalarioBruto.getText());
                            if (!checkEmpleado.equals("")) {
                                check += checkEmpleado;
                            }
                        } else {
                            check += "- Ompliu tots els camps d'empleat abans de continuar.\n";
                        }
                    }
                    // Si no ha fallado nada...
                    if (check.equals("")) {
                        // Comenzamos por subir a la persona.
                        Boolean personaSubida = personaASubir.altaPersona();
                        // Si se sube correctamente...
                        if (personaSubida) {
                            // Añadimos eso al mensaje
                            String textSubido = "S'ha creat la persona amb èxit. \n";
                            // Extraemos el ID de la persona recien subida.
                            int IDPersona = model.getIdPersona(textDNI.getText());
                            // Y comenzamos a subir al cliente si esta seleccionado.
                            if (isCliente.isSelected()) {
                                Client clienteASubir;
                                clienteASubir = new Client(textNom.getText(), textCognom.getText(), textAdreça.getText(), textDNI.getText(), date, textTelefon.getText(), textEmail.getText(), dateClient, (Tipus_Client) TipusClient.getValue(), targetaCreditASubir);
                                clienteASubir.setID_Persona(IDPersona);
                                // Aqui es como subimos al cliente como tal.
                                Boolean clienteSubido = clienteASubir.altaCliente();
                                if (personaSubida) {
                                    textSubido += "El client s'ha creat amb èxit. \n";
                                }
                            }
                            // O al empleado si esta seleccionado.
                            if (isEmpleado.isSelected()) {
                                Empleat empleadoASubir;
                                empleadoASubir = new Empleat(textNom.getText(), textCognom.getText(), textAdreça.getText(), textDNI.getText(), date, textTelefon.getText(), textEmail.getText(), textLugarTrabajo.getText(), dateEmpleado, Integer.parseInt(textSalarioBruto.getText()), (Estat_Laboral) estadoLaboral.getValue());
                                empleadoASubir.setID_Persona(IDPersona);
                                // Aqui es como subimos al empleado como tal.
                                Boolean empleadoSubido = empleadoASubir.altaEmpleado();
                                if (empleadoSubido) {
                                    textSubido += "L'empleat s'ha creat amb èxit. \n";
                                }
                            }
                            // Una vez terminado, mandamos un mensaje con los resultados y reiniciamos campos.
                            alterMos(textSubido, false);
                            restartCampos();
                        } else {
                            // Error por si algo falla.
                            alterMos("Error. El document d'identitat que s'ha donat en ús, o alguna cosa ha fallat en el procés de pujada.", true);
                        }

                    } else {
                        // Mensaje de error con todos los errores.
                        alterMos(check, true);
                    }
                } else {
                    alterMos(check, true);
                }
            } catch (NullPointerException e) {
                // En el caso de que alguna variable de null por lo que sea.
                System.out.println(e.toString());
                alterMos("Error fatal, contacteu amb l'administrador de l'aplicació.", true);
            } catch (NumberFormatException e) {
                // En el caso de que algun numero haya sido introducido mal.
                System.out.println(e.toString());
                alterMos("Verifiqueu que totes les dates necessàries han estat seleccionades abans de continuar.", true);
            } catch (Exception e) {
                // En el caso de cualquier excepcion.
                System.out.println(e.toString());
                alterMos("Error fatal, contacteu amb l'administrador de l'aplicació.", true);
            }
        } else {
            alterMos("Ompliu tots els camps abans de seguir.", true);
        }

    }

    @FXML
    // Esta funcion se encargara de reiniciar todos los campos.
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
    // Esta funcion se encarga de hacer acciones al iniciar el formulario.
    public void initialize() {
        ClientEmpleat.setVisible(false);
        // Aqui se asignan los objetos a los comboboxes.
        TipusClient.getItems().addAll(model.getTipoCliente());
        estadoLaboral.getItems().addAll(model.getEstatLaboral());
    }
    // Y aqui, es para cuando se le da click a una checkbox.
    public void changeCheckboxes() {
        // Aqui vemos si esta escondido el anchor
        if (ClientEmpleat.visibleProperty().get() == false) {
            ClientEmpleat.setVisible(true);
        }
        // Aqui vemos si esta checkeado el cliente
        if (isCliente.selectedProperty().get()) {
            // Si lo esta, mostramos cliente
            ClientEmpleat.getSelectionModel().select(0);
            ClientEmpleat.getTabs().get(0).setDisable(false);
        } else {
            // Si no, lo escondemos.
            ClientEmpleat.getTabs().get(0).setDisable(true);
        }
        // Aqui vemos si esta checkeado el empleado
        if (isEmpleado.selectedProperty().get()) {
            // Si lo esta, mostramos empleado
            ClientEmpleat.getSelectionModel().select(1);
            ClientEmpleat.getTabs().get(1).setDisable(false);
        } else {
            // Si no, lo escondemos.
            ClientEmpleat.getTabs().get(1).setDisable(true);
        }
        // Si ninguno de los dos esta seleccionado, escondemos la TabBar
        if (!isEmpleado.selectedProperty().get() && !isCliente.selectedProperty().get()) {
            ClientEmpleat.setVisible(false);
        }
    }
    // Esta funcion inserta el modelo que se le pasa a la clase.
    public void injecta(Model obj) {
        model = obj;
    }
}
