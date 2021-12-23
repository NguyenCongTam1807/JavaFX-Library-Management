package pojo;


import java.sql.Date;

public class IssueDetail {
    private int bookId,issueId;
    private Date returnDate;
    private String bookState;

    public IssueDetail(int bookId,int issueId,Date returnDate,String bookState){
        this.bookId=bookId;
        this.issueId=issueId;
        this.returnDate=returnDate;
        this.bookState=bookState;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getIssueId() {
        return issueId;
    }

    public void setIssueId(int issueId) {
        this.issueId = issueId;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public String getBookState() {
        return bookState;
    }

    public void setBookState(String bookState) {
        this.bookState = bookState;
    }
}
