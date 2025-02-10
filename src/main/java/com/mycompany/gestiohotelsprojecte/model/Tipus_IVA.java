/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestiohotelsprojecte.model;

/**
 *
 * @author alumne
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
