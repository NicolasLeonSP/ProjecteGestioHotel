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
public class Realitza {

    
    private int ID_Empleat;
    private int ID_Tasca;
    private Estat estat_Per_Empleat;
    private Date data_Assignacio;

    public Realitza(int ID_Tasca, int ID_Empleat, Estat estat_Per_Empleat, Date data_Assignacio) {
        this.ID_Tasca = ID_Tasca;
        this.ID_Empleat = ID_Empleat;
        this.estat_Per_Empleat = estat_Per_Empleat;
        this.data_Assignacio = data_Assignacio;
    }

    public int getID_Tasca() {
        return ID_Tasca;
    }

    public void setID_Tasca(int ID_Tasca) {
        this.ID_Tasca = ID_Tasca;
    }

    public int getID_Empleat() {
        return ID_Empleat;
    }

    public void setID_Empleat(int ID_Empleat) {
        this.ID_Empleat = ID_Empleat;
    }

    public Estat getEstat_Per_Empleat() {
        return estat_Per_Empleat;
    }

    public void setEstat_Per_Empleat(Estat estat_Per_Empleat) {
        this.estat_Per_Empleat = estat_Per_Empleat;
    }

    public Date getData_Assignacio() {
        return data_Assignacio;
    }

    public void setData_Assignacio(Date data_Assignacio) {
        this.data_Assignacio = data_Assignacio;
    }
    
    

    public boolean altaRealitza() {
        Boolean RealitzaSubida = false;
        Connection conectar = new Connexio().connecta();
        String sql = "INSERT INTO REALITZA VALUES (?,?,?,?)";
        try {
            PreparedStatement orden = conectar.prepareStatement(sql);
            orden.setInt(1, getID_Tasca());
            orden.setInt(2, getID_Empleat());
            orden.setString(3, getEstat_Per_Empleat().name());
            orden.setDate(4, getData_Assignacio());
            orden.executeUpdate();
            RealitzaSubida = true;
            return RealitzaSubida;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return RealitzaSubida;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return RealitzaSubida;
        }
    }

}
