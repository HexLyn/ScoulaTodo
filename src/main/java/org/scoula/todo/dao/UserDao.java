package org.scoula.todo.dao;

import org.scoula.todo.domain.UserVO;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

//Dao에선 틀만 구현해 놓고, Imp에서 세부 데이터들을
public interface UserDao {
    int create(UserVO user) throws SQLException;

    List<UserVO> getList() throws SQLException;

//    존재하지 않는 경우 빈 Optional 객체 반환
    Optional<UserVO> get(String id) throws SQLException;

    int update(UserVO user) throws SQLException;

    int delete(String id) throws SQLException;
// CRUD에서 보통 R은 두 개를 만든다.
}
