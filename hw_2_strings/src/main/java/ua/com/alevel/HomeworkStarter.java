package ua.com.alevel;

import lombok.Cleanup;

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

                }
                case "2" -> {

                }
                case "3" -> {

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

    private static void processingModuleApp(BufferedReader reader) throws IOException {
        String command;
        while (true) {
            clearScreen();

//            ====>

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
                        
                        **Выберите нужный вам метод для теста**
                        
                 1) Стандарный
                    <- Принимает строку.
                    -> Возвращает перевернутую строку.
                        
                 2) Стандартный (Пословный)
                    <- Принимает строку.
                    -> Возвращает строку с перевернутыми словами, без смещения слов, и 
                       с сохранением всех пробелов.
                                    
                 3) По подстроке
                    <- Принимает две строки. 
                        1-я исходная строка которую хотите перевернуть,
                        2-я подстрока
                    -> Возвращает, при условии наличия совпадений, содержания подстроки 
                        в исходной, перевернет все участки вхождения подстроки 
                        не затронув остальной порядок, иначе вернет изначальную строку.
                    
                 4) По подстроке (Пословный)
                    <- Принимает две строки. 
                        1-я исходная строка которую хотите перевернуть,
                        2-я подстрока
                    -> Возвращает то же, что и 3-й(По подстроке), с пословным переворотом.
                    
                 5) По индексу
                    <- Принимает строку и два индекска(целых числа)
                    -> Возвращает   
                                    
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
