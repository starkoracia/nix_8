package ua.com.alevel.binarytree;

import ua.com.alevel.binarytree.tree.Tree;
import ua.com.alevel.binarytree.util.TreePrinter;
import ua.com.alevel.interfaces.controllers.ConsoleController;

import java.io.BufferedReader;
import java.io.IOException;

import static ua.com.alevel.AppStarter.clearScreen;

public class BinaryTreeController implements ConsoleController {
    Tree tree;
    BufferedReader reader;

    @Override
    public void start(BufferedReader reader) throws IOException {
        tree = new Tree();
        this.reader = reader;
        mainLoopRun();
    }

    private void mainLoopRun() throws IOException {
        while (true) {
            printChooseApp();

            String command = reader.readLine();
            switch (command.toLowerCase()) {
                case "1" -> {
                    addTreeNode();
                }
                case "2" -> {
                    deleteTreeNode();
                }
                case "3" -> {
                    printTree();
                }
                case "4" -> {
                    printMaxDepth();
                }
                case "q", "й" -> {
                    clearScreen();
                    return;
                }
                default -> {
                    clearScreen();
                }
            }
        }
    }

    private void printMaxDepth() throws IOException {
        System.out.printf("\nМаксимальная глубина дерева = %d\n", Tree.maxDepth(tree.getRootNode()));
        enterToContinue();
    }

    private static boolean isExit(String command) {
        return command.equalsIgnoreCase("q")
                || command.equalsIgnoreCase("й");
    }

    private void printChooseApp() {
        clearScreen();
        System.out.print("""
                        
                        ** Бинарное дерево целых чисел **
                        
                 1) Добавить узел.
                 2) Удалить узел по значению.
                 3) Распечатать дерево.
                 4) Показать максимальную глубину дерева.
                                    
                Введите номер нужного метода "1-4",
                Для выхода введите "q"
                ->\040""");
    }

    private void addTreeNode() throws IOException {
        clearScreen();
        while (true) {
            System.out.println("\nЧтобы закончить вводить данные -> введите \"q\"");
            System.out.print("\nВведите целое число для добавления узла дерева: \n ->");
            try {
                String command = reader.readLine();
                if (isExit(command)) {
                    return;
                }
                int parseInt = Integer.parseInt(command);
                tree.insertNode(parseInt);
                System.out.printf("\nУзел со значением {%d} успешно добавлен.\n", parseInt);
            } catch (NumberFormatException e) {
                System.out.println("\nНе вверный ввод! Введите целое число");
                enterToContinue();
            }
        }
    }

    private void deleteTreeNode() throws IOException {
        clearScreen();
        System.out.println("\nВведите целое число для удаления узла из дерева: \n ->");
        try {
            int parseInt = Integer.parseInt(reader.readLine());
            if (tree.deleteNode(parseInt)) {
                System.out.printf("\nУзел со значением {%d} успешно удален.", parseInt);
                enterToContinue();
                return;
            }
            System.out.printf("\nУзел со значением {%d} не существует.", parseInt);
            enterToContinue();
        } catch (NumberFormatException e) {
            System.out.println("\nНе вверный ввод! Введите целое число");
            enterToContinue();
        }
    }

    private void printTree() throws IOException {
        clearScreen();
        TreePrinter.printNode(tree.getRootNode());
        enterToContinue();
    }

    private static void printRepeatOrExit() {
        System.out.println(("""
                                
                Введите "q" чтобы выйти из приложения. 
                "Enter" чтобы повторить  
                -> """));
    }

    private void enterToContinue() throws IOException {
        System.out.println("\n Нажмите enter для продолжения");
        reader.readLine();
    }

}
