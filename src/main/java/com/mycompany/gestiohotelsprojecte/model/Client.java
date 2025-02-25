/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestiohotelsprojecte.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Nicolas Leon Sapoznik Pancani
 */
public class Client extends Persona {

    private Date data_Registre;
    private Tipus_Client tipus_Client;
    private String targeta_Credit;

    public Client(String nom, String cognom, String adreça, String document_Identitat, Date data_Naixement, String telefon, String email, Date data_Registre, Tipus_Client tipus_Client, String targeta_Credit) {
        super(nom, cognom, adreça, document_Identitat, data_Naixement, telefon, email);
        this.data_Registre = data_Registre;
        this.tipus_Client = tipus_Client;
        this.targeta_Credit = targeta_Credit;
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

    public Boolean altaCliente() {
        boolean ClienteSubidoABaseDeDatos = true;
        Connection conectar = new Connexio().connecta();
        String sql = "INSERT INTO CLIENT VALUES (?,?,?,?)";
        try {
            PreparedStatement orden = conectar.prepareStatement(sql);
            orden.setInt(1, getID_Persona());
            orden.setDate(2, getData_Registre());
            orden.setString(3, getTipus_Client().name());
            if (getTargeta_Credit() != null) {
                orden.setString(4, getTargeta_Credit().replace(" ", ""));

            } else {
                orden.setString(4, getTargeta_Credit());
            }
            orden.executeUpdate();
            return ClienteSubidoABaseDeDatos;
        } catch (SQLException e) {
            System.out.println(e.toString());
            ClienteSubidoABaseDeDatos = false;
            return ClienteSubidoABaseDeDatos;
        } catch (Exception e) {
            System.out.println(e.toString());
            ClienteSubidoABaseDeDatos = false;
            return ClienteSubidoABaseDeDatos;
        }
    }

}
