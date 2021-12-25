//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ui;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;
import utils.Context;

import java.time.LocalDate;

public class SignUpControllerTest {
    SignUpController controller = Context.getInstance().getSignUpController();
    public SignUpControllerTest() {
    }

    @ParameterizedTest
    @CsvSource({"0,congtam ", "1,a bcd", "2,abcd!!", "3,congtamcongtamcongtam"})
    public void checkUsername(int expected, String username) {
        Assertions.assertEquals(SignUpController.checkUsername(username), expected);
    }

    @ParameterizedTest
    @CsvSource({"false,congtam ", "false,congtamcongtamcongtamcongtamcongtamcongtamcongtamcongtamcongtamcongtamcongtamcong", "true,abcd!!", "true,abcdef1"})
    public void validName(boolean expected, String username) {
        Assertions.assertEquals(SignUpController.validName(username), expected);
    }

    @ParameterizedTest
    @CsvSource({"true,Khiem@288","false,Khie@","false,Khiem@","false,khiem288","true,Khiemmmmmmmmm28282822992!","true,Khiem<288"})
    public void checkPassword(boolean expected, String pw){
        Assertions.assertEquals(SignUpController.checkPassword(pw),expected);
    }

   /* @ParameterizedTest
    @CsvSource({"true,2001/05/05"})
    public void validBirthday(boolean expected, LocalDate @ConvertWith(SlashyDateConverter.class)date){
        Assertions.assertEquals(controller.validBirthday(date),expected);
    }
*/
    @ParameterizedTest
    @CsvSource({"true,1951012038","false,22223","false,m232232323","false,29292929292929","true,1234567890","true,2828282828"})
    public void validStudentId(boolean expected, String studentId){
        Assertions.assertEquals(controller.validStudentId(studentId),expected);
    }

    @ParameterizedTest
    @CsvSource({})
    public void validMobile(){

    }
}
