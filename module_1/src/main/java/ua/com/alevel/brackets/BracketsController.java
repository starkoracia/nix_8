package ua.com.alevel.brackets;

import ua.com.alevel.brackets.util.BracketsValidator;
import ua.com.alevel.interfaces.controllers.ConsoleController;

import java.io.BufferedReader;
import java.io.IOException;

public class BracketsController implements ConsoleController {
    @Override
    public void start(BufferedReader reader) throws IOException {
        System.out.print(("\n\t** Проверка строки на правильность открытия и закрытия скобок **\n\n"));
        System.out.print((" Введите строку для обработки:\n-> "));
        boolean isBracketsValid = BracketsValidator.isBracketsValid(reader.readLine());
        if (isBracketsValid) {
            System.out.println("\n Всё верно! =)\n Все правила соблюдены");
        } else {
            System.out.println("\n Очень жаль =(\n Строка не соответствует правилам размещения скобок!");
        }
    }
}
