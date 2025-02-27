package com.mycompany.gestiohotelsprojecte;

import com.mycompany.gestiohotelsprojecte.model.Model;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

/**
 * FXML Controller class
 *
 * @author Nicolas Leon Sapoznik Pancani
 */
public class ListarTasquesController {
    // Variables del controlador.
    private Model model;
    private MenuBarController menuBar;
    @FXML
    ListView tasques;
    @FXML
    ListView empleatsAsignats;

    // Esta funcion inserta el modelo que se le pasa a la clase, y tambien el controlador de menu.
    public void injecta(Model obj, MenuBarController controler) {
        model = obj;
        menuBar = controler;
    }

    // Funcion para inicializar alguna cosa del formulario, en este caso recargar las tareas disponibles.
    public void initialize() {
        model.recargarTareasAvanzadas();
        tasques.setItems(model.getTareasAvanzadas());
    }

    @FXML
    // Funcion para cuando se seleccione una tarea.
    private void tascaSelecionada() {
        // Ahora comprobaremos si de verdad se ha seleccionado una tarea.
        if (tasques.getSelectionModel().getSelectedItem() != null) {
            // En el caso de que sea correcto, cargaremos los empleados asignados a esa tarea.
            String[] separado = tasques.getSelectionModel().getSelectedItem().toString().split("|");
            String ID_Tasca = separado[0].strip();
            model.getEmpleadosTasca(Integer.parseInt(ID_Tasca));
            empleatsAsignats.setItems(model.getEmpleadosTarea());
        }
    }

    @FXML
    // Funcion para cuando se le de al boton de editar debajo de tareas.
    private void editarTasca() {
        // Enviaremos el ID a ModificarEstatTasca, e iniciaremos ese formulario.
        String[] separado = tasques.getSelectionModel().getSelectedItem().toString().split("\\|");
        String ID_Tasca = separado[0].strip();
        model.setIDTascaORealitzaSeleccionada("T," + ID_Tasca);
        try {
            menuBar.cambiarModificarEstatTasca();
        } catch (IOException e) {
        }
    }

    @FXML
    // Funcion para cuando se le de al boton de editar debajo de asignaciones.
    private void editarRealitza() {
        // Enviaremos el ID a ModificarEstatTasca, e iniciaremos ese formulario.
        String[] separado = tasques.getSelectionModel().getSelectedItem().toString().split("\\|");
        String ID_Tasca = separado[0].strip();
        String[] separadoRealitza = empleatsAsignats.getSelectionModel().getSelectedItem().toString().split("\\|");
        int ID_Empleat = model.getIDEmpleatTasca(separadoRealitza[1].strip());
        model.setIDTascaORealitzaSeleccionada("R," + ID_Tasca + "," + ID_Empleat);
        try {
            menuBar.cambiarModificarEstatTasca();
        } catch (IOException e) {
        }

    }
}
