package ua.com.alevel.trianglearea;

import ua.com.alevel.interfaces.controllers.ConsoleController;
import ua.com.alevel.trianglearea.exception.TriangleNotExistFromThisCoordinate;
import ua.com.alevel.trianglearea.util.TriangleAreaCalculator;
import ua.com.alevel.uniquesymbols.util.UniqueSymbolsCounter;

import java.io.BufferedReader;
import java.io.IOException;

public class TriangleAreaController implements ConsoleController {

    private BufferedReader reader;

    @Override
    public void start(BufferedReader reader) throws IOException {
        this.reader = reader;

        System.out.print(("\n\t** Подсчет площади треугольника по координатам **\n\n"));

        try {
            Coordinate coordinateA = makeCoordinate("A");
            Coordinate coordinateB = makeCoordinate("B");
            Coordinate coordinateC = makeCoordinate("C");

            System.out.printf("\n Площадь треугольника равна: \n-> %.2f\n", TriangleAreaCalculator.calculateArea(coordinateA, coordinateB, coordinateC));
        } catch (NumberFormatException e) {
            System.out.println("\n Неверный формат ввода координат");
            enterToContinue();
        } catch (TriangleNotExistFromThisCoordinate e) {
            System.out.printf("\n %s\n", e.getMessage());
        }
    }

    private Coordinate makeCoordinate(String coordinateName) throws IOException, NumberFormatException {
        System.out.printf(" Введите координату %s(x) :\n x -> ", coordinateName);
        double aX = takeCoordinateFromConsole();
        System.out.printf(" Введите координату %s(y) :\n y -> ", coordinateName);
        double aY = takeCoordinateFromConsole();
        return new Coordinate(aX, aY);
    }

    private double takeCoordinateFromConsole() throws IOException, NumberFormatException {
        return Double.parseDouble(reader.readLine());
    }

    private void enterToContinue() throws IOException {
        System.out.println("\n Нажмите enter для продолжения");
        reader.readLine();
    }
}
