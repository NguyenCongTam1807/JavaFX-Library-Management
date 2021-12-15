package utils;

import ui.LoginLoader;

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
    public void setLoginLoader( LoginLoader tabRough) {
        this.loginLoader=tabRough;
    }

    public  LoginLoader getLoginLoader() {
        return loginLoader;
    }
}
