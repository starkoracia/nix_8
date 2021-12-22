package ua.com.alevel;

import lombok.Cleanup;
import org.apache.commons.io.FileUtils;
import ua.com.alevel.interfaces.starter.RunnableMethodTest;
import ua.com.alevel.interfaces.starter.Starter;
import ua.com.alevel.util.Graph;
import ua.com.alevel.util.Node;

import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AppStarter implements Starter {
    private static InputStreamReader inputStreamReader;
    private BufferedReader reader;
    private File file;

    private final String dateTaskFlag = "#1";
    private final String namesTaskFlag = "#2";
    private final String citiesTaskFlag = "#3";

    private final String datePattern1 = "yyyy/mm/dd";
    private final String datePattern2 = "dd/mm/yyyy";
    private final String datePattern3 = "mm-dd-yyyy";

    private String filename = "data.txt";

    private void initialize() throws IOException {
        file = new File(filename);

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
        File file = new File(filename);
        while (true) {
            printChooseApp();

            String command = reader.readLine();
            switch (command.toLowerCase()) {
                case "1" -> {
                    Desktop.getDesktop().open(file);
                }
                case "2" -> {
                    printFormatDates();
                }
                case "3" -> {
                    printFirstUniqName();
                }
                case "4" -> {
                    getCheapestWay();
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

    private void printFormatDates() throws IOException {
        List<String> dates = transformDates();
        System.out.println("\nСписок дат:");
        dates.forEach(System.out::println);
        enterToContinue();
    }

    private List<String> transformDates() throws IOException {
        String regexFromPattern1 = createRegexFromPattern(datePattern1);
        String regexFromPattern2 = createRegexFromPattern(datePattern2);
        String regexFromPattern3 = createRegexFromPattern(datePattern3);
        List<String> formatDates = new ArrayList<>();
        List<String> dateList = getTaskLines(dateTaskFlag);
        for (String date : dateList) {
            if (Pattern.compile(regexFromPattern1).matcher(date).find()) {
                String[] splitDate = date.split("/");
                String formatDate = "";
                for (String s : splitDate) {
                    formatDate += s;
                }
                formatDates.add(formatDate);
            } else if (Pattern.compile(regexFromPattern2).matcher(date).find()) {
                String[] splitDate = date.split("/");
                String formatDate = "";
                formatDate += splitDate[2];
                formatDate += splitDate[1];
                formatDate += splitDate[0];
                formatDates.add(formatDate);
            } else if (Pattern.compile(regexFromPattern3).matcher(date).find()) {
                String[] splitDate = date.split("-");
                String formatDate = "";
                formatDate += splitDate[2];
                formatDate += splitDate[0];
                formatDate += splitDate[1];
                formatDates.add(formatDate);
            }
        }

        return formatDates;
    }

    private void printFirstUniqName() throws IOException {
        String firstUniqName;
        try {
            firstUniqName = getFirstUniqName();
            System.out.println("\nПервое уникальное имя = " + firstUniqName);
        } catch (RuntimeException e) {
            System.out.println("\n" + e.getMessage());
        }
        enterToContinue();
    }

    private String getFirstUniqName() throws IOException {
        List<String> namesList = getTaskLines(namesTaskFlag);
        List<String> uniqNames = getUniqNames(namesList);
        for (String name : namesList) {
            for (String uniqName : uniqNames) {
                if (uniqName.equals(name)) {
                    return name;
                }
            }
        }
        throw new RuntimeException("Уникальных имен не найдено!");
    }

    private void getCheapestWay() throws IOException {
        List<String> citiesList = getTaskLines(citiesTaskFlag);
        try {
            int numbersOfCities = Integer.parseInt(citiesList.get(0));

            List<List<Node>> listListNode = new ArrayList<>();
            for (int i = 0; i < numbersOfCities; i++) {
                List<Node> item = new ArrayList<>();
                listListNode.add(item);
            }
            HashMap<String, Integer> citiesNames = new HashMap<>();

            int currentCityIndex = 0;
            int i = 1;
            while (!citiesList.get(i).isBlank()) {
                String cityName = citiesList.get(i);
                i++;
                int numbersOfNeighbours = Integer.parseInt(citiesList.get(i));
                i++;
                for (int j = 0; j < numbersOfNeighbours; j++) {
                    String[] indexCost = citiesList.get(i).split(" ");
                    int nodeIndex = Integer.parseInt(indexCost[0]) - 1;
                    int nodeCost = Integer.parseInt(indexCost[1]);
                    Node node = new Node(nodeIndex, nodeCost);
                    citiesNames.put(cityName, currentCityIndex);
                    listListNode.get(currentCityIndex).add(node);
                    i++;
                }
                currentCityIndex++;
            }

            i++;
            while (i < citiesList.size()) {
                String[] names = citiesList.get(i).split(" ");
                String startCityName = names[0];
                String finishCityName = names[1];
                int startIndex;
                int finishIndex;
                if (citiesNames.containsKey(startCityName) && citiesNames.containsKey(finishCityName)) {
                    startIndex = citiesNames.get(startCityName);
                    finishIndex = citiesNames.get(finishCityName);
                } else {
                    throw new RuntimeException("Такого города не найдено! Попробуйте изменить данные.");
                }
                int cheapestCost = getCheapestCost(numbersOfCities, listListNode, startIndex, finishIndex);
                System.out.printf("\nНаименьшая стоимоимость %s -> %s = %d",
                        startCityName, finishCityName, cheapestCost);
                i++;
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("\nОшибка структуры данных в файле!");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();
        enterToContinue();
    }

    private int getCheapestCost(int numbersOfCities, List<List<Node>> listListNode, int startIndex, int finishIndex) {
        Graph graph = new Graph(numbersOfCities);
        graph.dijkstra(listListNode, startIndex);
        return graph.getDist()[finishIndex];
    }

    private List<String> getTaskLines(String flag) throws IOException {
        List<String> fileLines = FileUtils.readLines(file, "UTF-8");
        List<String> namesList = new ArrayList<>();
        int flagIndex = fileLines.indexOf(flag);
        for (int i = flagIndex + 1; i < fileLines.size(); i++) {
            String name = fileLines.get(i);
            if (fileLines.get(i).equals("##")) {
                break;
            }
            namesList.add(name);
        }
        return namesList;
    }

    private List<String> getUniqNames(List<String> names) {
        return names.stream()
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()))
                .entrySet()
                .stream()
                .filter(e -> e.getValue() == 1)
                .map(Map.Entry::getKey)
                .toList();
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
        System.out.print("""
                 
                 1) Открыть файл для редактирования.
                 2) Получить отформатированные даты.
                 3) Получить первое уникальное имя.
                 4) Узнать наименьшую стоимость между городами.
                              
                            
                Введите номер пункта меню "1-4",
                Для выхода введите "q"
                ->\040""");
    }

    private String createRegexFromPattern(String pattern) {
        String d = "(3[01]|[12][0-9]|[1-9])";
        String dd = "(0?[1-9]|[12][0-9]|3[01])";
        String mmm = "(Январь|Февраль|Март|Апрель|Май|Июнь|Июль|Август|Сентябрь|Октябрь|Ноябрь|Декабрь)";
        String mm = "(0?[1-9]|1[012])";
        String m = "([1-9]|1[012])";
        String yyyy = "([0-9]{4})";
        String timeMillis = "([01][0-9]|2[0-4]):([0-5][0-9]):([0-5][0-9]):([0-9][0-9][0-9])";
        String timeSeconds = "([01][0-9]|2[0-4]):([0-5][0-9]):([0-5][0-9])";
        String timeMinutes = "([01][0-9]|2[0-4]):([0-5][0-9])";

        return "^" + pattern
                .replace("dd", dd)
                .replace("d", d)
                .replace("mmm", mmm)
                .replace("mm", mm)
                .replace("m", m)
                .replace("yyyy", yyyy)
                .replace("00:00:00:000", timeMillis)
                .replace("00:00:00", timeSeconds)
                .replace("00:00", timeMinutes)
                + "$";
    }

    public static void clearScreen() {
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
