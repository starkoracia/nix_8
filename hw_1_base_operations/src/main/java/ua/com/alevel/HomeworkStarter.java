package ua.com.alevel;

import com.google.common.collect.ImmutableSortedMultiset;
import com.google.common.collect.SortedMultiset;
import lombok.Cleanup;
import ua.com.alevel.IntegersSumFromString.IntegersSumFromString;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class HomeworkStarter {

    public static void run() throws IOException {
        @Cleanup BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        new IntegersSumFromString().start(reader);
    }
//
//
//    public static void hwNumberTwoStart() {
//        System.out.print("\n\t**Буквенная сортировка**\nВходные данные: ");
//        printLettersSet(
//                sortedMultisetFromLetters(
//                        engRuLettersFromString(
//                                getConsoleString())));
//
//    }
//
//    public static void hwNumberThreeStart() {
//        System.out.print("\n\t**Время окончания урока**\nВходные данные: ");
//
//        int a = getValidIntFromConsole();
//        int startTime = 60 * 9;
//        int lessonsTime = (45 * a) + ((a / 2 * 5) + ((a - 1) / 2 * 15));
//        int finishHour = (startTime + lessonsTime) / 60;
//        int finishMinutes = (startTime + lessonsTime) % 60;
//
//        System.out.printf("Выходные данные: %d : %02d\n", finishHour, finishMinutes);
//    }
//
//    private static void printLettersSet(SortedMultiset<String> lettersSet) {
//        System.out.println("Выходные данные: ");
//        lettersSet.entrySet()
//                .forEach(entry -> {
//                    System.out.printf("%s - %s\n", entry.getElement(), entry.getCount());
//                });
//    }
//
//
//    private static int getConsoleInt() {
//        Scanner console = new Scanner(System.in);
//        return console.nextInt();
//    }
//
//    private static int getValidIntFromConsole() {
//        int consoleInt = 0;
//        while (true) {
//            try {
//                consoleInt = getConsoleInt();
//                if (consoleInt > 0 && consoleInt <= 10) {
//                    break;
//                } else {
//                    printInvalidEnter();
//                }
//            } catch (InputMismatchException exception) {
//                printInvalidEnter();
//            }
//        }
//        return consoleInt;
//    }
//
//    private static void printInvalidEnter() {
//        System.out.println("Не верный ввод: введите количество уроков от 1 до 10");
//        System.out.print("\n\t**Время окончания урока**\nВходные данные: ");
//    }
//
//
//    public static List<String> engRuLettersFromString(String inputString) {
//        List<String> letters = new ArrayList<>();
//        Matcher matcher = Pattern.compile("[a-zA-Zа-яА-Я]").matcher(inputString);
//        while (matcher.find()) {
//            letters.add(matcher.group());
//        }
//        return letters;
//    }
//
//    private static SortedMultiset<String> sortedMultisetFromLetters(List<String> letters) {
//        return ImmutableSortedMultiset.copyOf(
//                letters.stream()
//                        .sorted()
//                        .collect(Collectors.toList()));
//    }
//
//
}
