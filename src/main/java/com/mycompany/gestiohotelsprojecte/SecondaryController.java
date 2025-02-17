package com.mycompany.gestiohotelsprojecte;

import com.mycompany.gestiohotelsprojecte.model.Model;
import com.mycompany.gestiohotelsprojecte.model.Reserva;
import com.mycompany.gestiohotelsprojecte.model.Tipus_IVA;
import com.mycompany.gestiohotelsprojecte.model.Tipus_Reserva;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class SecondaryController {

    private Model model;
    @FXML
    AnchorPane Reserva;
    @FXML
    TextField textDNI;
    @FXML
    DatePicker dataInici;
    @FXML
    DatePicker dataFinal;
    @FXML
    ComboBox habitacions;
    @FXML
    ComboBox tipusIVA;
    @FXML
    ComboBox tipusReserva;

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
    private void switchToPersona() throws IOException {
        App.setRoot("primary");
    }

    @FXML
    private void switchToReserves() throws IOException {
        App.setRoot("secondary");
    }

    @FXML
    private void buscarPersona() {
        if (!textDNI.getText().isEmpty()) {
            if (model.getIdPersona(textDNI.getText()) != 0) {
                alterMos("Se ha encontrado la persona con exito.", false);
                Reserva.disableProperty().set(false);
                model.recargarHabitaciones();
                habitacions.setItems(model.getHabitaciones());
                tipusIVA.setItems(model.getTipoIVA());
                tipusReserva.setItems(model.getTipoReserva());
            } else {
                alterMos("Verifique si ha introducido el DNI bien", true);
            }
        }
    }

    @FXML
    private void crearReserva() {
        if (dataInici.getValue() != null && dataFinal.getValue() != null && habitacions.getValue() != null && tipusIVA.getValue() != null && tipusReserva.getValue() != null) {
            Date dataActual = new java.sql.Date((Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()).getTime()));
            Date dataIniciTemp = new java.sql.Date(Date.from(dataInici.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()).getTime());
            Date dataFinalTemp = new java.sql.Date(Date.from(dataFinal.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()).getTime());
            // Por terminar, los dos ID
            model.altaReserva(new Reserva(dataActual, dataIniciTemp, dataFinalTemp, (Tipus_Reserva) tipusReserva.getValue(), (Tipus_IVA) tipusIVA.getValue(), 0, 0, 0));
        } else {
            alterMos("Verifique que todos los campos han sido rellenados", true);
        }
    }

    @FXML
    private void restartCampos() throws IOException {
        // Al cambiar de pestaña, se reinician los campos introducidos
        Reserva.disableProperty().set(true);
        App.setRoot("secondary");
    }

    public void injecta(Model obj) {
        model = obj;
    }
}
