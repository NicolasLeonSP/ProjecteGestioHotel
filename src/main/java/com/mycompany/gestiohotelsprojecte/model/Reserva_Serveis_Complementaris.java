package com.mycompany.gestiohotelsprojecte.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Nicolas Leon Sapoznik Pancani
 */
public class Reserva_Serveis_Complementaris {
    // Variables de la clase
    private int ID_Servei;
    private int ID_Reserva;
    private int quantitat;
    // Constructor de la clase
    public Reserva_Serveis_Complementaris(int ID_Servei, int ID_Reserva, int quantitat) {
        this.ID_Servei = ID_Servei;
        this.ID_Reserva = ID_Reserva;
        this.quantitat = quantitat;
    }
    // Getters y setters de la clase
    public int getID_Servei() {
        return ID_Servei;
    }

    public void setID_Servei(int ID_Servei) {
        this.ID_Servei = ID_Servei;
    }

    public int getID_Reserva() {
        return ID_Reserva;
    }

    public void setID_Reserva(int ID_Reserva) {
        this.ID_Reserva = ID_Reserva;
    }

    public int getQuantitat() {
        return quantitat;
    }

    public void setQuantitat(int quantitat) {
        this.quantitat = quantitat;
    }
    
    // Funcion SQL que se encarga de hacer un alta de una asignacion de un servicio complementario a una reserva.
    public Boolean altaReservaServeiComplementari() {
        boolean ReservaServeiComplementariSubido = true;
        Connection conectar = new Connexio().connecta();
        String sql = "INSERT INTO RESERVA_SERVEIS_COMPLEMENTARIS VALUES (?,?,?)";
        try {
            PreparedStatement orden = conectar.prepareStatement(sql);
            orden.setInt(1, getID_Reserva());
            orden.setInt(2, getID_Servei());
            orden.setInt(3, getQuantitat());
            orden.executeUpdate();
            orden.close();
            return ReservaServeiComplementariSubido;
        } catch (SQLException e) {
            System.out.println(e.toString());
            ReservaServeiComplementariSubido = false;
            return ReservaServeiComplementariSubido;
        } catch (Exception e) {
            System.out.println(e.toString());
            ReservaServeiComplementariSubido = false;
            return ReservaServeiComplementariSubido;
        }
    }
}
