package com.mycompany.gestiohotelsprojecte.model;

/**
 *
 * @author Nicolas Leon Sapoznik Pancani
 */
enum Tipus_IVA {

    Tipo_Cero(0),
    Reduccio(7),
    General(21);

    private int Por_IVA;

    Tipus_IVA(int num) {
        Por_IVA = num;
    }

    public double getPorIVA() {
        return Por_IVA;
    }

}
