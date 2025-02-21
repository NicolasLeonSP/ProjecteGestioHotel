/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.gestiohotelsprojecte;

import com.mycompany.gestiohotelsprojecte.model.Model;

/**
 * FXML Controller class
 *
 * @author nicol
 */
public class FacturaController {

    private Model model;
    // Seria hacer que solo aparezca una tab, dependiendo de si se ha creado o no la factura.
    public void injecta(Model obj) {
        model = obj;
    }
}
