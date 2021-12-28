package services;

import configs.JdbcUtils;
import pojo.Book;
import pojo.Issue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public boolean upDateBook(Book book){
        try(Connection conn=JdbcUtils.getConn()){
            conn.setAutoCommit(false);
            PreparedStatement stm=conn.prepareStatement("UPDATE book Set title=?,amount=?,author=?,published_year=?,genre=?,publisher=?,summary=? WHERE book_id=?");
            stm.setString(1,book.getTitle());
            stm.setInt(2,book.getAmount());
            stm.setString(3,book.getAuthor());
            stm.setInt(4,book.getPublishedYear());
            stm.setString(5,book.getGenre());
            stm.setString(6,book.getPublisher());
            stm.setString(7,book.getSummary());
            stm.setInt(8,book.getId());
            stm.executeUpdate();
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

    public Book checkBookTitle(String title){
        try(Connection conn=JdbcUtils.getConn()){
            PreparedStatement stm=conn.prepareStatement("SELECT * FROM book WHERE title=?");
            stm.setString(1,title);
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

    public List<Book> getBooks(){
        try(Connection conn = JdbcUtils.getConn()){
            PreparedStatement stm=conn.prepareStatement("SELECT * from book");
            ResultSet rs = stm.executeQuery();
            List<Book> books = new ArrayList<>();
            while(rs.next()) {
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

    public List<Book> getIssuedBooks(int issueID) {
        try(Connection conn = JdbcUtils.getConn()){
            PreparedStatement stm=conn.prepareStatement("SELECT * FROM book WHERE book_id IN " +
                    "(SELECT book_id FROM issue_details " +
                        "WHERE issue_id = ? )"
                    );
            stm.setInt(1,issueID);
            ResultSet rs = stm.executeQuery();
            List<Book> books = new ArrayList<>();
            while(rs.next()) {
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

    public void deleteBook(int bookId){
        try(Connection conn=JdbcUtils.getConn()) {
            conn.setAutoCommit(false);
            PreparedStatement stm=conn.prepareStatement("DELETE FROM book WHERE book_id=?");
            stm.setInt(1,bookId);
            stm.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getNumberOfBooks(){
        int quantity=0;
        try(Connection conn=JdbcUtils.getConn()){
            PreparedStatement stm=conn.prepareStatement("SELECT COUNT(book_id) FROM book");
            ResultSet rs=stm.executeQuery();
            while(rs.next()) {
                quantity=rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quantity;
    }

}
