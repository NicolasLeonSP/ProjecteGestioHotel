package com.mycompany.gestiohotelsprojecte;

import com.mycompany.gestiohotelsprojecte.model.Model;
import java.io.IOException;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Nicolas Leon Sapoznik Pancani
 */
public class ModificarEstatTascaController {

    @FXML
    TextField NumTasca;
    @FXML
    ComboBox EstatTasca;

    private Model model;
    private MenuBarController menuBar;
    private Boolean isTasca = false;
    private Boolean isRealitza = false;
    private int ID;
    private int IDEmpleat;

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

    private boolean confirMos(String misgg) {
        Alert alerta;
        alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setContentText(misgg);
        Optional<ButtonType> confir = alerta.showAndWait();
        if (confir.isPresent() && confir.get().equals(ButtonType.OK)) {
            return true;
        } else {
            return false;
        }
    }

    public void initialize() {
        EstatTasca.setItems(model.getEstados());
        String[] ID_Comp = model.getIDTascaORealitzaSeleccionada().split(",");
        if (ID_Comp[0].equals("T")) {
            isTasca = true;
            ID = Integer.parseInt(ID_Comp[1]);
            EstatTasca.getSelectionModel().select(model.getIDFromObservableList(model.getEstatTasca(ID), model.getEstados()));
        } else if (ID_Comp[0].equals("R")) {
            isRealitza = true;
            ID = Integer.parseInt(ID_Comp[1]);
            IDEmpleat = Integer.parseInt(ID_Comp[2]);
            EstatTasca.getSelectionModel().select(model.getIDFromObservableList(model.getEstatRealitza(ID, IDEmpleat), model.getEstados()));
        }
    }

    public void editarEstatTasca() {
        if (!model.getEstatTasca(ID).equals(EstatTasca.getValue().toString())) {
            if (isTasca) {
                int EstadoTareaAntiguo = model.getIDFromObservableList(model.getEstatTasca(ID), model.getEstados());
                int EstadoTareaActual = model.getIDFromObservableList(EstatTasca.getValue().toString(), model.getEstados());
                if (EstadoTareaActual > EstadoTareaAntiguo) {
                    if (confirMos("Esta segur que voleu avançar l'estat de la tasca? Recordeu que un cop pujat, no es pot tornar a canviar.")) {
                        model.changeEstatTasca(ID, EstatTasca.getValue().toString());
                        if (EstatTasca.getValue().toString().equals("Completada")) {
                            model.changeFinalizadoRealizado(ID);
                        }
                        try {
                            menuBar.cambiarListarTasques();
                        } catch (IOException e) {
                        }
                    }
                } else {
                    alterMos("No podeu canviar l'estat a un anterior de la tasca un cop iniciada / acabada.", true);
                }
            } else if (isRealitza) {
                int EstadoTareaAntiguo = model.getIDFromObservableList(model.getEstatRealitza(ID, IDEmpleat), model.getEstados());
                int EstadoTareaActual = model.getIDFromObservableList(EstatTasca.getValue().toString(), model.getEstados());
                if (EstadoTareaActual > EstadoTareaAntiguo) {
                    if (confirMos("Esteu segur que voleu avançar l'estat de la tasca? Recordeu que un cop pujat, no es pot tornar a canviar.")) {
                        model.changeEstatRealitza(ID, IDEmpleat, EstatTasca.getValue().toString());
                        try {
                            menuBar.cambiarListarTasques();
                        } catch (IOException e) {
                        }
                    }
                } else {
                    alterMos("No podeu canviar l'estat a un anterior de la tasca un cop iniciada / acabada.", true);
                }
            }
        } else {
            alterMos("Canvieu l'estat de la tasca si voleu canviar-la.", true);
        }
    }

    public void injecta(Model obj, MenuBarController controler) {
        model = obj;
        menuBar = controler;
    }

}
