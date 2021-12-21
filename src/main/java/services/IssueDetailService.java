package services;

import configs.JdbcUtils;
import pojo.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IssueDetailService {

    public int notReturnedBookCount(String userId) {
        try(Connection conn = JdbcUtils.getConn()){
            PreparedStatement stm=conn.prepareStatement("SELECT Count(*) AS not_returned_count FROM issue_details " +
                    "WHERE issue_id IN " +
                        "(SELECT issue_id FROM issue WHERE user_id = ?)"+
                    "AND return_date IS NULL" +
                    "GROUP BY issue_id");
            stm.setString(1,userId);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt(0);
            }
            return 0;
        }catch (SQLException throwables){
            throwables.printStackTrace();
            return 0;
        }
    }
    public List<Book> notReturnedBooks(String userId) {
        try(Connection conn = JdbcUtils.getConn()){
            PreparedStatement stm=conn.prepareStatement("SELECT * FROM book WHERE book_id IN " +
                    "(SELECT book_id FROM issue_details " +
                    "WHERE issue_id IN " +
                    "(SELECT issue_id FROM issue WHERE user_id = ?)"+
                    "AND return_date IS NULL" +
                    "GROUP BY issue_id)");
            stm.setString(1,userId);
            ResultSet rs = stm.executeQuery();
            List<Book> books = new ArrayList<>();
            while (rs.next()) {
                books.add(new Book(rs.getInt("book_id"),rs.getString("title"),
                        rs.getInt("amount"),rs.getString("author"),
                        rs.getInt("published_year"),rs.getString("genre"),
                        rs.getString("publisher"),rs.getString("summary") ));
            }
            return books;
        }catch (SQLException throwables){
            throwables.printStackTrace();
            return null;
        }
    }
    public int addIssueDetail(List<Integer> bookIDs, Integer issueId){
        try(Connection conn = JdbcUtils.getConn()){
            conn.setAutoCommit(false);
            int rowsToAdd = bookIDs.size();
            int i=0;
            String query = "INSERT INTO issue_details " +
                    "(book_id, issue_id)" +
                    " VALUES(?,?)";
            for (;i<rowsToAdd-1;i++)
                query += ",(?,?)";
            PreparedStatement stm = conn.prepareStatement(query);
            for (i=0;i<rowsToAdd;i++) {
                stm.setInt(i*2+1, bookIDs.get(i));
                stm.setInt(i*2+2, issueId);
            }
            stm.executeUpdate();
            conn.commit();
            return i;
        }catch (SQLException throwables){
            throwables.printStackTrace();
            return 0;
        }
    }
    
}
