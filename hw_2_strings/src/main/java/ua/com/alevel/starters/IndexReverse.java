package ua.com.alevel.starters;

import ua.com.alevel.interfaces.RunnableMethodTest;
import ua.com.alevel.utils.StringReverser;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.InputMismatchException;

import static ua.com.alevel.utils.StringReverser.reverse;

public class IndexReverse implements RunnableMethodTest {
    @Override
    public void start(BufferedReader reader, boolean isWordsReverse) throws IOException {
        if (isWordsReverse) {
            startIndexReverse(reader, "По индексу (Пословный)", true);
        } else {
            startIndexReverse(reader, "По индексу", false);
        }
    }

    private void startIndexReverse(BufferedReader reader, String methodName, boolean isWordsReverse) throws IOException {
        String inputString;
        String firstIndex;
        String lastIndex;
        System.out.printf("\n\t**%s**\n\n", methodName);
        System.out.print((" Введите строку для обработки:\n-> "));
        inputString = reader.readLine();

        System.out.print((" Введите первый индекс:\n-> "));
        firstIndex = reader.readLine();
        if (!isValidInteger(firstIndex)) {
            printInvalidEnter();
            return;
        }

        System.out.printf(" Введите второй индекс: \n-> ");
        lastIndex = reader.readLine();
        if (!isValidInteger(lastIndex)) {
            printInvalidEnter();
            return;
        }

        System.out.printf(" Выходные данные: \n-> %s\n",
                indexReverse(
                        inputString,
                        Integer.parseInt(firstIndex),
                        Integer.parseInt(lastIndex),
                        isWordsReverse));
    }

    private boolean isValidInteger(String inputString) {
        try {
            Integer.parseInt(inputString);
            return true;
        } catch (InputMismatchException | NumberFormatException exception) {
            return false;
        }
    }

    private void printInvalidEnter() {
        System.out.println("\n Не верный ввод: введите целое число в поле индекс.");
    }

    private String indexReverse(String inputString, int firstIndex, int lastIndex, boolean isWordsReverse) {
        return reverse(inputString, firstIndex, lastIndex, isWordsReverse);
    }
}
