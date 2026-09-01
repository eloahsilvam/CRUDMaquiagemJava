package com.template.main;

import com.template.controller.MainController;
import com.template.validator.IMaquiagemValidator;
import com.template.validator.MaquiagemValidator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        // Instancia o validador usando a interface (polimorfismo)
        IMaquiagemValidator mValidador = new MaquiagemValidator();

        // Carrega o arquivo FXML da interface gráfica
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/com/template/main.fxml"));

        // Injeta a dependência do validador na controller via construtor
        loader.setControllerFactory(controllerClass -> {
            if (controllerClass == MainController.class) {
                return new MainController(mValidador);
            }
            try {
                return controllerClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        // Monta e exibe a janela no tamanho dado
        Parent root = loader.load();
        Scene scene = new Scene(root, 700, 500);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        // Inicializa a aplicação JavaFX
        launch(args);
    }
}