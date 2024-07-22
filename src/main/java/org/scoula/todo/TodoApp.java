package org.scoula.todo;

import org.scoula.todo.context.Context;
import org.scoula.todo.exception.LoginFailException;
import org.scoula.todo.service.*;
import org.scoula.todo.ui.Input;

import java.awt.*;
import java.sql.SQLException;

public class TodoApp extends App{
    Menu userMenu;
    Menu anonymousMenu;

    LoginService loginService = new LoginService();
    AccountService accountService = new AccountService();

    @Override
    public void init() {
        anonymousMenu = new Menu();
        anonymousMenu.add(new MenuItem("Login", this::login));
        anonymousMenu.add(new MenuItem("Register", accountService::join));
        anonymousMenu.add(new MenuItem("Terminate", this::exit));

        userMenu = new Menu();

        userMenu.add(new MenuItem("List", todoService::print));
        userMenu.add(new MenuItem("Search", todoService::search));
        userMenu.add(new MenuItem("Desc", todoService::detail));
        userMenu.add(new MenuItem("Create", todoService::create));
        userMenu.add(new MenuItem("Update", todoService::update));
        userMenu.add(new MenuItem("Delete", todoService::delete));

        userMenu.add(new MenuItem("Logout", this::logout));
        userMenu.add(new MenuItem("Terminate", this::exit));

        setMenu(anonymousMenu);
    }

    public void join(){}
    public void login(){
        try {
            loginService.login();
            setMenu(userMenu);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (LoginFailException e) {
            System.out.println(e.getMessage());
        }
    }
    public void logout(){
        if(Input.confirm("Logout?")) {
            Context.getContext().setUser(null);
            setMenu(anonymousMenu);
        }
    }

    public static void main(final String[] args) {
        final TodoApp app = new TodoApp();
        app.run();
    }
}
