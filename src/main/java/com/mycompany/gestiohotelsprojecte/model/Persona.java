package com.mycompany.gestiohotelsprojecte.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Nicolas Leon Sapoznik Pancani
 */
public class Persona {
    // Variables de la clase persona.
    private int ID_Persona;
    private String nom;
    private String cognom;
    private String adreça;
    private String document_Identitat;
    private final Date data_Naixement;
    private String telefon;
    private String email;
    // Constructor de la clase persona.        
    public Persona(String nom, String cognom, String adreça, String document_Identitat, Date data_Naixement, String telefon, String email) {
        this.nom = nom;
        this.cognom = cognom;
        this.adreça = adreça;
        this.document_Identitat = document_Identitat;
        this.data_Naixement = data_Naixement;
        this.telefon = telefon;
        this.email = email;
    }
    // Getters y setters de la clase persona.
    public int getID_Persona() {
        return ID_Persona;
    }

    public void setID_Persona(int ID_Persona) {
        this.ID_Persona = ID_Persona;
    }
    
    public Date getData_Naixement() {
        return data_Naixement;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCognom() {
        return cognom;
    }

    public void setCognom(String cognom) {
        this.cognom = cognom;
    }

    public String getAdreça() {
        return adreça;
    }

    public void setAdreça(String adreça) {
        this.adreça = adreça;
    }

    public String getDocument_Identitat() {
        return document_Identitat;
    }

    public void setDocument_Identitat(String document_Identitat) {
        this.document_Identitat = document_Identitat;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    // Funcion que se encarga de revisar si la fecha de nacimiento es menor a la fecha actual.
    public boolean checkDateNacimiento(){
        if (data_Naixement.after(Date.valueOf(LocalDate.now()))) {
            return false;
        } else {
            return true;
        }
    }
    // Funcion que se encarga de revisar si el telefono esta escrito de forma correcta.
    public boolean checkTelefono(){
        Pattern regexTelefono = Pattern.compile("^(\\+34|0034|34)?[6789]\\d{8}$", Pattern.CASE_INSENSITIVE);
        Matcher telefonoCheck = regexTelefono.matcher(telefon);
        if (telefonoCheck.find()) {
            return true;
        } else {
            return false;
        }
    }
    // Funcion que se encarga de revisar si el email esta escrito de forma correcta.
    public boolean checkEmail(){
        Pattern regexEmail = Pattern.compile("^(.+)@(.+).(.+)$", Pattern.CASE_INSENSITIVE);
        Matcher EmailCheck = regexEmail.matcher(email);
        if (EmailCheck.find()) {
            return true;
        } else {
            return false;
        }
    }
    // Funcion que se encarga de revisar si el DNI esta escrito de forma correcta.
    public String checkDNI(){
        // Primero revisamos la longitud como tal.
        Pattern regexDNI = Pattern.compile("^[0-9]{8}[A-Z]{1}$",Pattern.CASE_INSENSITIVE);
        Matcher DNICheck = regexDNI.matcher(document_Identitat);
        if (!DNICheck.find()) {
            return "1";
        } else {
            // Luego revisamos si la letra encaja correctamente.
            String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
            String docCheck = document_Identitat;
            docCheck = docCheck.replace(docCheck.charAt(8), ' ').strip();
            char letra = letras.charAt(Integer.parseInt(docCheck) % letras.length());
            String dniEntero = "" + docCheck + letra;
            // Devolvemos 0 si es correcto, 1 o 2 si es erroneo.
            if (dniEntero.equals(document_Identitat)) {
                return "0";
            } else {
                return "2" + letra;
            }
        }
    }
    // Esta funcion SQL se encarga de añadir una persona a la base de datos. Ir a la linea 909 del modelo para mas info
    public Boolean altaPersona() {
        boolean PersonaSubidaBaseDeDatos = true;
        Connection conectar = new Connexio().connecta();
        String sql = "INSERT INTO PERSONA (Nom, Cognom, Adreça, Document_Identitat, Data_Naixement, Telefon, Email) VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement orden = conectar.prepareStatement(sql);
            orden.setString(1, getNom());
            orden.setString(2, getCognom());
            orden.setString(3, getAdreça());
            orden.setString(4, getDocument_Identitat());
            orden.setDate(5, getData_Naixement());
            orden.setString(6, getTelefon());
            orden.setString(7, getEmail());
            orden.executeUpdate();
            return PersonaSubidaBaseDeDatos;
        } catch (SQLException e) {
            System.out.println(e.toString());
            PersonaSubidaBaseDeDatos = false;
            return PersonaSubidaBaseDeDatos;
        } catch (Exception e) {
            System.out.println(e.toString());
            PersonaSubidaBaseDeDatos = false;
            return PersonaSubidaBaseDeDatos;
        }
    }
}
