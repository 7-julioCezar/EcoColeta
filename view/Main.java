package ecocoleta.view;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        LoginView login = new LoginView();
        login.show(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
