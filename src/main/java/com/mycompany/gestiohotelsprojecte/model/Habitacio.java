/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestiohotelsprojecte.model;

/**
 *
 * @author Nicolas Leon Sapoznik Pancani
 */
public class Habitacio {
    private int ID_Habitacio;
    private int numero_Habitacio;
    private Tipus_Habitacio tipus_Habitacio;
    private int capacitat;
    private double preu_Nit_AD;
    private double preu_Nit_MP;
    private Estat_Habitacio estat_Habitacio;
    private String descripcio;

    public Habitacio(int numero_Habitacio, Tipus_Habitacio tipus_Habitacio, int capacitat, double preu_Nit_AD, double preu_Nit_MP, Estat_Habitacio estat_Habitacio, String descripcio) {
        this.numero_Habitacio = numero_Habitacio;
        this.tipus_Habitacio = tipus_Habitacio;
        this.capacitat = capacitat;
        this.preu_Nit_AD = preu_Nit_AD;
        this.preu_Nit_MP = preu_Nit_MP;
        this.estat_Habitacio = estat_Habitacio;
        this.descripcio = descripcio;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public int getID_Habitacio() {
        return ID_Habitacio;
    }

    public void setID_Habitacio(int ID_Habitacio) {
        this.ID_Habitacio = ID_Habitacio;
    }

    public int getNumero_Habitacio() {
        return numero_Habitacio;
    }

    public void setNumero_Habitacio(int numero_Habitacio) {
        this.numero_Habitacio = numero_Habitacio;
    }

    public Tipus_Habitacio getTipus_Habitacio() {
        return tipus_Habitacio;
    }

    public void setTipus_Habitacio(Tipus_Habitacio tipus_Habitacio) {
        this.tipus_Habitacio = tipus_Habitacio;
    }

    public int getCapacitat() {
        return capacitat;
    }

    public void setCapacitat(int capacitat) {
        this.capacitat = capacitat;
    }

    public double getPreu_Nit_AD() {
        return preu_Nit_AD;
    }

    public void setPreu_Nit_AD(double preu_Nit_AD) {
        this.preu_Nit_AD = preu_Nit_AD;
    }

    public double getPreu_Nit_MP() {
        return preu_Nit_MP;
    }

    public void setPreu_Nit_MP(double preu_Nit_MP) {
        this.preu_Nit_MP = preu_Nit_MP;
    }

    public Estat_Habitacio getEstat_Habitacio() {
        return estat_Habitacio;
    }

    public void setEstat_Habitacio(Estat_Habitacio estat_Habitacio) {
        this.estat_Habitacio = estat_Habitacio;
    }
    
    
}
