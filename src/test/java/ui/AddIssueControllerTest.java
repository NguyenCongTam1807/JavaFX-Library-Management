package ui;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pojo.IssueDetail;
import pojo.User;
import services.IssueDetailService;
import services.IssueService;
import services.UserService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AddIssueControllerTest {

    private UserService us=new UserService();

    private List<User> users=us.getStudentUsers();


    AddIssueController addIssueController = new AddIssueController();

    @ParameterizedTest
    @CsvSource({"1,0","2,1","3,-1"})
    public void checkBookTest(String id, int expectedIndex){
        Assertions.assertEquals(addIssueController.checkBook(id),expectedIndex);
    }

    @ParameterizedTest
    @CsvSource({"1,0","3,1","9,4", "2,-1"})
    public void checkUsernameTest(String id, int expectedIndex){
        Assertions.assertEquals(addIssueController.checkUsername(id),expectedIndex);
    }


}