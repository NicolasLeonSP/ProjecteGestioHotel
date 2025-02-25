package com.mycompany.gestiohotelsprojecte;

import com.mycompany.gestiohotelsprojecte.model.Factura;
import com.mycompany.gestiohotelsprojecte.model.Model;
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
                alterMos("Se ha encontrado la persona con exito.", false);
                reservaAFacturar.disableProperty().set(false);
                model.recargarCodigoReserva(clienteFacturar.getText());
                reservaAFacturar.setItems(model.getReservas());
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
                resetFactura();
                reservaSeleccionada();
            } else {
                alterMos("Algo ha fallado a la hora de eliminar la reserva", true);
            }
        }
    }
    
    // Tienes el metodo hecho de checkTarjetaCredito, faltaria el metodo de la edicion como tal.
    @FXML
    private void editarFactura() {
    
    }

    public void resetFactura() {
        FacturaAnchor.setVisible(false);
        verFactura.setDisable(true);
        generar.setDisable(true);
        metodePagamentGenerar.valueProperty().set(null);
        metodePagamentVerFactura.valueProperty().set(null);
        baseImposable.clear();
        IVA.clear();
        preuTotal.clear();
    }

    public void initialize() {
        FacturaAnchor.setVisible(false);
    }

    // Seria hacer que solo aparezca una tab, dependiendo de si se ha creado o no la factura.
    public void injecta(Model obj) {
        model = obj;
    }

}
