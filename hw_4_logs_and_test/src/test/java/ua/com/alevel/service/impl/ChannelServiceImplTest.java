package ua.com.alevel.service.impl;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import ua.com.alevel.entity.Channel;
import ua.com.alevel.entity.Channel;
import ua.com.alevel.entity.User;
import ua.com.alevel.utils.simplearray.impl.SimpleList;

import java.nio.file.attribute.UserPrincipalNotFoundException;

public class ChannelServiceImplTest {

    private static ChannelServiceImpl channelService; 
    private static String firstChannelId;
    private static Channel firstChannel;
    private static Channel secondChannel;
    private static Channel thirdChannel;


    @BeforeClass
    public static void setUp() throws UserPrincipalNotFoundException {
        channelService = ChannelServiceImpl.getInstance();
        firstChannel = new Channel("First channel");
        secondChannel = new Channel("Second channel");
        thirdChannel = new Channel("Third channel");
        channelService.create(firstChannel);
        channelService.create(secondChannel);
        channelService.create(thirdChannel);
        SimpleList<Channel> allChannels = channelService.findAll();
        firstChannelId = allChannels.get(0).getId();
    }

    @Test
    @Order(1)
    public void findByIdShouldReturnChannelWhenIdEqualsChannelIdWhichContainsInChannelList() throws UserPrincipalNotFoundException {
        channelService.create(new Channel("new channel"));
        SimpleList<Channel> channels = channelService.findAll();
        String id = channels.get(channels.size() - 1).getId();

        Channel channelById = channelService.findById(id);

        Assertions.assertEquals(channelById.getId(), id);
    }

    @Test
    public void findByIdShouldThrowUserPrincipalExceptionWhenIdIsNull() throws UserPrincipalNotFoundException {
        Assertions.assertThrows(
                UserPrincipalNotFoundException.class,
                () -> channelService.findById(null));
    }

    @Test
    @Order(0)
    public void findAllShouldReturnSimpleListOfChannels() {
        SimpleList<Channel> channelSimpleList = channelService.findAll();
        Assertions.assertEquals(SimpleList.class, channelSimpleList.getClass());
    }

    @Test
    public void createShouldAddChannelToChannelServiceList() throws UserPrincipalNotFoundException {
        int size = channelService.findAll().size();
        channelService.create(new Channel("new"));
        Assertions.assertEquals(size + 1, channelService.findAll().size());
    }

    @Test
    @Order(2)
    public void updateShouldUpdateChannelInChannelServiceList() throws UserPrincipalNotFoundException {
        channelService.create(new Channel("new channel"));
        SimpleList<Channel> channels = channelService.findAll();
        String id = channels.get(channels.size() - 1).getId();

        Channel channelById = channelService.findById(id);
        channelById.setChannelName("New name");
        channelService.update(channelById);
        Channel updatedChannel = channelService.findById(id);
        Assertions.assertEquals(channelById, updatedChannel);
    }

    @Test
    public void updateShouldThrowUserPrincipalNotFoundExceptionWhenChannelNotFoundInChannelServiceList() throws UserPrincipalNotFoundException {
        Assertions.assertThrows(
                UserPrincipalNotFoundException.class,
                () -> channelService.findById("firstChannelId"),
                "Ошибка поиска. Пользователя с таким ID не существует");
    }

    @Test
    public void deleteShouldDeletedChannelFromChannelServiceList() throws UserPrincipalNotFoundException {
        Channel channelById = channelService.findById(firstChannelId);
        int size = channelService.findAll().size();
        channelService.delete(channelById);
        Assertions.assertEquals(size - 1, channelService.findAll().size());
    }

    @Test
    public void deleteShouldThrowUserPrincipalNotFoundExceptionWhenDeleteNotExistingChannelFromChannelServiceList() throws UserPrincipalNotFoundException {
        Assertions.assertThrows(
                UserPrincipalNotFoundException.class,
                () -> channelService.findById("firstChannelId"),
                "Ошибка поиска. Пользователя с таким ID не существует");
    }

    @Test
    public void findByNameShouldReturnChannelsSimpleListWhenNameIsExistInChannelServiceList() throws UserPrincipalNotFoundException {
        Channel byName = channelService.findByName("First channel");
        Assertions.assertEquals(firstChannelId, byName.getId());
    }

    @Test
    public void findByNameShouldThrowUserPrincipalNotFoundExceptionWhenNameIsNotExistInUserServiceList() throws UserPrincipalNotFoundException {
        Assertions.assertThrows(
                UserPrincipalNotFoundException.class,
                () -> channelService.findByName("Not Exist Name"),
                "Ошибка поиска. Пользователя с таким ID не существует");
    }

    @Test
    public void isChannelExist() {
    }
}