package org.scoula.todo.dao;

import org.scoula.todo.common.JDBCUtill;
import org.scoula.todo.domain.UserVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//UserDao 인터페이스를 구현하여 사용자 정보에 대한 실제 CRUD 작업 수행
public class UserDaoImpl implements UserDao {
//    JDBCUtill에서 이미 만들어 놓았으니 가져오기만 하면 된다.
    Connection conn = JDBCUtill.getConnection();

//    private으로 SQL명령어를 미리 적어 놓고, 아래에서 넣어주기만 한다.
    private String USER_LIST = "select * from user";
    private String USER_GET = " select * from user where id = ?";
    private String USER_INSERT = "insert into user values(?,?,?,?)";
    private String USER_UPDATE = "update user set name = ?, role = ? where id = ?";
    private String USER_DELETE = "delete from user where id = ?";

    @Override
    public int create(UserVO user) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(USER_INSERT)) {
            stmt.setString(1, user.getId());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getName());
            stmt.setString(4, user.getRole());
            return stmt.executeUpdate();
        }
    }
//    map: mapping, 모든 요소들을 돌면서 매칭, 변환해주는 것.
//    전부 동일하게 적용. 파라미터로 ResultSet을 받아오고 있다. 테이블(표) 형태로 들어옴.
//    들어온 것(테이블 형태)을 UserVO(객체)형태로 바꿔준다.
//    ResultSet을 UserVO형태로 매핑(변환)하는 메서드.
    private UserVO map(ResultSet rs) throws SQLException {
        UserVO user = new UserVO();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));
        user.setRole(rs.getString("role"));
        return user;
    }

//    회원 목록 조회
    @Override
    public List<UserVO> getList() throws SQLException {
        List<UserVO> userList = new ArrayList<>();
        Connection conn = JDBCUtill.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(USER_LIST)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
//                next를 사용해 ResultSet의 마지막 행까지 데이터를 가져온다.
                UserVO user = map(rs);
//              결과값으로 받아온 ResultSet을 UserVO로 변환.
                userList.add(user);
//              변환된 UserVO를 리스트에 추가.
            }
        }
        return userList;
    }

//    회원 정보 조회
    @Override
    public Optional<UserVO> get(String id) throws SQLException {
        try(PreparedStatement stmt = conn.prepareStatement(USER_GET)) {
            stmt.setString(1, id);
            try(ResultSet rs = stmt.executeQuery()) {
//                어차피 특정 id의 정보를 불러오기 때문에 데이터 하나만 가져옴
//                그래서 if문 씀
                if (rs.next()) {
//                    of사용 : 팩토리 메서드.
//                    팩토리 메서드 사용해서 UserVO를 Optional 객체로 바꿔줌.
//                    map에서는 rs(ResultSet)을 UserVO로 바꿔줌 -> of에선 Optional 객체로 변환.
                    return Optional.of(map(rs));
                }
            }
        }
//        해당 아이디를 가진 회원이 없을 때 도달하는 코드 = 빈 객체 반환.
        return Optional.empty();
    }

//    회원 수정
    @Override
    public int update(UserVO user) throws SQLException {
        Connection conn = JDBCUtill.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(USER_UPDATE)) {
//            특정 아이디를 가진 회원의 이름과 역할 수정.
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getRole());
            stmt.setString(3, user.getId());
            return stmt.executeUpdate();
        }
    }

//    회원 삭제
    @Override
    public int delete(String id) throws SQLException {
//        특정 아이디를 가진 회원을 삭제.
        try(PreparedStatement stmt = conn.prepareStatement(USER_DELETE)) {
            stmt.setString(1, id);
            return stmt.executeUpdate();
        }
    }
}
