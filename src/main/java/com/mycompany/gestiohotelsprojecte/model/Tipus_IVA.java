package com.mycompany.gestiohotelsprojecte.model;

/**
 *
 * @author Nicolas Leon Sapoznik Pancani
 */
public enum Tipus_IVA {

    Tipo_Cero(0),
    Reduccio(7),
    General(21);

    private int Por_IVA;

    Tipus_IVA(int num) {
        Por_IVA = num;
    }

    public int getPorIVA() {
        return Por_IVA;
    }

}
