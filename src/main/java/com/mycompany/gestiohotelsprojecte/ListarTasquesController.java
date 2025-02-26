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

    private Model model;
    private MenuBarController menuBar;
    @FXML
    ListView tasques;
    @FXML
    ListView empleatsAsignats;

    public void injecta(Model obj, MenuBarController controler) {
        model = obj;
        menuBar = controler;
    }

    public void initialize() {
        model.recargarTareasAvanzadas();
        tasques.setItems(model.getTareasAvanzadas());
    }

    @FXML
    private void tascaSelecionada() {
        if (tasques.getSelectionModel().getSelectedItem() != null) {
            String[] separado = tasques.getSelectionModel().getSelectedItem().toString().split("|");
            String ID_Tasca = separado[0].strip();
            model.getEmpleadosTasca(Integer.parseInt(ID_Tasca));
            empleatsAsignats.setItems(model.getEmpleadosTarea());
        }
    }

    @FXML
    private void editarTasca() {
        String[] separado = tasques.getSelectionModel().getSelectedItem().toString().split("\\|");
        String ID_Tasca = separado[0].strip();
        model.setIDTascaORealitzaSeleccionada("T," + ID_Tasca);
        try {
            menuBar.cambiarModificarEstatTasca();
        } catch (IOException e) {
        }
    }

    @FXML
    private void editarRealitza() {
        String[] separado = tasques.getSelectionModel().getSelectedItem().toString().split("\\|");
        String ID_Tasca = separado[0].strip();
        String[] separadoRealitza = empleatsAsignats.getSelectionModel().getSelectedItem().toString().split("\\|");
        int ID_Empleat = model.getEmpleatTasca(separadoRealitza[1].strip());
        model.setIDTascaORealitzaSeleccionada("R," + ID_Tasca + "," + ID_Empleat);
        try {
            menuBar.cambiarModificarEstatTasca();
        } catch (IOException e) {
        }

    }
}
