package com.mycompany.gestiohotelsprojecte.model;

import java.sql.Date;

/**
 *
 * @author Nicolas Leon Sapoznik Pancani
 */
public class Factura {
    enum Metode_Pagament {
        Targeta,
        Transferencia_Paypal,
        Transferencia_Bancaria,
        Efectiu
    }
    
    private int ID_Factura;
    private Date data_Emissio;
    private Metode_Pagament metode_Pagament;
    private double base_Imposable;
    private double iva;
    private double total;
    private int ID_Reserva;

    public Factura(Date data_Emissio, Metode_Pagament metode_Pagament, double base_Imposable, double iva, double total, int ID_Reserva) {
        this.data_Emissio = data_Emissio;
        this.metode_Pagament = metode_Pagament;
        this.base_Imposable = base_Imposable;
        this.iva = iva;
        this.total = total;
        this.ID_Reserva = ID_Reserva;
    }

    public int getID_Factura() {
        return ID_Factura;
    }

    public void setID_Factura(int ID_Factura) {
        this.ID_Factura = ID_Factura;
    }

    public Date getData_Emissio() {
        return data_Emissio;
    }

    public void setData_Emissio(Date data_Emissio) {
        this.data_Emissio = data_Emissio;
    }

    public Metode_Pagament getMetode_Pagament() {
        return metode_Pagament;
    }

    public void setMetode_Pagament(Metode_Pagament metode_Pagament) {
        this.metode_Pagament = metode_Pagament;
    }

    public double getBase_Imposable() {
        return base_Imposable;
    }

    public void setBase_Imposable(double base_Imposable) {
        this.base_Imposable = base_Imposable;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getID_Reserva() {
        return ID_Reserva;
    }

    public void setID_Reserva(int ID_Reserva) {
        this.ID_Reserva = ID_Reserva;
    }
    
    
}
