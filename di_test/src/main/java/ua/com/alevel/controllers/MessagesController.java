package ua.com.alevel.controllers;

import ua.com.alevel.annotations.Autowired;
import ua.com.alevel.annotations.Service;
import ua.com.alevel.entity.Message;
import ua.com.alevel.entity.User;
import ua.com.alevel.service.MessageService;
import ua.com.alevel.service.UserService;
import ua.com.alevel.utils.ConsoleHelperUtil;
import ua.com.alevel.utils.StorageOfState;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.attribute.UserPrincipalNotFoundException;

import static ua.com.alevel.HomeworkStarter.clearScreen;

@Service
public class MessagesController implements ConsoleController {

    @Autowired
    private MessageService messageService;
    private BufferedReader reader;
    @Autowired
    private UserService userService;

    @Override
    public void start(BufferedReader reader) throws IOException {
        this.reader = reader;
        showMessages();
    }

    public void addMessage(BufferedReader reader) throws IOException {
        this.reader = reader;
        if (StorageOfState.getInstance().getAuthUser() == null) {
            login(reader);
        }
        if (StorageOfState.getInstance().getAuthUser() != null) {
            createMessage();
        }
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

        StorageOfState.getInstance().getAuthUser(userFromDB);
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
