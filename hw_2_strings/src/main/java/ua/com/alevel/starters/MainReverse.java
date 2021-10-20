package ua.com.alevel.starters;

import ua.com.alevel.interfaces.RunnableMethodTest;

import static ua.com.alevel.utils.StringReverser.*;

import java.io.BufferedReader;
import java.io.IOException;

public class MainReverse implements RunnableMethodTest {
    @Override
    public void start(BufferedReader reader, boolean isWordsReverse) throws IOException {
        if (isWordsReverse) {
            System.out.print(("\n\t**Стандарный (Пословный)**\n\n"));
            System.out.print((" Введите строку для обработки:\n-> "));
            System.out.printf("\n Выходные данные: \n-> %s\n", mainReverse(reader.readLine(), true));
        } else {
            System.out.print(("\n\t**Стандарный**\n\n"));
            System.out.print((" Введите строку для обработки:\n-> "));
            System.out.printf("\n Выходные данные: \n-> %s\n", mainReverse(reader.readLine(), false));
        }
    }

    private String mainReverse(String inputString, boolean isWordsReverse) {
        return reverse(inputString, isWordsReverse);
    }
}
