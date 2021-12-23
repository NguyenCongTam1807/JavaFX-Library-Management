package ui;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pojo.User;
import services.UserService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import java.awt.*;
import java.sql.Date;

public class SignUpControllerTest {

    @ParameterizedTest
    @CsvSource({"0,congtam ","1,a bcd","2,abcd!!","3,congtamcongtamcongtam"})
    public void checkUsername(int expected, String username) {
        Assertions.assertEquals(SignUpController.checkUsername(username),expected);
    }

    @ParameterizedTest
    @CsvSource({"false,congtam ","false,congtamcongtamcongtamcongtamcongtamcongtamcongtamcongtamcongtamcongtamcongtamcong"
            ,"true,abcd!!","true,abcdef1"})
    public void validName(boolean expected, String username) {
        Assertions.assertEquals(SignUpController.validName(username),expected);
    }


}
