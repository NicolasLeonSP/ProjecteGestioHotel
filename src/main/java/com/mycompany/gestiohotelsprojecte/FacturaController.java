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
    private void buscarPersona() {
        if (!clienteFacturar.getText().isEmpty()) {
            if (model.getClientReserva(clienteFacturar.getText()) != 0) {
                alterMos("Se ha encontrado el cliente con exito.", false);
                reservaAFacturar.disableProperty().set(false);
                model.recargarCodigoReserva(clienteFacturar.getText());
                reservaAFacturar.setItems(model.getReservas());
                resetFactura();
            } else {
                alterMos("Verifique si ha introducido el DNI bien", true);
            }
        } else {
            reservaAFacturar.disableProperty().set(true);
        }
    }

    @FXML
    private void reservaSeleccionada() {
        if (!reservaAFacturar.selectionModelProperty().isNull().get()) {
            FacturaReserva = model.getFactura(Integer.parseInt(reservaAFacturar.getValue().toString()));
            if (FacturaReserva != null) {
                FacturaAnchor.setVisible(true);
                verFactura.setDisable(false);
                FacturaTabPane.getSelectionModel().select(1);
                generar.setDisable(true);
                metodePagamentVerFactura.setItems(model.getMetodePagament());
                metodePagamentVerFactura.getSelectionModel().select(model.getIDFromObservableList(FacturaReserva.getMetode_Pagament().toString(), model.getMetodePagament()));
                baseImposable.setText(String.valueOf(FacturaReserva.getBase_Imposable()));
                IVA.setText(String.valueOf(FacturaReserva.getIva()));
                preuTotal.setText(String.valueOf(FacturaReserva.getTotal()));
            } else {
                metodePagamentGenerar.setItems(model.getMetodePagament());
                FacturaAnchor.setVisible(true);
                verFactura.setDisable(true);
                generar.setDisable(false);
                FacturaTabPane.getSelectionModel().select(0);

            }
        }
    }

    @FXML
    private void eliminarFactura() {
        if (confirMos("¿Esta seguro de que quiere eliminar la reserva seleccionada?")) {
            if (model.eliminarFactura(FacturaReserva.getID_Factura())) {
                alterMos("Se ha eliminado la reserva con exito", false);
                model.actualizarReserva(Integer.parseInt(reservaAFacturar.getValue().toString()), 0);
                resetFactura();
                reservaSeleccionada();
            } else {
                alterMos("Algo ha fallado a la hora de eliminar la reserva", true);
            }
        }
    }

    @FXML
    private void generarFactura() {
        if (metodePagamentGenerar.getValue() != null) {
            int ID_Reserva = Integer.parseInt(reservaAFacturar.getValue().toString());
            if (metodePagamentGenerar.getValue().toString().equals("Targeta")) {
                double[] calculosFactura = model.calcularFactura(ID_Reserva);
                Factura factura = new Factura(model.LocalDateToSqlDate(LocalDate.now()), model.getMetodePagamentFromString(metodePagamentGenerar.getValue().toString()), calculosFactura[0], calculosFactura[1], calculosFactura[2], ID_Reserva);
                if (factura.checkClienteTarjetaCredito() == true) {
                    if (factura.altaFactura()) {
                        resetFactura();
                        model.actualizarReserva(Integer.parseInt(reservaAFacturar.getValue().toString()), calculosFactura[2]);
                        reservaSeleccionada();
                        alterMos("Se ha creado la factura con exito.", false);
                    } else {
                        alterMos("Algo ha fallado en la creacion de la factura.", false);
                    }
                } else {
                    alterMos("Ese cliente no tiene una tarjeta asociada, elija otro metodo de pago", false);
                }
            } else {
                double[] calculosFactura = model.calcularFactura(ID_Reserva);
                Factura factura = new Factura(model.LocalDateToSqlDate(LocalDate.now()), model.getMetodePagamentFromString(metodePagamentGenerar.getValue().toString()), calculosFactura[0], calculosFactura[1], calculosFactura[2], ID_Reserva);
                if (factura.altaFactura()) {
                    resetFactura();
                    model.actualizarReserva(Integer.parseInt(reservaAFacturar.getValue().toString()), calculosFactura[2]);
                    reservaSeleccionada();
                    alterMos("Se ha creado la factura con exito.", false);
                } else {
                    alterMos("Algo ha fallado en la creacion de la factura.", false);
                }

            }
        }
    }

    // Tienes el metodo hecho de checkTarjetaCredito, faltaria el metodo de la edicion como tal.
    @FXML
    private void editarFactura() {
        if (!metodePagamentVerFactura.getValue().toString().equals(FacturaReserva.getMetode_Pagament().toString())) {
            if (metodePagamentVerFactura.getValue().toString().equals("Targeta")) {
                if (FacturaReserva.checkClienteTarjetaCredito()) {
                    FacturaReserva.setMetode_Pagament(Metode_Pagament.Targeta);
                    if (FacturaReserva.editarFactura()) {
                        alterMos("Modificacion hecha con exito", false);

                    } else {
                        alterMos("Algo ha fallado en la modificacion de la factura", true);
                    }
                } else {
                    alterMos("Ese cliente no tiene tarjeta, cambielo a otro metodo de pago de los disponibles", true);
                }
            } else {
                FacturaReserva.setMetode_Pagament(model.getMetodePagamentFromString(metodePagamentVerFactura.getValue().toString()));
                if (FacturaReserva.editarFactura()) {
                    alterMos("Modificacion hecha con exito", false);

                } else {
                    alterMos("Algo ha fallado en la modificacion de la factura", true);
                }
            }
        }
    }

    public void resetFactura() {
        FacturaAnchor.setVisible(false);
        if (!generar.isDisabled()) {
            metodePagamentGenerar.valueProperty().set(null);
            generar.setDisable(true);
        }
        if (!verFactura.isDisable()) {
            metodePagamentVerFactura.valueProperty().set(null);
            baseImposable.clear();
            IVA.clear();
            preuTotal.clear();
            verFactura.setDisable(true);
        }
    }

    public void initialize() {
        FacturaAnchor.setVisible(false);
    }

    public void injecta(Model obj) {
        model = obj;
    }

}
