package com.mycompany.gestiohotelsprojecte.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Nicolas Leon Sapoznik Pancani
 */
public class Reserva {
    // Variables de la clase Reserva
    private int ID_Reserva;
    private Date data_Reserva;
    private Date data_Inici;
    private Date data_Fi;
    private Tipus_Reserva tipus_Reserva;
    private Tipus_IVA tipus_IVA;
    private double preu_Total_Reserva;
    private int ID_Client;
    private int ID_Habitacio;
    // Constructor de la clase reserva
    public Reserva(Date data_Reserva, Date data_Inici, Date data_Fi, Tipus_Reserva tipus_Reserva, Tipus_IVA tipus_IVA, double preu_Total_Reserva, int ID_Client, int ID_Habitacio) {
        this.data_Reserva = data_Reserva;
        this.data_Inici = data_Inici;
        this.data_Fi = data_Fi;
        this.tipus_Reserva = tipus_Reserva;
        this.tipus_IVA = tipus_IVA;
        this.preu_Total_Reserva = preu_Total_Reserva;
        this.ID_Client = ID_Client;
        this.ID_Habitacio = ID_Habitacio;
    }
    // Getters y setters de la clase Reserva
    public int getID_Client() {
        return ID_Client;
    }

    public void setID_Client(int ID_Client) {
        this.ID_Client = ID_Client;
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

    // Esta funcion SQL se encarga de añadir una reserva a la base de datos. Ir a la linea 1029 del modelo para mas info
    public String altaReserva() {
        String ReservaMensaje = "";
        Connection conectar = new Connexio().connecta();
        String sql = "INSERT INTO RESERVA (data_Reserva, data_Inici, data_Fi, tipus_Reserva, tipus_IVA, preu_Total_Reserva, ID_Client, ID_Habitacio) VALUES (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement orden = conectar.prepareStatement(sql);
            orden.setDate(1, getData_Reserva());
            orden.setDate(2, getData_Inici());
            orden.setDate(3, getData_Fi());
            orden.setString(4, getTipus_Reserva().name());
            orden.setInt(5, getTipus_IVA().getPorIVA());
            orden.setDouble(6, getPreu_Total_Reserva());
            orden.setInt(7, getID_Client());
            orden.setInt(8, getID_Habitacio());
            orden.executeUpdate();
            orden.close();
            return ReservaMensaje;
        } catch (SQLException e) {
            ReservaMensaje = e.getMessage();
            return ReservaMensaje;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            ReservaMensaje = e.getMessage();
            return ReservaMensaje;
        }
    }

    // Esta funcion SQL se encarga de modificar una reserva ya existente en la base de datos, modificando la mitad sus datos. Ir a la linea 1029 del modelo para mas info
    public String modificarReserva() {
        String ReservaMensaje = "";
        Connection conectar = new Connexio().connecta();
        String sql = "UPDATE RESERVA SET data_Inici = ?, data_Fi = ?, tipus_Reserva = ?, ID_Habitacio = ?  WHERE ID_Reserva = ?";
        try {
            PreparedStatement orden = conectar.prepareStatement(sql);
            orden.setDate(1, getData_Inici());
            orden.setDate(2, getData_Fi());
            orden.setString(3, getTipus_Reserva().name());
            orden.setInt(4, getID_Habitacio());
            orden.setInt(5, getID_Reserva());
            orden.executeUpdate();
            orden.close();
            return ReservaMensaje;
        } catch (SQLException e) {
            System.out.println(e.toString());
            ReservaMensaje = e.toString();
            return ReservaMensaje;
        } catch (Exception e) {
            System.out.println(e.toString());
            ReservaMensaje = e.toString();
            return ReservaMensaje;
        }
    }
}
