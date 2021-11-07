package ua.com.alevel.uniquesymbols.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class UniqueSymbolsCounter {

    private UniqueSymbolsCounter() {
    }

    public static Integer countUniqueSymbols(String inputString) {
        String[] symbolsArray = inputString.split("");
        Set<String> digits = Arrays.stream(symbolsArray)
                .filter(ch -> ch.matches("\\d"))
                .collect(Collectors.toSet());
        return digits.size();
    }

}
