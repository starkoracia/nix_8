package ua.com.alevel;

import lombok.Cleanup;
import ua.com.alevel.interfaces.RunnableMethodTest;
import ua.com.alevel.starters.IndexReverse;
import ua.com.alevel.starters.MainReverse;
import ua.com.alevel.starters.SubstringReverse;

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
                    processingModuleApp(reader, new MainReverse(), false);
                }
                case "2" -> {
                    processingModuleApp(reader, new MainReverse(), true);
                }
                case "3" -> {
                    processingModuleApp(reader, new SubstringReverse(), false);
                }
                case "4" -> {
                    processingModuleApp(reader, new SubstringReverse(), true);
                }
                case "5" -> {
                    processingModuleApp(reader, new IndexReverse(), false);
                }
                case "6" -> {
                    processingModuleApp(reader, new IndexReverse(), true);
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

    private static void processingModuleApp(BufferedReader reader, RunnableMethodTest moduleApp, boolean isWordsReverse) throws IOException {
        String command;
        while (true) {
            clearScreen();
            moduleApp.start(reader, isWordsReverse);
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
                    <- Принимает строку и два индекска(целых числа) подстроки.
                    -> Возвращает строку с перевернутой подстрокой в указанном диапозоне.
                       - Оба индекса, могут быть как начальным так и конечным, в зависимости
                         от того, который больше.
                       - При указании индекса, выходящего за пределы строки, он будет приравнен
                         к ближайщему крайнему индексу строки.  
                         
                 6) По индексу (Пословный)
                    <- Принимает строку, два индекска(целых числа) подстроки.
                    -> Возвращает то же, что и 5-й(По индексу), с пословным переворотом.
                    
                                    
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
