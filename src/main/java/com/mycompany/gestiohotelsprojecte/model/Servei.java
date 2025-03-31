package com.mycompany.gestiohotelsprojecte.model;

/**
 *
 * @author Nicolas Leon Sapoznik Pancani
 */
public class Servei {
    // Variables de la clase
    private int ID_Servei;
    private String nom;
    private String descripcio;
    private double preu;
    // Constructor de la clase
    public Servei(String nom, String descripcio, double preu) {
        this.nom = nom;
        this.descripcio = descripcio;
        this.preu = preu;
    }
    // Getters y setters de la clase
    public int getID_Servei() {
        return ID_Servei;
    }

    public void setID_Servei(int ID_Servei) {
        this.ID_Servei = ID_Servei;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public double getPreu() {
        return preu;
    }

    public void setPreu(int preu) {
        this.preu = preu;
    }
    
    
}
