package ui;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pojo.User;
import services.UserService;

import static org.junit.jupiter.api.Assertions.*;

class LoginLoaderTest {

    private UserService us = new UserService();

    @ParameterizedTest
    @CsvSource({"thanhdat27,123456","dangkhoa92,1234","1,1","2,3"})
    public void getUser(String username, String password) {
        Assertions.assertNull(us.getUser(username,password));
    }

}