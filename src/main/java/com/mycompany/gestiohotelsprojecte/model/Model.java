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

    // Variables de model
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

    // Constructor de model
    public Model() {
    }

    // Inicializador de modelo, principalmente para las ObservableList
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

    // Getters de modelo.
    public String getIDTascaORealitzaSeleccionada() {
        return IDTascaORealitzaSeleccionada;
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

    // Setters de modelo
    public void setTipusClienteReserva(Tipus_Client TipusClienteReserva) {
        this.TipusClienteReserva = TipusClienteReserva;
    }

    public void setIDClienteReserva(int IDClienteReserva) {
        this.IDClienteReserva = IDClienteReserva;
    }

    public void setIDTascaORealitzaSeleccionada(String IDTascaORealitzaSeleccionada) {
        this.IDTascaORealitzaSeleccionada = IDTascaORealitzaSeleccionada;
    }

    // Esta funcion se encarga de hacer los checks a una persona.
    public String checkPersona(Persona persona) {
        String msgError = "";
        // Verificar fecha si es correcta
        if (!checkDateIsBefore(persona.getData_Naixement())) {
            msgError += "- Verifiqueu que la data de naixement sigui correcta, ja que supera la data actual.\n";
            // Veremos si la edad es superior a 16.
        }
        if (persona.getData_Naixement().after(Date.valueOf(LocalDate.now().minusYears(18)))) {
            msgError += "- Has de ser major de 18 anys per a inscribirte en el hotel, cambia la data de naixement per ser major de 18.\n";
        }
        // Verificar telefono si es correcto
        if (!persona.checkTelefono()) {
            msgError += "- Verifiqueu que el telèfon estigui escrit correctament.\n";
        }
        // Verificar email si es correcto
        if (!persona.checkEmail()) {
            msgError += "- Verifiqueu que el correu electrònic està escrit de forma correcta.\n";
        } else {
            // Comprobar si el correo ya existe previamente.
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
        // Verificar que el DNI este correcto
        String errorDNI = persona.checkDNI();
        if (!errorDNI.equals("0")) {
            // Si el DNI no tiene la longitud correcta...
            if (errorDNI.equals("1")) {
                msgError += "- Verifiqueu que el DNI conté 9 caràcters, sent 8 números i una lletra.\n";
            }
            // Si el DNI no tiene la letra correcta...
            if (errorDNI.charAt(0) == '2') {
                msgError += "- Verifiqueu que la lletra del DNI sigui correcta. La lletra retornada pel generador és " + errorDNI.charAt(1) + " .\n";
            }
        } else {
            // Comprobar si el documento de identidad ya existe.
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
        // Y luego, devolvemos los mensaje de error.
        return msgError;
    }

    // Funcion para los checks del cliente.
    public String checkCliente(Date date, String credito) {
        String msgError = "";
        // Comprobaremos que la fecha introducida sea mayor a la actual.
        if (!checkDateIsBefore(date)) {
            msgError += "- Verifiqueu que la data de registre del client sigui correcta, ja que supera la data actual.\n";
        }
        // Si la tarjeta no es nula...
        if (credito != null) {
            // Veremos si esta bien escrita.
            if (!checkTargetaCredito(credito)) {
                msgError += "- Verifiqueu que la targeta de crèdit estigui escrit en un format correcte. Ex (1111 1111 1111 1111 o 111111111111111)\n";
            }
        }
        // Devolvemos el resultado.
        return msgError;
    }

    // Funcion para los checks de Empleado
    public String checkEmpleado(Date dateContratacion, String salario) {
        String msgError = "";
        // Veremos si la fecha de contratacion es superior a la actual.
        if (!checkDateIsBefore(dateContratacion)) {
            msgError += "- Verifiqueu que la data de contractació de l'empleat sigui correcta, ja que supera la data actual.\n";
        }
        // Veremos si el salario son numeros.
        if (!checkStringToInt(salario)) {
            msgError += "- Verifiqueu que el vostre salari siguin només números.";
        }
        // Devolvemos el resultado..
        return msgError;
    }

    // Esta funcion se encarga de comprobar que la tarjeta de credito este bien formatada
    public boolean checkTargetaCredito(String targeta) {
        // Definimos el patron
        Pattern regexCredito = Pattern.compile("([0-9]{4}\\s?){4}", Pattern.CASE_INSENSITIVE);
        // Lo comparamos
        Matcher creditoCheck = regexCredito.matcher(targeta);
        // Y vemos si es correcto o no.
        if (creditoCheck.find()) {
            return true;
        } else {
            return false;
        }
    }

    // Esta funcion comprueba si la fecha es antes de la fecha actual.
    public boolean checkDateIsBefore(Date date) {
        if (date.before(Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()))) {
            return true;
        } else {
            return false;
        }
    }

    // Esta funcion comprueba si un string esta formado por solo numeros o no.
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

    // Esta funcion pasa un LocalDate a java.sql.Date
    public java.sql.Date LocalDateToSqlDate(LocalDate fecha) {
        try {
            Date date = new java.sql.Date(Date.from(fecha.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()).getTime());
            return date;
        } catch (Exception e) {
            return null;
        }
    }

    // Esta funcion pilla un atributo del enumerator tipoCliente segun el string que se le pase.
    public Tipus_Client getTipoCliente(String tipoCliente) {
        Tipus_Client cliente = null;
        for (Tipus_Client e : Tipus_Client.values()) {
            if (e.toString().equals(tipoCliente)) {
                cliente = e;
            }
        }
        return cliente;
    }

    // Esta funcion pilla un atributo del enumerator IVA segun el string que se le pase.
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

    // Esta funcion elige un IVA segun el numero que se le pase.
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

    // Esta funcion pilla un atributo del enumerator TipoReserva segun el string que se le pase.
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

    // Esta funcion pilla un atributo del enumerator MetodePagament segun el string que se le pase.
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

    // Esta funcion pilla un atributo del enumerator TipusHabitacio segun el string que se le pase.
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

    // Esta funcion pilla un atributo del enumerator EstatHabitacio segun el string que se le pase.
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

    // Esta funcion devuelve la posicion de un string, si se encuentra en el observableList que se le pase.
    public int getIDFromObservableList(String buscar, ObservableList ol) {
        int ID_Conseguida = -1;
        for (int i = 0; i < ol.size(); i++) {
            if (ol.get(i).toString().equals(buscar)) {
                ID_Conseguida = i;
            }
        }
        return ID_Conseguida;
    }

    // Esta funcion retorna un campo del mensaje de error de SQL que se le pase.
    public String retornarMensajeCorrectoReserva(String msgError) {
        String[] msgErrorPartido = msgError.split("`");
        String[] campoErrorPartido = msgErrorPartido[1].split("\\.");
        return campoErrorPartido[1];
    }

    // Esta funcion se encarga de calcular la fechaYaAsignada, la cual encontraremos con el ID de Reserva.
    public double[] calcularFactura(int ID_Reserva) {
        // Agarremos los datos que necesitamos antes de comenzar, de entre ellos, la reserva, habitacion, duracion de la estadia e inicializar variables.
        Reserva reserva = getReserva(ID_Reserva);
        Habitacio habitacion = getHabitacion(reserva.getID_Habitacio());
        Duration duration = Duration.between(reserva.getData_Inici().toLocalDate().atStartOfDay(), reserva.getData_Fi().toLocalDate().atStartOfDay());
        long diasDiferencia = duration.toDays();
        Double PreuTotalNoIva = null;
        Double IVA = null;
        Double PreuTotal = null;
        double[] calculosFactura = new double[3];
        // Y luego veremos, si el tipo de reserva es AD, lo calcularemos con el precio de AD
        if (reserva.getTipus_Reserva() == Tipus_Reserva.AD) {
            PreuTotalNoIva = habitacion.getPreu_Nit_AD() * diasDiferencia;
            IVA = PreuTotalNoIva * reserva.getTipus_IVA().getPorIVA() / 100;
            PreuTotal = PreuTotalNoIva + IVA;
        } // Si no, con el precio de MP
        else if (reserva.getTipus_Reserva() == Tipus_Reserva.MP) {
            PreuTotalNoIva = habitacion.getPreu_Nit_MP() * diasDiferencia;
            IVA = PreuTotalNoIva * reserva.getTipus_IVA().getPorIVA() / 100;
            PreuTotal = PreuTotalNoIva + IVA;
        }
        // Una vez terminado los calculos, los añadimos al array y devolvemos el resultado.
        calculosFactura[0] = PreuTotalNoIva;
        calculosFactura[1] = IVA;
        calculosFactura[2] = PreuTotal;
        return calculosFactura;
    }

    // Esta funcion SQL se encarga de eliminar una reserva de la base de datos, segun el ID que se le pase. Ir a la linea 1004 para mas info
    public boolean eliminarReserva(int ID_Reserva) {
        boolean ReservaEliminada = true;
        Connection conectar = new Connexio().connecta();
        String sql = "DELETE FROM RESERVA WHERE ID_Reserva = ?";
        try {
            PreparedStatement orden = conectar.prepareStatement(sql);
            orden.setInt(1, ID_Reserva);
            orden.executeUpdate();
            orden.close();
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

    // Esta funcion SQL se encarga de eliminar una fechaYaAsignada de la base de datos, segun el ID que se le pase. Ir a la linea 1004 para mas info
    public boolean eliminarFactura(int ID_Factura) {
        boolean facturaEliminada = true;
        Connection conectar = new Connexio().connecta();
        String sql = "DELETE FROM FACTURA WHERE ID_Factura = ?";
        try {
            PreparedStatement orden = conectar.prepareStatement(sql);
            orden.setInt(1, ID_Factura);
            orden.executeUpdate();
            orden.close();
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

    // Esta funcion SQL se encarga de editar una reserva de la base de datos, añadiendole el precio total que se le pase, segun el ID que se le pase. Ir a la linea 1004 para mas info
    public boolean actualizarReserva(int ID_Reserva, double preu_Total) {
        boolean reservaActualizada = false;
        Connection conectar = new Connexio().connecta();
        String sql = "UPDATE RESERVA SET preu_Total_Reserva = ? WHERE ID_Reserva = ?";
        try {
            PreparedStatement orden = conectar.prepareStatement(sql);
            orden.setDouble(1, preu_Total);
            orden.setInt(2, ID_Reserva);
            orden.executeUpdate();
            orden.close();
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

    // Esta funcion SQL se encarga de conseguir el ID de Persona de la base de datos, segun el Documento de Identidad que se le pase. Ir a la linea 1004 para mas info
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
            orden.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
            return 0;
        } catch (Exception e) {
            System.out.println(e.toString());
            return 0;
        }
        return ID_Persona;
    }

    // Esta funcion SQL se encarga de conseguir una Persona de la base de datos, segun el Documento de Identidad que se le pase. Ir a la linea 1004 para mas info
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
            orden.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
            return ID_Client;
        } catch (Exception e) {
            System.out.println(e.toString());
            return ID_Client;
        }
        return ID_Client;
    }

    // Esta funcion SQL se encarga de conseguir el ID de Empleado de la base de datos, segun el Documento de Identidad que se le pase. Ir a la linea 1004 para mas info
    public int getIDEmpleatTasca(String document_Identitat) {
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
            orden.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
            return ID_Empleat;
        } catch (Exception e) {
            System.out.println(e.toString());
            return ID_Empleat;
        }
        return ID_Empleat;
    }

    // Esta funcion SQL se encarga de conseguir el nombre del empleado, segun el ID que se le pase. Ir a la linea 1004 para mas info
    public String getNomEmpleat(int ID_Empleat) {
        String nomEmpleat = null;
        Connection conectar = new Connexio().connecta();
        String sql = "SELECT nom, cognom FROM EMPLEAT e INNER JOIN PERSONA p ON e.ID_Empleat = p.ID_Persona WHERE ID_Empleat = ?";
        try {
            PreparedStatement orden = conectar.prepareStatement(sql);
            orden.setInt(1, ID_Empleat);
            ResultSet resultados = orden.executeQuery();
            while (resultados.next()) {
                nomEmpleat = resultados.getString(1) + " " + resultados.getString(2);
            }
            orden.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return nomEmpleat;
    }

    // Esta funcion SQL se encarga de conseguir el ID de Habitacion de la base de datos, segun el Numero de Habitacion que se le pase. Ir a la linea 1004 para mas info
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
            orden.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return ID_Habitacio;
    }

    // Esta funcion SQL se encarga de conseguir una Habitacion de la base de datos, segun el ID de Habitacion que se le pase. Ir a la linea 1004 para mas info
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
            orden.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return habitacio;
    }

    // Esta funcion SQL se encarga de conseguir todos los Email y Documentos de Identidad que hayan en Persona. Ir a la linea 1004 para mas info
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
            orden.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
            return null;
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
        return emailYDocIde;
    }

    // Esta funcion SQL se encarga de ver si la asignacion que se quiere hacer existe. Ir a la linea 1004 para mas info
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
            orden.close();
        } catch (SQLException e) {
            // Al ser esperado que de error la mayoria de veces, quitamos esto.
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return exists;
    }

    // Esta funcion SQL se encarga de conseguir una reserva segun el ID que le pases.. Ir a la linea 1004 para mas info
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
            orden.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return reserva;
    }

    // Esta funcion SQL de añadir las reservas de un cliente en reservas. El cliente se busca cdon el documento de identidad. Ir a la linea 1004 para mas info
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
            orden.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    // Esta funcion SQL se encarga de conseguir el numero de la habitacion segun el ID de la Habitacion. Ir a la linea 1004 para mas info
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
            orden.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return Num_Habitacion;
    }

    // Esta funcion SQL se encarga de conseguir la tarea recien creada, o la mas nueva, en la fecha que se le pase. Ir a la linea 1004 para mas info
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
            orden.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return ID_Tasca;
    }

    // Esta funcion SQL se encarga de conseguir el estado de una tarea segun el ID de la tarea. Ir a la linea 1004 para mas info
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
            orden.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return estado;
    }

    // Esta funcion SQL se encarga de conseguir el estado de una asignacion. Ir a la linea 1004 para mas info
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
            orden.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return estadoRealitza;
    }

    // Esta funcion SQL se encarga de conseguir el estado de una asignacion. Ir a la linea 909 para mas info
    public Boolean comprobarSiCompletadoRealitza(int ID_Tasca) {
        Boolean estadoRealitza = true;
        Connection conectar = new Connexio().connecta();
        String sql = "SELECT estat_Per_Empleat FROM REALITZA WHERE ID_Tasca = ?";
        try {
            PreparedStatement orden = conectar.prepareStatement(sql);
            orden.setInt(1, ID_Tasca);
            ResultSet resultados = orden.executeQuery();
            while (resultados.next()) {
                if (!resultados.getString(1).equals("Completada")) {
                    estadoRealitza = false;
                }
            }
            orden.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return estadoRealitza;
    }

    // Esta funcion SQL se encarga de cambiar el estado de una tarea al estado que se le ponga en la funcion, seleccionando la tarea segun el ID_Tasca. Ir a la linea 909 para mas info
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
            orden.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return estadoCambiado;
    }

    // Esta funcion SQL se encarga de cambiar el estado de una asignacion a una tarea. Ir a la linea 1004 para mas info
    public Boolean changeEstatRealitza(int ID_Tasca, int ID_Empleat, String Estat) {
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
            orden.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return estadoCambiado;
    }

    // Esta funcion SQL se encarga de poner completado a todas las subtareas del ID_Tarea que se le especifique. Ir a la linea 1004 para mas info
    public Boolean changeFinalizadoRealizado(int ID_Tasca) {
        Boolean estadosCambiados = false;
        Connection conectar = new Connexio().connecta();
        String sql = "UPDATE REALITZA SET estat_Per_Empleat = 'Completada' WHERE ID_Tasca = ?";
        try {
            PreparedStatement orden = conectar.prepareStatement(sql);
            orden.setInt(1, ID_Tasca);
            orden.executeUpdate();
            estadosCambiados = true;
            orden.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return estadosCambiados;
    }

    // Esta funcion SQL se encarga de conseguir la fechaYaAsignada de una reserva, segun el ID_Reserva que se le pase. Ir a la linea 1004 para mas info
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
            orden.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return factura;
    }
    
    // Esta funcion SQL se encarga de conseguir todas las fechas de inicio y fin que esten ligadas a una habitacion y reserva, y compararlas con una fecha que se le pase.
    public boolean fechaYaAsignada(Date fechaInicio, Date fechaFin, int ID_Habitacion){
        Boolean fechaYaAsignada = false;
        Connection conectar = new Connexio().connecta();
        String sql = "SELECT DISTINCT data_inici, data_fi FROM RESERVA where ID_Habitacio = ? && data_inici > ?";
        try {
            PreparedStatement orden = conectar.prepareStatement(sql);
            orden.setInt(1, ID_Habitacion);
            orden.setDate(2, LocalDateToSqlDate(LocalDate.now()));
            ResultSet resultados = orden.executeQuery();
            while (resultados.next() && fechaYaAsignada == false) {
                if (!(fechaInicio.after(resultados.getDate(2)) || fechaFin.before(resultados.getDate(1)))) {
                    fechaYaAsignada = true;
                }
            }
            orden.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return fechaYaAsignada;
    }
    
    // Esta funcion SQL se encarga de conseguir todas las fechas de inicio y fin que esten ligadas a una habitacion y reserva, y compararlas con una fecha que se le pase. Lo unico diferente con la anterior es que supone que esto se hace sobre una reserva ya existente, entonces, esa fecha no la trae.
    public boolean fechaYaAsignadaYExisteReserva(Date fechaInicio, Date fechaFin, int ID_Habitacion, int ID_Reserva){
        Boolean fechaYaAsignada = false;
        Connection conectar = new Connexio().connecta();
        String sql = "SELECT DISTINCT data_inici, data_fi FROM RESERVA where ID_Habitacio = ? && data_inici > ? && ID_Reserva != ?";
        try {
            PreparedStatement orden = conectar.prepareStatement(sql);
            orden.setInt(1, ID_Habitacion);
            orden.setDate(2, LocalDateToSqlDate(LocalDate.now()));
            orden.setInt(3, ID_Reserva);
            ResultSet resultados = orden.executeQuery();
            while (resultados.next() && fechaYaAsignada == false) {
                if (!(fechaInicio.after(resultados.getDate(2)) || fechaFin.before(resultados.getDate(1)))) {
                    fechaYaAsignada = true;
                }
            }
            orden.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return fechaYaAsignada;
    }

    // Esta funcion SQL se encarga de conseguir la asignacion de una tarea, de una forma que los datos sean leibles. Todo segun el ID_Tarea. Ir a la linea 1004 para mas info
    public void getEmpleadosTasca(int ID_Tasca) {
        Connection conectar = new Connexio().connecta();
        String sql = "SELECT nom, cognom, document_Identitat, data_Assignacio FROM EMPLEAT e INNER JOIN PERSONA p ON e.ID_Empleat = p.ID_Persona INNER JOIN REALITZA r ON e.ID_Empleat = r.ID_Empleat WHERE r.ID_Tasca = ? && estat_per_empleat != 'Completada'";
        try {
            PreparedStatement orden = conectar.prepareStatement(sql);
            orden.setInt(1, ID_Tasca);
            ResultSet resultados = orden.executeQuery();
            empleadosTarea.clear();
            while (resultados.next()) {
                empleadosTarea.add(resultados.getString(1) + " " + resultados.getString(2) + " | " + resultados.getString(3) + " | " + resultados.getString(4));
            }
            orden.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    // Esta funcion SQL se encarga de recargar las habitaciones disponibles en la lista de habitaciones. Ir a la linea 1004 para mas info
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
            orden.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    // Esta funcion SQL se encarga de recargar las tareas en la lista de tareas. Ir a la linea 1004 para mas info
    public void recargarTareas() {
        Connection conectar = new Connexio().connecta();
        String sql = "SELECT ID_Tasca FROM TASCA WHERE estat != 'Completada'";
        try {
            Statement orden = conectar.createStatement();
            ResultSet resultados = orden.executeQuery(sql);
            tareas.clear();
            while (resultados.next()) {
                tareas.add(String.valueOf(resultados.getInt(1)));
            }
            orden.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    // Esta funcion SQL se encarga de recargar las tareas, pero con mas informacion, ademas de filtrando las que esten completadas. Ir a la linea 1004 para mas info
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
            orden.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}

// Para evitar repeticion, comentare el estilo basico de una funcion SQL, y arriba de cada funcion solo pondre lo que hace.
//        # Aqui se introduce la variable que retornaremos, sea un booleano o el resultado como tal de la busqueda.
//        variable nombreVariable = otraVariable;
//        # Debajo, se define la conexion con la base de datos y la sentencia SQL
//        Connection conectar = new Connexio().connecta();
//        String sql = "(Sentencia SQL)";
//        try {
//        # Ahora, aqui dentro, pueden haber distintas diferencias. El primer formato es con PreparedStatement, que es cuando se requiere introducir cosas extra a la sentencia SQL
//        PreparedStatement orden = conectar.prepareStatement(sql);
//        orden.setInt(1, ID_Reserva);
//        # O si no se require nada extra, solo se pondra esto.
//        Statement orden = conectar.createStatement();
//        # Ahora, hay dos formas de ejecutar el SQL, la primera es ejecutandolo y ya. Esto solo seria para cosas como eliminar o editar.
//        orden.executeUpdate();
//        # La segunda es cuando se obtienen resultados y se quieren introducir en una variable, que seria con lo siguiente:
//        ResultSet resultados = orden.executeQuery();
//        while (resultados.next()) {
//              nombreVariable = resultados.getX(1) [X siendo replazado por el tipo de dato que obtiene]
//        }
//            return nombreVariable;
//        } 
//        # Ahora, si por algun casual fallara o espero que falle, agarraria la excepcion con esto y retornaria el resultado que quisiese.
//        catch (SQLException e) {
//            System.out.println(e.toString());
//            ReservaEliminada = false;
//            return ReservaEliminada;
//        }  
//        # Y esto es un catch de excepciones extras, por si las dudas.
//        catch (Exception e) {
//            System.out.println(e.toString());
//            ReservaEliminada = false;
//            return ReservaEliminada;
//        }
// 
//      # Y esto seria el contenido de una funcion SQL normal. Indicare que con funciones SQL en las que lo sean.

