import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:postgresql://sci-didattica.unitn.it/db_170";

        Properties props = new Properties();
        props.setProperty("user", "db_170");
        props.setProperty("password", "pass_170");
        props.setProperty("ssl", "false");

        Connection db = DriverManager.getConnection(url, props);
        System.out.println("Connected");

        db.close();
    }
}