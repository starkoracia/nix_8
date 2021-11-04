package ua.com.alevel;

import lombok.Cleanup;
import ua.com.alevel.endsoflessons.EndsOfLessons;
import ua.com.alevel.integerssumsromstring.IntegersSumFromString;
import ua.com.alevel.interfaces.RunnableModuleApp;
import ua.com.alevel.сountingandsortingletters.CountingAndSortingLetters;

import java.io.*;
import java.nio.charset.StandardCharsets;


public class HomeworkStarter {

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

    public static void run() throws IOException {
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
                    processingModuleApp(reader, new IntegersSumFromString());
                }
                case "2" -> {
                    processingModuleApp(reader, new CountingAndSortingLetters());
                }
                case "3" -> {
                    processingModuleApp(reader, new EndsOfLessons());
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

    private static void processingModuleApp(BufferedReader reader, RunnableModuleApp moduleApp) throws IOException {
        String command;
        while (true) {
            clearScreen();
            moduleApp.start(reader);
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
                        
                        **Выберите нужное приложение в модуле**
                        
                 1) "Сумма чисел в строке" - нахождение суммы целых, положительных и отрицательных
                                            чисел в введенной строке.
                        
                 2) "Буквенная сортировка" - подсчет и сортировка буквенных символов латиницы и кирилицы
                                            в введенной строке.
                                    
                 3) "Конец уроков" - подсчет времени окончания введенного номера урока с учетом начала в 9:00.
                    
                                    
                Введите номер нужного приложения "1-3",
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
