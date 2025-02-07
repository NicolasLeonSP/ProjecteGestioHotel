package com.mycompany.gestiohotelsprojecte.model;

import java.sql.Date;

/**
 *
 * @author Nicolas Leon Sapoznik Pancani
 */
public class Tasca {
    
    enum Estat {
        No_Iniciada,
        En_Progres,
        Completada
    }
    
    private int ID_Tasca;
    private String descripcio;
    private Date data_Creacio;
    private Date data_Ejecuccio;
    private Estat estat;

    public Tasca(String descripcio, Date data_Creacio, Date data_Ejecuccio, Estat estat) {
        this.descripcio = descripcio;
        this.data_Creacio = data_Creacio;
        this.data_Ejecuccio = data_Ejecuccio;
        this.estat = estat;
    }

    public int getID_Tasca() {
        return ID_Tasca;
    }

    public void setID_Tasca(int ID_Tasca) {
        this.ID_Tasca = ID_Tasca;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public Date getData_Creacio() {
        return data_Creacio;
    }

    public void setData_Creacio(Date data_Creacio) {
        this.data_Creacio = data_Creacio;
    }

    public Date getData_Ejecuccio() {
        return data_Ejecuccio;
    }

    public void setData_Ejecuccio(Date data_Ejecuccio) {
        this.data_Ejecuccio = data_Ejecuccio;
    }

    public Estat getEstat() {
        return estat;
    }

    public void setEstat(Estat estat) {
        this.estat = estat;
    }
    
    
    
}
