package ua.com.alevel;

import lombok.Cleanup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.interfaces.starter.Starter;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.attribute.UserPrincipalNotFoundException;

public class AppStarter implements Starter {
    private static InputStreamReader inputStreamReader;
    private Logger infoLogger = LoggerFactory.getLogger("info");
    private Logger errorLogger = LoggerFactory.getLogger("error");

    private void initialize() throws UnsupportedEncodingException, UserPrincipalNotFoundException {
        infoLogger.info("start application");
        setShutdownHook();
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

    private void setShutdownHook() {
        Runtime.getRuntime().
                addShutdownHook(new Thread(() -> infoLogger.info("application shutdown"), "Shutdown-thread"));
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
                        
                        ** Залогиньтесь и оставьте свой комментарий **
                        
                """);
        System.out.print("""
                 
                 1) Просмотр всех коментариев.
                        
                
                                 
                Введите номер пункта меню "1-8",
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
