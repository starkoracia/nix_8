package ua.com.alevel.service.impl;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import ua.com.alevel.entity.User;
import ua.com.alevel.utils.simplearray.impl.SimpleList;

import java.nio.file.attribute.UserPrincipalNotFoundException;

public class UserServiceImplTest {

    private static UserServiceImpl usersService;
    private static String firstUserId;
    private static User firstUser;
    private static User secondUser;
    private static User thirdUser;


    @BeforeClass
    public static void setUp() throws UserPrincipalNotFoundException {
        usersService = UserServiceImpl.getInstance();
        firstUser = new User("First email", "First Password", "First name");
        secondUser = new User("Second email", "Second Password", "Second name");
        thirdUser = new User("Third email", "Third Password", "Third name");
        usersService.create(firstUser);
        usersService.create(secondUser);
        usersService.create(thirdUser);
        SimpleList<User> allUsers = usersService.findAll();
        firstUserId = allUsers.get(0).getId();
    }

    @Test
    public void findByIdShouldReturnUserWhenIdEqualsUserIdWhichContainsInUserList() throws UserPrincipalNotFoundException {
        User userById = usersService.findById(firstUserId);

        Assertions.assertEquals(userById.getName(), "First name");
        Assertions.assertEquals(userById.getEmail(), "First email");
        Assertions.assertEquals(userById.getPassword(), "First Password");
    }

    @Test
    public void findByIdShouldThrowUserPrincipalExceptionWhenIdIsNull() throws UserPrincipalNotFoundException {
        Assertions.assertThrows(
                UserPrincipalNotFoundException.class,
                () -> usersService.findById(null),
                "Ошибка поиска. Пользователя с таким ID не существует");
    }

    @Test
    @Order(0)
    public void findAllShouldReturnSimpleListOfUsers() {
        SimpleList<User> userSimpleList = usersService.findAll();
        Assertions.assertEquals(SimpleList.class, userSimpleList.getClass());
    }

    @Test
    public void createShouldAddUserToUserServiceList() throws UserPrincipalNotFoundException {
        int size = usersService.findAll().size();
        usersService.create(new User("em", "pw", "nm"));
        Assertions.assertEquals(size + 1, usersService.findAll().size());
    }

    @Test
    public void createShouldThrowException() throws UserPrincipalNotFoundException {
        Assertions.assertThrows(
                UserPrincipalNotFoundException.class,
                () -> usersService.create(null),
                "Ошибка зоздания пользователя");
    }

    @Test
    public void updateShouldUpdateUserInUserServiceList() throws UserPrincipalNotFoundException {
        firstUser = usersService.findById(firstUserId);
        firstUser.setName("New name");
        usersService.update(firstUser);
        User updatedUser = usersService.findById(firstUserId);
        Assertions.assertEquals(firstUser, updatedUser);
    }

    @Test
    public void updateShouldThrowUserPrincipalNotFoundExceptionWhenUserNotFoundInUserServiceList() throws UserPrincipalNotFoundException {
        Assertions.assertThrows(
                UserPrincipalNotFoundException.class,
                () -> usersService.findById("firstUserId"),
                "Ошибка поиска. Пользователя с таким ID не существует");
    }

    @Test
    public void deleteShouldDeletedUserFromUserServiceList() throws UserPrincipalNotFoundException {
        User userById = usersService.findById(firstUserId);
        int size = usersService.findAll().size();
        usersService.delete(userById);
        Assertions.assertEquals(size - 1, usersService.findAll().size());
    }

    @Test
    public void deleteShouldThrowUserPrincipalNotFoundExceptionWhenDeleteNotExistingUserFromUserServiceList() throws UserPrincipalNotFoundException {
        Assertions.assertThrows(
                UserPrincipalNotFoundException.class,
                () -> usersService.findById("firstUserId"),
                "Ошибка поиска. Пользователя с таким ID не существует");
    }

    @Test
    public void existByEmailShouldReturnTrueIfUserExistInUserServiceList() {
        Assertions.assertTrue(usersService.existByEmail("Second email"));
    }

    @Test
    public void existByEmailShouldReturnFalseIfUserNotExistInUserServiceList() {
        Assertions.assertFalse(usersService.existByEmail("Not Exist email"));
    }

    @Test
    public void findByEmailShouldReturnUserWhenEmailIsExistInUserServiceList() throws UserPrincipalNotFoundException {
        User byEmail = usersService.findByEmail("Second email");
        Assertions.assertEquals(byEmail.getName(), "Second name");
    }

    @Test
    public void findByEmailShouldThrowUserPrincipalNotFoundExceptionWhenEmailIsNotExistInUserServiceList() throws UserPrincipalNotFoundException {
        Assertions.assertThrows(
                UserPrincipalNotFoundException.class,
                () -> usersService.findByEmail("NotExist email"),
                "Ошибка поиска. Пользователя с таким ID не существует");
    }

}