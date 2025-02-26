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
    private static CrearTascaController controlador4;
    private static ListarTasquesController controlador5;
    private static ModificarEstatTascaController controlador6;

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
            if (controllerType == CrearTascaController.class) {
                return controlador4;
            }
            if (controllerType == ListarTasquesController.class) {
                return controlador5;
            }
            if (controllerType == ModificarEstatTascaController.class) {
                return controlador6;
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
    
    @FXML
    private void cambiarCrearTasca() throws IOException {
        switchFxml("CrearTasca.fxml");
    }
    
    @FXML
    public void cambiarListarTasques() throws IOException {
        switchFxml("ListarTasques.fxml");
    }
    
    @FXML
    public void cambiarModificarEstatTasca() throws IOException {
        switchFxml("ModificarEstatTasca.fxml");
    }

    @FXML
    private void cambiarBienvenida() throws IOException {
        switchFxml("Bienvenida.fxml");
    }

    public void initialize() throws IOException {
        cambiarBienvenida();
    }

    public void injecta(Model obj) {
        model = obj;
        controlador = new ClientEmpleatController();
        controlador2 = new ReservaController();
        controlador3 = new FacturaController();
        controlador4 = new CrearTascaController();
        controlador5 = new ListarTasquesController();
        controlador6 = new ModificarEstatTascaController();
        controlador.injecta(model);
        controlador2.injecta(model);
        controlador3.injecta(model);
        controlador4.injecta(model);
        controlador5.injecta(model, this);
        controlador6.injecta(model, this);
        model.initModel();
    }

}
