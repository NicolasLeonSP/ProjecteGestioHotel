package com.mycompany.gestiohotelsprojecte.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Nicolas Leon Sapoznik Pancani
 */
public class Factura {

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

    public boolean editarFactura() {
        Boolean FacturaEditada = false;
        Connection conectar = new Connexio().connecta();
        String sql = "UPDATE FACTURA SET metode_Pagament = ? WHERE ID_Factura = ?";
        try {
            PreparedStatement orden = conectar.prepareStatement(sql);
            orden.setString(1, getMetode_Pagament().toString());
            orden.setInt(2, getID_Factura());
            orden.executeUpdate();
            FacturaEditada = true;
            return FacturaEditada;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return FacturaEditada;
        } catch (Exception e) {
            System.out.println(e.toString());
            return FacturaEditada;
        }
    }

    public boolean checkClienteTarjetaCredito() {
        boolean TargetaCreditoExiste = false;
        Connection conectar = new Connexio().connecta();
        String sql = "SELECT targeta_Credit FROM RESERVA r INNER JOIN CLIENT c ON c.ID_Client = r.ID_Client WHERE ID_Reserva = ?";
        try {
            PreparedStatement orden = conectar.prepareStatement(sql);
            orden.setInt(1, getID_Reserva());
            ResultSet resultados = orden.executeQuery();
            while (resultados.next()) {
                if (resultados.getLong(1) != 0) {
                    TargetaCreditoExiste = true;
                }
            }
            return TargetaCreditoExiste;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return TargetaCreditoExiste;
        } catch (Exception e) {
            System.out.println(e.toString());
            return TargetaCreditoExiste;
        }
    }
    public boolean altaFactura() {
        boolean facturaSubida = false;
        Connection conectar = new Connexio().connecta();
        String sql = "INSERT INTO FACTURA (data_Emisio, metode_Pagament, base_Imposable, iva, total, ID_Reserva) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement orden = conectar.prepareStatement(sql);
            orden.setDate(1, getData_Emissio());
            orden.setString(2, getMetode_Pagament().toString());
            orden.setDouble(3, getBase_Imposable());
            orden.setDouble(4, getIva());
            orden.setDouble(5, getTotal());
            orden.setInt(6, getID_Reserva());
            orden.executeUpdate();
            facturaSubida = true;
            return facturaSubida;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return facturaSubida;
        } catch (Exception e) {
            System.out.println(e.toString());
            return facturaSubida;
        }
    }
    

    public boolean modificarFactura() {
        boolean modificadoFactura = false;
        Connection conectar = new Connexio().connecta();
        String sql = "UPDATE FACTURA SET metode_Pagament = ? WHERE ID_Factura = ?";
        try {
            PreparedStatement orden = conectar.prepareStatement(sql);
            orden.setString(1, getMetode_Pagament().toString());
            orden.setInt(2, getID_Factura());
            orden.executeUpdate();
            modificadoFactura = true;
            return modificadoFactura;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return modificadoFactura;
        } catch (Exception e) {
            System.out.println(e.toString());
            return modificadoFactura;
        }
    }

}
