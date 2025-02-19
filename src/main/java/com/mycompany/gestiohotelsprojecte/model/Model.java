package com.mycompany.gestiohotelsprojecte.model;

import static com.mycompany.gestiohotelsprojecte.model.Tipus_Client.Empleados;
import static com.mycompany.gestiohotelsprojecte.model.Tipus_Client.Empresa;
import static com.mycompany.gestiohotelsprojecte.model.Tipus_Client.Premium;
import static com.mycompany.gestiohotelsprojecte.model.Tipus_Client.Regular;
import static com.mycompany.gestiohotelsprojecte.model.Tipus_Client.VIP;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Nicolas Leon Sapoznik Pancani
 */
public class Model {

    ObservableList tipoCliente = FXCollections.observableArrayList();
    ObservableList estatLaboral = FXCollections.observableArrayList();
    ObservableList tipoReserva = FXCollections.observableArrayList();
    ObservableList habitaciones = FXCollections.observableArrayList();
    ObservableList reservas = FXCollections.observableArrayList();
    private int IDClienteReserva;
    private Tipus_Client TipusClienteReserva;

    public Model() {
    }

    public void initModel() {
        for (Tipus_Client value : Tipus_Client.values()) {
            tipoCliente.add(value);
        }
        for (Estat_Laboral value : Estat_Laboral.values()) {
            estatLaboral.add(value);
        }
        for (Tipus_Reserva value : Tipus_Reserva.values()) {
            tipoReserva.add(value);
        }
    }

    public Tipus_Client getTipusClienteReserva() {
        return TipusClienteReserva;
    }

    public int getIDClienteReserva() {
        return IDClienteReserva;
    }

    public ObservableList getReservas() {
        return reservas;
    }

    public void setTipusClienteReserva(Tipus_Client TipusClienteReserva) {
        this.TipusClienteReserva = TipusClienteReserva;
    }

    public void setIDClienteReserva(int IDClienteReserva) {
        this.IDClienteReserva = IDClienteReserva;
    }

    public ObservableList getHabitaciones() {
        return habitaciones;
    }

    public ObservableList getTipoCliente() {
        return tipoCliente;
    }

    public ObservableList getEstatLaboral() {
        return estatLaboral;
    }

    public ObservableList getTipoReserva() {
        return tipoReserva;
    }

    public String checkPersona(Persona persona) {
        String msgError = "";
        if (!checkDate(persona.getData_Naixement())) {
            msgError += "- Verifique que la fecha de nacimiento sea correcta, ya que supera la fecha actual.\n";
        }
        if (!persona.checkTelefono()) {
            msgError += "- Verifique que el telefono este escrito de forma correcta.\n";
        }
        if (!persona.checkEmail()) {
            msgError += "- Verifique que el email esta escrito de forma correcta.\n";
        }
        String errorDNI = persona.checkDNI();
        if (!errorDNI.equals("0")) {
            if (errorDNI.equals("1")) {
                msgError += "- Verifique que el DNI contiene 9 caracteres, siendo 8 numeros y una letra.\n";
            }
            if (errorDNI.charAt(0) == '2') {
                msgError += "- Verifique que la letra del DNI sea correcta. La letra retornada por el generador es " + errorDNI.charAt(1) + " .\n";
            }
        }
        return msgError;
    }

    public String checkCliente(Date date, String credito) {
        String msgError = "";
        if (!checkDate(date)) {
            msgError += "- Verifique que la fecha de registro del cliente sea correcta, ya que supera la fecha actual.\n";
        }
        if (credito != null) {
            if (!checkTargetaCredito(credito)) {
                msgError += "- Verifique que su tarjeta de credito este escrito en un formato correcto. Ej (1111 1111 1111 1111 o 111111111111111)\n";
            }
        }
        return msgError;
    }

    public String checkEmpleado(Date date, String salario) {
        String msgError = "";
        if (!checkDate(date)) {
            msgError += "- Verifique que la fecha de contratacion del empleado sea correcta, ya que supera la fecha actual.\n";
        }
        if (!checkStringToInt(salario)) {
            msgError += "- Verifique que su salario sean solo numeros.";
        }
        return msgError;
    }

