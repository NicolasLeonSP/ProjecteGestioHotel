package com.mycompany.gestiohotelsprojecte;

import com.mycompany.gestiohotelsprojecte.model.Model;
import java.io.IOException;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Nicolas Leon Sapoznik Pancani
 */
public class ModificarEstatTascaController {

    // Variables del controlador.
    @FXML
    ComboBox EstatTasca;
    @FXML
    Text textTasca;

    private Model model;
    private MenuBarController menuBar;
    private Boolean isTasca = false;
    private Boolean isRealitza = false;
    private int ID;
    private int IDEmpleat;

    // Funcion para crear una alerta, pasandole el mensaje por un parametro y si es un error o no.
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

    // Funcion para crear una alerta de confirmacion, pasandole el mensaje por un parametro
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

    // Funcion que se ejecuta al inicializar el formulario, en este caso, determina si estamos trabajando con el estado de una tarea o de una asignacion.
    public void initialize() {
        EstatTasca.setItems(model.getEstados());
        String[] ID_Comp = model.getIDTascaORealitzaSeleccionada().split(",");
        // Eso se comprube aqui, viendo la primera parte del mensaje donde determina esto.
        if (ID_Comp[0].equals("T")) {
            // Si es una tarea, cambia isTasca to true y carga su estado.
            isTasca = true;
            ID = Integer.parseInt(ID_Comp[1]);
            EstatTasca.getSelectionModel().select(model.getIDFromObservableList(model.getEstatTasca(ID), model.getEstados()));
            textTasca.setText("Editar estat de la Tasca " + ID);
        } else if (ID_Comp[0].equals("R")) {
            // Si es una asignacion, cambia isRealitza to true y carga su estado.
            isRealitza = true;
            ID = Integer.parseInt(ID_Comp[1]);
            IDEmpleat = Integer.parseInt(ID_Comp[2]);
            EstatTasca.getSelectionModel().select(model.getIDFromObservableList(model.getEstatRealitza(ID, IDEmpleat), model.getEstados()));
            String nomEmpleat = model.getNomEmpleat(IDEmpleat);
            textTasca.setText("Editar estat de la Asignacion de la Tasca " + ID + "\ndel Empleat " + nomEmpleat);
        }
    }

    // Funcion que se ejecuta al editar el estado de la tarea
    public void editarEstatTasca() {
        // Primero, veremos si es una tarea o una asignacion.
        if (isTasca) {
            // Si es una tarea, primero veremos si el estado a cambiado o no.
            if (!model.getEstatTasca(ID).equals(EstatTasca.getValue().toString())) {
                // En el caso que haya cambiado, veremos si ha cambiado a un paso superior al previamente introducido.
                int EstadoTareaAntiguo = model.getIDFromObservableList(model.getEstatTasca(ID), model.getEstados());
                int EstadoTareaActual = model.getIDFromObservableList(EstatTasca.getValue().toString(), model.getEstados());
                if (EstadoTareaActual > EstadoTareaAntiguo) {
                    // Si ese es el caso, nos aseguraremos que quiera cambiarlo.
                    if (confirMos("Esta segur que voleu avançar l'estat de la tasca? Recordeu que un cop pujat, no es pot tornar a canviar.")) {
                        // Si ese es el caso, cambiaremos el estado de la tarea al ya dado.
                        model.changeEstatTasca(ID, EstatTasca.getValue().toString());
                        // Si es el estado era completado, tambien cambiaremos el estado de las asignaciones a completado.
                        if (EstatTasca.getValue().toString().equals("Completada")) {
                            model.changeFinalizadoRealizado(ID);
                        }
                        try {
                            // Y luego de eso, nos volveremos a la lista de tareas.
                            menuBar.cambiarListarTasques();
                        } catch (IOException e) {
                        }
                    }
                } else {
                    // Caso que el estado al que se quiera cambiar sea uno anterior al ya puesto.
                    alterMos("No podeu canviar l'estat a un anterior de la tasca un cop iniciada / acabada.", true);
                }
            } else {
                // Caso que no se haya cambiado el estado.
                alterMos("Canvieu l'estat de la tasca si voleu canviar-la.", true);
            }
        } else if (isRealitza) {
            // Si es una asignacion, primero veremos si el estado a cambiado o no.
            if (!model.getEstatRealitza(ID, IDEmpleat).equals(EstatTasca.getValue().toString())) {
                // En el caso que haya cambiado, veremos si ha cambiado a un paso superior al previamente introducido.
                int EstadoTareaAntiguo = model.getIDFromObservableList(model.getEstatRealitza(ID, IDEmpleat), model.getEstados());
                int EstadoTareaActual = model.getIDFromObservableList(EstatTasca.getValue().toString(), model.getEstados());
                if (EstadoTareaActual > EstadoTareaAntiguo) {
                    // Si ese es el caso, nos aseguraremos que quiera cambiarlo.
                    if (confirMos("Esteu segur que voleu avançar l'estat de la tasca? Recordeu que un cop pujat, no es pot tornar a canviar.")) {
                        // Si ese es el caso, cambiaremos el estado de la tarea al ya dado.
                        model.changeEstatRealitza(ID, IDEmpleat, EstatTasca.getValue().toString());
                        System.out.println("a");
                        try {
                            // Y luego de eso, nos volveremos a la lista de tareas.
                            menuBar.cambiarListarTasques();
                        } catch (IOException e) {
                        }
                    }
                } else {
                    // Caso que el estado al que se quiera cambiar sea uno anterior al ya puesto.
                    alterMos("No podeu canviar l'estat a un anterior de la tasca un cop iniciada / acabada.", true);
                }
            } else {
                // Caso que no se haya cambiado el estado.
                alterMos("Canvieu l'estat de la tasca si voleu canviar-la.", true);
            }

        }
    }

    // Esta funcion inserta el modelo que se le pasa a la clase, ademas del menuBar que controla los formularios.
    public void injecta(Model obj, MenuBarController controler) {
        model = obj;
        menuBar = controler;
    }

}
