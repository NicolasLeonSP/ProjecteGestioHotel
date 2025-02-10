package com.mycompany.gestiohotelsprojecte.model;

import java.sql.Date;

/**
 *
 * @author Nicolas Leon Sapoznik Pancani
 */
public class Reserva {
    enum Tipus_Reserva {
        AD,
        MP
    }
    private int ID_Reserva;
    private Date data_Reserva;
    private Date data_Inici;
    private Date data_Fi;
    private Tipus_Reserva tipus_Reserva;
    private Tipus_IVA tipus_IVA;
    
}
