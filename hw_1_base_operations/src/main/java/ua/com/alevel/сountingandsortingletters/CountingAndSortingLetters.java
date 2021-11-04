package ua.com.alevel.сountingandsortingletters;

import com.google.common.collect.ImmutableSortedMultiset;
import com.google.common.collect.SortedMultiset;
import ua.com.alevel.interfaces.RunnableModuleApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CountingAndSortingLetters implements RunnableModuleApp {
    @Override
    public void start(BufferedReader reader) throws IOException {
        System.out.print("\n\t**Буквенная сортировка**\n\n Входные данные: ");
        printLettersSet(
                sortedMultisetFromLetters(
                        engRuLettersFromString(
                                reader.readLine())));
    }

    private static SortedMultiset<String> sortedMultisetFromLetters(List<String> letters) {
        return ImmutableSortedMultiset.copyOf(
                letters.stream()
                        .sorted()
                        .collect(Collectors.toList()));
    }

    private static void printLettersSet(SortedMultiset<String> lettersSet) {
        System.out.println(" Выходные данные: ");
        lettersSet.entrySet()
                .forEach(entry -> {
                    System.out.printf(" %s - %s\n", entry.getElement(), entry.getCount());
                });
        System.out.println();
    }

    public static List<String> engRuLettersFromString(String inputString) {
        List<String> letters = new ArrayList<>();
        Matcher matcher = Pattern.compile("[A-zА-я]").matcher(inputString);
        while (matcher.find()) {
            letters.add(matcher.group());
        }
        return letters;
    }
}
