package utils;

import ui.LoginLoader;
import ui.SignUpController;

import java.sql.Connection;

public class Context {
    private final static Context instance = new Context();
    public static Context getInstance() {
        return instance;
    }

//    private Connection con;
//    public void setConnection(Connection con)
//    {
//        this.con=con;
//    }
//    public Connection getConnection() {
//        return con;
//    }

    private LoginLoader loginLoader;
    public void setLoginLoader( LoginLoader loginLoader) {
        this.loginLoader=loginLoader;
    }
    public  LoginLoader getLoginLoader() {
        return loginLoader;
    }

    private SignUpController signUpController;
    public SignUpController getSignUpController() {
        return signUpController;
    }
    public void setSignUpController(SignUpController signUpController) {
        this.signUpController = signUpController;
    }
}
