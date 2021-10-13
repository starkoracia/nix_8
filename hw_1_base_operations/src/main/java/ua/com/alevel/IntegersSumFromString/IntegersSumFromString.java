package ua.com.alevel.IntegersSumFromString;

import lombok.Cleanup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IntegersSumFromString {

    public void start(BufferedReader reader) throws IOException {
        System.out.print("\n\t**Сумма чисел в строке**\nВходные данные: ");
        System.out.printf("Выходные данные: %d\n", integersSumFromString(reader.readLine()));
    }

    private Integer integersSumFromString(String inputString) {
        return integersArraySumming(findingIntegersIntoString(inputString));
    }

    private List<Integer> findingIntegersIntoString(String inputString) {
        List<Integer> digitList = new ArrayList<>();
        Matcher matcher = Pattern.compile("\\d+").matcher(inputString);
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
