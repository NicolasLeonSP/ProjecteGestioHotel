package com.mycompany.gestiohotelsprojecte;

import com.mycompany.gestiohotelsprojecte.model.Model;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {
    
    private static Scene scene;
    private Model model;
    private static PrimaryController controlador;
    private static SecondaryController controlador2;

    @Override
    public void start(Stage stage) throws IOException {
        model = new Model();
        controlador = new PrimaryController();
        controlador2 = new SecondaryController();
        controlador.injecta(model);
        controlador2.injecta(model);
        scene = new Scene(loadFXML("primary"));
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        fxmlLoader.setControllerFactory(controllerType -> {
           if (controllerType==PrimaryController.class){
               return controlador;
           }
            if (controllerType==SecondaryController.class){
               return controlador2;
           }
            return null;
        });
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}