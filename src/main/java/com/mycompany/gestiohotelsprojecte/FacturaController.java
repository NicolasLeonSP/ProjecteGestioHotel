package com.mycompany.gestiohotelsprojecte;

import com.mycompany.gestiohotelsprojecte.model.Model;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Nicolas Leon Sapoznik Pancani
 */
public class FacturaController {

    private Model model;

    @FXML
    TextField clienteFacturar;

    @FXML
    ComboBox reservaAFacturar;
    
    @FXML
    AnchorPane Factura;
    
    @FXML
    Tab verFactura;

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
            
        }
    }

    // Seria hacer que solo aparezca una tab, dependiendo de si se ha creado o no la factura.
    public void injecta(Model obj) {
        model = obj;
    }

}
