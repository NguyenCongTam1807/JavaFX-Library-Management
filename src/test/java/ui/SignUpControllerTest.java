package ui;

import org.junit.jupiter.api.Test;
import pojo.User;
import services.UserService;

import java.awt.*;
import java.sql.Date;

public class SignUpControllerTest {

    private static UserService us = new UserService();

    private String accountId,password,name,email,studentId,phoneNumber;
    private Date birthday;

    @Test
    public void signUpSuccessfulTest() {
        accountId = "";
        password = "";
        name = "";
        birthday = null;
        phoneNumber = "";
        email = "";
        studentId = "";
        User user = new User(accountId,password,1,name,birthday,
                phoneNumber,email,studentId);

    }
}
