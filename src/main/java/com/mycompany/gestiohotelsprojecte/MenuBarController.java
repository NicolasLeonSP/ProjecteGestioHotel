package com.mycompany.gestiohotelsprojecte;

import com.mycompany.gestiohotelsprojecte.model.Model;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;


/**
 * FXML Controller class
 *
 * @author alumne
 */
public class MenuBarController {

    private Model model;
    
    @FXML
    AnchorPane Principal;
    @FXML
    AnchorPane Centre;
    private static ClientEmpleatController controlador;
    private static ReservaController controlador2;
    private static FacturaController controlador3;
    
    @FXML
    private void switchFxml(String nomFxml) throws IOException {
        Centre.getChildren().clear();
        //Crear el fxml a visualitar
        FXMLLoader loader = new FXMLLoader(getClass().getResource(nomFxml));
        loader.setControllerFactory(controllerType -> {
            if (controllerType == ClientEmpleatController.class) {
                return controlador;
            }
            if (controllerType == ReservaController.class) {
                return controlador2;
            }
            if (controllerType == FacturaController.class) {
                return controlador3;
            }
            return null;
        });
        AnchorPane vistaAcarregar = loader.load();
        //Obtenim els fills del panel central i afegim la nova vista.
        Centre.getChildren().add(vistaAcarregar);
    }
    
    @FXML
    private void cambiarPersona() throws IOException {
        switchFxml("ClientEmpleat.fxml");
    }
    @FXML
    private void cambiarReserva() throws IOException {
        switchFxml("Reserva.fxml");
    }
    @FXML
    private void cambiarFactura() throws IOException {
        switchFxml("Factura.fxml");
    }
    
    public void injecta(Model obj) {
        model = obj;
        controlador = new ClientEmpleatController();
        controlador2 = new ReservaController();
        controlador3 = new FacturaController();
        controlador.injecta(model);
        controlador2.injecta(model);
        controlador3.injecta(model);
        model.initModel();
    }

}
