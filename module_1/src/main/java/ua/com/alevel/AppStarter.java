package ua.com.alevel;

import lombok.Cleanup;
import ua.com.alevel.brackets.BracketsController;
import ua.com.alevel.interfaces.controllers.ConsoleController;
import ua.com.alevel.interfaces.starter.Starter;
import ua.com.alevel.knightmove.KnightMoveController;
import ua.com.alevel.trianglearea.TriangleAreaController;
import ua.com.alevel.uniquesymbols.UniqueSymbolsController;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class AppStarter implements Starter {

    private static InputStreamReader inputStreamReader;

    private static void initialize() throws UnsupportedEncodingException {
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
        @Cleanup BufferedReader reader = new BufferedReader(inputStreamReader);
        mainLoopRun(reader);
    }

    private static void mainLoopRun(BufferedReader reader) throws IOException {
        while (true) {
            printChooseApp();

            String command = reader.readLine();
            switch (command.toLowerCase()) {
                case "1" -> {
                    processingModuleApp(reader, new UniqueSymbolsController());
                }
                case "2" -> {
                    processingModuleApp(reader, new KnightMoveController());
                }
                case "3" -> {
                    processingModuleApp(reader, new TriangleAreaController());
                }
                case "4" -> {
                    processingModuleApp(reader, new BracketsController());
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

    private static void processingModuleApp(BufferedReader reader, ConsoleController appController) throws IOException {
        String command;
        while (true) {
            clearScreen();
            appController.start(reader);
            printRepeatOrExit();
            command = reader.readLine();
            if (isExit(command)) {
                clearScreen();
                break;
            }
        }
    }

    private static boolean isExit(String command) {
        return command.equalsIgnoreCase("q")
                || command.equalsIgnoreCase("й");
    }

    private static void printRepeatOrExit() {
        System.out.println(("""
                                
                Введите "q" чтобы выйти из приложения. 
                "Enter" чтобы повторить  
                -> """));
    }

    private static void printChooseApp() {
        System.out.print("""
                        
                        ** Модуль номер 1 **
                        
                 * Выберите нужное приложение *
                    
                 1) Число уникальных символов.
                 2) Лошадью ходи, век воли не видать.
                 3) Площадь треугольника.
                   
                 4) Скобки наше всё!
                 5) Глубина бинарного дерева.
                    
                 6) Игра Жизни!
                    
                                    
                Введите номер нужного метода "1-6",
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
    }

}
