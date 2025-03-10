package com.mycompany.gestiohotelsprojecte.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Nicolas Leon Sapoznik Pancani
 */
public class Tasca {
    
    // Variables de la clase Tarea
    private int ID_Tasca;
    private String descripcio;
    private Date data_Creacio;
    private Date data_Ejecuccio;
    private Estat estat;
    
    // Constructor de la clase Tarea
    public Tasca(String descripcio, Date data_Creacio, Date data_Ejecuccio, Estat estat) {
        this.descripcio = descripcio;
        this.data_Creacio = data_Creacio;
        this.data_Ejecuccio = data_Ejecuccio;
        this.estat = estat;
    }
    
    // Getters y setters de Tarea
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

    // Esta funcion SQL se encarga de añadir una tarea a la base de datos. Ir a la linea 980 del modelo para mas info
    public boolean altaTasca() {
        Boolean TascaSubida = false;
        Connection conectar = new Connexio().connecta();
        String sql = "INSERT INTO TASCA (descripció, data_Creacio, data_Ejecucio, estat) VALUES (?,?,?,?)";
        try {
            PreparedStatement orden = conectar.prepareStatement(sql);
            orden.setString(1, getDescripcio());
            orden.setDate(2, getData_Creacio());
            orden.setDate(3, getData_Ejecuccio());
            orden.setString(4, getEstat().name());
            orden.executeUpdate();
            TascaSubida = true;
            orden.close();
            return TascaSubida;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return TascaSubida;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return TascaSubida;
        }
    }

}
