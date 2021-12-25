//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ui;

import com.jfoenix.controls.JFXTextField;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;
import utils.Context;

import java.time.LocalDate;

public class SignUpControllerTest {
    SignUpController controller = new SignUpController();
    public SignUpControllerTest() {
    }
    public static boolean validMobile(String mb){
        if (mb.indexOf("00")==0){
            return false;
        }
        else if (mb.indexOf("0")==0){
            mb=mb.substring(1);
            //$mobile.setText(mb);
            return validMobile(mb);
        }
        else if (mb.length()==9){
            return mb.matches("-?\\d+?");
        }

        return false;
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

    @ParameterizedTest
    @CsvSource({"true,2001-05-05","false,2020-03-06","false,1850-03-06"})
    public void validBirthday(boolean expected, LocalDate date){
        Assertions.assertEquals(controller.validBirthday(date),expected);
    }

    @ParameterizedTest
    @CsvSource({"true,1951012038","false,22223","false,m232232323","false,29292929292929","true,1234567890","true,2828282828"})
    public void validStudentId(boolean expected, String studentId){
        Assertions.assertEquals(controller.validStudentId(studentId),expected);
    }

    @ParameterizedTest
    @CsvSource({"true,0947549001","false,00947549001","true,947549001","false,ja293929999889","false,999999999999999999999999"})
    public void validMobile(boolean expected, String mobile){
        Assertions.assertEquals(validMobile(mobile),expected);
    }

    @ParameterizedTest
    @CsvSource({"true,giahuyoke@gmail.com","false,giai@.com","false,giahuy@","true,giahuy@ui.vn"})
    public void validEmail(boolean expected, String mail){
        Assertions.assertEquals(controller.validEmail(mail),expected);
    }

    @ParameterizedTest
    @CsvSource({"Huy,hUy","Dddddd,DDDDDD","Asdd,aSdD","212mm,212mm"})
    public void upperCaseFirstLetter(String expected,String s){
        Assertions.assertEquals(controller.upperCaseFirstLetter(s),expected);
    }

    @ParameterizedTest
    @CsvSource({"Luu Gia Huy,luu     gia   hUY","Cong Tam,cong     taM"})
    public void styleString(String expected,String s){
        Assertions.assertEquals(controller.styleString(s),expected);
    }

    @ParameterizedTest
    @CsvSource({"Dang Dat,Đặng Đạt","Cong Tam,Công Tâm","Dang Khoa,Dang Khoa"})
    public void unAccent(String expected,String s){
        Assertions.assertEquals(controller.unAccent(s),expected);
    }
}
