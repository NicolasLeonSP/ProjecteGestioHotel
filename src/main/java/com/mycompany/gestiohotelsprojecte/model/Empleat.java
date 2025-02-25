/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestiohotelsprojecte.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Empleat extends Persona {

    private String lloc_Feina;
    private Date data_Contractacio;
    private int salari_Brut;
    private Estat_Laboral estat_Laboral;

    public Empleat(String nom, String cognom, String adreça, String document_Identitat, Date data_Naixement, String telefon, String email, String lloc_Feina, Date data_Contractacio, int salari_Brut, Estat_Laboral estat_Laboral) {
        super(nom, cognom, adreça, document_Identitat, data_Naixement, telefon, email);
        this.lloc_Feina = lloc_Feina;
        this.data_Contractacio = data_Contractacio;
        this.salari_Brut = salari_Brut;
        this.estat_Laboral = estat_Laboral;
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
    
    public Boolean altaEmpleado() {
        boolean EmpleadoSubidoABaseDeDatos = true;
        Connection conectar = new Connexio().connecta();
        String sql = "INSERT INTO EMPLEAT VALUES (?,?,?,?,?)";
        try {
            PreparedStatement orden = conectar.prepareStatement(sql);
            orden.setInt(1, getID_Persona());
            orden.setString(2, getLloc_Feina());
            orden.setDate(3, getData_Contractacio());
            orden.setInt(4, getSalari_Brut());
            orden.setString(5, getEstat_Laboral().name());
            orden.executeUpdate();
            return EmpleadoSubidoABaseDeDatos;
        } catch (SQLException e) {
            System.out.println(e.toString());
            EmpleadoSubidoABaseDeDatos = false;
            return EmpleadoSubidoABaseDeDatos;
        } catch (Exception e) {
            System.out.println(e.toString());
            EmpleadoSubidoABaseDeDatos = false;
            return EmpleadoSubidoABaseDeDatos;
        }
    }
}
