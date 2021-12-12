package pojo;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import java.util.Date;

public class Issue extends RecursiveTreeObject<Issue> {
    private int id,userId;
    private Date date,returnDueDate;

    public Issue(){};

    public Issue(int id, int userId, Date date, Date returnDueDate){
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.returnDueDate = returnDueDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getReturnDueDate() {
        return returnDueDate;
    }

    public void setReturnDueDate(Date returnDueDate) {
        this.returnDueDate = returnDueDate;
    }
}
