package com.mycompany.gestiohotelsprojecte;

import com.mycompany.gestiohotelsprojecte.model.Factura;
import com.mycompany.gestiohotelsprojecte.model.Metode_Pagament;
import com.mycompany.gestiohotelsprojecte.model.Model;
import java.time.LocalDate;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Nicolas Leon Sapoznik Pancani
 */
public class FacturaController {

    // Variables del controlador
    private Model model;
    private Factura FacturaReserva;

    @FXML
    TextField clienteFacturar;
    @FXML
    ComboBox reservaAFacturar;
    @FXML
    AnchorPane FacturaAnchor;
    @FXML
    TabPane FacturaTabPane;
    @FXML
    Tab generar;
    @FXML
    Tab verFactura;
    @FXML
    ComboBox metodePagamentGenerar;
    @FXML
    ComboBox metodePagamentVerFactura;
    @FXML
    TextField baseImposable;
    @FXML
    TextField IVA;
    @FXML
    TextField preuTotal;

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
    // Funcion para buscar la persona a traves del DNI una vez dado a buscar.
    private void buscarPersona() {
        // Primero comprobaremos que el texto no este vacio.
        if (!clienteFacturar.getText().isEmpty()) {
            // Ahora comprobaremos si el cliente existe en verdad
            if (model.getClientReserva(clienteFacturar.getText()) != 0) {
                // Si es el caso, soltamos mensaje y preparamos las reservas del mismo.
                alterMos("El client s'ha trobat amb èxit.", false);
                reservaAFacturar.disableProperty().set(false);
                model.recargarCodigoReserva(clienteFacturar.getText());
                reservaAFacturar.setItems(model.getReservas());
                resetFactura();
            } else {
                // Caso que no se encuentre el cliente una vez buscado.
                alterMos("Verifiqueu si heu introduït el DNI bé.", true);
                reservaAFacturar.disableProperty().set(true);
                resetFactura();
            }
        } else {
            // Caso que el campo este vacio.
            reservaAFacturar.disableProperty().set(true);
            resetFactura();
        }
    }

    @FXML
    // Funcion para cargar todo una vez se seleccione una reserva.
    private void reservaSeleccionada() {
        // Si se ha seleccionado de verdad una reserva
        if (!reservaAFacturar.selectionModelProperty().isNull().get()) {
            // Cargaremos la factura de la reserva, si es que existe.
            try {
                FacturaReserva = model.getFactura(Integer.parseInt(reservaAFacturar.getValue().toString()));
            } catch (NumberFormatException e) {
                FacturaReserva = null;
            } catch (Exception e) {
                FacturaReserva = null;
            }
            if (FacturaReserva != null) {
                // Si existe, cargaremos el campo de modificacion de factura.
                FacturaAnchor.setVisible(true);
                verFactura.setDisable(false);
                FacturaTabPane.getSelectionModel().select(1);
                generar.setDisable(true);
                // Tambien nos traeremos todos los datos de la misma factura.
                metodePagamentVerFactura.setItems(model.getMetodePagament());
                metodePagamentVerFactura.getSelectionModel().select(model.getIDFromObservableList(FacturaReserva.getMetode_Pagament().toString(), model.getMetodePagament()));
                baseImposable.setText(String.valueOf(FacturaReserva.getBase_Imposable()));
                IVA.setText(String.valueOf(FacturaReserva.getIva()));
                preuTotal.setText(String.valueOf(FacturaReserva.getTotal()));
            } else {
                // En cambio, si no existe, cargaremos el menu de crear factura.
                metodePagamentGenerar.setItems(model.getMetodePagament());
                FacturaAnchor.setVisible(true);
                verFactura.setDisable(true);
                generar.setDisable(false);
                FacturaTabPane.getSelectionModel().select(0);

            }
        }
    }

    @FXML
    // Esta funcion elimina la factura una vez se le de al boton de eliminar.
    private void eliminarFactura() {
        // Nos aseguraremos que es lo que quiere el cliente.
        if (confirMos("Esteu segur que voleu eliminar la factura seleccionada?")) {
            // Una vez aceptado, eliminaremos la factura.
            if (model.eliminarFactura(FacturaReserva.getID_Factura())) {
                // Si se elimina con exito, lo diremos y reiniciaremos el formulario, para cargar el menu de crear factura.
                alterMos("S'ha eliminat la reserva amb èxit", false);
                model.actualizarReserva(Integer.parseInt(reservaAFacturar.getValue().toString()), 0);
                resetFactura();
                reservaSeleccionada();
            } else {
                // Si no, soltaremos un mensaje.
                alterMos("Alguna cosa ha fallat a l'hora d'eliminar la reserva", true);
            }
        }
    }

