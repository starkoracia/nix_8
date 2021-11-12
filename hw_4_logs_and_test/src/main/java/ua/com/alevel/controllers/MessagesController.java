package ua.com.alevel.controllers;

import ua.com.alevel.entity.Message;
import ua.com.alevel.entity.User;
import ua.com.alevel.service.impl.MessageServiceImpl;
import ua.com.alevel.service.impl.UserServiceImpl;
import ua.com.alevel.utils.ConsoleHelperUtil;
import ua.com.alevel.utils.StorageOfState;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.attribute.UserPrincipalNotFoundException;

import static ua.com.alevel.AppStarter.clearScreen;

public class MessagesController implements ConsoleController {

    private final MessageServiceImpl messageService = MessageServiceImpl.getInstance();
    private BufferedReader reader;
    private UserServiceImpl userService = UserServiceImpl.getInstance();

    @Override
    public void start(BufferedReader reader) throws IOException {
        this.reader = reader;
        showMessages();
    }

    public void addMessage(BufferedReader reader) throws IOException {
        StorageOfState storageOfState = StorageOfState.getInstance();
        this.reader = reader;
        if (storageOfState.getAuthUser() == null) {
            login(reader);
        }
        if (storageOfState.getAuthUser() != null && storageOfState.getCurrentChannel() == null) {
            connectToChannel(reader);
        }
        if (storageOfState.getCurrentChannel() != null &&
                storageOfState.getAuthUser() != null) {
            createMessage();
        }
    }

    private void connectToChannel(BufferedReader reader) {
        this.reader = reader;
        clearScreen();

    }

    public void login(BufferedReader reader) throws IOException {
        this.reader = reader;
        clearScreen();
        String email;

        System.out.print("\n  ** Вход в систему **\n\n Укажите ваш email:\n -> ");
        email = this.reader.readLine();
        if (isEmailNotExist(email) || email.equals("")) {
            System.out.println("\n Пользователя с таким email не существует");
            enterToContinue();
            return;
        }
        User userFromDB = getUserFromDB(email);

        System.out.print(" Введите пароль:\n -> ");
        String password = this.reader.readLine();

        if (isPasswordNotValid(userFromDB, password)) {
            System.out.println("\n Пароль не верный");
            enterToContinue();
            return;
        }

        StorageOfState.getInstance().setAuthUser(userFromDB);
        System.out.printf("Вы вошли как \"%s\"", userFromDB.getName());
        enterToContinue();
        clearScreen();
    }

    private boolean isPasswordNotValid(User userFromDB, String password) {
        return !userFromDB.getPassword().equals(password);
    }

    private User getUserFromDB(String email) throws IOException {
        User userByEmail;
        try {
            userByEmail = userService.findByEmail(email);
        } catch (UserPrincipalNotFoundException e) {
            return null;
        }
        return userByEmail;
    }

    private boolean isEmailNotExist(String email) throws IOException {
        User userByEmail;
        try {
            userByEmail = userService.findByEmail(email);
        } catch (UserPrincipalNotFoundException e) {
            return true;
        }
        return false;
    }

    private void createMessage() throws IOException {
        clearScreen();
        String message;
        System.out.print(" Оставьте комментарий:\n -> ");
        message = reader.readLine();

        User user = StorageOfState.getInstance().getAuthUser();
        messageService.create(new Message(message, user));

        enterToContinue();
        clearScreen();
    }

    private void showMessages() throws IOException {
        clearScreen();
        String usersTableString = ConsoleHelperUtil.createMessagesTableString(messageService.findAll());
        System.out.println(usersTableString);
        enterToContinue();
    }

    private void enterToContinue() throws IOException {
        System.out.println("\n Нажмите enter для продолжения");
        reader.readLine();
    }


}
