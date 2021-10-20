package ua.com.alevel.starters;

import ua.com.alevel.interfaces.RunnableMethodTest;

import java.io.BufferedReader;
import java.io.IOException;

import static ua.com.alevel.utils.StringReverser.reverse;

public class SubstringReverse implements RunnableMethodTest {
    @Override
    public void start(BufferedReader reader, boolean isWordsReverse) throws IOException {
        String inputString;
        String substring;
        if (isWordsReverse) {
            System.out.print(("\n\t**По подстроке (Пословный)**\n\n"));
            System.out.print((" Введите строку для обработки:\n-> "));
            inputString = reader.readLine();
            System.out.print((" Введите подстроку:\n-> "));
            substring = reader.readLine();
            System.out.printf(" Выходные данные: \n-> %s\n", substringReverse(inputString, substring, true));
        } else {
            System.out.print(("\n\t**По подстроке**\n\n"));
            System.out.print((" Введите строку для обработки:\n-> "));
            inputString = reader.readLine();
            System.out.print((" Введите подстроку:\n-> "));
            substring = reader.readLine();
            System.out.printf("\n Выходные данные: \n-> %s\n", substringReverse(inputString, substring, false));
        }
    }

    private String substringReverse(String inputString, String substring, boolean isWordsReverse) {
        return reverse(inputString, substring, isWordsReverse);
    }
}
