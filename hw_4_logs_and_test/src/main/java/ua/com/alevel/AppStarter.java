package ua.com.alevel;

import lombok.Cleanup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.controllers.ChannelController;
import ua.com.alevel.controllers.MessagesController;
import ua.com.alevel.controllers.UsersManagementController;
import ua.com.alevel.entity.Channel;
import ua.com.alevel.entity.Message;
import ua.com.alevel.entity.User;
import ua.com.alevel.interfaces.starter.Starter;
import ua.com.alevel.service.impl.ChannelServiceImpl;
import ua.com.alevel.service.impl.MessageServiceImpl;
import ua.com.alevel.service.impl.UserServiceImpl;
import ua.com.alevel.utils.StateStorage;
import ua.com.alevel.utils.simplearray.impl.SimpleList;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.attribute.UserPrincipalNotFoundException;

public class AppStarter implements Starter {
    private static InputStreamReader inputStreamReader;
    private Logger infoLogger = LoggerFactory.getLogger("info");
    private Logger errorLogger = LoggerFactory.getLogger("error");

    private void initialize() throws UnsupportedEncodingException, UserPrincipalNotFoundException {
        infoLogger.info("start application");
        setShutdownHook();
        String charsetName;
        Console console = System.console();
        if (console != null && System.getProperty("os.name").contains("Windows")) {
            charsetName = console.charset().name();
            System.setOut(new PrintStream(System.out, true, charsetName));
            inputStreamReader = new InputStreamReader(System.in, charsetName);
        } else {
            System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
            inputStreamReader = new InputStreamReader(System.in, StandardCharsets.UTF_8);
        }

        createTestUsersAndMessages();
    }

    private void setShutdownHook() {
        Runtime.getRuntime().
                addShutdownHook(new Thread(() -> infoLogger.info("application shutdown"), "Shutdown-thread"));
    }

    private void createTestUsersAndMessages() throws UserPrincipalNotFoundException {
        infoLogger.info("create test users and messages");
        UserServiceImpl userService = UserServiceImpl.getInstance();
        userService.create(new User("admin@admin", "admin", "admin"));
        userService.create(new User("111", "111", "111"));
        userService.create(new User("222", "222", "222"));
        userService.create(new User("333", "333", "333"));

        ChannelServiceImpl channelService = ChannelServiceImpl.getInstance();
        Channel channelOne = new Channel("ch1");
        Channel channelTwo = new Channel("ch2");
        channelService.create(channelOne);
        channelService.create(channelTwo);

        MessageServiceImpl messageService = MessageServiceImpl.getInstance();
        messageService.create(
                new Message(
                        "Hi, is first test comment in ch1",
                        userService.findByEmail("admin@admin"),
                        channelService.findByName("ch1")));
        messageService.create(
                new Message(
                        "Hi, is ch2",
                        userService.findByEmail("admin@admin"),
                        channelService.findByName("ch2")));
    }

    @Override
    public void start() throws IOException {
        initialize();
        @Cleanup BufferedReader reader = new BufferedReader(inputStreamReader);
        mainLoopRun(reader);
    }

    private void mainLoopRun(BufferedReader reader) throws IOException {
        MessagesController messagesController = new MessagesController(reader);
        while (true) {
            printChooseApp();

            String command = reader.readLine();
            switch (command.toLowerCase()) {
                case "1" -> {
                    messagesController.start(reader);
                }
                case "2" -> {
                    messagesController.addMessage();
                }
                case "3" -> {
                    new ChannelController().start(reader);
                }
                case "4" -> {
                    new UsersManagementController().start(reader);
                }
                case "5" -> {
                    messagesController.login();
                }
                case "6" -> {
                    messagesController.connectToChannel();
                }
                case "7" -> {
                    messagesController.showMessagesByAuthor();
                }
                case "8" -> {
                    messagesController.showMessagesByChannel();
                }
                case "q", "й" -> {
                    System.exit(0);
                }
                default -> {
                    clearScreen();
                }
            }
        }
    }


    private static boolean isExit(String command) {
        return command.equalsIgnoreCase("q")
                || command.equalsIgnoreCase("й");
    }

    private static void printRepeatOrExit() {
        System.out.println(("""
                                
                Введите "q" чтобы выйти из приложения. 
                "Enter" чтобы повторить  
                -> """));
    }

    private static void printChooseApp() {
        System.out.print("""
                        
                        ** Залогиньтесь и оставьте свой комментарий **
                        
                """);
        if (StateStorage.getInstance().getAuthUser() != null) {
            System.out.printf("Вы вошли как: %s\n", StateStorage.getInstance().getAuthUser().getName());
        }
        if (StateStorage.getInstance().getCurrentChannel() != null) {
            System.out.printf("Вы находитесь в канале: %s\n", StateStorage.getInstance().getCurrentChannel().getChannelName());
        }
        System.out.print("""
                 
                 1) Просмотр всех коментариев.
                        
                 2) Оставить комментарий "".
                                   
                 3) Управление каналами.
                 
                 4) Управление пользователями.
                 
                 5) Залогиниться.
                  
                 6) Войти в канал. 
                 
                 7) Комментарии по автору.
                 
                 8) Комментарии по каналу.
                                 
                Введите номер пункта меню "1-8",
                Для выхода введите "q"
                ->\040""");
    }

    public static void clearScreen() {
        //Clears Screen in java
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException ignored) {
        }
    }

}
