package services;

import configs.JdbcUtils;
import pojo.Issue;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IssueService {

    public List<Issue> getIssues(){
        try(Connection conn = JdbcUtils.getConn()){
            PreparedStatement stm=conn.prepareStatement("SELECT * from issue");
            ResultSet rs = stm.executeQuery();
            List<Issue> issues = new ArrayList<>();
            while(rs.next()) {
                issues.add(new Issue(rs.getInt("issue_id"),rs.getInt("user_id"),
                        rs.getDate("issue_date"),rs.getDate("return_due_date")));
            }
            return issues;
        }catch (SQLException throwables){
            throwables.printStackTrace();
            return null;
        }
    }
    public boolean addIssue(Issue issue){
        try(Connection conn = JdbcUtils.getConn()){
            conn.setAutoCommit(false);
            PreparedStatement stm = conn.prepareStatement("INSERT INTO issue " +
                    "(user_id,issue_date,return_due_date)" +
                    " VALUES(?,?,?)");
            stm.setInt(1,issue.getUserId());
            stm.setDate(2, issue.getDate());
            stm.setDate(3,issue.getReturnDueDate());
            stm.executeUpdate();
            conn.commit();
            return true;
        }catch (SQLException throwables){
            throwables.printStackTrace();
            return false;
        }
    }
    public boolean addIssue(int userId, Date issueDate, Date returnDueDate){
        try(Connection conn = JdbcUtils.getConn()){
            conn.setAutoCommit(false);
            PreparedStatement stm = conn.prepareStatement("INSERT INTO issue " +
                    "(user_id,issue_date,return_due_date)" +
                    " VALUES(?,?,?)");
            stm.setInt(1,userId);
            stm.setDate(2,issueDate);
            stm.setDate(3,returnDueDate);
            stm.executeUpdate();
            conn.commit();
            return true;
        }catch (SQLException throwables){
            throwables.printStackTrace();
            return false;
        }
    }
}
