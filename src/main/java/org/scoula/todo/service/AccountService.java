package org.scoula.todo.service;

import org.scoula.todo.dao.UserDao;
import org.scoula.todo.dao.UserDaoImpl;
import org.scoula.todo.domain.UserVO;
import org.scoula.todo.exception.PasswordMissmatchException;
import org.scoula.todo.exception.UsernameDuplicateException;
import org.scoula.todo.ui.Input;

import java.sql.SQLException;
import java.util.Optional;

public class AccountService {
    UserDao dao = new UserDaoImpl();

    public void join() {
        try {
            UserVO user = getUser();
            dao.create(user);
        } catch (UsernameDuplicateException | PasswordMissmatchException e) {
            System.out.println(e.getMessage());
        } catch ( Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkDuplicate(String username) throws UsernameDuplicateException, SQLException {
        Optional<UserVO> result = dao.get(username);
        if(result.isPresent()) {
            throw new UsernameDuplicateException();
        }
        return false;
    }
    private UserVO getUser() throws SQLException, UsernameDuplicateException, PasswordMissmatchException {
        String username = Input.getLine("User ID : ", "Enter username : ");
        checkDuplicate(username);

        String password = Input.getLine("Password", "input password");
        String password2 = Input.getLine("Check Password", "input password");
        if(!password.equals(password2)) {
            throw new PasswordMissmatchException();
        }

        String name = Input.getLine("Name : ", "input name");
        String role = Input.getLine("Role : ", "input role");

        return UserVO.builder()
                .id(username)
                .password(password)
                .name(name)
                .role(role)
                .build();
    }
}
