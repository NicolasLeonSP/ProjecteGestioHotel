package com.mycompany.gestiohotelsprojecte;

import com.mycompany.gestiohotelsprojecte.model.Model;
import com.mycompany.gestiohotelsprojecte.model.Reserva;
import com.mycompany.gestiohotelsprojecte.model.Tipus_Client;
import com.mycompany.gestiohotelsprojecte.model.Tipus_IVA;
import com.mycompany.gestiohotelsprojecte.model.Tipus_Reserva;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class SecondaryController {

    private Model model;
    @FXML
    AnchorPane Reserva;
    @FXML
    TextField textDNI;
    @FXML
    DatePicker dataIniciCreacion;
    @FXML
    DatePicker dataFinalCreacion;
    @FXML
    ComboBox habitacionsCreacion;
    @FXML
    ComboBox tipusReservaCreacion;
    @FXML
    DatePicker dataIniciEdicion;
    @FXML
    DatePicker dataFinalEdicion;
    @FXML
    ComboBox habitacionsEdicion;
    @FXML
    ComboBox tipusReservaEdicion;

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
    private void switchToPersona() throws IOException {
        App.setRoot("primary");
    }

    @FXML
    private void switchToReserves() throws IOException {
        App.setRoot("secondary");
    }

    @FXML
    private void buscarPersona() {
        if (!textDNI.getText().isEmpty()) {
            if (model.getClientReserva(textDNI.getText()) != 0) {
                alterMos("Se ha encontrado la persona con exito.", false);
                Reserva.disableProperty().set(false);
                model.recargarHabitaciones();
                habitacionsCreacion.setItems(model.getHabitaciones());
                tipusReservaCreacion.setItems(model.getTipoReserva());
                habitacionsEdicion.setItems(model.getHabitaciones());
                tipusReservaEdicion.setItems(model.getTipoReserva());
            } else {
                alterMos("Verifique si ha introducido el DNI bien", true);
            }
        }
    }
    // Comprobar habitaciones durante la creacion, para ver si para esa fecha esta ocupada o no.
    @FXML
    private void crearReserva() {
        if (dataIniciCreacion.getValue() != null && dataFinalCreacion.getValue() != null && habitacionsCreacion.getValue() != null && tipusReservaCreacion.getValue() != null) {
            Date dataActual = new java.sql.Date((Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()).getTime()));
            Date dataIniciTemp = new java.sql.Date(Date.from(dataIniciCreacion.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()).getTime());
            Date dataFinalTemp = new java.sql.Date(Date.from(dataFinalCreacion.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()).getTime());
            int ID_Habitacio = model.getIDHabitacion(Integer.parseInt(habitacionsCreacion.getValue().toString()));
            // Por terminar, los dos ID
            String errorReserva = model.altaReserva(new Reserva(dataActual, dataIniciTemp, dataFinalTemp, (Tipus_Reserva) tipusReservaCreacion.getValue(), model.getIVAClient(model.getTipusClienteReserva()), 0, model.getIDClienteReserva(), ID_Habitacio));
            if (errorReserva.equals("")) {
                alterMos("Creacion de la reserva completada", false);
                restartCamposCreacion();
            } else {
                alterMos(errorReserva, true);
            }
        } else {
            alterMos("Verifique que todos los campos han sido rellenados", true);
        }
    }
    
    @FXML
    private void reservaEdicionSeleccionada(){
        
    }

    @FXML
    private void restartCamposCreacion() {
        dataIniciCreacion.setValue(null);
        dataFinalCreacion.setValue(null);
        habitacionsCreacion.setValue(null);
        tipusReservaCreacion.setValue(null);
    }
    
    @FXML
    private void restartCamposEdicion() {
        dataIniciCreacion.setValue(null);
        dataFinalCreacion.setValue(null);
        habitacionsCreacion.setValue(null);
        tipusReservaCreacion.setValue(null);
    }

    @FXML
    private void restartForm() throws IOException {
        // Al cambiar de pestaña, se reinician los campos introducidos
        Reserva.disableProperty().set(true);
        App.setRoot("secondary");
    }

    public void injecta(Model obj) {
        model = obj;
    }
}
