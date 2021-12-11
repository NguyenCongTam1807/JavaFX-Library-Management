package services;

import configs.JdbcUtils;
import pojo.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookService {
    public boolean addBook(Book book){
        try(Connection conn= JdbcUtils.getConn()){
            conn.setAutoCommit(false);
            if(checkBookId(book.getId())==null){
                PreparedStatement stm=conn.prepareStatement("INSERT INTO book"+
                        "(book_id,title,amount,author,published_year,genre,publisher,summary)"+
                        "VALUES (?,?,?,?,?,?,?,?)");
                stm.setInt(1,book.getId());
                stm.setString(2,book.getTitle());
                stm.setInt(3,book.getAmount());
                stm.setString(4,book.getAuthor());
                stm.setInt(5,book.getPublishedYear());
                stm.setString(6,book.getGenre());
                stm.setString(7, book.getPublisher());
                stm.setString(8, book.getSummary());
                stm.executeUpdate();
            }
            else {
                PreparedStatement stm=conn.prepareStatement("UPDATE book SET amount=?+? where book_id=?");
                stm.setInt(1,book.getAmount());
                stm.setInt(2,checkBookId(book.getId()).getAmount());
                stm.setInt(3,book.getId());
                stm.executeUpdate();
            }
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
    public Book checkBookId(int id){
        try(Connection conn=JdbcUtils.getConn()){
            PreparedStatement stm=conn.prepareStatement("SELECT * FROM book WHERE book_id=?");
            stm.setInt(1,id);
            ResultSet rs = stm.executeQuery();
            Book book=null;
            if(rs.next()){
                book=new Book(rs.getInt("book_id"), rs.getString("title"),rs.getInt("amount"),rs.getString("author"),rs.getInt("published_year"),rs.getString("genre"),rs.getString("publisher"),rs.getString("summary") );
            }
            return book;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
