package com.mycompany.gestiohotelsprojecte;

import com.mycompany.gestiohotelsprojecte.model.Model;
import com.mycompany.gestiohotelsprojecte.model.Reserva;
import com.mycompany.gestiohotelsprojecte.model.Tipus_Reserva;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class ReservaController {

    private Model model;
    private Reserva ReservaEnEdicion;
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

    private boolean confirMos(String misgg) {
        Alert alerta;
        alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setContentText(misgg);
        Optional<ButtonType> confir = alerta.showAndWait();
        if (confir.isPresent() && confir.get().equals(ButtonType.OK)) {
            return true;
        } else {
            return false;
        }
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
            if (model.getClientReserva(textDNI.getText()) != -1) {
                alterMos("El client s'ha trobat amb èxit.", false);
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
                alterMos("Verifiqueu si heu introduït el DNI bé.", true);
            }
        }
    }

    @FXML
    private void recargarReservas() {
        model.recargarHabitaciones();
        habitacionsCreacion.setItems(model.getHabitaciones());
        tipusReservaCreacion.setItems(model.getTipoReserva());
        habitacionsEdicion.setItems(model.getHabitaciones());
        tipusReservaEdicion.setItems(model.getTipoReserva());
        model.recargarCodigoReserva(textDNI.getText());
        reservaEdicio.setItems(model.getReservas());
        reservaEliminacio.setItems(model.getReservas());
    }

    // Comprobar habitaciones durante la creacion, para ver si para esa fecha esta ocupada o no.
    @FXML
    private void crearReserva() {
        if (dataIniciCreacion.getValue() != null && dataFinalCreacion.getValue() != null && habitacionsCreacion.getValue() != null && tipusReservaCreacion.getValue() != null) {
            Date dataActual = model.LocalDateToSqlDate(LocalDate.now());
            Date dataIniciTemp = model.LocalDateToSqlDate(dataIniciCreacion.getValue());
            Date dataFinalTemp = model.LocalDateToSqlDate(dataFinalCreacion.getValue());
            int ID_Habitacio = model.getIDHabitacion(Integer.parseInt(habitacionsCreacion.getValue().toString()));
            Reserva reserva = new Reserva(dataActual, dataIniciTemp, dataFinalTemp, (Tipus_Reserva) tipusReservaCreacion.getValue(), model.getIVAClient(model.getTipusClienteReserva()), 0, model.getIDClienteReserva(), ID_Habitacio);
            String errorReserva = reserva.altaReserva();
            if (errorReserva.equals("")) {
                alterMos("Creació de la reserva completada.", false);
                restartCamposCreacion();
                recargarReservas();
            } else {
                String campoError = model.retornarMensajeCorrectoReserva(errorReserva);
                if (campoError.equals("data_Inici")) {
                    alterMos("Verifiqueu que la data d'inici estigui ben posada. Ha de ser superior a l'actual i menor a la data final.", true);
                } else if (campoError.equals("data_Fi")) {
                    alterMos("Verifiqueu que la data final estigui ben posada. Deu ser més gran a la data d'inici.", true);
                } else {
                    alterMos(campoError, true);
                }
            }
        } else {
            alterMos("Verifiqueu que tots els camps han estat emplenats", true);
        }
    }

    @FXML
    private void reservaEdicionSeleccionada() {
        if (reservaEdicio.getValue() != null) {
            ReservaEnEdicion = model.getReserva(Integer.parseInt(reservaEdicio.getValue().toString()));
            dataIniciEdicion.setValue(ReservaEnEdicion.getData_Inici().toLocalDate());
            dataFinalEdicion.setValue(ReservaEnEdicion.getData_Fi().toLocalDate());
            tipusReservaEdicion.getSelectionModel().select(model.getIDFromObservableList(ReservaEnEdicion.getTipus_Reserva().toString(), model.getTipoReserva()));
            habitacionsEdicion.getSelectionModel().select(model.getIDFromObservableList(String.valueOf(model.getNumeroHabitacion(ReservaEnEdicion.getID_Habitacio())), model.getHabitaciones()));
        }
    }

    @FXML
    private void editarReservaSeleccionada() {
        Boolean AlgoHaCambiado = false;
        if (!model.LocalDateToSqlDate(dataIniciEdicion.getValue()).equals(ReservaEnEdicion.getData_Inici())) {
            AlgoHaCambiado = true;
        }
        if (!model.LocalDateToSqlDate(dataFinalEdicion.getValue()).equals(ReservaEnEdicion.getData_Fi())) {
            AlgoHaCambiado = true;
        }
        if (!tipusReservaEdicion.getValue().toString().equals(ReservaEnEdicion.getTipus_Reserva().toString())) {
            AlgoHaCambiado = true;
        }
        if (Integer.parseInt(habitacionsEdicion.getValue().toString()) != (model.getNumeroHabitacion(ReservaEnEdicion.getID_Habitacio()))) {
            AlgoHaCambiado = true;
        }
        if (AlgoHaCambiado) {
            ReservaEnEdicion.setData_Inici(model.LocalDateToSqlDate(dataIniciEdicion.getValue()));
            ReservaEnEdicion.setData_Fi(model.LocalDateToSqlDate(dataFinalEdicion.getValue()));
            ReservaEnEdicion.setTipus_Reserva((Tipus_Reserva) tipusReservaEdicion.getValue());
            ReservaEnEdicion.setID_Habitacio(model.getIDHabitacion(Integer.parseInt(habitacionsEdicion.getValue().toString())));
            String errorReserva = ReservaEnEdicion.modificarReserva();
            if (errorReserva.equals("")) {
                alterMos("S'ha modificat la reserva amb èxit", false);
                restartCamposEdicion();
                ReservaEnEdicion = null;
            } else {
                String campoError = model.retornarMensajeCorrectoReserva(errorReserva);
                if (campoError.equals("data_Inici")) {
                    alterMos("Verifiqueu que la data d'inici estigui ben posada. Ha de ser superior a l'actual i menor a la data final", true);
                } else if (campoError.equals("data_Fi")) {
                    alterMos("Verifiqueu que la data final estigui ben posada. Deu ser més gran a la data d'inici.", true);
                } else {
                    alterMos(campoError, true);
                }
            }
        } else {
            alterMos("Modifiqueu algun camp dels presents si voleu modificar la reserva.", true);
        }
    }

    @FXML
    private void eliminarReservaSeleccionada() {
        if (reservaEliminacio.getValue() != null) {
            if (confirMos("Esteu segur que voleu eliminar la reserva seleccionada?")) {
                if (model.eliminarReserva(Integer.parseInt(reservaEliminacio.getValue().toString()))) {
                    alterMos("S'ha eliminat la reserva amb èxit.", false);
                    recargarReservas();
                } else {
                    alterMos("Alguna cosa ha fallat a l'hora d'eliminar la reserva.", true);
                }
            }
        } else {
            alterMos("Seleccioneu una reserva abans de suprimir-la.", true);
        }
    }

    @FXML
    private void restartCamposCreacion() {
        dataIniciCreacion.getEditor().clear();
        dataFinalCreacion.getEditor().clear();
        habitacionsCreacion.valueProperty().set(null);
        tipusReservaCreacion.valueProperty().set(null);
    }

    @FXML
    private void restartCamposEdicion() {
        reservaEdicio.valueProperty().set(null);
        dataIniciEdicion.setValue(null);
        dataFinalEdicion.setValue(null);
        habitacionsEdicion.valueProperty().set(null);
        tipusReservaEdicion.valueProperty().set(null);
    }

    @FXML
    private void restarCamposEliminacion() {
        reservaEliminacio.valueProperty().set(null);
    }

    public void injecta(Model obj) {
        model = obj;
    }
}
