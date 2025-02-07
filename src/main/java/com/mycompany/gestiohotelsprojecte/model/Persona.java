package com.mycompany.gestiohotelsprojecte.model;

import java.sql.Date;

/**
 *
 * @author Nicolas Leon Sapoznik Pancani
 */
public abstract class Persona {
    private int ID_Persona;
    private String nom;
    private String cognom;
    private String adreça;
    private String document_Identitat;
    private final Date data_Naixement;
    private String telefon;
    private String email;
            
    public Persona(String nom, String cognom, String adreça, String document_Identitat, Date data_Naixement, String telefon, String email) {
        this.nom = nom;
        this.cognom = cognom;
        this.adreça = adreça;
        this.document_Identitat = document_Identitat;
        this.data_Naixement = data_Naixement;
        this.telefon = telefon;
        this.email = email;
    }
    
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
    
    
}
