package org.scoula.todo.dao;

import org.junit.jupiter.api.*;
import org.scoula.todo.common.JDBCUtill;
import org.scoula.todo.domain.UserVO;
import org.scoula.todo.dao.UserDaoImpl;
import org.scoula.todo.dao.UserDao;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserDaoTest {
    UserDao dao = new UserDaoImpl();

    @AfterAll
    static void tearDown() {
        JDBCUtill.close();
    }

    @Test
    @DisplayName("user enlist.")
    @Order(1)
    void create() throws SQLException {
        UserVO user = new UserVO("ssamz3", "ssamz123", "쌤즈", "ADMIN");
        int count = dao.create(user);
        assertEquals(1, count);
    }

    @Test
    @DisplayName("UserDao User extract")
    @Order(2)
    void getList() throws SQLException {
        List<UserVO> users = dao.getList();
        for(UserVO vo : users) {
            System.out.println(vo);
        }
    }

    @Test
    @DisplayName("specific user extract")
    @Order(3)
    void get() throws SQLException {
        UserVO user = dao.get("ssamz3").orElseThrow(NoSuchElementException::new);
        Assertions.assertNotNull(user);
    }

    @Test
    @DisplayName("update user info")
    @Order(4)
    void update() throws SQLException {
        UserVO user = dao.get("ssamz3").orElseThrow(NoSuchElementException::new);
        user.setName("쌤즈3");
        int count = dao.update(user);
        Assertions.assertEquals(1, count);
    }

    @Test
    @DisplayName("delete user")
    @Order(5)
    void delete() throws SQLException {
        int count = dao.delete("ssamz3");
        Assertions.assertEquals(1, count);
    }
}