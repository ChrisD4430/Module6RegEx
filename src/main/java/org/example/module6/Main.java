package org.example.module6;

/**
 * Main entry point for the Registration Form application.
 * @author Christopher D'Aleo
 * @version 1.0
 * @throws Exception if JavaFX fails to launch
 */
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        RegistrationForm form = new RegistrationForm(primaryStage);
        Scene scene = new Scene(form.getView(), 450, 450);

        primaryStage.setTitle("Registration Form");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
