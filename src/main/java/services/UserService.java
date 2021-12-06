package services;

import configs.JdbcUtils;
import pojo.User;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
        }
        return true;
    }
}
