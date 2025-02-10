package com.mycompany.gestiohotelsprojecte.model;

import java.sql.Date;

/**
 *
 * @author Nicolas Leon Sapoznik Pancani
 */
public class Reserva {
    enum Tipus_Reserva {
        AD,
        MP
    }
    private int ID_Reserva;
    private Date data_Reserva;
    private Date data_Inici;
    private Date data_Fi;
    private Tipus_Reserva tipus_Reserva;
    private Tipus_IVA tipus_IVA;
    private double preu_Total_Reserva;
    private int ID_Habitacio;

    public Reserva(Date data_Reserva, Date data_Inici, Date data_Fi, Tipus_Reserva tipus_Reserva, Tipus_IVA tipus_IVA, double preu_Total_Reserva, int ID_Habitacio) {
        this.data_Reserva = data_Reserva;
        this.data_Inici = data_Inici;
        this.data_Fi = data_Fi;
        this.tipus_Reserva = tipus_Reserva;
        this.tipus_IVA = tipus_IVA;
        this.preu_Total_Reserva = preu_Total_Reserva;
        this.ID_Habitacio = ID_Habitacio;
    }

    public int getID_Habitacio() {
        return ID_Habitacio;
    }

    public void setID_Habitacio(int ID_Habitacio) {
        this.ID_Habitacio = ID_Habitacio;
    }

    public int getID_Reserva() {
        return ID_Reserva;
    }

    public void setID_Reserva(int ID_Reserva) {
        this.ID_Reserva = ID_Reserva;
    }

    public Date getData_Reserva() {
        return data_Reserva;
    }

    public void setData_Reserva(Date data_Reserva) {
        this.data_Reserva = data_Reserva;
    }

    public Date getData_Inici() {
        return data_Inici;
    }

    public void setData_Inici(Date data_Inici) {
        this.data_Inici = data_Inici;
    }

    public Date getData_Fi() {
        return data_Fi;
    }

    public void setData_Fi(Date data_Fi) {
        this.data_Fi = data_Fi;
    }

    public Tipus_Reserva getTipus_Reserva() {
        return tipus_Reserva;
    }

    public void setTipus_Reserva(Tipus_Reserva tipus_Reserva) {
        this.tipus_Reserva = tipus_Reserva;
    }

    public Tipus_IVA getTipus_IVA() {
        return tipus_IVA;
    }

    public void setTipus_IVA(Tipus_IVA tipus_IVA) {
        this.tipus_IVA = tipus_IVA;
    }

    public double getPreu_Total_Reserva() {
        return preu_Total_Reserva;
    }

    public void setPreu_Total_Reserva(double preu_Total_Reserva) {
        this.preu_Total_Reserva = preu_Total_Reserva;
    }
    
    
    
}
