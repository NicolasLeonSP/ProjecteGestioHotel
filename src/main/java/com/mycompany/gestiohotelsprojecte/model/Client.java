/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestiohotelsprojecte.model;

import java.sql.Date;

/**
 *
 * @author Nicolas Leon Sapoznik Pancani
 */
public class Client extends Persona {
    
    enum Tipus_Client {
        Empresa,
        Empleados,
        Individual,
        VIP
    }
    
    private int ID_Client;
    private Date data_Registre;
    private Tipus_Client tipus_Client;
    private String targeta_Credit;

    public Client(String nom, String cognom, String adreça, String document_Identitat, Date data_Naixement, String telefon, String email, int ID_Client, Date data_Registre, Tipus_Client tipus_Client, String targeta_Credit) {
        super(nom, cognom, adreça, document_Identitat, data_Naixement, telefon, email);
        this.ID_Client = ID_Client;
        this.data_Registre = data_Registre;
        this.tipus_Client = tipus_Client;
        this.targeta_Credit = targeta_Credit;
    }

    public int getID_Client() {
        return ID_Client;
    }

    public void setID_Client(int ID_Client) {
        this.ID_Client = ID_Client;
    }

    public Date getData_Registre() {
        return data_Registre;
    }

    public void setData_Registre(Date data_Registre) {
        this.data_Registre = data_Registre;
    }

    public Tipus_Client getTipus_Client() {
        return tipus_Client;
    }

    public void setTipus_Client(Tipus_Client tipus_Client) {
        this.tipus_Client = tipus_Client;
    }

    public String getTargeta_Credit() {
        return targeta_Credit;
    }

    public void setTargeta_Credit(String targeta_Credit) {
        this.targeta_Credit = targeta_Credit;
    }
    
    public boolean CheckTargetaCredito(String targeta) {
        try {
            Integer.valueOf(targeta);
            return true;
        } catch (NumberFormatException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    
}
