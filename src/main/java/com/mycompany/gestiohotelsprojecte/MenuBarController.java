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
    private static PrimaryController controlador;
    private static SecondaryController controlador2;
    
    @FXML
    private void switchFxml(String nomFxml) throws IOException {
        Centre.getChildren().clear();
        //Crear el fxml a visualitar
        FXMLLoader loader = new FXMLLoader(getClass().getResource(nomFxml));
        loader.setControllerFactory(controllerType -> {
            if (controllerType == PrimaryController.class) {
                return controlador;
            }
            if (controllerType == SecondaryController.class) {
                return controlador2;
            }
            return null;
        });
        AnchorPane vistaAcarregar = loader.load();
        //Obtenim els fills del panel central i afegim la nova vista.
        Centre.getChildren().add(vistaAcarregar);
    }
    
    @FXML
    private void cambiarPersona() throws IOException {
        switchFxml("primary.fxml");
    }
    @FXML
    private void cambiarReserva() throws IOException {
        switchFxml("secondary.fxml");
    }
    
    public void injecta(Model obj) {
        model = obj;
        controlador = new PrimaryController();
        controlador2 = new SecondaryController();
        controlador.injecta(model);
        controlador2.injecta(model);
        model.initModel();
    }

}
