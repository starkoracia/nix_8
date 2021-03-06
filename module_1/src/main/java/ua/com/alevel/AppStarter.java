package ua.com.alevel;

import lombok.Cleanup;
import ua.com.alevel.binarytree.BinaryTreeController;
import ua.com.alevel.brackets.BracketsController;
import ua.com.alevel.gameoflive.GameOfLifeController;
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

    private void mainLoopRun(BufferedReader reader) throws IOException {
        while (true) {
            printChooseApp();

            String command = reader.readLine();
            switch (command.toLowerCase()) {
                case "1" -> {
                    processingModuleApp(reader, new UniqueSymbolsController());
                }
                case "2" -> {
                    new KnightMoveController().start(reader);
                }
                case "3" -> {
                    processingModuleApp(reader, new TriangleAreaController());
                }
                case "4" -> {
                    processingModuleApp(reader, new BracketsController());
                }
                case "5" -> {
                     new BinaryTreeController().start(reader);
                }
                case "6" -> {
                    new GameOfLifeController().start(reader);
                }
                case "q", "??" -> {
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
                || command.equalsIgnoreCase("??");
    }

    private static void printRepeatOrExit() {
        System.out.println(("""
                                
                ?????????????? "q" ?????????? ?????????? ???? ????????????????????. 
                "Enter" ?????????? ??????????????????  
                -> """));
    }

    private static void printChooseApp() {
        System.out.print("""
                        
                        ** ???????????? ?????????? 1 **
                        
                 * ???????????????? ???????????? ???????????????????? *
                    
                 1) ?????????? ???????????????????? ????????????????.
                 2) ?????????????? ????????, ?????? ???????? ???? ????????????.
                 3) ?????????????? ????????????????????????.
                   
                 4) ???????????? ???????? ??????!
                 5) ?????????????? ?????????????????? ????????????.
                    
                 6) ???????? ??????????!
                    
                                    
                ?????????????? ?????????? ?????????????? ???????????? "1-6",
                ?????? ???????????? ?????????????? "q"
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
