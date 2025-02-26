package com.mycompany.gestiohotelsprojecte;

import com.mycompany.gestiohotelsprojecte.model.Estat;
import com.mycompany.gestiohotelsprojecte.model.Estat_Habitacio;
import com.mycompany.gestiohotelsprojecte.model.Model;
import com.mycompany.gestiohotelsprojecte.model.Realitza;
import com.mycompany.gestiohotelsprojecte.model.Tasca;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import javafx.scene.control.CheckBox;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
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
    ListView seleccionarTasca;
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
    private void isCreadaChange() {
        if (isCreada.isSelected()) {
            crearTasca.disableProperty().set(true);
            crearTascaEmpleat.disableProperty().set(true);
            asignarTasca.disableProperty().set(false);
            creacioTasca.getSelectionModel().select(2);
            restartCreacioTasca();
            restartCreacioTascaEmpleat();
            model.recargarTareas();
            seleccionarTasca.setItems(model.getTareas());

        } else if (!isCreada.isSelected()) {
            crearTasca.disableProperty().set(false);
            crearTascaEmpleat.disableProperty().set(false);
            asignarTasca.disableProperty().set(true);
            creacioTasca.getSelectionModel().select(0);
            restartAsignarTasca();

        }
    }

    @FXML
    private void aceptar() {
        switch (creacioTasca.getSelectionModel().getSelectedIndex()) {
            case 0:
                if (dataExecucioCreacioTasca.getValue() != null && descripcioCreacio.getText() != null) {
                    Date date = model.LocalDateToSqlDate(dataExecucioCreacioTasca.getValue());
                    if (!model.checkDateIsBefore(date)) {
                        Tasca tasca = new Tasca(descripcioCreacio.getText(), model.LocalDateToSqlDate(LocalDate.now()), date, Estat.No_Iniciada);
                        if (tasca.altaTasca()) {
                            alterMos("La tasca s'ha creat satisfactòriament.", false);
                            restartCreacioTasca();
                        } else {
                            alterMos("Alguna cosa ha fallat a l'hora de pujar la tasca.", true);
                        }
                    } else {
                        alterMos("La data que introduïu ha de ser superior a l'actual.", true);
                    }
                } else {
                    alterMos("Ompliu tots els camps abans d'acceptar.", true);
                }
                break;
            case 1:
                if (dataExecucioCreacioTascaEmpleat.getValue() != null && descripcioCreacioTascaEmpleat.getText() != null && dniCreacioTascaEmpleat.getText() != null) {
                    Date date = model.LocalDateToSqlDate(dataExecucioCreacioTascaEmpleat.getValue());
                    int ID_Empleat = model.getEmpleatTasca(dniCreacioTascaEmpleat.getText());
                    if (ID_Empleat != -1) {
                        if (!model.checkDateIsBefore(date)) {
                            Tasca tasca = new Tasca(descripcioCreacioTascaEmpleat.getText(), model.LocalDateToSqlDate(LocalDate.now()), date, Estat.No_Iniciada);
                            if (tasca.altaTasca()) {
                                int ID_Tasca = model.getIDTasca(tasca.getData_Creacio());
                                Realitza realitza = new Realitza(ID_Tasca, ID_Empleat, Estat.No_Iniciada, model.LocalDateToSqlDate(LocalDate.now()));
                                if (realitza.altaRealitza()) {
                                    alterMos("S'ha creat la tasca i s'ha assignat a l'empleat satisfactòriament.", false);
                                    restartCreacioTascaEmpleat();
                                } else {
                                    alterMos("Alguna cosa ha fallat a l'hora d'assignar l'empleat amb la tasca.", true);
                                }
                            } else {
                                alterMos("Alguna cosa ha fallat a l'hora de pujar la tasca.", true);
                            }
                        } else {
                            alterMos("La data que s'introdueix ha de ser superior a l'actual.", true);
                        }
                    } else {
                        alterMos("Verifiqueu que heu escrit el DNI correctament.", true);
                    }
                } else {
                    alterMos("Ompliu tots els camps abans d'acceptar.", true);
                }
                break;
            case 2:
                if (seleccionarTasca.getSelectionModel().getSelectedItem() != null && dniAsignarTasca.getText() != null) {
                    int ID_Tasca = Integer.parseInt(seleccionarTasca.getSelectionModel().getSelectedItem().toString());
                    int ID_Empleat = model.getIdPersona(dniAsignarTasca.getText());
                    if (ID_Empleat != -1) {
                        if (!model.checkIfRealitzaAlrExists(ID_Tasca, ID_Empleat)) {
                            Realitza realitza = new Realitza(ID_Tasca, ID_Empleat, Estat.No_Iniciada, model.LocalDateToSqlDate(LocalDate.now()));
                            if (realitza.altaRealitza()) {
                                alterMos("S'ha assignat la tasca a l'empleat satisfactòriament.", false);
                                restartAsignarTasca();
                            } else {
                                alterMos("Alguna cosa ha fallat a l'hora d'assignar l'empleat amb la tasca.", true);
                            }
                        } else {
                            alterMos("Aquest empleat ja ha estat assignat en aquesta tasca.", true);
                        }
                    } else {
                        alterMos("Verifiqueu que heu escrit el DNI correctament.", true);
                    }
                } else {
                    alterMos("Ompliu tots els camps abans d'acceptar.", true);
                }
                break;
            default:
                break;
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

    public void restartAsignarTasca() {
        seleccionarTasca.getSelectionModel().clearSelection();
        dniAsignarTasca.clear();
    }

    public void initialize() {
        asignarTasca.disableProperty().set(true);
    }

    public void injecta(Model obj) {
        model = obj;
    }
}
