package com.mycompany.gestiohotelsprojecte;

import com.mycompany.gestiohotelsprojecte.model.Model;
import com.mycompany.gestiohotelsprojecte.model.Reserva;
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
    private Reserva ReservaEnEdicionOEliminacion;
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
    @FXML
    ComboBox reservaEdicio;
    @FXML
    ComboBox reservaEliminacio;

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
                model.recargarCodigoReserva(textDNI.getText());
                reservaEdicio.setItems(model.getReservas());
                reservaEliminacio.setItems(model.getReservas());
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
            Date dataIniciTemp = model.LocalDateToSqlDate(dataIniciCreacion.getValue());
            Date dataFinalTemp = model.LocalDateToSqlDate(dataFinalCreacion.getValue());
            int ID_Habitacio = model.getIDHabitacion(Integer.parseInt(habitacionsCreacion.getValue().toString()));
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
    private void reservaEdicionSeleccionada() {
        if (reservaEdicio.getValue() != null) {
            ReservaEnEdicionOEliminacion = model.getReserva(Integer.parseInt(reservaEdicio.getValue().toString()));
            dataIniciEdicion.setValue(ReservaEnEdicionOEliminacion.getData_Inici().toLocalDate());
            dataFinalEdicion.setValue(ReservaEnEdicionOEliminacion.getData_Fi().toLocalDate());
            tipusReservaEdicion.getSelectionModel().select(model.getIDFromObservableList(ReservaEnEdicionOEliminacion.getTipus_Reserva().toString(), model.getTipoReserva()));
            habitacionsEdicion.getSelectionModel().select(model.getIDFromObservableList(String.valueOf(model.getNumeroHabitacion(ReservaEnEdicionOEliminacion.getID_Habitacio())), model.getHabitaciones()));
        }
    }

    @FXML
    private void editarReservaSeleccionada() {
        Boolean AlgoHaCambiado = false;
        if (!model.LocalDateToSqlDate(dataIniciEdicion.getValue()).equals(ReservaEnEdicionOEliminacion.getData_Inici())) {
            AlgoHaCambiado = true;
            System.out.println("a");
        }
        if (!model.LocalDateToSqlDate(dataFinalEdicion.getValue()).equals(ReservaEnEdicionOEliminacion.getData_Fi())) {
            AlgoHaCambiado = true;
            System.out.println("b");
        }
        if (!tipusReservaEdicion.getValue().toString().equals(ReservaEnEdicionOEliminacion.getTipus_Reserva().toString())) {
            AlgoHaCambiado = true;
            System.out.println("c");
        }
        if (Integer.parseInt(habitacionsEdicion.getValue().toString()) != (model.getNumeroHabitacion(ReservaEnEdicionOEliminacion.getID_Habitacio()))) {
            AlgoHaCambiado = true;
            System.out.println("d");
        }
        if (AlgoHaCambiado) {
            System.out.println("Si");
            // ReservaEnEdicionOEliminacion
        } else{
            System.out.println("No");
        }
    }

    ;

    @FXML
    private void restartCamposCreacion() {
        dataIniciCreacion.setValue(null);
        dataFinalCreacion.setValue(null);
        habitacionsCreacion.setValue(null);
        tipusReservaCreacion.setValue(null);
    }

    @FXML
    private void restartCamposEdicion() {
        reservaEdicio.setValue(null);
        dataIniciEdicion.setValue(null);
        dataFinalEdicion.setValue(null);
        habitacionsEdicion.setValue(null);
        tipusReservaEdicion.setValue(null);
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
