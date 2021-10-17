package ua.com.alevel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Util class, for work with strings.
 * It has four, reverse() method implementations, which return String.
 * */
public final class StringReverser {

    private StringReverser() {}

/**
 * Main simple reverse method that takes String and return same full reverse String.
 * @param src any String, which you want to reverse.
 * @return reversed Sting.
 * */
    public static String reverse(String src) {
        StringBuilder reversedOutString = new StringBuilder("");

        char[] tempChars = src.toCharArray();
        for (int i = tempChars.length; i > 0; i--) {
            reversedOutString.append(tempChars[i - 1]);
        }
        return reversedOutString.toString();
    }

    /**
     * Implementing a reverse method, that takes a String and reverses the individual words
     * in the string, remaining all spaces, on their places.
     * @param src any String, which you want to reverse.
     * @param isWordsReverse - if true - reverses only words, if false, work same {@link StringReverser#reverse(String)}
     * */
    public static String reverse(String src, boolean isWordsReverse) {
        if (isWordsReverse) {
            StringBuilder outString = new StringBuilder("");
            String[] words = src.split("\\s+");
            Matcher spaceMatcher = Pattern.compile("\\s+").matcher(src);

            for (String s : words) {
                outString.append(reverse(s));
                if (spaceMatcher.find()) {
                    outString.append(spaceMatcher.group());
                }
            }
            return outString.toString();
        }
        return reverse(src);
    }

    public static String reverse(String src, String destString) {
        StringBuilder outString = new StringBuilder("");
        String[] words = src.split(destString);
        Matcher spaceMatcher = Pattern.compile(destString).matcher(src);

        for (String s : words) {
            outString.append(s);
            if (spaceMatcher.find()) {
                outString.append(reverse(spaceMatcher.group()));
            }
        }
        return outString.toString();
    }

    public static String reverse(String src, int firstIndex, int lastIndex) {
        StringBuilder outString = new StringBuilder("");

        if (lastIndex > (src.length())) lastIndex = src.length();
        if (firstIndex > (src.length())) firstIndex = src.length();
        if (lastIndex < 0) lastIndex = 0;
        if (firstIndex < 0) firstIndex = 0;
        if(firstIndex > lastIndex) {
            int temp = firstIndex;
            firstIndex = lastIndex;
            lastIndex = temp;
        }
        return outString
                .append(src, 0, firstIndex)
                .append(reverse(src.substring(firstIndex, lastIndex), true))
                .append(src.substring(lastIndex))
                .toString();
    }
}