    public boolean checkTargetaCredito(String targeta) {
        Pattern regexCredito = Pattern.compile("([0-9]{4}\\s?){4}", Pattern.CASE_INSENSITIVE);
        Matcher creditoCheck = regexCredito.matcher(targeta);
        if (creditoCheck.find()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkDate(Date date) {
        if (date.after(Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()))) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkStringToInt(String string) {
        try {
            Integer.valueOf(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public Tipus_Client getTipoCliente(String tipoCliente) {
        Tipus_Client cliente = null;
        for (Tipus_Client e : Tipus_Client.values()) {
            if (e.toString().equals(tipoCliente)) {
                cliente = e;
            }
        }
        return cliente;
    }

    public Tipus_IVA getIVAClient(Tipus_Client TipusClient) {
        switch (TipusClient) {
            case Regular:
                return Tipus_IVA.General;
            case Premium:
                return Tipus_IVA.Reduccio;
            case VIP:
                return Tipus_IVA.Tipo_Cero;
            case Empresa:
                return Tipus_IVA.Reduccio;
            case Empleados:
                return Tipus_IVA.Tipo_Cero;
            default:
                return null;
        }
    }
    
    public Tipus_IVA getIVAFromValue(int Iva) {
        switch (Iva) {
            case 0:
                return Tipus_IVA.Tipo_Cero;
            case 7:
                return Tipus_IVA.Reduccio;
            case 21:
                return Tipus_IVA.General;
            default:
                return null;
        }
    }
    
    public Tipus_Reserva getReservaFromString(String tipo) {
        switch (tipo) {
            case "AD":
                return Tipus_Reserva.AD;
            case "MP":
                return Tipus_Reserva.MP;
            default:
                return null;
        }
    }

    public String altaReserva(Reserva reserva) {
        String ReservaMensaje = "";
        Connection conectar = new Connexio().connecta();
        String sql = "INSERT INTO RESERVA (data_Reserva, data_Inici, data_Fi, tipus_Reserva, tipus_IVA, preu_Total_Reserva, ID_Client, ID_Habitacio) VALUES (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement orden = conectar.prepareStatement(sql);
            orden.setDate(1, reserva.getData_Reserva());
            orden.setDate(2, reserva.getData_Inici());
            orden.setDate(3, reserva.getData_Fi());
            orden.setString(4, reserva.getTipus_Reserva().name());
            orden.setInt(5, reserva.getTipus_IVA().getPorIVA());
            orden.setDouble(6, reserva.getPreu_Total_Reserva());
            orden.setInt(7, reserva.getID_Client());
            orden.setInt(8, reserva.getID_Habitacio());
            orden.executeUpdate();
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

    public Boolean altaPersona(Persona persona) {
        boolean PersonaSubidaBaseDeDatos = true;
        Connection conectar = new Connexio().connecta();
        String sql = "INSERT INTO PERSONA (Nom, Cognom, Adreça, Document_Identitat, Data_Naixement, Telefon, Email) VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement orden = conectar.prepareStatement(sql);
            orden.setString(1, persona.getNom());
            orden.setString(2, persona.getCognom());
            orden.setString(3, persona.getAdreça());
            orden.setString(4, persona.getDocument_Identitat());
            orden.setDate(5, persona.getData_Naixement());
            orden.setString(6, persona.getTelefon());
            orden.setString(7, persona.getEmail());
            orden.executeUpdate();
            return PersonaSubidaBaseDeDatos;
        } catch (SQLException e) {
            System.out.println(e);
            PersonaSubidaBaseDeDatos = false;
            return PersonaSubidaBaseDeDatos;
        } catch (Exception e) {
            System.out.println(e);
            PersonaSubidaBaseDeDatos = false;
            return PersonaSubidaBaseDeDatos;
        }
    }

    public Boolean altaEmpleado(Empleat empleado) {
        boolean EmpleadoSubidoABaseDeDatos = true;
        Connection conectar = new Connexio().connecta();
        String sql = "INSERT INTO EMPLEAT VALUES (?,?,?,?,?)";
        try {
            PreparedStatement orden = conectar.prepareStatement(sql);
            orden.setInt(1, empleado.getID_Persona());
            orden.setString(2, empleado.getLloc_Feina());
            orden.setDate(3, empleado.getData_Contractacio());
            orden.setInt(4, empleado.getSalari_Brut());
            orden.setString(5, empleado.getEstat_Laboral().name());
            orden.executeUpdate();
            return EmpleadoSubidoABaseDeDatos;
        } catch (SQLException e) {
            System.out.println(e);
            EmpleadoSubidoABaseDeDatos = false;
            return EmpleadoSubidoABaseDeDatos;
        } catch (Exception e) {
            System.out.println(e);
            EmpleadoSubidoABaseDeDatos = false;
            return EmpleadoSubidoABaseDeDatos;
        }
    }

    public Boolean altaCliente(Client cliente) {
        boolean ClienteSubidoABaseDeDatos = true;
        Connection conectar = new Connexio().connecta();
        String sql = "INSERT INTO CLIENT VALUES (?,?,?,?)";
        try {
            PreparedStatement orden = conectar.prepareStatement(sql);
            orden.setInt(1, cliente.getID_Persona());
            orden.setDate(2, cliente.getData_Registre());
            orden.setString(3, cliente.getTipus_Client().name());
            orden.setString(4, cliente.getTargeta_Credit().replace(" ", ""));
            orden.executeUpdate();
            return ClienteSubidoABaseDeDatos;
        } catch (SQLException e) {
            System.out.println(e);
            ClienteSubidoABaseDeDatos = false;
            return ClienteSubidoABaseDeDatos;
        } catch (Exception e) {
            System.out.println(e);
            ClienteSubidoABaseDeDatos = false;
            return ClienteSubidoABaseDeDatos;
        }
    }

    // Conseguir solo el ID de la persona
    public int getIdPersona(String document_Identitat) {
        int ID_Persona = 0;
        Connection conectar = new Connexio().connecta();
        String sql = "SELECT ID_Persona FROM PERSONA WHERE Document_Identitat = ?";
        try {
            PreparedStatement orden = conectar.prepareStatement(sql);
            orden.setString(1, document_Identitat);
            ResultSet resultados = orden.executeQuery();
            while (resultados.next()) {
                ID_Persona = resultados.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        } catch (Exception e) {
            System.out.println(e);
            return 0;
        }
        return ID_Persona;
    }

    // Conseguir toda la persona
    public int getClientReserva(String document_Identitat) {
        int ID_Persona = 0;
        Tipus_Client tipoCliente = null;
        Connection conectar = new Connexio().connecta();
        String sql = "SELECT ID_Client, Tipus_Client FROM CLIENT WHERE ID_Client = (SELECT ID_Persona FROM PERSONA WHERE Document_Identitat = ?)";
        try {
            PreparedStatement orden = conectar.prepareStatement(sql);
            orden.setString(1, document_Identitat);
            ResultSet resultados = orden.executeQuery();
            while (resultados.next()) {
                ID_Persona = resultados.getInt(1);
                setIDClienteReserva(ID_Persona);
                tipoCliente = getTipoCliente(resultados.getString(2));
                setTipusClienteReserva(tipoCliente);
            }
        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        } catch (Exception e) {
            System.out.println(e);
            return 0;
        }
        return ID_Persona;
    }

    public int getIDHabitacion(int numeroHabitacio) {
        int ID_Habitacio = 0;
        Connection conectar = new Connexio().connecta();
        String sql = "SELECT ID_Habitacio FROM HABITACIO WHERE numero_Habitacio = ?";
        try {
            PreparedStatement orden = conectar.prepareStatement(sql);
            orden.setInt(1, numeroHabitacio);
            ResultSet resultados = orden.executeQuery();
            while (resultados.next()) {
                ID_Habitacio = resultados.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } catch (Exception e) {
            System.out.println(e);
        }
        return ID_Habitacio;
    }
    // Solucionar error getReserva
    public Reserva getReserva(int ID_Reserva) {
        Reserva reserva = null;
        Connection conectar = new Connexio().connecta();
        String sql = "SELECT * FROM RESERVA WHERE ID_Reserva = ?";
        try {
            PreparedStatement orden = conectar.prepareStatement(sql);
            orden.setInt(1, ID_Reserva);
            ResultSet resultados = orden.executeQuery();
            while (resultados.next()) {
                reserva = new Reserva(resultados.getDate(1),resultados.getDate(2), resultados.getDate(3), getReservaFromString(resultados.getString(4)), getIVAFromValue(resultados.getInt(5)), resultados.getDouble(6), resultados.getInt(7), resultados.getInt(8));
            }
        } catch (SQLException e) {
            System.out.println(e);
        } catch (Exception e) {
            System.out.println(e);
        }
        return reserva;
    }

    // Por hacer
    public void recargarCodigoReserva(String document_Identitat) {
        Connection conectar = new Connexio().connecta();
        String sql = "SELECT ID_Reserva FROM RESERVA WHERE ID_Client = (SELECT ID_Client FROM CLIENT WHERE ID_Client = (SELECT ID_Persona FROM PERSONA WHERE Document_Identitat = ?))";
        try {
            PreparedStatement orden = conectar.prepareStatement(sql);
            orden.setString(1, document_Identitat);
            ResultSet resultados = orden.executeQuery();
            reservas.clear();
            while (resultados.next()) {
                reservas.add(String.valueOf(resultados.getInt(1)));
            }
        } catch (SQLException e) {
            System.out.println(e);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void recargarHabitaciones() {
        Connection conectar = new Connexio().connecta();
        String sql = "SELECT Numero_Habitacio FROM HABITACIO";
        try {
            Statement orden = conectar.createStatement();
            ResultSet resultados = orden.executeQuery(sql);
            habitaciones.clear();
            while (resultados.next()) {
                habitaciones.add(String.valueOf(resultados.getInt(1)));
            }
        } catch (SQLException e) {
            System.out.println(e);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
