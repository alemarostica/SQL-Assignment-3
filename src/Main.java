import javax.swing.plaf.nimbus.State;
import java.sql.*;
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


        /*Statement stmt = db.createStatement();

        String sqlCheck = "DROP TABLE IF EXISTS student";
        String sql = "CREATE TABLE student"
                + "(id INTEGER not NULL, "
                + "name VARCHAR(255), "
                + "surname VARCHAR(255), "
                + "birthplace VARCHAR(255), "
                + "PRIMARY KEY (id));";

        stmt.executeUpdate(sqlCheck);
        stmt.executeUpdate(sql);

        System.out.println("Table created");*/

        /*String sqlInsert = "INSERT INTO student (id, name, surname, birthplace) "
                + "VALUES (183475, 'Mario', 'Rossi', 'Trento');";
        stmt.executeUpdate(sqlInsert);
        System.out.println("Added");*/

        /*String query = "INSERT INTO student (id, name, surname, birthplace) "
                + "VALUES (?, ?, ?, ?);";
        PreparedStatement st = db.prepareStatement(query);
        st.setObject(1, 123456);
        st.setObject(2, "Marco");
        st.setObject(3, "Sbizzera");
        st.setObject(4, "Napoli");
        st.executeUpdate();
        st.close();*/

        /*Statement st = db.createStatement();
        ResultSet rs = st.executeQuery(
                "SELECT * FROM student;"
        );

        while(rs.next()){
            System.out.println("ID: " + rs.getInt(1));
            System.out.println("Name: " + rs.getString("name"));
            System.out.println("Surname: " + rs.getString("surname"));
            System.out.println("Birthplace: " + rs.getString(4) + '\n');
        }

        rs.close();
        st.close();
        */

        /*String surname = "Neri";
        PreparedStatement pst = db.prepareStatement(
                "SELECT * FROM student " +
                        "WHERE surname = ?;"
        );

        pst.setString(1, surname);
        ResultSet rs = pst.executeQuery();
        while(rs.next()){
            System.out.println("ID: " + rs.getInt(1));
            System.out.println("Name: " + rs.getString("name"));
            System.out.println("Surname: " + rs.getString("surname"));
            System.out.println("Birthplace: " + rs.getString(4) + '\n');
        }*/



        db.close();
    }
}