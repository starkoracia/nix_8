package com.example.demofx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class KnightsMoveStarter {

    private static volatile boolean javaFxLaunched = false;

    public static void main(String[] args) {
        myLaunch(KnightsMoveApplication.class);
    }

    public static void myLaunch(Class<? extends Application> applicationClass) {
        if (!javaFxLaunched) { // First time
            Platform.setImplicitExit(false);
            new Thread(() ->
                    Application.launch(applicationClass)
            ).start();
            javaFxLaunched = true;
        } else { // Next times
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
