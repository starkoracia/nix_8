package ua.com.alevel.integersSumFromString;

import ua.com.alevel.interfaces.RunnableModuleApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IntegersSumFromString implements RunnableModuleApp {
    @Override
    public void start(BufferedReader reader) throws IOException {
        System.out.print(("\n\t**Сумма чисел в строке**\n\n Входные данные: "));
        System.out.printf(" Выходные данные: %d\n", integersSumFromString(reader.readLine()));
    }

    private Integer integersSumFromString(String inputString) {
        return integersArraySumming(findingIntegersIntoString(inputString));
    }

    private List<Integer> findingIntegersIntoString(String inputString) {
        List<Integer> digitList = new ArrayList<>();
        Matcher matcher = Pattern.compile("-?\\d+").matcher(inputString);
        while (matcher.find()) {
            digitList.add(Integer.parseInt(matcher.group()));
        }
        return digitList;
    }

    private Integer integersArraySumming(Collection<Integer> integerCollection) {
        return integerCollection
                .stream()
                .mapToInt(a -> a)
                .sum();
    }

}
