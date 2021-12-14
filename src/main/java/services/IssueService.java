package services;

import configs.JdbcUtils;
import pojo.Issue;
import pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IssueService {
    public List<Issue> getIssues(String account){
        try(Connection conn = JdbcUtils.getConn()){
            PreparedStatement stm=conn.prepareStatement("SELECT * from issue WHERE user_id IN " +
                    "SELECT user_id FROM user WHERE account_id=?");
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

}
