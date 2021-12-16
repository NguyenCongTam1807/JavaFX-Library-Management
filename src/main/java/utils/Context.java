package utils;

import ui.BookReturnController;
import ui.LoginLoader;
import ui.MainController;
import ui.SignUpController;

public class Context {
    private final static Context instance = new Context();
    public static Context getInstance() {
        return instance;
    }

    ////////////////////////////////////////
    private LoginLoader loginLoader;

    public void setLoginLoader( LoginLoader loginLoader) {
        this.loginLoader=loginLoader;
    }

    public  LoginLoader getLoginLoader() {
        return loginLoader;
    }

    ////////////////////////////////////////
    private SignUpController signUpController;

    public SignUpController getSignUpController() {
        return signUpController;
    }

    public void setSignUpController(SignUpController signUpController) {
        this.signUpController = signUpController;
    }

    ////////////////////////////////////////
    private MainController mainController;

    public MainController getMainController() {
        return mainController;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    ////////////////////////////////////////
    private BookReturnController bookReturnController;

    public BookReturnController getBookReturnController() {
        return bookReturnController;
    }

    public void setBookReturnController(BookReturnController bookReturnController) {
        this.bookReturnController = bookReturnController;
    }
}
