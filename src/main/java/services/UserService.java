package services;

import configs.JdbcUtils;
import pojo.User;

import java.sql.*;
import java.util.*;

public class UserService {
    public boolean addUser(User user) {
        try (Connection conn = JdbcUtils.getConn()) {
            conn.setAutoCommit(false);
            PreparedStatement stm = conn.prepareStatement("INSERT INTO user " +
                    "(account_id,password,status,name,birthday,phone_number,email,student_id)" +
                    " VALUES(?,?,?,?,?,?,?,?)");
            stm.setString(1,user.getAccountId());
            stm.setString(2,user.getPassword());
            stm.setInt(3,user.getStatus());
            stm.setString(4,user.getName());
            stm.setDate(5, user.getBirthday());
            stm.setString(6,user.getPhoneNumber());
            stm.setString(7,user.getEmail());
            stm.setString(8,user.getStudentId());
            stm.executeUpdate();
            conn.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        return true;
    }

    public User getUser(String account, String password){
        try(Connection conn = JdbcUtils.getConn()){
            PreparedStatement stm=conn.prepareStatement("SELECT * from user where account_id=? AND password = ?");
            stm.setString(1,account);
            stm.setString(2,password);
            ResultSet rs = stm.executeQuery();
            if(rs.next()) {
                return new User(rs.getInt("user_id"),rs.getString("account_id"), rs.getString("password"),
                        rs.getByte("status"), rs.getString("name"), rs.getDate("birthday"),
                        rs.getString("phone_number"), rs.getString("email"), rs.getString("student_id"));
            }
            return null;
        }catch (SQLException throwables){
            throwables.printStackTrace();
            return null;
        }
    }

    public List<User> getStudentUsers(){
        try(Connection conn=JdbcUtils.getConn()){
            PreparedStatement stm=conn.prepareStatement("SELECT * FROM user WHERE student_id != ? OR student_id is not null ");
            stm.setString(1,"");
            ResultSet rs = stm.executeQuery();
            List<User> users=new ArrayList<>();
            while (rs.next()){
                users.add(new User(rs.getInt("user_id"),rs.getString("account_id"), rs.getString("password"),rs.getInt("status"),
                        rs.getString("name"),rs.getDate("birthday"),rs.getString("phone_number"),
                        rs.getString("email"), rs.getString("student_id") ));
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteUser(int id) {
        try (Connection conn = JdbcUtils.getConn()) {
            conn.setAutoCommit(false);
            PreparedStatement stm = conn.prepareStatement("DELETE FROM user WHERE user_id = ?");
            stm.setInt(1,id);
            stm.executeUpdate();
            conn.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean changePass(User user,String newPass ){
        try(Connection conn=JdbcUtils.getConn()){
            conn.setAutoCommit(false);
            PreparedStatement stm=conn.prepareStatement("UPDATE user SET password=? WHERE user_id=?");
            stm.setString(1,newPass);
            stm.setInt(2,user.getId());
            stm.executeUpdate();
            conn.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean changeInforUser(User user,String mobile,String email){
        try(Connection conn = JdbcUtils.getConn()){
            conn.setAutoCommit(false);
            PreparedStatement stm=conn.prepareStatement("UPDATE user SET phone_number=?,email=? WHERE user_id=?");
            stm.setString(1,mobile);
            stm.setString(2,email);
            stm.setInt(3,user.getId());
            stm.executeUpdate();
            conn.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