    @FXML
    // Esta funcion genera una factura una vez dado al boton.
    private void generarFactura() {
        // Comprobaremos que en verdad haya un metodo de pagamiento generado.
        if (metodePagamentGenerar.getValue() != null) {
            // Una vez comprobado, veremos si el metodo introducido es tarjeta o no.
            int ID_Reserva = Integer.parseInt(reservaAFacturar.getValue().toString());
            if (metodePagamentGenerar.getValue().toString().equals("Targeta")) {
                // Si lo es, aparte de generar la factura como tal
                double[] calculosFactura = model.calcularFactura(ID_Reserva);
                Factura factura = new Factura(model.LocalDateToSqlDate(LocalDate.now()), model.getMetodePagamentFromString(metodePagamentGenerar.getValue().toString()), calculosFactura[0], calculosFactura[1], calculosFactura[2], ID_Reserva);
                // Tambien comprobaremos que el cliente tenga una tarjeta de credito.
                if (factura.checkClienteTarjetaCredito() == true) {
                    // Si la tiene, haremos el alta de factura.
                    if (factura.altaFactura()) {
                        // Una vez hecho, reiniciaremos el formulario por si se quiere introducir otra factura.
                        resetFactura();
                        model.actualizarReserva(Integer.parseInt(reservaAFacturar.getValue().toString()), calculosFactura[2]);
                        reservaSeleccionada();
                        alterMos("S'ha creat la factura amb èxit.", false);
                    } else {
                        // Caso que falle algo en la subida de la factura
                        alterMos("Alguna cosa ha fallat en la creació de la factura.", false);
                    }
                } else {
                    // Caso que el cliente no tenga una tarjeta asociada.
                    alterMos("Aquest client no té una targeta associada, esculli un altre mètode de pagament.", true);
                }
            } else {
                // En el caso de que no sea tarjeta, crearemos la factura y la subiremos.
                double[] calculosFactura = model.calcularFactura(ID_Reserva);
                Factura factura = new Factura(model.LocalDateToSqlDate(LocalDate.now()), model.getMetodePagamentFromString(metodePagamentGenerar.getValue().toString()), calculosFactura[0], calculosFactura[1], calculosFactura[2], ID_Reserva);
                if (factura.altaFactura()) {
                    // Una vez hecho, reiniciaremos el formulario por si se quiere introducir otra factura.
                    resetFactura();
                    model.actualizarReserva(Integer.parseInt(reservaAFacturar.getValue().toString()), calculosFactura[2]);
                    reservaSeleccionada();
                    alterMos("S'ha creat la factura amb èxit.", false);
                } else {
                    // Caso que falle algo en la subida de la factura
                    alterMos("Alguna cosa ha fallat en la creació de la factura.", false);
                }

            }
        }
    }

    @FXML
    // Esta funcion se encarga de editar una factura con informacion nueva, si es que tiene.
    private void editarFactura() {
        // Comenzaremos por comprobar si ha cambiado el metodo de pagamiento.
        if (!metodePagamentVerFactura.getValue().toString().equals(FacturaReserva.getMetode_Pagament().toString())) {
            // Si ha cambiado, veremos si ha cambiado a tarjeta.
            if (metodePagamentVerFactura.getValue().toString().equals("Targeta")) {
                // Si ese es el caso, comprobaremos a ver si el cliente tiene tarjeta
                if (FacturaReserva.checkClienteTarjetaCredito()) {
                    // Si es el caso, cambiaremos eso en el Java y luego haremos la funcion para editar factura.
                    FacturaReserva.setMetode_Pagament(Metode_Pagament.Targeta);
                    if (FacturaReserva.editarFactura()) {
                        // Si funciona, soltamos un mensaje sobre ello.
                        alterMos("Modificació feta amb èxit.", false);

                    } else {
                        // Caso que falle la modificacion de la factura.
                        alterMos("Alguna cosa ha fallat en la modificació de la factura.", true);
                    }
                } else {
                    // Caso que el cliente no tenga tarjeta.
                    alterMos("Aquest client no té targeta, canvieu-lo a un altre mètode de pagament dels disponibles.", true);
                }
            } else {
                // En el caso de que el metodo de pago no sea tarjeta, lo cambiaremos a ese metodo de pago y editaremos la factura
                FacturaReserva.setMetode_Pagament(model.getMetodePagamentFromString(metodePagamentVerFactura.getValue().toString()));
                if (FacturaReserva.editarFactura()) {
                    // Si se edita correctamente, soltaremos un mensaje sobre ello.
                    alterMos("Modificació feta amb èxit.", false);
                } else {
                    // Caso que algo falle en la edicion.
                    alterMos("Alguna cosa ha fallat en la modificació de la factura.", true);
                }
            }
        }
    }

    // Funcion para reiniciar los campos de factura.
    public void resetFactura() {
        FacturaAnchor.setVisible(false);
        // En el caso de que la tab generar no este desactivado, la desactivaremos.
        if (!generar.isDisabled()) {
            metodePagamentGenerar.valueProperty().set(null);
            generar.setDisable(true);
        }
        // En el caso de que la tab verFactura no este desactivado, la desactivaremos.
        if (!verFactura.isDisable()) {
            metodePagamentVerFactura.valueProperty().set(null);
            baseImposable.clear();
            IVA.clear();
            preuTotal.clear();
            verFactura.setDisable(true);
        }
    }

    // Funcion para inicializar alguna cosa del formulario, en este caso esconderlo.
    public void initialize() {
        FacturaAnchor.setVisible(false);
    }

    // Esta funcion inserta el modelo que se le pasa a la clase.
    public void injecta(Model obj) {
        model = obj;
    }

}
