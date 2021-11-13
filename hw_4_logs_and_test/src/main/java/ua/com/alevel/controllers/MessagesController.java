package ua.com.alevel.controllers;

import ua.com.alevel.entity.Channel;
import ua.com.alevel.entity.Message;
import ua.com.alevel.entity.User;
import ua.com.alevel.service.impl.ChannelServiceImpl;
import ua.com.alevel.service.impl.MessageServiceImpl;
import ua.com.alevel.service.impl.UserServiceImpl;
import ua.com.alevel.utils.ConsoleHelperUtil;
import ua.com.alevel.utils.StateStorage;
import ua.com.alevel.utils.simplearray.impl.SimpleList;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.attribute.UserPrincipalNotFoundException;

import static ua.com.alevel.AppStarter.clearScreen;

public class MessagesController implements ConsoleController {

    private final MessageServiceImpl messageService = MessageServiceImpl.getInstance();
    private BufferedReader reader;
    private UserServiceImpl userService = UserServiceImpl.getInstance();

    public MessagesController(BufferedReader reader) {
        this.reader = reader;
    }

    @Override
    public void start(BufferedReader reader) throws IOException {
        showMessages(messageService.findAll());
    }

    public void addMessage() throws IOException {
        StateStorage stateStorage = StateStorage.getInstance();
        if (stateStorage.getAuthUser() == null) {
            login();
        }
        if (stateStorage.getAuthUser() != null && stateStorage.getCurrentChannel() == null) {
            connectToChannel();
        }
        if (stateStorage.getCurrentChannel() != null &&
                stateStorage.getAuthUser() != null) {
            createMessage();
        }
    }

    public void connectToChannel() throws IOException {
        clearScreen();

        String channelName;

        System.out.print("\n  ** Выбор канала **\n\n Укажите имя канала:\n -> ");
        channelName = this.reader.readLine();
        if (!ChannelServiceImpl.getInstance().isChannelExist(channelName) || channelName.equals("")) {
            System.out.println("\n Канала с таким именем не существует");
            enterToContinue();
            return;
        }
        Channel channelFromDB = getChannelFromDB(channelName);

        StateStorage.getInstance().setChannel(channelFromDB);
        System.out.printf("Вы вошли в канал \"%s\"", channelFromDB.getChannelName());
        enterToContinue();
        clearScreen();
    }

    private Channel getChannelFromDB(String channelName) throws IOException {
        Channel channel;
        try {
            channel = ChannelServiceImpl.getInstance().findByName(channelName);
        } catch (UserPrincipalNotFoundException e) {
            return null;
        }
        return channel;
    }

    public void login() throws IOException {
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

        StateStorage.getInstance().setAuthUser(userFromDB);
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

        User user = StateStorage.getInstance().getAuthUser();
        Channel channel = StateStorage.getInstance().getCurrentChannel();
        messageService.create(new Message(message, user, channel));

        enterToContinue();
        clearScreen();
    }

    private void showMessages(SimpleList<Message> messages) throws IOException {
        clearScreen();
        String messagesTableString = ConsoleHelperUtil.createMessagesTableString(messages);
        System.out.println(messagesTableString);
        enterToContinue();
    }

    public void showMessagesByChannel() throws IOException {
        if (StateStorage.getInstance().getCurrentChannel() == null) {
            connectToChannel();
        }
        if (StateStorage.getInstance().getCurrentChannel() != null) {
            try {
                showMessages(messageService.findByChannel(StateStorage.getInstance().getCurrentChannel()));
            } catch (UserPrincipalNotFoundException e) {
                System.out.println(e.getName());
                enterToContinue();
            }
        }
    }

    public void showMessagesByAuthor() throws IOException {
        if (StateStorage.getInstance().getAuthUser() == null) {
            login();
        }
        if (StateStorage.getInstance().getAuthUser() != null) {
            try {
                showMessages(messageService.findByAuthor(StateStorage.getInstance().getAuthUser()));
            } catch (UserPrincipalNotFoundException e) {
                System.out.println(e.getName());
                enterToContinue();
            }
        }
    }

    private void enterToContinue() throws IOException {
        System.out.println("\n Нажмите enter для продолжения");
        reader.readLine();
    }

}
