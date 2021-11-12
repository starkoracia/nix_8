package ua.com.alevel.controllers;

import ua.com.alevel.entity.User;
import ua.com.alevel.service.impl.UserServiceImpl;
import ua.com.alevel.utils.ConsoleHelperUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.attribute.UserPrincipalNotFoundException;

import static ua.com.alevel.AppStarter.clearScreen;

public class UsersManagementController implements ConsoleController {

    private UserServiceImpl userService = UserServiceImpl.getInstance();
    private BufferedReader reader;

    @Override
    public void start(BufferedReader reader) throws IOException {
        this.reader = reader;
        mainLoopRun();
    }

    private void mainLoopRun() throws IOException {
        while (true) {
            clearScreen();
            printUsersMenu();

            String command = reader.readLine();
            switch (command.toLowerCase()) {
                case "1" -> {
                    createUser();
                }
                case "2" -> {
                    editUser();
                }
                case "3" -> {
                    deleteUser();
                }
                case "4" -> {
                    findByEmail();
                }
                case "5" -> {
                    showUsers();
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


    private void editUser() throws IOException {
        clearScreen();
        System.out.print(" Введите email для изменения пользователя:\n -> ");
        String email = reader.readLine();
        User userByEmail;
        try {
            userByEmail = userService.findByEmail(email);
        } catch (UserPrincipalNotFoundException e) {
            System.out.println("\n Пользователя с таким email не существует");
            enterToContinue();
            return;
        }
        printCurrentUser(userByEmail);
        editUserMenu(userByEmail);
    }

    private void printCurrentUser(User userByEmail) {
        System.out.println("\n" + ConsoleHelperUtil.tableFromUser(userByEmail));
    }

    private void editUserMenu(User userByEmail) throws IOException {
        while (true) {
            clearScreen();
            printCurrentUser(userByEmail);
            System.out.print("""
                     
                     1) Изменить email.
                     
                     2) Изменить пароль.
                     
                     3) Изменить имя.
                                        
                                        
                    Введите номер пункта меню "1-3",
                    Для выхода введите "q"
                    ->\040""");

            String command = reader.readLine();
            switch (command.toLowerCase()) {
                case "1" -> {
                    editUserEmail(userByEmail);
                    return;
                }
                case "2" -> {
                    editUserPassword(userByEmail);
                    return;
                }
                case "3" -> {
                    editUserName(userByEmail);
                    return;
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

    private void editUserName(User userByEmail) throws IOException {
        System.out.print(" Введите новое имя пользователя:\n -> ");
        String name = reader.readLine();
        userByEmail.setName(name);
        try {
            userService.update(userByEmail);
        } catch (UserPrincipalNotFoundException e) {
            System.out.println("\n Пользователя с таким id не существует");
            enterToContinue();
            return;
        }
        printCurrentUser(userByEmail);
        System.out.println("Пользователь изменен");
        enterToContinue();
    }

    private void editUserPassword(User userByEmail) throws IOException {
        System.out.print(" Введите новый пароль:\n -> ");
        String password = reader.readLine();
        userByEmail.setPassword(password);
        try {
            userService.update(userByEmail);
        } catch (UserPrincipalNotFoundException e) {
            System.out.println("\n Пользователя с таким id не существует");
            enterToContinue();
            return;
        }
        printCurrentUser(userByEmail);
        System.out.println("Пользователь изменен");
        enterToContinue();
    }

    private void editUserEmail(User userByEmail) throws IOException {
        System.out.print(" Введите новый email:\n -> ");
        String email = reader.readLine();
        userByEmail.setEmail(email);
        try {
            userService.update(userByEmail);
        } catch (UserPrincipalNotFoundException e) {
            System.out.println("\n Пользователя с таким id не существует");
            enterToContinue();
            return;
        }
        printCurrentUser(userByEmail);
        System.out.println("Пользователь изменен");
        enterToContinue();
    }

    private void deleteUser() throws IOException {
        clearScreen();
        System.out.print(" Введите email для удаления пользователя:\n -> ");
        String email = reader.readLine();
        User userByEmail;
        try {
            userByEmail = userService.findByEmail(email);
            userService.delete(userByEmail);
        } catch (UserPrincipalNotFoundException e) {
            System.out.println("\n Пользователя с таким email не существует");
            enterToContinue();
            return;
        }
        printCurrentUser(userByEmail);
        System.out.println("Пользователь удален");
        enterToContinue();
    }

    private void findByEmail() throws IOException {
        clearScreen();
        System.out.print(" Введите email для поиска пользователя:\n -> ");
        String email = reader.readLine();
        User userByEmail;
        try {
            userByEmail = userService.findByEmail(email);
        } catch (UserPrincipalNotFoundException e) {
            System.out.println("\n Пользователя с таким email не существует");
            enterToContinue();
            return;
        }
        printCurrentUser(userByEmail);
        enterToContinue();
    }

    private void showUsers() throws IOException {
        clearScreen();
        String usersTableString = ConsoleHelperUtil.createUsersTableString(userService.findAll());
        System.out.println(usersTableString);
        enterToContinue();
    }

    private void enterToContinue() throws IOException {
        System.out.println("\n Нажмите enter для продолжения");
        reader.readLine();
    }

    private void createUser() throws IOException {
        clearScreen();
        String email;

        System.out.print(" Укажите уникальный email, он же будет являться логином:\n -> ");
        email = reader.readLine();
        if (isEmailExist(email) || email.equals("")) {
            System.out.printf("\n Пользователь с email \"%s\" существует\n", email);
            enterToContinue();
            return;
        }
        System.out.print(" Создайте пароль:\n -> ");
        String password = reader.readLine();
        System.out.print(" Укажите имя пользователя:\n -> ");
        String name = reader.readLine();
        userService.create(new User(email, password, name));

        clearScreen();
    }

    private boolean isEmailExist(String email) throws IOException {
        User userByEmail;
        try {
            userByEmail = userService.findByEmail(email);
            System.out.println("\n Пользователь с таким email существует");
            enterToContinue();
        } catch (UserPrincipalNotFoundException e) {
            return false;
        }
        return true;
    }

    private void printUsersMenu() {
        System.out.print("""
                        
                        ** Управление пользователями **
                      
                 1) Создать нового пользователя.
                        
                 2) Изменить существующего пользователя.
                                   
                 3) Удалить существующего пользователя.
                 
                 4) Найти пользователя по email.
                 
                 5) Показать всех пользователей
                  
                                 
                Введите номер пункта меню "1-5",
                Для выхода введите "q"
                ->\040""");
    }

}
