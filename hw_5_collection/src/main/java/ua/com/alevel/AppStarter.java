package ua.com.alevel;

import lombok.Cleanup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.interfaces.starter.Starter;
import ua.com.alevel.util.ConsoleHelperUtil;
import ua.com.alevel.util.MathSet;
import ua.com.alevel.util.interfaces.RunnableMethodTest;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppStarter implements Starter {
    private static InputStreamReader inputStreamReader;
    private Logger infoLogger = LoggerFactory.getLogger("info");
    private Logger errorLogger = LoggerFactory.getLogger("error");
    private List<MathSet> mathSetList = new ArrayList<>();
    private BufferedReader reader;

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
        createTestMathSet();
    }

    private void createTestMathSet() {
        MathSet numbers = new MathSet();
        numbers.add(22.0, 11.0, 108.0, 1.3, 6.088, 11.001, -0.25);
        mathSetList.add(numbers);
    }

    private void setShutdownHook() {
        Runtime.getRuntime().
                addShutdownHook(new Thread(() -> infoLogger.info("application shutdown"), "Shutdown-thread"));
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
                    mathSetAdd();
                }
                case "2" -> {
                    mathSetJoin();
                }
                case "3" -> {
                    mathSetIntersection();
                }
                case "4" -> {
                    mathSetSortAsc();
                }
                case "5" -> {
                    mathSetSortAscOnIndex();
                }
                case "6" -> {
                    mathSetSortAscOnNumber();
                }
                case "7" -> {
                    mathSetSortDesc();
                }
                case "8" -> {
                    mathSetSortDescOnIndex();
                }
                case "9" -> {
                    mathSetSortDescOnNumber();
                }
                case "10" -> {
                    mathSetGetMax();
                }
                case "11" -> {
                    mathSetGetMin();
                }
                case "12" -> {
                    mathSetGetAverage();
                }
                case "13" -> {
                    mathSetGetMedian();
                }
                case "14" -> {
                    mathSetCut();
                }
                case "15" -> {
                    mathSetClear();
                }
                case "16" -> {
                    mathSetClearNumbers();
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

    private void mathSetClearNumbers() throws IOException {
        printMathSet(0);
        System.out.print("\nВведите число, или несколько чисел разделенных запятой,\n пробелом или любым другим символом кроме точки и нажмите enter.\n ->");
        String readLine = reader.readLine();
        List<BigDecimal> bigDecimals = findingNumbersIntoString(readLine);
        MathSet newMathSet = new MathSet();
        for (BigDecimal bigDecimal : bigDecimals) {
            newMathSet.add(bigDecimal.doubleValue());
        }
        mathSetList.get(0).clear(newMathSet.toArray());
        printMathSet(0);
        enterToContinue();
    }

    private void mathSetClear() throws IOException {
        printMathSet(0);

        mathSetList.get(0).clear();
        printMathSet(0);
        enterToContinue();
    }

    private void mathSetCut() throws IOException {
        printMathSet(0);
        try {
            System.out.print("\nВведите firstIndex:\n ->");
            int firstIndex = Integer.parseInt(reader.readLine());
            System.out.print("\nВведите lastIndex:\n ->");
            int lastIndex = Integer.parseInt(reader.readLine());
            MathSet cut = mathSetList.get(0).cut(firstIndex, lastIndex);
            printMathSet(cut);
            enterToContinue();
        } catch (Exception e) {
            System.out.println("\nНеверный ввод индекса!");
            enterToContinue();
        }
    }

    private void mathSetGetMedian() throws IOException {
        printMathSet(0);
        Number max = mathSetList.get(0).getMedian();
        MathSet mathSet = new MathSet();
        mathSet.add(max);
        printMathSet(mathSet);
        enterToContinue();
    }

    private void mathSetGetAverage() throws IOException {
        printMathSet(0);
        Number max = mathSetList.get(0).getAverage();
        MathSet mathSet = new MathSet();
        mathSet.add(max);
        printMathSet(mathSet);
        enterToContinue();
    }

    private void mathSetGetMin() throws IOException {
        printMathSet(0);
        Number max = mathSetList.get(0).getMin();
        MathSet mathSet = new MathSet();
        mathSet.add(max);
        printMathSet(mathSet);
        enterToContinue();
    }

    private void mathSetGetMax() throws IOException {
        printMathSet(0);
        Number max = mathSetList.get(0).getMax();
        MathSet mathSet = new MathSet();
        mathSet.add(max);
        printMathSet(mathSet);
        enterToContinue();
    }

    private void mathSetSortDescOnNumber() throws IOException {
        printMathSet(0);
        try {
            System.out.print("\nВведите число для сортировки:\n ->");
            double sortNumber = Double.parseDouble(reader.readLine());
            mathSetList.get(0).sortDesc(sortNumber);
            printMathSet(0);
            enterToContinue();
        } catch (Exception e) {
            System.out.println("\nНеверный ввод!");
            enterToContinue();
        }
    }

    private void mathSetSortDescOnIndex() throws IOException {
        printMathSet(0);
        try {
            System.out.print("\nВведите firstIndex:\n ->");
            int firstIndex = Integer.parseInt(reader.readLine());
            System.out.print("\nВведите lastIndex:\n ->");
            int lastIndex = Integer.parseInt(reader.readLine());
            mathSetList.get(0).sortDesc(firstIndex, lastIndex);
            printMathSet(0);
            enterToContinue();
        } catch (Exception e) {
            System.out.println("\nНеверный ввод индекса!");
            enterToContinue();
        }
    }

    private void mathSetSortDesc() throws IOException {
        printMathSet(0);

        mathSetList.get(0).sortDesc();
        printMathSet(0);
        enterToContinue();
    }

    private void mathSetSortAscOnNumber() throws IOException {
        printMathSet(0);
        try {
            System.out.print("\nВведите число для сортировки:\n ->");
            double sortNumber = Double.parseDouble(reader.readLine());
            mathSetList.get(0).sortAsc(sortNumber);
            printMathSet(0);
            enterToContinue();
        } catch (Exception e) {
            System.out.println("\nНеверный ввод!");
            enterToContinue();
        }
    }

    private void mathSetSortAscOnIndex() throws IOException {
        printMathSet(0);
        try {
            System.out.print("\nВведите firstIndex:\n ->");
            int firstIndex = Integer.parseInt(reader.readLine());
            System.out.print("\nВведите lastIndex:\n ->");
            int lastIndex = Integer.parseInt(reader.readLine());
            mathSetList.get(0).sortAsc(firstIndex, lastIndex);
            printMathSet(0);
            enterToContinue();
        } catch (Exception e) {
            System.out.println("\nНеверный ввод индекса!");
            enterToContinue();
        }
    }

    private void mathSetSortAsc() throws IOException {
        printMathSet(0);

        mathSetList.get(0).sortAsc();
        printMathSet(0);
        enterToContinue();
    }

    private void mathSetIntersection() throws IOException {
        printMathSet(0);
        System.out.print("\nВведите число, или несколько чисел разделенных запятой,\n пробелом или любым другим символом кроме точки и нажмите enter.\n ->");
        String readLine = reader.readLine();
        List<BigDecimal> bigDecimals = findingNumbersIntoString(readLine);
        MathSet newMathSet = new MathSet();
        for (BigDecimal bigDecimal : bigDecimals) {
            newMathSet.add(bigDecimal.doubleValue());
        }
        mathSetList.get(0).intersection(newMathSet);
        printMathSet(0);
        enterToContinue();
    }

    private void mathSetJoin() throws IOException {
        printMathSet(0);
        System.out.print("\nВведите число, или несколько чисел разделенных запятой,\n пробелом или любым другим символом кроме точки и нажмите enter.\n ->");
        String readLine = reader.readLine();
        List<BigDecimal> bigDecimals = findingNumbersIntoString(readLine);
        MathSet newMathSet = new MathSet();
        for (BigDecimal bigDecimal : bigDecimals) {
            newMathSet.add(bigDecimal.doubleValue());
        }
        mathSetList.get(0).join(newMathSet);
        printMathSet(0);
        enterToContinue();
    }


    private void mathSetAdd() throws IOException {
        printMathSet(0);
        System.out.println("\nВведите число, или несколько чисел разделенных запятой, пробелом или любым другим символом кроме точки и нажмите enter.\n ->");
        String readLine = reader.readLine();
        List<BigDecimal> bigDecimals = findingNumbersIntoString(readLine);
        for (BigDecimal bigDecimal : bigDecimals) {
            mathSetList.get(0).add(bigDecimal.doubleValue());
        }
        printMathSet(0);
        enterToContinue();
    }

    private List<BigDecimal> findingNumbersIntoString(String inputString) {
        List<BigDecimal> digitList = new ArrayList<>();
        Matcher matcher = Pattern.compile("[-+]?[0-9]*[.]?[0-9]+(?:[eE][-+]?[0-9]+)?").matcher(inputString);
        while (matcher.find()) {
            digitList.add(new BigDecimal(matcher.group()));
        }
        return digitList;
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
        printMathSetList();
        System.out.print("""
                        
                        ** Выберите метод **
                """);
        System.out.print("""
                 
                 1) add()                               
                 2) join()                              
                 3) intersection()   
                 4) sortAsc()    
                 5) sortAsc(firstIndex, lastIndex)
                 6) sortAsc(value)
                 7) sortDesc()
                 8) sortDesc(firstIndex, lastIndex)
                 9) sortDesc(value)
                 10) getMax()
                 11) getMax()
                 12) getAverage()
                 13) getMedian()
                 14) cut(firstIndex, lastIndex)
                 15) clear()
                 16) clear(numbers)
                            
                Введите номер пункта меню "1-16",
                Для выхода введите "q"
                ->\040""");
    }

    private void printMathSetList() {
        MathSet[] setsArr = new MathSet[mathSetList.size()];
        String mathSetsTableString = ConsoleHelperUtil.createMathSetsTableString(mathSetList.toArray(setsArr));
        System.out.print(mathSetsTableString);
    }

    private void printMathSet(int index) {
        MathSet[] setsArr = new MathSet[]{mathSetList.get(index)};
        String mathSetsTableString = ConsoleHelperUtil.createMathSetsTableString(setsArr);
        System.out.println(mathSetsTableString);
    }

    private void printMathSet(MathSet mathSet) {
        MathSet[] setsArr = new MathSet[]{mathSet};
        String mathSetsTableString = ConsoleHelperUtil.createMathSetsTableString(setsArr);
        System.out.println(mathSetsTableString);
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
