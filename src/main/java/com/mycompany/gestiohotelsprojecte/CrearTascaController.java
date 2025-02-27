package com.mycompany.gestiohotelsprojecte;

import com.mycompany.gestiohotelsprojecte.model.Estat;
import com.mycompany.gestiohotelsprojecte.model.Model;
import com.mycompany.gestiohotelsprojecte.model.Realitza;
import com.mycompany.gestiohotelsprojecte.model.Tasca;
import java.sql.Date;
import java.time.LocalDate;
import javafx.scene.control.CheckBox;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
    // Variables del controlador
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

    // Funcion para crear una alerta, pasandole el mensaje por un parametro y tambien si es un mensaje de error o no
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
    // Cada vez que se cambie el checkbox, nos fijaremos en si esta activo o no.
    private void isCreadaChange() {
        // Si lo esta...
        if (isCreada.isSelected()) {
            // Activamos la pestaña de asignar tarea y desactivamos las otras dos.
            crearTasca.disableProperty().set(true);
            crearTascaEmpleat.disableProperty().set(true);
            asignarTasca.disableProperty().set(false);
            creacioTasca.getSelectionModel().select(2);
            // Reiniciamos los otros formularios.
            restartCreacioTasca();
            restartCreacioTascaEmpleat();
            model.recargarTareas();
            // Preparamos el formulario.
            seleccionarTasca.setItems(model.getTareas());

        } 
        // Si no lo esta...
        else if (!isCreada.isSelected()) {
            // Activamos la pestaña de crear tareas y desactivamos la de asignar.
            crearTasca.disableProperty().set(false);
            crearTascaEmpleat.disableProperty().set(false);
            asignarTasca.disableProperty().set(true);
            creacioTasca.getSelectionModel().select(0);
            // Reiniciamos el formulario de asignar.
            restartAsignarTasca();

        }
    }

    @FXML
    // Funcion la cual se activa al aceptar, dependiendo de que formulario estemos.
    private void aceptar() {
        switch (creacioTasca.getSelectionModel().getSelectedIndex()) {
            case 0:
                // Si estamos en el primer formulario, veremos que los campos esten llenos.
                if (dataExecucioCreacioTasca.getValue() != null && descripcioCreacio.getText() != null) {
                    Date date = model.LocalDateToSqlDate(dataExecucioCreacioTasca.getValue());
                    // Miramos que la fecha de ejecucion sea despues de la actual.
                    if (!model.checkDateIsBefore(date)) {
                        // Si lo es, crearemos la tarea y la subimos.
                        Tasca tasca = new Tasca(descripcioCreacio.getText(), model.LocalDateToSqlDate(LocalDate.now()), date, Estat.No_Iniciada);
                        if (tasca.altaTasca()) {
                            // Si sale bien, mandamos mensaje y reiniciamos los campos.
                            alterMos("La tasca s'ha creat satisfactòriament.", false);
                            restartCreacioTasca();
                        } else {
                            // Caso que no se haya subido la tarea.
                            alterMos("Alguna cosa ha fallat a l'hora de pujar la tasca.", true);
                        }
                    } else {
                        // Caso que la fecha de ejecucion no sea superior a la actual.
                        alterMos("La data que introduïu ha de ser superior a l'actual.", true);
                    }
                } else {
                    // Caso que no todos los campos hayan sido rellenados.
                    alterMos("Ompliu tots els camps abans d'acceptar.", true);
                }
                break;
            case 1:
                // Si estamos en el segundo formulario, veremos que todos esos campos esten rellenados.
                if (dataExecucioCreacioTascaEmpleat.getValue() != null && descripcioCreacioTascaEmpleat.getText() != null && dniCreacioTascaEmpleat.getText() != null) {
                    Date date = model.LocalDateToSqlDate(dataExecucioCreacioTascaEmpleat.getValue());
                    int ID_Empleat = model.getIDEmpleatTasca(dniCreacioTascaEmpleat.getText());
                    // Primero veremos que el empleado exista.
                    if (ID_Empleat != -1) {
                        // Si existe, veremos que la fecha de ejecucion sea despues de la actual.
                        if (!model.checkDateIsBefore(date)) {
                            // Una vez comprobado todo y que de bien, subimos la tarea.
                            Tasca tasca = new Tasca(descripcioCreacioTascaEmpleat.getText(), model.LocalDateToSqlDate(LocalDate.now()), date, Estat.No_Iniciada);
                            if (tasca.altaTasca()) {
                                // Una vez subida, extraeremos el ID de la tarea que acabamos de subir.
                                int ID_Tasca = model.getIDTasca(tasca.getData_Creacio());
                                Realitza realitza = new Realitza(ID_Tasca, ID_Empleat, Estat.No_Iniciada, model.LocalDateToSqlDate(LocalDate.now()));
                                // Y luego subimos la asignacion del empleado a la tarea.
                                if (realitza.altaRealitza()) {
                                    // Una vez finalizado, mandamos mensaje y reiniciamos los campos.
                                    alterMos("S'ha creat la tasca i s'ha assignat a l'empleat satisfactòriament.", false);
                                    restartCreacioTascaEmpleat();
                                } else {
                                    // En el caso de que algo falle al subir la asignacion de la tarea.
                                    alterMos("Alguna cosa ha fallat a l'hora d'assignar l'empleat amb la tasca.", true);
                                }
                            } else {
                                    // En el caso de que algo falle al subir la tarea.
                                alterMos("Alguna cosa ha fallat a l'hora de pujar la tasca.", true);
                            }
                        } else {
                            // En el caso de que la fecha de ejecucion no sea superior a la actual.
                            alterMos("La data que s'introdueix ha de ser superior a l'actual.", true);
                        }
                    } else {
                        // En el caso de que no se encuentre al empleado
                        alterMos("Verifiqueu que heu escrit el DNI correctament.", true);
                    }
                } else {
                    // En caso de que no se hayan rellenado todos los campos.
                    alterMos("Ompliu tots els camps abans d'acceptar.", true);
                }
                break;
            case 2:
                // Si estamos en el tercer formulario, miraremos que todos los campos estan rellenados.
                if (seleccionarTasca.getSelectionModel().getSelectedItem() != null && dniAsignarTasca.getText() != null) {
                    int ID_Tasca = Integer.parseInt(seleccionarTasca.getSelectionModel().getSelectedItem().toString());
                    int ID_Empleat = model.getIdPersona(dniAsignarTasca.getText());
                    // Si lo estan, miraremos que el empleado de verdad exista.
                    if (ID_Empleat != -1) {
                        // Si existe, comprobaremos que esta asignacion no exista previamente.
                        if (!model.checkIfRealitzaAlrExists(ID_Tasca, ID_Empleat)) {
                            // Si no existe, entonces la crearemos y la subiremos
                            Realitza realitza = new Realitza(ID_Tasca, ID_Empleat, Estat.No_Iniciada, model.LocalDateToSqlDate(LocalDate.now()));
                            if (realitza.altaRealitza()) {
                                // Una vez subida, enviamos mensaje y reiniciamos campos
                                alterMos("S'ha assignat la tasca a l'empleat satisfactòriament.", false);
                                restartAsignarTasca();
                            } else {
                                // En caso de que falle alguna cosa en la subida.
                                alterMos("Alguna cosa ha fallat a l'hora d'assignar l'empleat amb la tasca.", true);
                            }
                        } else {
                            // En caso de que ya exista esta asignacion.
                            alterMos("Aquest empleat ja ha estat assignat en aquesta tasca.", true);
                        }
                    } else {
                        // En caso de que el empleado no exista
                        alterMos("Verifiqueu que heu escrit el DNI correctament.", true);
                    }
                } else {
                    // En caso de que no esten rellenados todos los campos.
                    alterMos("Ompliu tots els camps abans d'acceptar.", true);
                }
                break;
            default:
                // En caso de que no sea ninguna de las opciones, lo cual es imposible, soltamos un error.
                alterMos("Error fatal, contacte con el administrador.", true);
                break;
        }
    }
    // Funcion para reiniciar los campos de creacion de tarea
    public void restartCreacioTasca() {
        dataExecucioCreacioTasca.getEditor().clear();
        descripcioCreacio.clear();
    }
    // Funcion para reiniciar los campos de creacion de tarea y asignacion de tarea
    public void restartCreacioTascaEmpleat() {
        dataExecucioCreacioTascaEmpleat.getEditor().clear();
        descripcioCreacioTascaEmpleat.clear();
        dniCreacioTascaEmpleat.clear();
    }
    // Funcion para reiniciar los campos de asignacion de tarea
    public void restartAsignarTasca() {
        seleccionarTasca.getSelectionModel().clearSelection();
        dniAsignarTasca.clear();
    }
    // Funcion para inicializar alguna cosa del formulario, en este caso poner el caso de que el check esta desactivado.
    public void initialize() {
        asignarTasca.disableProperty().set(true);
    }
    // Esta funcion inserta el modelo que se le pasa a la clase.
    public void injecta(Model obj) {
        model = obj;
    }
}
