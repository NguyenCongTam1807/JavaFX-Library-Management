package ui;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class AddBookControllerTest {

    AddBookController controller=new AddBookController();

    @ParameterizedTest
    @CsvSource({"true,2012", "false,1205251", "false,20@4", "false,201"})
    void validYear(boolean expected,String year) {
        Assertions.assertEquals(controller.validYear(year),expected);
    }

    @ParameterizedTest
    @CsvSource({"Dang Khoa,Dang    Khoa", "Dang Khoa,Dang  Khoa", "Dang Khoa,Dang                         Khoa"})
    void clearSpace(String expected,String s) {
        Assertions.assertEquals(controller.clearSpace(s),expected);
    }

    @ParameterizedTest
    @CsvSource({"false,100", "true,1!0", "true,af@4", "true,01dg"})
    void validAmount(boolean expected,String amount) {
        Assertions.assertEquals(controller.validAmount(amount),expected);

    }
}