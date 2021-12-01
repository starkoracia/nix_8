package ua.com.alevel;

import lombok.Cleanup;
import ua.com.alevel.interfaces.starter.RunnableMethodTest;
import ua.com.alevel.interfaces.starter.Starter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.attribute.UserPrincipalNotFoundException;

public class AppStarter implements Starter {
    private static InputStreamReader inputStreamReader;
    private BufferedReader reader;

    private void initialize() throws UnsupportedEncodingException, UserPrincipalNotFoundException {
        String charsetName;
        Console console = System.console();
        if (console != null && System.getProperty("os.name").contains("Windows")) {
            charsetName = console.charset().name();
            System.setOut(new PrintStream(System.out, true, charsetName));
            inputStreamReader = new InputStreamReader(System.in, charsetName);
        } else {
            System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
            inputStreamReader = new InputStreamReader(System.in, StandardCharsets.UTF_8);
        }
    }

    @Override
    public void start() throws IOException {
        initialize();
        @Cleanup BufferedReader initReader = new BufferedReader(inputStreamReader);
        mainLoopRun(initReader);
    }

    private void mainLoopRun(BufferedReader initReader) throws IOException {
        this.reader = initReader;
        while (true) {
            printChooseApp();

            String command = reader.readLine();
            switch (command.toLowerCase()) {
                case "1" -> {

                }
                case "q", "й" -> {
                    System.exit(0);
                }
                default -> {
                    clearScreen();
                }
            }
        }
    }

    private void processingMethod(RunnableMethodTest moduleApp) throws IOException {
        String command;
        while (true) {
            clearScreen();
            moduleApp.start();
            printRepeatOrExit();
            command = reader.readLine();
            if (isExit(command)) {
                clearScreen();
                break;
            }
        }
    }

    private void enterToContinue() throws IOException {
        System.out.println("\n Нажмите enter для продолжения");
        reader.readLine();
    }


    private boolean isExit(String command) {
        return command.equalsIgnoreCase("q")
                || command.equalsIgnoreCase("й");
    }

    private void printRepeatOrExit() {
        System.out.println(("""
                                
                Введите "q" чтобы выйти из приложения. 
                "Enter" чтобы повторить  
                -> """));
    }

    private void printChooseApp() {
        System.out.print("""
                        
                        ** Выберите метод **
                """);
        System.out.print("""
                 
                 1) 
                            
                Введите номер пункта меню "1-16",
                Для выхода введите "q"
                ->\040""");
    }

    public static void clearScreen() {
        //Clears Screen in java
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException ignored) {
        }
        System.out.println();
    }

}
