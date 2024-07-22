package org.scoula.todo.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCUtill {
//     static으로 써서 본 java db에서 사용함을 의미.
    static Connection conn = null;

//     정적 초기화 블록을 사용하여 jdbc 드라이버를 로드하고 db연결 설정.
    static {
        try {
//            properties 객체를 생성하고 application.properties파일 로드.
            Properties properties = new Properties();
            properties.load(JDBCUtill.class.getResourceAsStream("/application.properties"));

//            프로퍼티 파일에서 driver, url, id, password를 가져옴.
            String driver = properties.getProperty("driver");
            String url = properties.getProperty("url");
            String id = properties.getProperty("id");
            String password = properties.getProperty("password");

//            MySQL JDBC 드라이버 로드.
            Class.forName(driver);

//            drivermanager를 사용해 db 연결 객체 생성.
            conn = DriverManager.getConnection(url, id, password);

        } catch (Exception e) {
//            예외 발생 시 stacktrace 출력.
            e.printStackTrace();
        }
    }

//    db 연결 객체를 반환하는 메서드
    public static Connection getConnection() {
        return conn;
    }

//    db 연결을 닫는 메서드
    public static void close() {
        try {
//            conn이 null이 아닐 때 닫아준다.(null이면 닫을 필요 없어서)
            if (conn != null) {
                conn.close();
                conn = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}