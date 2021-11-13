package ua.com.alevel.controllers;

import ua.com.alevel.entity.Channel;
import ua.com.alevel.entity.User;
import ua.com.alevel.service.ChannelService;
import ua.com.alevel.service.impl.ChannelServiceImpl;
import ua.com.alevel.utils.ConsoleHelperUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.attribute.UserPrincipalNotFoundException;

import static ua.com.alevel.AppStarter.clearScreen;

public class ChannelController implements ConsoleController {

    private ChannelService channelService = ChannelServiceImpl.getInstance();
    private BufferedReader reader;

    @Override
    public void start(BufferedReader reader) throws IOException {
        this.reader = reader;
        mainLoopRun();
    }

    private void mainLoopRun() throws IOException {
        while (true) {
            clearScreen();
            printChannelMenu();

            String command = reader.readLine();
            switch (command.toLowerCase()) {
                case "1" -> {
                    createChannel();
                }
                case "2" -> {
                    deleteChannel();
                }
                case "3" -> {
                    showChannels();
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

    private void deleteChannel() throws IOException {
        clearScreen();
        System.out.print(" Введите имя канала, который хотите удалить\n -> ");
        String channelName = reader.readLine();
        Channel channelByName;
        try {
            channelByName = channelService.findByName(channelName);
            channelService.delete(channelByName);
        } catch (UserPrincipalNotFoundException e) {
            System.out.println("\n Канала с таким именем не существует");
            enterToContinue();
            return;
        }
        System.out.printf("Канал: %s успешно удален", channelByName.getChannelName());
        enterToContinue();
    }

    private void showChannels() throws IOException {
        clearScreen();
        String usersTableString = ConsoleHelperUtil.createChannelsTableString(channelService.findAll());
        System.out.println(usersTableString);
        enterToContinue();
    }

    private void createChannel() throws IOException {
        clearScreen();
        String channelName;

        System.out.print(" Укажите имя канала:\n -> ");
        channelName = reader.readLine();
        if (channelService.isChannelExist(channelName) || channelName.equals("")) {
            System.out.printf("\n Канал с именем \"%s\" существует\n", channelName);
            enterToContinue();
            return;
        }
        Channel channel = new Channel(channelName);
        channelService.create(channel);
        System.out.printf("\n Канал: %s успешно создан!\n", channel.getChannelName());
        enterToContinue();

        clearScreen();
    }

    private void enterToContinue() throws IOException {
        System.out.println("\n Нажмите enter для продолжения");
        reader.readLine();
    }

    private void printChannelMenu() {
        System.out.print("""
                        
                        ** Управление каналами **
                      
                 1) Создать канал.
                        
                 2) Удалить канал.
                 
                 3) Показать список каналов.
                  
                                 
                Введите номер пункта меню "1-3",
                Для выхода введите "q"
                ->\040""");
    }
}
