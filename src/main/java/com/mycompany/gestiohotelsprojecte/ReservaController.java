package com.mycompany.gestiohotelsprojecte;

import com.mycompany.gestiohotelsprojecte.model.Model;
import com.mycompany.gestiohotelsprojecte.model.Reserva;
import com.mycompany.gestiohotelsprojecte.model.Tipus_Reserva;
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

    // Variables del controlador.
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

    // Funcion para crear una alerta de confirmacion, pasandole el mensaje por un parametro
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
    // Funcion para buscar la persona una vez se le de al boton de buscar.
    private void buscarCliente() {
        // Ahora comprobaremos que el texto no este vacio.
        if (!textDNI.getText().isEmpty()) {
            // Si no lo esta, comprobaremos que el cliente de verdad exista, tambien lo guardaremos si existe.
            if (model.getClientReserva(textDNI.getText()) != -1) {
                // Si el cliente existe, cargaremos sus reservas, si es que tiene, y las otras partes del formulario. 
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
                // Caso que no exista el cliente.
                Reserva.disableProperty().set(true);
                alterMos("Verifiqueu si heu introduït el DNI bé.", true);
            }
        }
    }

    @FXML
    // Funcion que se encargara de recargar todos los datos que sean necesarios de recargar.
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

    @FXML
    // Funcion que se encargara de crear la reserva una vez le demos a crear.
    private void crearReserva() {
        // Nos aseguraremosq ue los campos no esten vacios.
        if (dataIniciCreacion.getValue() != null && dataFinalCreacion.getValue() != null && habitacionsCreacion.getValue() != null && tipusReservaCreacion.getValue() != null) {
            // Si no lo estan, formataremos las fechas y subiremos la reserva.
            Date dataActual = model.LocalDateToSqlDate(LocalDate.now());
            Date dataIniciTemp = model.LocalDateToSqlDate(dataIniciCreacion.getValue());
            Date dataFinalTemp = model.LocalDateToSqlDate(dataFinalCreacion.getValue());
            int ID_Habitacio = model.getIDHabitacion(Integer.parseInt(habitacionsCreacion.getValue().toString()));
            Reserva reserva = new Reserva(dataActual, dataIniciTemp, dataFinalTemp, (Tipus_Reserva) tipusReservaCreacion.getValue(), model.getIVAClient(model.getTipusClienteReserva()), 0, model.getIDClienteReserva(), ID_Habitacio);
            String errorReserva = reserva.altaReserva();
            // Veremos a ver si ha habido algun error
            if (errorReserva.equals("")) {
                // Caso que no, soltaremos un mensaje y reiniciaremos creacion y recargaremos las reservas.
                alterMos("Creació de la reserva completada.", false);
                restartCamposCreacion();
                recargarReservas();
            } else {
                // Caso que si haya, separaremos el mensaje para que nos retorne el campo en concreto.
                String campoError = model.retornarMensajeCorrectoReserva(errorReserva);
                // Si el campo que ha dado error es fecha de inicio, lo decimos.
                if (campoError.equals("data_Inici")) {
                    alterMos("Verifiqueu que la data d'inici estigui ben posada. Ha de ser superior a l'actual i menor a la data final.", true);
                } // Si el campo que ha dado error es la fecha final, lo decimos tambien.
                else if (campoError.equals("data_Fi")) {
                    alterMos("Verifiqueu que la data final estigui ben posada. Deu ser més gran a la data d'inici.", true);
                } // Si el campo que ha dado error no es ninguno de los dos anteriores, lo soltaremos en el mensaje.
                else {
                    alterMos(campoError, true);
                }
            }
        } else {
            // Caso que todoso los campos no esten rellenados.
            alterMos("Verifiqueu que tots els camps han estat emplenats", true);
        }
    }

    @FXML
    // Esta funcion se encarga de cargar los datos de la reserva en edicion seleccionada.
    private void reservaEdicionSeleccionada() {
        // Primero veremos si ha seleccionado alguna como tal.
        if (reservaEdicio.getValue() != null) {
            // Si es el caso, cargaremos todos los campos del formulario de edicion.
            ReservaEnEdicion = model.getReserva(Integer.parseInt(reservaEdicio.getValue().toString()));
            dataIniciEdicion.setValue(ReservaEnEdicion.getData_Inici().toLocalDate());
            dataFinalEdicion.setValue(ReservaEnEdicion.getData_Fi().toLocalDate());
            tipusReservaEdicion.getSelectionModel().select(model.getIDFromObservableList(ReservaEnEdicion.getTipus_Reserva().toString(), model.getTipoReserva()));
            habitacionsEdicion.getSelectionModel().select(model.getIDFromObservableList(String.valueOf(model.getNumeroHabitacion(ReservaEnEdicion.getID_Habitacio())), model.getHabitaciones()));
        }
    }

    @FXML
    // Esta funcion se encarga de editar la reserva una vez le demos al boton.
    private void editarReservaSeleccionada() {
        // Primero, verificaremos que algo haya cambiado como tal.
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
        // Si ese es el caso...
        if (AlgoHaCambiado) {
            // Editaremos la reserva en Java, y luego la modificaremos en la base de datos.
            ReservaEnEdicion.setData_Inici(model.LocalDateToSqlDate(dataIniciEdicion.getValue()));
            ReservaEnEdicion.setData_Fi(model.LocalDateToSqlDate(dataFinalEdicion.getValue()));
            ReservaEnEdicion.setTipus_Reserva((Tipus_Reserva) tipusReservaEdicion.getValue());
            ReservaEnEdicion.setID_Habitacio(model.getIDHabitacion(Integer.parseInt(habitacionsEdicion.getValue().toString())));
            String errorReserva = ReservaEnEdicion.modificarReserva();
            // Veremos si retorna algun error.
            if (errorReserva.equals("")) {
                // Caso que no, soltaremos un mensaje y reiniciaremos creacion y recargaremos las reservas.
                alterMos("S'ha modificat la reserva amb èxit", false);
                restartCamposEdicion();
                ReservaEnEdicion = null;
            } else {
                // Caso que si haya, separaremos el mensaje para que nos retorne el campo en concreto.
                String campoError = model.retornarMensajeCorrectoReserva(errorReserva);
                // Si el campo que ha dado error es fecha de inicio, lo decimos.
                if (campoError.equals("data_Inici")) {
                    alterMos("Verifiqueu que la data d'inici estigui ben posada. Ha de ser superior a l'actual i menor a la data final.", true);
                } // Si el campo que ha dado error es la fecha final, lo decimos tambien.
                else if (campoError.equals("data_Fi")) {
                    alterMos("Verifiqueu que la data final estigui ben posada. Deu ser més gran a la data d'inici.", true);
                } // Si el campo que ha dado error no es ninguno de los dos anteriores, lo soltaremos en el mensaje.
                else {
                    alterMos(campoError, true);
                }
            }
        } else {
            // Caso que ningun campo haya sido editado.
            alterMos("Modifiqueu algun camp dels presents si voleu modificar la reserva.", true);
        }
    }

    @FXML
    // Esta funcion se encarga de eliminar una reserva seleccionada.
    private void eliminarReservaSeleccionada() {
        // Veremos si hay alguna reserva seleccionada como tal.
        if (reservaEliminacio.getValue() != null) {
            // Preguntaremos al usuario si es eso lo que queria.
            if (confirMos("Esteu segur que voleu eliminar la reserva seleccionada?")) {
                // Si es el caso, eliminaremos la reserva.
                if (model.eliminarReserva(Integer.parseInt(reservaEliminacio.getValue().toString()))) {
                    // Si se ha eliminado correctamente, soltamos mensaje y recargamos reserva.
                    alterMos("S'ha eliminat la reserva amb èxit.", false);
                    recargarReservas();
                } else {
                    // Si algo ha fallado, soltamos un mensaje al respecto.
                    alterMos("Alguna cosa ha fallat a l'hora d'eliminar la reserva.", true);
                }
            }
        } else {
            // Si no se ha seleccionado una reserva, tambien soltaremos un mensaje sobre ello.
            alterMos("Seleccioneu una reserva abans de suprimir-la.", true);
        }
    }

    @FXML
    // Esta funcion se encarga de reiniciar los campos de la pestaña de creacion
    private void restartCamposCreacion() {
        dataIniciCreacion.getEditor().clear();
        dataFinalCreacion.getEditor().clear();
        habitacionsCreacion.valueProperty().set(null);
        tipusReservaCreacion.valueProperty().set(null);
    }

    @FXML
    // Esta funcion se encarga de reiniciar los campos de la pestaña de edicion
    private void restartCamposEdicion() {
        reservaEdicio.valueProperty().set(null);
        dataIniciEdicion.setValue(null);
        dataFinalEdicion.setValue(null);
        habitacionsEdicion.valueProperty().set(null);
        tipusReservaEdicion.valueProperty().set(null);
    }

    @FXML
    // Esta funcion se encarga de reiniciar los campos de la pestaña de eliminacion
    private void restarCamposEliminacion() {
        reservaEliminacio.valueProperty().set(null);
    }
    // Esta funcion inserta el modelo que se le pasa a la clase.
    public void injecta(Model obj) {
        model = obj;
    }
}
