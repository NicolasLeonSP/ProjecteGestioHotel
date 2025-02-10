/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestiohotelsprojecte.model;

/**
 *
 * @author Nicolas Leon Sapoznik Pancani
 */
public class Realitza {
    
    private Estat estat_Per_Empleat;
    private int ID_Empleat;
    private int ID_Tasca;

    public Realitza(Estat estat_Per_Empleat, int ID_Empleat, int ID_Tasca) {
        this.estat_Per_Empleat = estat_Per_Empleat;
        this.ID_Empleat = ID_Empleat;
        this.ID_Tasca = ID_Tasca;
    }
    
    public Estat getEstat_Per_Empleat() {
        return estat_Per_Empleat;
    }

    public void setEstat_Per_Empleat(Estat estat_Per_Empleat) {
        this.estat_Per_Empleat = estat_Per_Empleat;
    }

    public int getID_Empleat() {
        return ID_Empleat;
    }

    public void setID_Empleat(int ID_Empleat) {
        this.ID_Empleat = ID_Empleat;
    }

    public int getID_Tasca() {
        return ID_Tasca;
    }

    public void setID_Tasca(int ID_Tasca) {
        this.ID_Tasca = ID_Tasca;
    }
    
    
}
