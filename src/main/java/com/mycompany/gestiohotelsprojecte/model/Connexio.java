package com.mycompany.gestiohotelsprojecte.model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author Nicolas Leon Sapoznik Pancani
 */
public class Connexio {
    // Datos de la base de datos como tal.
    private final String URL = "jdbc:mysql://localhost:3306/gestioHotel_bd"; //nom bd
    private final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private final String USER = "usuaridam";
    private final String PASSWD = "usuaridam";   
   
    // Funcion utilizada para conectarse a la bse de datos.
    public Connection connecta() {
        Connection connexio = null;
        try {
            // Cargarmos el driver por aqui.        
            Class.forName(DRIVER); 
            // Y luego realizamos la conexion a la base de datos.
            connexio = DriverManager.getConnection(URL, USER, PASSWD); 
        } catch (SQLException | ClassNotFoundException throwables) {
            System.out.println(throwables.getLocalizedMessage());
        }    
        // Devolvemos la conexion a la base de datos.
        return connexio;
    }
}
