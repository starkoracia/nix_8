package ua.com.alevel;

import lombok.Cleanup;
import ua.com.alevel.controllers.MessagesController;
import ua.com.alevel.controllers.UsersManagementController;
import ua.com.alevel.entity.Message;
import ua.com.alevel.entity.User;
import ua.com.alevel.interfaces.starter.Starter;
import ua.com.alevel.service.impl.MessageServiceImpl;
import ua.com.alevel.service.impl.UserServiceImpl;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.attribute.UserPrincipalNotFoundException;

public class AppStarter implements Starter {
    private static InputStreamReader inputStreamReader;

    private static void initialize() throws UnsupportedEncodingException, UserPrincipalNotFoundException {
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

    private static void createTestUsersAndMessages() throws UserPrincipalNotFoundException {
        UserServiceImpl userService = UserServiceImpl.getInstance();
        userService.create(new User("admin@admin","admin","admin"));
        userService.create(new User("111","111","111"));
        userService.create(new User("222","222","222"));
        userService.create(new User("333","333","333"));

        MessageServiceImpl messageService = MessageServiceImpl.getInstance();
        messageService.create(new Message("Hi, is firt test comment", userService.findByEmail("admin@admin")));
    }

    @Override
    public void start() throws IOException {
        initialize();
        @Cleanup BufferedReader reader = new BufferedReader(inputStreamReader);
        mainLoopRun(reader);
    }

    private static void mainLoopRun(BufferedReader reader) throws IOException {
        MessagesController messagesController = new MessagesController();
        while (true) {
            printChooseApp();

            String command = reader.readLine();
            switch (command.toLowerCase()) {
                case "1" -> {
                    messagesController.start(reader);
                }
                case "2" -> {
                    messagesController.addMessage(reader);
                }
                case "3" -> {
                    new UsersManagementController().start(reader);
                }
                case "4" -> {
                    messagesController.login(reader);
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
                      
                 1) Просмотр всех коментариев.
                        
                 2) Оставить комментарий "".
                                   
                 3) Управление пользователями.
                 
                 4) Залогиниться
                  
                                 
                Введите номер пункта меню "1-3",
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
