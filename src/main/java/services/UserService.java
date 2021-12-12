package services;

import configs.JdbcUtils;
import pojo.User;

import java.sql.*;
import java.util.List;

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

    public User getUser(String account,String password,boolean isLibrarian ){
        try(Connection conn = JdbcUtils.getConn()){
            PreparedStatement stm=conn.prepareStatement("SELECT * from user where account_id=?");
            stm.setString(1,account);
            ResultSet rs = stm.executeQuery();
            User user=null;
            if(rs.next()) {
                user = new User(rs.getString("account_id"), rs.getString("password"), rs.getByte("status"), rs.getString("name"), rs.getDate("birthday"), rs.getString("phone_number"), rs.getString("email"), rs.getString("student_id"));
                if(user.getPassword().equals(password))
                {
                    //if(user.getStudentId().isEmpty()==isLibrarian )
                    if((user.getStudentId()==null)==isLibrarian )
                        return user;
                }
            }
                return  null;
        }catch (SQLException throwables){
            throwables.printStackTrace();
            return null;
        }

    }

}
