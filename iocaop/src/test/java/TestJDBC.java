import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestJDBC {

    @Test
    public void getConn() throws SQLException {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/samp_db";
        String username = "root";
        String password = "";
        java.sql.Connection conn = null;

        //Class.forName(driver);
        conn = (Connection) DriverManager.getConnection(url, username, password);

        System.out.println(conn);
    }
}
