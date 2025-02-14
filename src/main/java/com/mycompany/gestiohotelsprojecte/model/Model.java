package com.mycompany.gestiohotelsprojecte.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Nicolas Leon Sapoznik Pancani
 */
public class Model {
    ObservableList tipoCliente = FXCollections.observableArrayList();
    ObservableList estatLaboral = FXCollections.observableArrayList();

    public Model() {
    }
    
    public void initModel() {
        for (Tipus_Client value : Tipus_Client.values()) {
            tipoCliente.add(value);
        }
        for (Estat_Laboral value : Estat_Laboral.values()) {
            estatLaboral.add(value);
        }
    }

    public ObservableList getTipoCliente() {
        return tipoCliente;
    }

    public ObservableList getEstatLaboral() {
        return estatLaboral;
    }
    
    public String checkPersona(Persona persona) {
        String msgError = "";
        if (!checkDate(persona.getData_Naixement())) {
            msgError += "- Verifique que la fecha de nacimiento sea correcta, ya que supera la fecha actual.\n";
        }
        if (!persona.checkTelefono()) {
            msgError += "- Verifique que el telefono este escrito de forma correcta.\n";
        }
        if (!persona.checkEmail()) {
            msgError += "- Verifique que el email esta escrito de forma correcta.\n";
        }
        String errorDNI = persona.checkDNI();
        if (!errorDNI.equals("0")) {
            if (errorDNI.equals("1")) {
                msgError += "- Verifique que el DNI contiene 9 caracteres, siendo 8 numeros y una letra.\n";
            }
            if (errorDNI.charAt(0) == '2') {
                msgError += "- Verifique que la letra del DNI sea correcta. La letra retornada por el generador es " + errorDNI.charAt(1) + " .\n";
            }
        }
        return msgError;
    }

    public String checkCliente(Date date, String credito) {
        String msgError = "";
        if (!checkDate(date)) {
            msgError += "- Verifique que la fecha de registro del cliente sea correcta, ya que supera la fecha actual.\n";
        }
        if (!credito.equals("")) {
            if (!checkTargetaCredito(credito)) {
                msgError += "- Verifique que su tarjeta de credito este escrito en un formato correcto. Ej (1111 1111 1111 1111 o 111111111111111)\n";
            }
        }
        return msgError;
    }

    public String checkEmpleado(Date date, String salario) {
        String msgError = "";
        if (!checkDate(date)) {
            msgError += "- Verifique que la fecha de contratacion del empleado sea correcta, ya que supera la fecha actual.\n";
        }
        if (!checkStringToInt(salario)) {
            msgError += "- Verifique que su salario sean solo numeros.";
        }
        return msgError;
    }

    public boolean checkTargetaCredito(String targeta) {
        Pattern regexCredito = Pattern.compile("([0-9]{4}\\s?){4}", Pattern.CASE_INSENSITIVE);
        Matcher creditoCheck = regexCredito.matcher(targeta);
        if (creditoCheck.find()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkDate(Date date) {
        if (date.after(Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()))) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkStringToInt(String string) {
        try {
            Integer.valueOf(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean altaPersona(Persona persona) {
        boolean PersonaSubidaBaseDeDatos = true;
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
            return PersonaSubidaBaseDeDatos;
        } catch (SQLException e) {
            System.out.println(e);
            PersonaSubidaBaseDeDatos = false;
            return PersonaSubidaBaseDeDatos;
        } catch (Exception e) {
            System.out.println(e);
            PersonaSubidaBaseDeDatos = false;
            return PersonaSubidaBaseDeDatos;
        }
    }
    
    public Boolean altaEmpleado(Empleat empleado) {
        boolean EmpleadoSubidoABaseDeDatos = true;
        Connection conectar = new Connexio().connecta();
        String sql = "INSERT INTO EMPLEAT VALUES (?,?,?,?,?)";
        try {
            PreparedStatement orden = conectar.prepareStatement(sql);
            orden.setInt(1, empleado.getID_Persona());
            orden.setString(2, empleado.getLloc_Feina());
            orden.setDate(3, empleado.getData_Contractacio());
            orden.setInt(4, empleado.getSalari_Brut());
            orden.setString(5, empleado.getEstat_Laboral().toString());
            orden.executeUpdate();
            return EmpleadoSubidoABaseDeDatos;
        } catch (SQLException e) {
            System.out.println(e);
            EmpleadoSubidoABaseDeDatos = false;
            return EmpleadoSubidoABaseDeDatos;
        } catch (Exception e) {
            System.out.println(e);
            EmpleadoSubidoABaseDeDatos = false;
            return EmpleadoSubidoABaseDeDatos;
        }
    }public Boolean altaCliente(Client cliente) {
        boolean ClienteSubidoABaseDeDatos = true;
        Connection conectar = new Connexio().connecta();
        String sql = "INSERT INTO CLIENT VALUES (?,?,?,?)";
        try {
            PreparedStatement orden = conectar.prepareStatement(sql);
            orden.setInt(1, cliente.getID_Persona());
            orden.setDate(2, cliente.getData_Registre());
            orden.setString(3, cliente.getTipus_Client().toString());
            orden.setString(4, cliente.getTargeta_Credit().replace(" ", ""));
            orden.executeUpdate();
            return ClienteSubidoABaseDeDatos;
        } catch (SQLException e) {
            System.out.println(e);
            ClienteSubidoABaseDeDatos = false;
            return ClienteSubidoABaseDeDatos;
        } catch (Exception e) {
            System.out.println(e);
            ClienteSubidoABaseDeDatos = false;
            return ClienteSubidoABaseDeDatos;
        }
    }
    
    public int getIdPersona(String document_Identitat){
        int ID_Persona = 0;
        Connection conectar = new Connexio().connecta();
        String sql = "SELECT ID_Persona FROM PERSONA WHERE Document_Identitat = ?";
        try {
            PreparedStatement orden = conectar.prepareStatement(sql);
            orden.setString(1, document_Identitat);
            ResultSet resultados = orden.executeQuery();
            while (resultados.next()) {
                ID_Persona = resultados.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        } catch (Exception e) {
            System.out.println(e);
            return 0;
        }
        return ID_Persona;
    }
}
