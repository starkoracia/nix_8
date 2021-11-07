package com.example.fxapps.knightmove;

import com.example.fxapps.knightmove.figure.Cell;
import com.example.fxapps.knightmove.figure.Figure;
import com.example.fxapps.knightmove.figure.ImpossibleMoveException;
import com.example.fxapps.knightmove.figure.KnightBlack;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class KnightsMoveApplication extends Application {

    private int size = 8;
    int boardSize = 650;
    int cellSize = boardSize / size;
    Rectangle rectKnight;
    KnightBlack knightBlack;

    List<Cell> possibleMoves = new ArrayList<>();

    Cell momentCell;
    Cell eventCell;


    @Override
    public void start(Stage stage) throws IOException {

        BorderPane root = new BorderPane();

        Group board = buildGrid(size);

        initNumbersVBox(root);
        initCharsHBox(root);

        HBox control = buildControlPanel(board);

        setKnigtToBoard(board);

        root.setCenter(board);
        root.setTop(control);
        root.setPadding(new Insets(0, 20, 0, 10));

        setStage(stage, root);
        stage.show();
    }

    private void setStage(Stage stage, BorderPane root) {
        stage.setTitle("Лошадью ходи!");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
    }

    private HBox buildControlPanel(Group board) {
        Button resetButton = new Button("Случайный ход");
        resetButton.setOnMouseClicked(
                event -> {
                    onResetButtonClicked();
                }
        );

        Button nextMoveButton = new Button("Сбросить");
        nextMoveButton.setOnMouseClicked(
                event -> {
                    onMoveButtonClicked(board);
                }
        );

        HBox control = new HBox();
        control.setPrefHeight(40);
        control.setSpacing(10.0);
        control.setAlignment(Pos.CENTER);

        control.getChildren().addAll(nextMoveButton, resetButton);
        return control;
    }

    private void initCharsHBox(BorderPane root) {
        HBox cellsCharsHBox = buildBottomSide();
        cellsCharsHBox.maxWidth(boardSize);
        cellsCharsHBox.maxHeight(50);
        root.setBottom(cellsCharsHBox);
    }

    private void initNumbersVBox(BorderPane root) {
        VBox cellsNumbersVBox = buildLeftSide();
        cellsNumbersVBox.maxWidth(50);
        cellsNumbersVBox.maxHeight(boardSize);
        root.setLeft(cellsNumbersVBox);
    }

    private void onMoveButtonClicked(Group board) {
        Cell[] cells = Cell.values();
        knightBlack = new KnightBlack(cells[new Random().nextInt(cells.length)]);
        update(knightBlack, board);
    }

    private void onResetButtonClicked() {
        List<Cell> possibleMoves = getPossibleMoves(knightBlack.position());
        Cell cell = possibleMoves.stream().collect(Collectors.toList()).get(new Random().nextInt(possibleMoves.size()));
        moveRectKnight(cell.getX() * cellSize, cell.getY() * cellSize);
    }

    private void setKnigtToBoard(Group board) {
        Cell[] cells = Cell.values();
        knightBlack = new KnightBlack(Cell.values()[new Random().nextInt(cells.length)]);
        add(knightBlack, board);
    }

    private HBox buildBottomSide() {
        Label label;
        HBox cellsCharsHBox = new HBox();
        cellsCharsHBox.setAlignment(Pos.CENTER_RIGHT);
        for (int i = 0; i <= size; i++) {
            if (i == 0) {
                label = new Label();
            } else {
                label = new Label(String.format("%s", (char) (64 + i)));
            }
            label.setPrefHeight(30);
            label.setPrefWidth(cellSize);
            label.setFont(new Font(cellSize / 2));
            cellsCharsHBox.getChildren().add(label);

        }
        cellsCharsHBox.setPadding(new Insets(0, 0, 0, 0));
        return cellsCharsHBox;
    }

    private VBox buildLeftSide() {
        VBox cellsNumbersVBox = new VBox();
        cellsNumbersVBox.setAlignment(Pos.CENTER_RIGHT);
        for (int i = size; i >= 1; i--) {
            Label label = new Label(String.format("%d", i));
            label.setPrefHeight(cellSize);
            label.setPrefWidth(30);
            label.setFont(new Font(cellSize / 2));
            label.setAlignment(Pos.CENTER_RIGHT);
            cellsNumbersVBox.getChildren().add(label);
        }
        return cellsNumbersVBox;
    }

    public void add(Figure figure, Group grid) {
        Cell position = figure.position();
        grid.getChildren().add(
                buildFigure(
                        position.getX() * cellSize + 5,
                        position.getY() * cellSize + 5,
                        cellSize - 20,
                        figure.icon()
                )
        );
    }

    public void update(Figure figure, Group grid) {
        Cell position = figure.position();
        buildFigure(
                position.getX() * cellSize + 5,
                position.getY() * cellSize + 5,
                cellSize - 20,
                figure.icon()
        );

    }

    private Rectangle buildFigure(int x, int y, int size, String image) {
        if (rectKnight == null) {
            rectKnight = new Rectangle();
        }
        rectKnight.setX(x);
        rectKnight.setY(y);
        rectKnight.setHeight(size);
        rectKnight.setWidth(size);

        Image img = new Image(getClass().getClassLoader().getResource(image).toString());
        rectKnight.setFill(new ImagePattern(img));

        final Rectangle momento = new Rectangle(x, y);
        setOnMouseBehaviorOnKnight(size, momento);

        return rectKnight;
    }

    private void setOnMouseBehaviorOnKnight(int size, Rectangle momento) {
        knightOnDragDetected(momento);
        knightOnMouseDrag(size);
        knightOnMouseReleased(momento);
    }

    private void knightOnDragDetected(Rectangle momento) {
        rectKnight.setOnDragDetected(
                event -> {
                    momento.setX(event.getX());
                    momento.setY(event.getY());
                }
        );
    }

    private void knightOnMouseReleased(Rectangle momento) {
        rectKnight.setOnMouseReleased(
                event -> {
                    try {
                        int eventX = event.getX() > 580 ? 580 : (int) (event.getX() < 15 ? 15 : event.getX());
                        int eventY = event.getY() > 580 ? 580 : (int) (event.getY() < 15 ? 15 : event.getY());
                        eventCell = findBy(eventX, eventY);
                        momentCell = findBy(momento.getX(), momento.getY());

                        if (isMovePossible(momentCell, eventCell)) {
                            moveRectKnight(eventX, eventY);
                        } else {
                            throw new ImpossibleMoveException(String.format("Ход на кледку %s невозможен", eventCell));
                        }
                    } catch (Exception e) {
                        Alert info = new Alert(Alert.AlertType.INFORMATION);
                        info.setHeaderText(null);
                        info.setGraphic(null);
                        info.setContentText(e.getMessage());
                        info.show();
                        rectKnight.setX(((int) momento.getX() / cellSize) * cellSize + 5);
                        rectKnight.setY(((int) momento.getY() / cellSize) * cellSize + 5);
                    }
                }
        );
    }

    private void knightOnMouseDrag(int size) {
        rectKnight.setOnMouseDragged(
                event -> {
                    if (event.getX() > 580) {
                        rectKnight.setX(580);
                    } else if (event.getX() < 10) {
                        rectKnight.setX(10);
                    } else {
                        rectKnight.setX(event.getX() - size / 2);
                    }

                    if (event.getY() > 580) {
                        rectKnight.setY(580);
                    } else if (event.getY() < 15) {
                        rectKnight.setY(15);
                    } else {
                        rectKnight.setY(event.getY() - size / 2);
                    }
                }
        );
    }

    private void moveRectKnight(int eventX, int eventY) {
        rectKnight.setX((eventX / cellSize) * cellSize + 5);
        rectKnight.setY((eventY / cellSize) * cellSize + 5);
        knightBlack.setPosition(findBy(eventX, eventY));
    }

    private boolean isMovePossible(Cell momemtCell, Cell moveCell) {
        return getPossibleMoves(momemtCell).contains(moveCell);
    }

    private List<Cell> getPossibleMoves(Cell momemtCell) {
        possibleMoves.clear();

        int xRight = momemtCell.getX() + 1;
        int xRightRight = momemtCell.getX() + 2;
        int xLeft = momemtCell.getX() - 1;
        int xLeftLeft = momemtCell.getX() - 2;

        int yDown = momemtCell.getY() + 1;
        int yDownDown = momemtCell.getY() + 2;
        int yUp = momemtCell.getY() - 1;
        int yUpUp = momemtCell.getY() - 2;

        if (isWithinBoard(xRightRight)) {
            if (isWithinBoard(yUp)) {
                possibleMoves.add(Cell.findBy(xRightRight, yUp));
            }
            if (isWithinBoard(yDown)) {
                possibleMoves.add(Cell.findBy(xRightRight, yDown));
            }
        }
        if (isWithinBoard(xLeftLeft)) {
            if (isWithinBoard(yUp)) {
                possibleMoves.add(Cell.findBy(xLeftLeft, yUp));
            }
            if (isWithinBoard(yDown)) {
                possibleMoves.add(Cell.findBy(xLeftLeft, yDown));
            }
        }
        if (isWithinBoard(yUpUp)) {
            if (isWithinBoard(xLeft)) {
                possibleMoves.add(Cell.findBy(xLeft, yUpUp));
            }
            if (isWithinBoard(xRight)) {
                possibleMoves.add(Cell.findBy(xRight, yUpUp));
            }
        }
        if (isWithinBoard(yDownDown)) {
            if (isWithinBoard(xLeft)) {
                possibleMoves.add(Cell.findBy(xLeft, yDownDown));
            }
            if (isWithinBoard(xRight)) {
                possibleMoves.add(Cell.findBy(xRight, yDownDown));
            }
        }
        return possibleMoves;
    }

    private boolean isWithinBoard(int coordinate) {
        return coordinate >= 0 && coordinate <= 7;
    }

    private Cell findBy(double graphX, double graphY) {
        Cell rst = Cell.A1;
        int x = (int) graphX / cellSize;
        int y = (int) graphY / cellSize;
        for (Cell cell : Cell.values()) {
            if (cell.getX() == x && cell.getY() == y) {
                rst = cell;
                break;
            }
        }
        return rst;
    }

    private Group buildGrid(int size) {
        Group panel = new Group();
        for (int y = 0; y != size; y++) {
            for (int x = 0; x != size; x++) {
                panel.getChildren().add(
                        buildRectangle(x, y, cellSize, (x + y) % 2 == 0)
                );
            }
        }
        return panel;
    }

    private Rectangle buildRectangle(int x, int y, int size, boolean white) {
        Rectangle rect = new Rectangle();
        rect.setX(x * size);
        rect.setY(y * size);
        rect.setHeight(size);
        rect.setWidth(size);
        if (white) {
            rect.setFill(Color.WHITE);
        } else {
            rect.setFill(Color.GRAY);
        }
        rect.setStroke(Color.BLACK);
        return rect;
    }

    public static void main(String[] args) {
        launch();
    }
}