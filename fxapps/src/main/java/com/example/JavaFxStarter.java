package com.example;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class JavaFxStarter {
    private static volatile boolean javaFxLaunched = false;

    public static void fxStart(Class<? extends Application> applicationClass) {
        if (!javaFxLaunched) {
            Platform.setImplicitExit(false);
            new Thread(() ->
                    Application.launch(applicationClass)
            ).start();
            javaFxLaunched = true;
        } else {
            Platform.runLater(() -> {
                try {
                    Application application = applicationClass.getConstructor().newInstance();
                    Stage primaryStage = new Stage();
                    application.start(primaryStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
