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
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
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
    ObservableList metodePagament = FXCollections.observableArrayList();
    ObservableList reservas = FXCollections.observableArrayList();
    ObservableList estados = FXCollections.observableArrayList();
    ObservableList tareas = FXCollections.observableArrayList();
    ObservableList tareasAvanzadas = FXCollections.observableArrayList();
    ObservableList empleadosTarea = FXCollections.observableArrayList();
    private String IDTascaORealitzaSeleccionada;
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
        for (Metode_Pagament value : Metode_Pagament.values()) {
            metodePagament.add(value);
        }
        for (Estat value : Estat.values()) {
            estados.add(value);
        }
    }

    public String getIDTascaORealitzaSeleccionada() {
        return IDTascaORealitzaSeleccionada;
    }

    public void setIDTascaORealitzaSeleccionada(String IDTascaORealitzaSeleccionada) {
        this.IDTascaORealitzaSeleccionada = IDTascaORealitzaSeleccionada;
    }

    public ObservableList getTareas() {
        return tareas;
    }

    public ObservableList getTareasAvanzadas() {
        return tareasAvanzadas;
    }

    public ObservableList getEstados() {
        return estados;
    }

    public ObservableList getEmpleadosTarea() {
        return empleadosTarea;
    }

    public Tipus_Client getTipusClienteReserva() {
        return TipusClienteReserva;
    }

    public int getIDClienteReserva() {
        return IDClienteReserva;
    }

    public ObservableList getMetodePagament() {
        return metodePagament;
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
        if (!checkDateIsBefore(persona.getData_Naixement())) {
            msgError += "- Verifiqueu que la data de naixement sigui correcta, ja que supera la data actual.\n";
        }
        if (!persona.checkTelefono()) {
            msgError += "- Verifiqueu que el telèfon estigui escrit correctament.\n";
        }
        if (!persona.checkEmail()) {
            msgError += "- Verifiqueu que el correu electrònic està escrit de forma correcta.\n";
        } else {
            ArrayList<String> check = getEmailDocIdeCheck();
            boolean errorYaIntroducido = false;
            for (String string : check) {
                if (!errorYaIntroducido) {
                    String[] temp = string.split(",");
                    if (temp[0].equals(persona.getEmail())) {
                        msgError += "- El correu que heu introduït ja existeix a la base de dades, si us plau introduïu un altre.\n";
                        errorYaIntroducido = true;

                    }
                }

            }
        }
        String errorDNI = persona.checkDNI();
        if (!errorDNI.equals("0")) {
            if (errorDNI.equals("1")) {
                msgError += "- Verifiqueu que el DNI conté 9 caràcters, sent 8 números i una lletra.\n";
            }
            if (errorDNI.charAt(0) == '2') {
                msgError += "- Verifiqueu que la lletra del DNI sigui correcta. La lletra retornada pel generador és " + errorDNI.charAt(1) + " .\n";
            }
        } else {
            ArrayList<String> check = getEmailDocIdeCheck();
            boolean errorYaIntroducido = false;
            for (String string : check) {
                if (!errorYaIntroducido) {
                    String[] temp = string.split(",");
                    if (temp[1].equals(persona.getDocument_Identitat())) {
                        msgError += "- El DNI que ha introduït ja existeix a la base de dades, si us plau introduïu-ne un altre. \n";
                        errorYaIntroducido = true;
                    }
                }
            }
        }
        return msgError;
    }

    public String checkCliente(Date date, String credito) {
        String msgError = "";
        if (!checkDateIsBefore(date)) {
            msgError += "- Verifiqueu que la data de registre del client sigui correcta, ja que supera la data actual.\n";
        }
        if (credito != null) {
            if (!checkTargetaCredito(credito)) {
                msgError += "- Verifiqueu que la targeta de crèdit estigui escrit en un format correcte. Ex (1111 1111 1111 1111 o 111111111111111)\n";
            }
        }
        return msgError;
    }

    public String checkEmpleado(Date date, String salario) {
        String msgError = "";
        if (!checkDateIsBefore(date)) {
            msgError += "- Verifiqueu que la data de contractació de l'empleat sigui correcta, ja que supera la data actual.\n";
        }
        if (!checkStringToInt(salario)) {
            msgError += "- Verifiqueu que el vostre salari siguin només números.";
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

    public boolean checkDateIsBefore(Date date) {
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

    public Metode_Pagament getMetodePagamentFromString(String tipo) {
        switch (tipo) {
            case "Targeta":
                return Metode_Pagament.Targeta;
            case "Efectiu":
                return Metode_Pagament.Efectiu;
            case "Transferencia_Bancaria":
                return Metode_Pagament.Transferencia_Bancaria;
            case "Transferencia_Paypal":
                return Metode_Pagament.Transferencia_Paypal;
            default:
                return null;
        }
    }

    public Tipus_Habitacio getTipusHabitacioFromString(String tipo) {
        switch (tipo) {
            case "Doble":
                return Tipus_Habitacio.Doble;
            case "Familiar":
                return Tipus_Habitacio.Familiar;
            case "Individual":
                return Tipus_Habitacio.Individual;
            default:
                return null;
        }
    }

    public Estat_Habitacio getEstatHabitacioFromString(String tipo) {
        switch (tipo) {
            case "Disponible":
                return Estat_Habitacio.Disponible;
            case "En_Neteja":
                return Estat_Habitacio.En_Neteja;
            case "Ocupada":
                return Estat_Habitacio.Ocupada;
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

    public double[] calcularFactura(int ID_Reserva) {
        Reserva reserva = getReserva(ID_Reserva);
        Habitacio habitacion = getHabitacion(reserva.getID_Habitacio());
        Duration duration = Duration.between(reserva.getData_Inici().toLocalDate().atStartOfDay(), reserva.getData_Fi().toLocalDate().atStartOfDay());
        long diasDiferencia = duration.toDays();
        Double PreuTotalNoIva = null;
        Double IVA = null;
        Double PreuTotal = null;
        double[] calculosFactura = new double[3];
        if (reserva.getTipus_Reserva() == Tipus_Reserva.AD) {
            PreuTotalNoIva = habitacion.getPreu_Nit_AD() * diasDiferencia;
            IVA = PreuTotalNoIva * reserva.getTipus_IVA().getPorIVA() / 100;
            PreuTotal = PreuTotalNoIva + IVA;
        } else if (reserva.getTipus_Reserva() == Tipus_Reserva.MP) {
            PreuTotalNoIva = habitacion.getPreu_Nit_MP() * diasDiferencia;
            IVA = PreuTotalNoIva * reserva.getTipus_IVA().getPorIVA() / 100;
            PreuTotal = PreuTotalNoIva + IVA;
        }
        calculosFactura[0] = PreuTotalNoIva;
        calculosFactura[1] = IVA;
        calculosFactura[2] = PreuTotal;
        return calculosFactura;
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

    public boolean eliminarFactura(int ID_Factura) {
        boolean facturaEliminada = true;
        Connection conectar = new Connexio().connecta();
        String sql = "DELETE FROM FACTURA WHERE ID_Factura = ?";
        try {
            PreparedStatement orden = conectar.prepareStatement(sql);
            orden.setInt(1, ID_Factura);
            orden.executeUpdate();
            return facturaEliminada;
        } catch (SQLException e) {
            System.out.println(e.toString());
            facturaEliminada = false;
            return facturaEliminada;
        } catch (Exception e) {
            System.out.println(e.toString());
            facturaEliminada = false;
            return facturaEliminada;
        }
    }

    public boolean actualizarReserva(int ID_Reserva, double preu_Total) {
        boolean reservaActualizada = false;
        Connection conectar = new Connexio().connecta();
        String sql = "UPDATE RESERVA SET preu_Total_Reserva = ? WHERE ID_Reserva = ?";
        try {
            PreparedStatement orden = conectar.prepareStatement(sql);
            orden.setDouble(1, preu_Total);
            orden.setInt(2, ID_Reserva);
            orden.executeUpdate();
            reservaActualizada = true;
            return reservaActualizada;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return reservaActualizada;
        } catch (Exception e) {
            System.out.println(e.toString());
            return reservaActualizada;
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
        int ID_Client = -1;
        Tipus_Client tipoCliente = null;
        Connection conectar = new Connexio().connecta();
        String sql = "SELECT ID_Client, Tipus_Client FROM CLIENT WHERE ID_Client = (SELECT ID_Persona FROM PERSONA WHERE Document_Identitat = ?)";
        try {
            PreparedStatement orden = conectar.prepareStatement(sql);
            orden.setString(1, document_Identitat);
            ResultSet resultados = orden.executeQuery();
            while (resultados.next()) {
                ID_Client = resultados.getInt(1);
                setIDClienteReserva(ID_Client);
                tipoCliente = getTipoCliente(resultados.getString(2));
                setTipusClienteReserva(tipoCliente);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
            return ID_Client;
        } catch (Exception e) {
            System.out.println(e.toString());
            return ID_Client;
        }
        return ID_Client;
    }

    public int getEmpleatTasca(String document_Identitat) {
        int ID_Empleat = -1;
        Connection conectar = new Connexio().connecta();
        String sql = "SELECT ID_Empleat FROM EMPLEAT WHERE ID_Empleat = (SELECT ID_Persona FROM PERSONA WHERE Document_Identitat = ?)";
        try {
            PreparedStatement orden = conectar.prepareStatement(sql);
            orden.setString(1, document_Identitat);
            ResultSet resultados = orden.executeQuery();
            while (resultados.next()) {
                ID_Empleat = resultados.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
            return ID_Empleat;
        } catch (Exception e) {
            System.out.println(e.toString());
            return ID_Empleat;
        }
        return ID_Empleat;
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

    public Habitacio getHabitacion(int ID_Habitacio) {
        Habitacio habitacio = null;
        Connection conectar = new Connexio().connecta();
        String sql = "SELECT * FROM HABITACIO WHERE ID_Habitacio = ?";
        try {
            PreparedStatement orden = conectar.prepareStatement(sql);
            orden.setInt(1, ID_Habitacio);
            ResultSet resultados = orden.executeQuery();
            while (resultados.next()) {
                habitacio = new Habitacio(resultados.getInt(2), getTipusHabitacioFromString(resultados.getString(3)), resultados.getInt(4), resultados.getDouble(5), resultados.getDouble(6), getEstatHabitacioFromString(resultados.getString(7)), resultados.getString(8));
                habitacio.setID_Habitacio(resultados.getInt(1));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return habitacio;
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

    // Conseguir solo el ID de la persona
    public Boolean checkIfRealitzaAlrExists(int ID_Tasca, int ID_Empleat) {
        Boolean exists = false;
        Connection conectar = new Connexio().connecta();
        String sql = "SELECT ID_Tasca FROM REALITZA WHERE ID_Tasca = ? AND ID_Empleat = ?";
        try {
            PreparedStatement orden = conectar.prepareStatement(sql);
            orden.setInt(1, ID_Tasca);
            orden.setInt(2, ID_Empleat);
            ResultSet resultados = orden.executeQuery();
            while (resultados.next()) {
                exists = true;
            }
        } catch (SQLException e) {
            // Al ser esperado que de error la mayoria de veces, quitamos esto.
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return exists;
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

    public int getIDTasca(Date data) {
        int ID_Tasca = -1;
        Connection conectar = new Connexio().connecta();
        String sql = "SELECT ID_Tasca FROM TASCA WHERE data_Creacio = ? AND ID_Tasca = (SELECT MAX(ID_Tasca) FROM TASCA WHERE data_Creacio = ?)";
        try {
            PreparedStatement orden = conectar.prepareStatement(sql);
            orden.setDate(1, data);
            orden.setDate(2, data);
            ResultSet resultados = orden.executeQuery();
            while (resultados.next()) {
                ID_Tasca = resultados.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return ID_Tasca;
    }

    public String getEstatTasca(int ID_Tasca) {
        String estado = null;
        Connection conectar = new Connexio().connecta();
        String sql = "SELECT estat FROM TASCA WHERE ID_Tasca = ?";
        try {
            PreparedStatement orden = conectar.prepareStatement(sql);
            orden.setInt(1, ID_Tasca);
            ResultSet resultados = orden.executeQuery();
            while (resultados.next()) {
                estado = resultados.getString(1);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return estado;
    }
    
    public String getEstatRealitza(int ID_Tasca, int ID_Empleat) {
        String estadoRealitza = null;
        Connection conectar = new Connexio().connecta();
        String sql = "SELECT estat_Per_Empleat FROM REALITZA WHERE ID_Tasca = ? AND ID_Empleat = ?";
        try {
            PreparedStatement orden = conectar.prepareStatement(sql);
            orden.setInt(1, ID_Tasca);
            orden.setInt(2, ID_Empleat);
            ResultSet resultados = orden.executeQuery();
            while (resultados.next()) {
                estadoRealitza = resultados.getString(1);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return estadoRealitza;
    }
    
    public Boolean changeEstatTasca(int ID_Tasca, String Estat) {
        Boolean estadoCambiado = false;
        Connection conectar = new Connexio().connecta();
        String sql = "UPDATE TASCA SET estat = ? WHERE ID_Tasca = ?";
        try {
            PreparedStatement orden = conectar.prepareStatement(sql);
            orden.setString(1, Estat);
            orden.setInt(2, ID_Tasca);
            orden.executeUpdate();
            estadoCambiado = true;
        } catch (SQLException e) {
            System.out.println(e.toString());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return estadoCambiado;
    }
    
    public Boolean changeEstatRealitza(int ID_Tasca, int ID_Empleat , String Estat) {
        Boolean estadoCambiado = false;
        Connection conectar = new Connexio().connecta();
        String sql = "UPDATE REALITZA SET estat_Per_Empleat = ? WHERE ID_Tasca = ? AND ID_Empleat = ?";
        try {
            PreparedStatement orden = conectar.prepareStatement(sql);
            orden.setString(1, Estat);
            orden.setInt(2, ID_Tasca);
            orden.setInt(3, ID_Empleat);
            orden.executeUpdate();
            estadoCambiado = true;
        } catch (SQLException e) {
            System.out.println(e.toString());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return estadoCambiado;
    }
    
    public Boolean changeFinalizadoRealizado(int ID_Tasca) {
        Boolean estadosCambiados = false;
        Connection conectar = new Connexio().connecta();
        String sql = "UPDATE REALITZA SET estat_Per_Empleat = 'Completada' WHERE ID_Tasca = ?";
        try {
            PreparedStatement orden = conectar.prepareStatement(sql);
            orden.setInt(1, ID_Tasca);
            orden.executeUpdate();
            estadosCambiados = true;
        } catch (SQLException e) {
            System.out.println(e.toString());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return estadosCambiados;
    }

    public Factura getFactura(int ID_Reserva) {
        Factura factura = null;
        Connection conectar = new Connexio().connecta();
        String sql = "SELECT * FROM FACTURA WHERE ID_Reserva = ?";
        try {
            PreparedStatement orden = conectar.prepareStatement(sql);
            orden.setInt(1, ID_Reserva);
            ResultSet resultados = orden.executeQuery();
            while (resultados.next()) {
                factura = new Factura(resultados.getDate(2), getMetodePagamentFromString(resultados.getString(3)), resultados.getDouble(4), resultados.getDouble(5), resultados.getDouble(6), resultados.getInt(7));
                factura.setID_Factura(resultados.getInt(1));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return factura;

    }

    public void getEmpleadosTasca(int ID_Tasca) {
        Connection conectar = new Connexio().connecta();
        String sql = "SELECT nom, cognom, document_Identitat, data_Assignacio FROM EMPLEAT e INNER JOIN PERSONA p ON e.ID_Empleat = p.ID_Persona INNER JOIN REALITZA r ON e.ID_Empleat = r.ID_Empleat WHERE r.ID_Tasca = ?";
        try {
            PreparedStatement orden = conectar.prepareStatement(sql);
            orden.setInt(1, ID_Tasca);
            ResultSet resultados = orden.executeQuery();
            empleadosTarea.clear();
            while (resultados.next()) {
                empleadosTarea.add(resultados.getString(1) + " " + resultados.getString(2) + " | " + resultados.getString(3) + " | " + resultados.getString(4));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        } catch (Exception e) {
            System.out.println(e.toString());
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
            System.out.println(e.toString());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void recargarTareas() {
        Connection conectar = new Connexio().connecta();
        String sql = "SELECT ID_Tasca FROM TASCA";
        try {
            Statement orden = conectar.createStatement();
            ResultSet resultados = orden.executeQuery(sql);
            tareas.clear();
            while (resultados.next()) {
                tareas.add(String.valueOf(resultados.getInt(1)));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void recargarTareasAvanzadas() {
        Connection conectar = new Connexio().connecta();
        String sql = "SELECT ID_Tasca, estat FROM TASCA WHERE estat != 'Completada'";
        try {
            Statement orden = conectar.createStatement();
            ResultSet resultados = orden.executeQuery(sql);
            tareasAvanzadas.clear();
            while (resultados.next()) {
                tareasAvanzadas.add(String.valueOf(resultados.getInt(1)) + " | " + resultados.getString(2));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
