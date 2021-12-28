package ui;

import com.jfoenix.controls.JFXTabPane;
import com.sun.tools.javac.Main;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import services.BookService;
import services.IssueService;
import services.UserService;

class MainControllerTest {

    IssueService is = new IssueService();
    BookService bs = new BookService();
    UserService us = new UserService();
    MainController mainController = new MainController();
    @Test
    public void testTotalRowCounts() {
        int[] rows = {5,2,14};
        Assertions.assertArrayEquals(rows,new int[]{is.getNumberOfIssues(),bs.getNumberOfBooks(),us.getNumberOfStudents()});
    }

    @ParameterizedTest
    @CsvSource({"1,1","2,1","3,3","4,0"})
    public void testIssueCounts(int userId, int expected ) {
        Assertions.assertEquals(expected,is.getNumberOfIssues(userId));
    }



//
//    @Test
//    void showInfo() {
//    }
}