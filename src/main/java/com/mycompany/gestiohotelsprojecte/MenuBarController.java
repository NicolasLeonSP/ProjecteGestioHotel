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
    // Variables del controlador.
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
    // Esta funcion se encarga de cambiar al FXML que se le diga en los parametros.
    private void switchFxml(String nomFxml) throws IOException {
        // Limpiamos el que habia
        Centre.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(nomFxml));
        // Y aqui vemos cual de todos los controladores, devolviendo el equivalente a objeto.
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
        // Aqui los cargamos como tal
        AnchorPane vistaAcarregar = loader.load();
        // Y añadimos la nueva vista.
        Centre.getChildren().add(vistaAcarregar);
    }
    
    @FXML
    // Funcion para cambiar al FXML de clienteEmpleado
    private void cambiarClientEmpleat() throws IOException {
        switchFxml("ClientEmpleat.fxml");
    }

    @FXML
    // Funcion para cambiar al FXML de Reserva
    private void cambiarReserva() throws IOException {
        switchFxml("Reserva.fxml");
    }

    @FXML
    // Funcion para cambiar al FXML de Factura
    private void cambiarFactura() throws IOException {
        switchFxml("Factura.fxml");
    }
    
    @FXML
    // Funcion para cambiar al FXML de CrearTarea
    private void cambiarCrearTasca() throws IOException {
        switchFxml("CrearTasca.fxml");
    }
    
    @FXML
    // Funcion para cambiar al FXML de ListarTareas
    public void cambiarListarTasques() throws IOException {
        switchFxml("ListarTasques.fxml");
    }
    
    @FXML
    // Funcion para cambiar al FXML de ModificarEstadoTarea
    public void cambiarModificarEstatTasca() throws IOException {
        switchFxml("ModificarEstatTasca.fxml");
    }

    @FXML
    // Funcion para cambiar al FXML de Bienvenida
    private void cambiarBienvenida() throws IOException {
        switchFxml("Bienvenida.fxml");
    }
    // Funcion para inicializar este formulario, en este caso, para mostrar el formulario de bienvenida.
    public void initialize() throws IOException {
        cambiarBienvenida();
    }
    // Funcion para inyectar el modelo en todos los formularios, ademas de crearlos como tal.
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
