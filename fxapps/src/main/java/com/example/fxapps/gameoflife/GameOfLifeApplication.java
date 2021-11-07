package com.example.fxapps.gameoflife;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameOfLifeApplication extends Application {
    double windowSize = 560.0;
    private int size = 80;
    private boolean cellMap[][];
    private boolean betwMap[][];
    Button[][] cells;
    GridPane gridPane;
    Random random;
    Timer timer;
    TimerTask timerTask;

    @Override
    public void start(Stage stage) throws Exception {
        cellMap = new boolean[size][size];
        betwMap = new boolean[size][size];
        cells = new Button[size][size];
        random = new Random();

        setGridPane();
        setTimer();

        timer.schedule(timerTask, 150, 150);

        setStage(stage, gridPane);
        stage.show();
    }

    private void nextMove() {
        boolean[][] tempCellMap = new boolean[size][size];

        createNextMoveCellsConditions(tempCellMap);

        checkOnEndGame(tempCellMap);

        betwMap = cellMap;
        cellMap = tempCellMap;

        setNextMoveCellsStyle();
    }

    private void setTimer() {
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    nextMove();
                });
            }
        };
    }

    private void createNextMoveCellsConditions(boolean[][] tempCellMap) {
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                int count = countNeighbours(x, y);
                if (cellMap[y][x]) {
                    if (count < 2 || count > 3) {
                        tempCellMap[y][x] = false;
                    } else {
                        tempCellMap[y][x] = true;
                    }
                } else {
                    if (count == 3) {
                        tempCellMap[y][x] = true;
                    }
                }
            }
        }
    }

    private void checkOnEndGame(boolean[][] tempCellMap) {
        if (arraysCompare(cellMap, tempCellMap) || arraysCompare(betwMap, tempCellMap)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Игра в жизнь окончена!");
            timer.cancel();
            alert.showAndWait();
        }
    }

    private void setNextMoveCellsStyle() {
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                if (cellMap[y][x]) {
                    cells[y][x].setStyle(
                            String.format(
                                    "-fx-background-color: rgb(%d, %d, %d); -fx-border-width: 1; -fx-border-color: black; -fx-border-radius: 1",
                                    random.nextInt(255),
                                    random.nextInt(255),
                                    random.nextInt(255)));
                } else {
                    cells[y][x].setStyle("-fx-background-color: white; -fx-border-width: 1; -fx-border-color: black; -fx-border-radius: 1");
                }
            }
        }
    }

    public boolean arraysCompare(boolean[][] firstDArray, boolean[][] secondDArray) {
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                if (firstDArray[y][x] != secondDArray[y][x]) {
                    return false;
                }
            }
        }
        return true;
    }

    private int countNeighbours(int cellX, int cellY) {
        int count = 0;
        for (int y = cellY - 1; y <= cellY + 1; y++) {
            for (int x = cellX - 1; x <= cellX + 1; x++) {
                try {
                    if (cellMap[y][x]) {
                        count++;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {

                }
            }
        }
        if (cellMap[cellY][cellX] && count > 0) {
            count--;
        }
        return count;
    }

    private void setGridPane() {
        gridPane = new GridPane();
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                cellMap[y][x] = random.nextInt(100) < 30;
                Button tempButton = new Button();
                setButtonSize(tempButton, 7);
                if (cellMap[y][x]) {
                    tempButton.setStyle("-fx-background-color: green; -fx-border-width: 1; -fx-border-color: black; -fx-border-radius: 1");
                } else {
                    tempButton.setStyle("-fx-background-color: white; -fx-border-width: 1; -fx-border-color: black; -fx-border-radius: 1");
                }
                gridPane.add(tempButton, y, x);
                cells[y][x] = tempButton;
            }
        }
    }

    private void setButtonSize(Button button, double value) {
        button.setMinWidth(value);
        button.setMaxWidth(value);
        button.setPrefWidth(value);

        button.setMinHeight(value);
        button.setMaxHeight(value);
        button.setPrefHeight(value);

        button.setMinSize(value, value);
        button.setMaxSize(value, value);
        button.setPrefSize(value, value);
    }

    private void setStage(Stage stage, Pane root) {
        stage.setTitle("Игра жизни!");
        stage.setScene(new Scene(root, windowSize, windowSize));
        stage.setResizable(true);
        stage.setOnCloseRequest(windowEvent -> {
            timer.cancel();
        });
    }

    public static void main(String[] args) {
        launch();
    }
}
