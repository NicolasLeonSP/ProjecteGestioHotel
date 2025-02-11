package com.mycompany.gestiohotelsprojecte.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Nicolas Leon Sapoznik Pancani
 */
public class Model {

    public Model() {
    }
    // Posiblemente cambiar de boolean a string, para manejar error mejor
    public boolean altaPersona(Persona persona) {
        boolean PersonaCorrecta = true;
        if (!persona.checkDateNacimiento()) {
            PersonaCorrecta = false;
        }
        if (!persona.checkTelefono()) {
            PersonaCorrecta = false;
        }
        if (PersonaCorrecta) {
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
                return true;
            } catch (SQLException e) {
                System.out.println(e);
                return false;
            } catch (Exception e) {
                System.out.println(e);
                return false;
            }
        } else {
            return false;
        }

    }
}
