import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by SiGae on 2016-05-27.
 */
public class dbconnect {
    public static Connection makeConnection() {
        dbconnect_private pri = new dbconnect_private();
        String url = pri.url;
        String id = pri.id;
        String pw = pri.pw;

        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("드라이버 적재 성공");
            con = DriverManager.getConnection(url, id, pw);
            System.out.println("데이터베이스 연결 성곷");
        } catch (ClassNotFoundException e) {
            System.out.println("notfounddriver");
        } catch (SQLException e) {
            System.out.println("fail");
        }
        return con;
    }
}