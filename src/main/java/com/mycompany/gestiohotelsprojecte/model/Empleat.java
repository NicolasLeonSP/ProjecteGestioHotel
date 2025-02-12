/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestiohotelsprojecte.model;

import java.sql.Date;


public class Empleat extends Persona {
    
    enum Estat_Laboral {
        Actiu,
        Baixa,
        Permis
}
    
    private int ID_Empleat;
    private String lloc_Feina;
    private Date data_Contractacio;
    private int salari_Brut;
    private Estat_Laboral estat_Laboral;

    public Empleat(String nom, String cognom, String adreça, String document_Identitat, Date data_Naixement, String telefon, String email, int ID_Empleat, String lloc_Feina, Date data_Contractacio, int salari_Brut, Estat_Laboral estat_Laboral) {
        super(nom, cognom, adreça, document_Identitat, data_Naixement, telefon, email);
        this.ID_Empleat = ID_Empleat;
        this.lloc_Feina = lloc_Feina;
        this.data_Contractacio = data_Contractacio;
        this.salari_Brut = salari_Brut;
        this.estat_Laboral = estat_Laboral;
    }

    public int getID_Empleat() {
        return ID_Empleat;
    }

    public void setID_Empleat(int ID_Empleat) {
        this.ID_Empleat = ID_Empleat;
    }

    public String getLloc_Feina() {
        return lloc_Feina;
    }

    public void setLloc_Feina(String lloc_Feina) {
        this.lloc_Feina = lloc_Feina;
    }

    public Date getData_Contractacio() {
        return data_Contractacio;
    }

    public void setData_Contractacio(Date data_Contractacio) {
        this.data_Contractacio = data_Contractacio;
    }

    public int getSalari_Brut() {
        return salari_Brut;
    }

    public void setSalari_Brut(int salari_Brut) {
        this.salari_Brut = salari_Brut;
    }

    public Estat_Laboral getEstat_Laboral() {
        return estat_Laboral;
    }

    public void setEstat_Laboral(Estat_Laboral estat_Laboral) {
        this.estat_Laboral = estat_Laboral;
    }
    
    
    
}
