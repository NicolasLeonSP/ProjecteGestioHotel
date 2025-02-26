package com.mycompany.gestiohotelsprojecte;

import com.mycompany.gestiohotelsprojecte.model.Model;
import java.awt.Checkbox;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author Nicolas Leon Sapoznik Pancani
 */
public class CrearTascaController {

    private Model model;
    @FXML
    Checkbox isCreada;
    @FXML
    DatePicker dataExecucioCreacioTasca;
    @FXML
    DatePicker dataExecucioCreacioTascaEmpleat;
    @FXML
    TextArea descripcioCreacio;
    @FXML
    TextArea descripcioCreacioTascaEmpleat;
    @FXML
    TextArea dniCreacioTascaEmpleat;
    @FXML
    ComboBox seleccionarTasca;
    @FXML
    TextArea dniAsignarTasca;
    
    public void injecta(Model obj) {
        model = obj;
    }
}
