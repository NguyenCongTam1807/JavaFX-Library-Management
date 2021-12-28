package ui;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pojo.IssueDetail;
import services.IssueDetailService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookReturnControllerTest {

    private IssueDetailService ids = new IssueDetailService();

    @ParameterizedTest
    @CsvSource({"1,1","2,1","3,2", "4,0"})
    public void getIssueDetailsTest(int issueId, int expectedBookCount){
        List<IssueDetail> issueDetails = ids.getIssueDetail(issueId);
        Assertions.assertEquals(expectedBookCount,issueDetails.size());
    }

}