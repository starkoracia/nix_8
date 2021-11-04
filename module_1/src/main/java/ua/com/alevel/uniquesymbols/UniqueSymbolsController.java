package ua.com.alevel.uniquesymbols;

import ua.com.alevel.interfaces.controllers.ConsoleController;
import ua.com.alevel.uniquesymbols.util.UniqueSymbolsCounter;

import java.io.BufferedReader;
import java.io.IOException;

public class UniqueSymbolsController implements ConsoleController {
    @Override
    public void start(BufferedReader reader) throws IOException {
        System.out.print(("\n\t** Подсчет уникальных числовых символов в строке **\n\n"));
        System.out.print((" Введите строку для обработки:\n-> "));
        System.out.printf("\n Уникальных чисел в строке: \n-> %s\n", UniqueSymbolsCounter.countUniqueSymbols(reader.readLine()));
    }
}
