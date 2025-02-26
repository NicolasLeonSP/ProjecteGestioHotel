package com.mycompany.gestiohotelsprojecte;

import com.mycompany.gestiohotelsprojecte.model.Model;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

/**
 * FXML Controller class
 *
 * @author Nicolas Leon Sapoznik Pancani
 */
public class ListarTasquesController {

    private Model model;
    @FXML
    ListView tasques;
    @FXML
    ListView empleatsAsignats;

    public void injecta(Model obj) {
        model = obj;
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
}
