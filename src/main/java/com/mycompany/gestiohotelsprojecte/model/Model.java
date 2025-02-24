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
import java.util.ArrayList;
import java.util.Arrays;
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
        } else {
            ArrayList<String> check = getEmailDocIdeCheck();
            boolean errorYaIntroducido = false;
            for (String string : check) {
                while (!errorYaIntroducido) {
                    String[] temp = string.split(",");
                    if (temp[0].equals(persona.getEmail())) {
                        msgError += "- El correo que ha introducido ya existe en la base de datos, porfavor introduzca otro. \n";
                        errorYaIntroducido = true;

                    }
                }

            }
        }
        String errorDNI = persona.checkDNI();
        if (!errorDNI.equals("0")) {
            if (errorDNI.equals("1")) {
                msgError += "- Verifique que el DNI contiene 9 caracteres, siendo 8 numeros y una letra.\n";
            }
            if (errorDNI.charAt(0) == '2') {
                msgError += "- Verifique que la letra del DNI sea correcta. La letra retornada por el generador es " + errorDNI.charAt(1) + " .\n";
            }
        } else {
            ArrayList<String> check = getEmailDocIdeCheck();
            boolean errorYaIntroducido = false;
            for (String string : check) {
                while (!errorYaIntroducido) {
                    String[] temp = string.split(",");
                    if (temp[1].equals(persona.getDocument_Identitat())) {
                        msgError += "- El DNI que ha introducido ya existe en la base de datos, porfavor introduzca otro. \n";
                        errorYaIntroducido = true;
                    }
                }
            }
        }
        return msgError;
    }

    public String checkCliente(Date date, String credito) {
        String msgError = "";
        if (!checkDate(date)) {
            msgError += "- Verifique que la fecha de registro del cliente sea correcta, ya que supera la fecha actual.\n";
        }
        if (!credito.equals("")) {
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

    public java.sql.Date LocalDateToSqlDate(LocalDate fecha) {
        try {
            Date date = new java.sql.Date(Date.from(fecha.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()).getTime());
            return date;
        } catch (Exception e) {
            return null;
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

    public int getIDFromObservableList(String buscar, ObservableList ol) {
        int ID_Conseguida = -1;
        for (int i = 0; i < ol.size(); i++) {
            if (ol.get(i).toString().equals(buscar)) {
                ID_Conseguida = i;
            }

        }
        return ID_Conseguida;
    }

    public String retornarMensajeCorrectoReserva(String msgError) {
        String[] msgErrorPartido = msgError.split("`");
        String[] campoErrorPartido = msgErrorPartido[1].split("\\.");
        return campoErrorPartido[1];
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
            ReservaMensaje = e.getMessage();
            return ReservaMensaje;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            ReservaMensaje = e.getMessage();
            return ReservaMensaje;
        }
    }

    public String modificarReserva(Reserva reserva) {
        String ReservaMensaje = "";
        Connection conectar = new Connexio().connecta();
        String sql = "UPDATE RESERVA SET data_Inici = ?, data_Fi = ?, tipus_Reserva = ?, ID_Habitacio = ?  WHERE ID_Reserva = ?";
        try {
            PreparedStatement orden = conectar.prepareStatement(sql);
            orden.setDate(1, reserva.getData_Inici());
            orden.setDate(2, reserva.getData_Fi());
            orden.setString(3, reserva.getTipus_Reserva().name());
            orden.setInt(4, reserva.getID_Habitacio());
            orden.setInt(5, reserva.getID_Reserva());
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

    public boolean eliminarReserva(int ID_Reserva) {
        boolean ReservaEliminada = true;
        Connection conectar = new Connexio().connecta();
        String sql = "DELETE FROM RESERVA WHERE ID_Reserva = ?";
        try {
            PreparedStatement orden = conectar.prepareStatement(sql);
            orden.setInt(1, ID_Reserva);
            orden.executeUpdate();
            return ReservaEliminada;
        } catch (SQLException e) {
            System.out.println(e.toString());
            ReservaEliminada = false;
            return ReservaEliminada;
        } catch (Exception e) {
            System.out.println(e.toString());
            ReservaEliminada = false;
            return ReservaEliminada;
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
            System.out.println(e.toString());
            PersonaSubidaBaseDeDatos = false;
            return PersonaSubidaBaseDeDatos;
        } catch (Exception e) {
            System.out.println(e.toString());
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
            System.out.println(e.toString());
            EmpleadoSubidoABaseDeDatos = false;
            return EmpleadoSubidoABaseDeDatos;
        } catch (Exception e) {
            System.out.println(e.toString());
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
            System.out.println(e.toString());
            ClienteSubidoABaseDeDatos = false;
            return ClienteSubidoABaseDeDatos;
        } catch (Exception e) {
            System.out.println(e.toString());
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
            System.out.println(e.toString());
            return 0;
        } catch (Exception e) {
            System.out.println(e.toString());
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
            System.out.println(e.toString());
            return 0;
        } catch (Exception e) {
            System.out.println(e.toString());
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
            System.out.println(e.toString());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return ID_Habitacio;
    }

    // Conseguir solo el ID de la persona
    public ArrayList<String> getEmailDocIdeCheck() {
        ArrayList<String> emailYDocIde = new ArrayList<>();
        Connection conectar = new Connexio().connecta();
        String sql = "SELECT email, document_Identitat FROM PERSONA";
        try {
            Statement orden = conectar.createStatement();
            ResultSet resultados = orden.executeQuery(sql);
            while (resultados.next()) {
                String temp = resultados.getString(1);
                temp += "," + resultados.getString(2);
                emailYDocIde.add(temp);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
            return null;
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
        return emailYDocIde;
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

                reserva = new Reserva(resultados.getDate(2), resultados.getDate(3), resultados.getDate(4), getReservaFromString(resultados.getString(5)), getIVAFromValue(resultados.getInt(6)), resultados.getDouble(7), resultados.getInt(8), resultados.getInt(9));
                reserva.setID_Reserva(resultados.getInt(1));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        } catch (Exception e) {
            System.out.println(e.toString());
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
            System.out.println(e.toString());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public int getNumeroHabitacion(int ID_Habitacion) {
        int Num_Habitacion = -1;
        Connection conectar = new Connexio().connecta();
        String sql = "SELECT Numero_Habitacio FROM HABITACIO WHERE ID_Habitacio = ?";
        try {
            PreparedStatement orden = conectar.prepareStatement(sql);
            orden.setInt(1, ID_Habitacion);
            ResultSet resultados = orden.executeQuery();
            while (resultados.next()) {
                Num_Habitacion = resultados.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return Num_Habitacion;
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
            System.out.println(e.toString());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
