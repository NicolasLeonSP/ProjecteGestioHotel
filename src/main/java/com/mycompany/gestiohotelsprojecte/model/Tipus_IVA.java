package com.mycompany.gestiohotelsprojecte.model;

/**
 *
 * @author Nicolas Leon Sapoznik Pancani
 */

// Enumeracion que representa los tipos de IVA que puede haber.
public enum Tipus_IVA {
    Tipo_Cero(0),
    Reduccio(7),
    General(21);
    private int Por_IVA;

    Tipus_IVA(int num) {
        Por_IVA = num;
    }
    // Funcion que devuelve el numero relacionado con el tipo de IVA
    public int getPorIVA() {
        return Por_IVA;
    }

}
