package ua.com.alevel;

import lombok.Cleanup;
import ua.com.alevel.interfaces.starter.RunnableMethodTest;
import ua.com.alevel.interfaces.starter.Starter;
import ua.com.alevel.util.DateT;
import ua.com.alevel.util.DateTHelper;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AppStarter implements Starter {
    private static InputStreamReader inputStreamReader;
    private BufferedReader reader;
    private String datePattern = "dd/mm/yyyy 00:00";

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
                    inputDatePattern();
                }
                case "2" -> {
                    if (this.datePattern != null) {
                        dateDifference();
                    } else {
                        System.out.println("\nВведите шаблон ввода даты");
                        enterToContinue();
                    }
                }
                case "3" -> {
                    addToDate();
                }
                case "4" -> {
                    compareDate();
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

    private void compareDate() throws IOException {
        List<DateT> dateTList = new ArrayList<>();
        while (true) {
            System.out.print("""
                     
                     Ввести дату -> Enter
                      
                        Показать даты: 
                     по возрастанию введите -> "s", 
                     по убыванию введите -> "ss".
                    
                    Для выхода введите "q"
                    ->\040""");
            String command = reader.readLine();
            switch (command.toLowerCase()) {
                case "s" -> {
                    dateTList.stream().sorted(Comparator.naturalOrder()).forEach(System.out::println);
                    enterToContinue();
                }
                case "ss" -> {
                    dateTList.stream().sorted(Comparator.reverseOrder()).forEach(System.out::println);
                    enterToContinue();
                }
                case "q", "й" -> {
                    return;
                }
                default -> {
                    DateT dateFromConsole = getDateFromConsole();
                    if (dateFromConsole != null) {
                        dateTList.add(dateFromConsole);
                    }
                }
            }
        }
    }

    private void addToDate() throws IOException {
        DateT dateFromConsole = getDateFromConsole();
        if (dateFromConsole == null) {
            return;
        }
        while (true) {
            System.out.print("""
                     
                     **      Выберите что хотите прибавить, 
                        чтобы вычесть укажите отрицательное число **
                     
                     1) Года
                     2) Дни
                     3) Часы
                     4) Минуты
                     5) Секунды
                     6) Миллисекунды
                                  
                    Введите номер пункта меню "1-6",
                    Для выхода введите "q"
                    ->\040""");

            String command = reader.readLine();
            switch (command.toLowerCase()) {
                case "1" -> {
                    addYears(dateFromConsole);
                }
                case "2" -> {
                    addDays(dateFromConsole);
                }
                case "3" -> {
                    addHours(dateFromConsole);
                }
                case "4" -> {
                    addMinutes(dateFromConsole);
                }
                case "5" -> {
                    addSeconds(dateFromConsole);
                }
                case "6" -> {
                    addMillis(dateFromConsole);
                }
                case "q", "й" -> {
                    return;
                }
                default -> {
                    clearScreen();
                }
            }
        }

    }

    private void addMillis(DateT dateFromConsole) throws IOException {
        System.out.print("\nВведите количество миллисекунд\n ->");
        String minutes = reader.readLine();
        try {
            long parseMillis = Long.parseLong(minutes);
            dateFromConsole.addMillis(parseMillis);
            System.out.println("Результат: " + dateFromConsole);
        } catch (NumberFormatException e) {
            System.out.println("Неверный ввод");
        }
        enterToContinue();
    }

    private void addSeconds(DateT dateFromConsole) throws IOException {
        System.out.print("\nВведите количество секунд\n ->");
        String minutes = reader.readLine();
        try {
            int parseSeconds = Integer.parseInt(minutes);
            dateFromConsole.addSeconds(parseSeconds);
            System.out.println("Результат: " + dateFromConsole);
        } catch (NumberFormatException e) {
            System.out.println("Неверный ввод");
        }
        enterToContinue();
    }

    private void addMinutes(DateT dateFromConsole) throws IOException {
        System.out.print("\nВведите количество минут\n ->");
        String minutes = reader.readLine();
        try {
            int parseMinutes = Integer.parseInt(minutes);
            dateFromConsole.addMinutes(parseMinutes);
            System.out.println("Результат: " + dateFromConsole);
        } catch (NumberFormatException e) {
            System.out.println("Неверный ввод");
        }
        enterToContinue();
    }

    private void addHours(DateT dateFromConsole) throws IOException {
        System.out.print("\nВведите количество часов\n ->");
        String hours = reader.readLine();
        try {
            int parseHours = Integer.parseInt(hours);
            dateFromConsole.addHours(parseHours);
            System.out.println("Результат: " + dateFromConsole);
        } catch (NumberFormatException e) {
            System.out.println("Неверный ввод");
        }
        enterToContinue();
    }

    private void addDays(DateT dateFromConsole) throws IOException {
        System.out.print("\nВведите количество дней\n ->");
        String days = reader.readLine();
        try {
            int parseDays = Integer.parseInt(days);
            dateFromConsole.addDays(parseDays);
            System.out.println("Результат: " + dateFromConsole);
        } catch (NumberFormatException e) {
            System.out.println("Неверный ввод");
        }
        enterToContinue();
    }

    private void addYears(DateT dateFromConsole) throws IOException {
        System.out.print("\nВведите количество лет\n ->");
        String years = reader.readLine();
        try {
            int parseYears = Integer.parseInt(years);
            dateFromConsole.addYears(parseYears);
            System.out.println("Результат: " + dateFromConsole);
        } catch (NumberFormatException e) {
            System.out.println("Неверный ввод");
        }
        enterToContinue();
    }

    private DateT getDateFromConsole() throws IOException {
        System.out.print("\nВведите дату в выбранном формате: " + datePattern + "\n ->");
        String dateString = reader.readLine();
        DateT date;
        if (isDateTimeStringValid(dateString)) {
            System.out.println("\nДата введена верно.");
            date = DateTHelper.createDateFromString(dateString, this.datePattern);
            return date;
        } else {
            System.out.println("Неверный ввод");
            enterToContinue();
            return null;
        }
    }

    private void dateDifference() throws IOException {
        if (datePattern != null) {
            System.out.println("\nШаблон = " + "\"" + datePattern + "\"");
        }
        System.out.print("\nВведите первую дату в выбранном вами формате\n ->");
        String firstDateString = reader.readLine();
        DateT firstDate;
        if (isDateTimeStringValid(firstDateString)) {
            firstDate = DateTHelper.createDateFromString(firstDateString, this.datePattern);
        } else {
            System.out.println("Неверный ввод");
            enterToContinue();
            return;
        }
        System.out.print("\nВведите вторую дату в выбранном вами формате\n ->");
        String secondDateString = reader.readLine();
        DateT secondDate;
        if (isDateTimeStringValid(secondDateString)) {
            secondDate = DateTHelper.createDateFromString(secondDateString, this.datePattern);
        } else {
            System.out.println("Неверный ввод");
            enterToContinue();
            return;
        }
        DateT difference = firstDate.difference(secondDate);
        System.out.printf("\nРазница между датами: \n -> %d лет, %d месяцев, %d дней, %d часов, %d минут, %d секунд, %d миллимекунд",
                difference.getYear(), difference.getMonth() - 1, difference.getDay() - 1, difference.getHours(), difference.getMinutes(), difference.getSeconds(), difference.getMillis());
        enterToContinue();
    }

    private boolean isDateTimeStringValid(String stringDate) {
        return Pattern.compile(
                        DateTHelper.createRegexFromPattern(this.datePattern))
                .matcher(stringDate)
                .find();
    }

    private void inputDatePattern() throws IOException {
        String dateTimePatternChecker = "^(((yyyy/(dd|d)/(mmm|mm|m))|(yyyy/(mmm|mm|m)/(dd|d))|((mmm|mm|m)/(dd|d)/yyyy)|((dd|d)/(mmm|mm|m)/yyyy))|((yyyy-(dd|d)-(mmm|mm|m))|(yyyy-(mmm|mm|m)-(dd|d))|((mmm|mm|m)-(dd|d)-yyyy)|((dd|d)-(mmm|mm|m)-yyyy)))( (00):(00)((:00)(:000)|(:00))?)?$";
        System.out.println("\nВведите шаблон формата даты в виде dd/mm/yyyy 00:00:00:000 " +
                "Где m,mm,mmm - месяцы, d,dd - дни, yyyy - год, 00:00:00:000 - часы, минуты, секунды, миллисекунды\n ->");
        String datePattern = reader.readLine();
        Matcher matcher = Pattern.compile(dateTimePatternChecker).matcher(datePattern);
        if (matcher.find()) {
            System.out.println("Шаблон введен верно");
            this.datePattern = datePattern;
        } else {
            System.out.println("Не верный ввод шаблона!!!");
        }
        enterToContinue();
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
                        
                        ** Выберите нужный пункт меню **
                        
                """);
        if (datePattern != null) {
            System.out.println("Текущий шаблон = " + "\"" + datePattern + "\"");
        }
        System.out.print("""
                 
                 1) Изменить шаблон ввода-вывода даты
                 2) Найти разницу между датами
                 3) Прибавить к дате - вычесть из даты
                 4) Сравнить даты
                              
                            
                Введите номер пункта меню "1-4",
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
