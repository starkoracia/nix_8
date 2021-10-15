package ua.com.alevel.endsOfLessons;

import ua.com.alevel.interfaces.RunnableModuleApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.InputMismatchException;

public class EndsOfLessons implements RunnableModuleApp {
    @Override
    public void start(BufferedReader reader) throws IOException {
        System.out.print("\n\t**Время окончания урока**\n\n Входные данные: ");

        String numOfLesson = reader.readLine();
        if (!isValidLesson(numOfLesson)) {
            printInvalidEnter();
            return;
        }
        int intFromConsole = Integer.parseInt(numOfLesson);
        int startTime = 60 * 9;
        int lessonsTime = (45 * intFromConsole) + ((intFromConsole / 2 * 5) + ((intFromConsole - 1) / 2 * 15));
        int finishHour = (startTime + lessonsTime) / 60;
        int finishMinutes = (startTime + lessonsTime) % 60;

        System.out.printf(" Выходные данные: %d : %02d\n", finishHour, finishMinutes);
    }

    private boolean isValidLesson(String inputSting) {
        int consoleInt;
        try {
            consoleInt = Integer.parseInt(inputSting);
            if (consoleInt > 0 && consoleInt <= 10) {
                return true;
            }
        } catch (InputMismatchException | NumberFormatException exception) {
            return false;
        }
        return false;
    }

    private void printInvalidEnter() {
        System.out.println("\n Не верный ввод: введите количество уроков \"1-10\"");
    }
}
