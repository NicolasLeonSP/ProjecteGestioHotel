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
    private static MenuBarController controladormenu;

    @Override
    public void start(Stage stage) throws IOException {
        model = new Model();
        controladormenu = new MenuBarController();
        controladormenu.injecta(model);
        
        scene = new Scene(loadFXML("MenuBar"));
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        fxmlLoader.setControllerFactory(controllerType -> {
            if (controllerType == MenuBarController.class) {
                return controladormenu;
            }
            return null;
        });
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}
