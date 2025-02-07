package com.mycompany.gestiohotelsprojecte.model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author Nicolas Leon Sapoznik Pancani
 */
public class Connexio {
    private final String URL = "jdbc:mysql://localhost:3306/gestioHotel_bd"; //nom bd
    private final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private final String USER = "usuaridam";
    private final String PASSWD = "usuaridam";   
   

    public Connection connecta() {
        Connection connexio = null;
        try {
            //Carreguem el driver          
            Class.forName(DRIVER); 
            connexio = DriverManager.getConnection(URL, USER, PASSWD); 
        } catch (SQLException | ClassNotFoundException throwables) {
            System.out.println(throwables.getLocalizedMessage());
        }    
        return connexio;
    }
}
