package com.mycompany.gestiohotelsprojecte;

import com.mycompany.gestiohotelsprojecte.model.Model;
import java.io.IOException;
import javafx.scene.control.CheckBox;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Nicolas Leon Sapoznik Pancani
 */
public class CrearTascaController {

    private Model model;
    @FXML
    CheckBox isCreada;
    @FXML
    DatePicker dataExecucioCreacioTasca;
    @FXML
    DatePicker dataExecucioCreacioTascaEmpleat;
    @FXML
    TextArea descripcioCreacio;
    @FXML
    TextArea descripcioCreacioTascaEmpleat;
    @FXML
    TextField dniCreacioTascaEmpleat;
    @FXML
    ComboBox seleccionarTasca;
    @FXML
    TextField dniAsignarTasca;
    @FXML
    TabPane creacioTasca;
    @FXML
    Tab crearTasca;
    @FXML
    Tab crearTascaEmpleat;
    @FXML
    Tab asignarTasca;

    @FXML
    private void isCreadaChange() {
        if (isCreada.isSelected()) {
            crearTasca.disableProperty().set(true);
            crearTascaEmpleat.disableProperty().set(true);
            asignarTasca.disableProperty().set(false);
            creacioTasca.getSelectionModel().select(2);
            restartCreacioTasca();
            restartCreacioTascaEmpleat();

        } else if (!isCreada.isSelected()) {
            crearTasca.disableProperty().set(false);
            crearTascaEmpleat.disableProperty().set(false);
            asignarTasca.disableProperty().set(true);
            creacioTasca.getSelectionModel().select(0);
            restartAsignarTasca();

        }
    }

    public void restartCreacioTasca() {
        dataExecucioCreacioTasca.getEditor().clear();
        descripcioCreacio.clear();
    }
    public void restartCreacioTascaEmpleat() {
        dataExecucioCreacioTascaEmpleat.getEditor().clear();
        descripcioCreacioTascaEmpleat.clear();
        dniCreacioTascaEmpleat.clear();
    }

    public void restartAsignarTasca(){
        seleccionarTasca.valueProperty().set(null);
        dniAsignarTasca.clear();
    }
    
    public void initialize() {
        asignarTasca.disableProperty().set(true);
    }

    public void injecta(Model obj) {
        model = obj;
    }
}
