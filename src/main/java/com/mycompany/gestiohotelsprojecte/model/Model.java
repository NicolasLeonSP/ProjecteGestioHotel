package com.mycompany.gestiohotelsprojecte.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Nicolas Leon Sapoznik Pancani
 */
public class Model {

    public Model() {
    }
    public String altaPersona(Persona persona) {
        boolean PersonaCorrecta = true;
        String msgError = "";
        if (!persona.checkDateNacimiento()) {
            PersonaCorrecta = false;
            msgError += "- Verifique que la fecha de nacimiento sea correcta.\n";
        }
        if (!persona.checkTelefono()) {
            PersonaCorrecta = false;
            msgError += "- Verifique que el telefono este escrito de forma correcta.\n";
        }
        if (!persona.checkEmail()) {
            PersonaCorrecta = false;
            msgError += "- Verifique que el email esta escrito de forma correcta.\n";
        }
        String errorDNI = persona.checkDNI();
        if (!errorDNI.equals("0")) {
            PersonaCorrecta = false;
            if (errorDNI.equals("1")) {
                msgError += "- Verifique que el DNI contiene 9 caracteres, siendo 8 numeros y una letra.\n";
            }
            if (errorDNI.charAt(0) == '2') {
                msgError += "- Verifique que la letra del DNI sea correcta. La letra retornada por el generador es " + errorDNI.charAt(1) + " .\n";
            }
        }
        if (PersonaCorrecta) {
            msgError = null;
            Connection conectar = new Connexio().connecta();
            String sql = "INSERT INTO PERSONA (Nom, Cognom, Adreça, Document_Identitat, Data_Naixement, Telefon, Email) VALUES (?,?,?,?,?,?,?)";
            try {
                PreparedStatement orden = conectar.prepareStatement(sql);
                orden.setString(1, persona.getNom());
                orden.setString(2, persona.getCognom());
                orden.setString(3, persona.getAdreça());
                orden.setString(4, persona.getDocument_Identitat());
                orden.setDate(5, persona.getData_Naixement());
                orden.setString(6, persona.getTelefon());
                orden.setString(7, persona.getEmail());
                orden.executeUpdate();
                return msgError;
            } catch (SQLException e) {
                System.out.println(e);
                return msgError;
            } catch (Exception e) {
                System.out.println(e);
                return msgError;
            }
        } else {
            return msgError;
        }
    }
}