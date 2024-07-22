package org.scoula.todo.dao;

import org.scoula.todo.domain.TodoVO;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface TodoDao {
    int getTotalCount(String userId) throws SQLException;
    int create(TodoVO todo) throws SQLException;
    List<TodoVO> getList(String userId) throws SQLException;
    Optional<TodoVO> get(String userId, Long id) throws SQLException;
    List<TodoVO> search(String userId, String keyword) throws SQLException;
    int update(String userId, TodoVO todo) throws SQLException;
    int delete(String userId, Long id) throws SQLException;
}


