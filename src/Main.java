import java.sql.*;
import java.util.*;
import java.util.Date;


public class Main {

    static int idn = 1;
    static class Professor {
        int id;
        String name;
        String address;
        int age;
        float department;

        public Professor() {
            Random rnd = new Random();
            this.id = idn++;
            this.name = UUID.randomUUID().toString().substring(0, 36);
            this.address = UUID.randomUUID().toString().substring(0, 36);
            this.age = rnd.nextInt(18,100);
            this.department = rnd.nextInt(1000000000);
        }
    }

    static class Course {
        String cid;
        String cname;
        String credits;
        int teacher;

        public Course(){
            Random rnd = new Random();
            this.cid = UUID.randomUUID().toString().substring(0, 24);
            this.cname = UUID.randomUUID().toString().substring(0, 36);
            this.credits = UUID.randomUUID().toString().substring(0, 29);
        }
    }
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:postgresql://sci-didattica.unitn.it/db_170";

        Properties props = new Properties();
        props.setProperty("user", "db_170");
        props.setProperty("password", "pass_170");
        props.setProperty("ssl", "false");

        Connection db = DriverManager.getConnection(url, props);
        System.out.println("Connected");

        Statement stmt;
        PreparedStatement pst;
        int time1, time2;
        int count;
        Random rnd = new Random();



        //ex1
        time1 = (int) new Date().getTime();
        stmt = db.createStatement();
        String sqlDelIfEx = "DROP TABLE IF EXISTS professor, course;";
        stmt.executeUpdate(sqlDelIfEx);
        time2 = (int) new Date().getTime();
        System.out.println("Step 1 needs " + (time2 - time1) + " ns");
        stmt.close();

        //ex2
        time1 = (int) new Date().getTime();
        stmt = db.createStatement();
        String sqlCreTab = "CREATE TABLE professor " +
                "(id INTEGER not NULL, " +
                "name VARCHAR(50), " +
                "address VARCHAR(50), " +
                "age INTEGER not NULL, " +
                "department FLOAT not NULL," +
                "PRIMARY KEY (id));" +
                "CREATE TABLE course " +
                "(cid VARCHAR(25), " +
                "cname VARCHAR(50), " +
                "credits VARCHAR(30), " +
                "teacher INTEGER not NULL, " +
                "PRIMARY KEY (cid), " +
                "FOREIGN KEY (teacher) REFERENCES professor(id));";
        stmt.executeUpdate(sqlCreTab);
        time2 = (int) new Date().getTime();
        System.out.println("Step 2 needs " + (time2 - time1) + " ns");
        stmt.close();

        //ex3
        time1 = (int) new Date().getTime();
        String query1 = "INSERT INTO professor (id, name, address, age, department) " +
                "VALUES (?, ?, ?, ?, ?);";
        pst = db.prepareStatement(query1);

        ArrayList<Professor> list = new ArrayList<Professor>();
        for(int i = 0; i < 1000; i++){
            list.add(new Professor());
        }

        count = 0;
        for(Professor prof : list){
            pst.setObject(1, prof.id);
            pst.setString(2, prof.name);
            pst.setString(3, prof.address);
            pst.setObject(4, prof.age);
            if(count != 999) pst.setObject(5, prof.department);
            else pst.setObject(5, 1940);

            pst.addBatch();
            count++;

            if(count % 100 == 0 || count == list.size()){
                pst.executeBatch();
            }
        }

        time2 = (int) new Date().getTime();
        System.out.println("Step 3 needs " + (time2 - time1) + " ns");
        pst.close();

        //ex4
        time1 = (int) new Date().getTime();
        String query2 = "INSERT INTO course (cid, cname, credits, teacher) " +
                "VALUES (?, ?, ?, ?);";
        pst = db.prepareStatement(query2);

        ArrayList<Course> clist = new ArrayList<Course>();
        for(int i = 0; i < 1000; i++){
            clist.add(new Course());
        }

        count = 0;
        for(Course c : clist){
            pst.setString(1, c.cid);
            pst.setString(2, c.cname);
            pst.setString(3, c.credits);
            pst.setObject(4, list.get(rnd.nextInt(list.size())).id);

            pst.addBatch();
            count++;

            if(count % 100 == 0 || count == clist.size()){
                pst.executeBatch();
            }
        }

        time2 = (int) new Date().getTime();
        System.out.println("Step 4 needs " + (time2 - time1) + " ns");
        pst.close();

        //ex5
        time1 = (int) new Date().getTime();
        stmt = db.createStatement();
        ResultSet rsp = stmt.executeQuery(
                "SELECT id FROM professor;"
        );

        while(rsp.next()){
            System.err.println(rsp.getInt(1));
        }

        time2 = (int) new Date().getTime();
        System.out.println("Step 5 needs " + (time2 - time1) + " ns");
        stmt.close();

        //ex6
        time1 = (int) new Date().getTime();
        stmt = db.createStatement();
        String updateDep = "UPDATE professor " +
                "SET department = 1973 " +
                "WHERE department = 1940;";
        stmt.executeUpdate(updateDep);

        time2 = (int) new Date().getTime();
        System.out.println("Step 6 needs " + (time2 - time1) + " ns");
        stmt.close();

        //ex7
        time1 = (int) new Date().getTime();
        stmt = db.createStatement();
        ResultSet rsp2 = stmt.executeQuery(
                "SELECT id, address FROM professor " +
                        "WHERE department = 1973;"
        );

        while(rsp2.next()){
            System.err.print("ID: " + rsp2.getInt(1) + ", ");
            System.err.println("Dep: " + rsp2.getString(2));
        }

        time2 = (int) new Date().getTime();
        System.out.println("Step 7 needs " + (time2 - time1) + " ns");
        stmt.close();

        //ex8

        //ex9
        time1 = (int) new Date().getTime();
        stmt = db.createStatement();
        ResultSet rsp3 = stmt.executeQuery(
                "SELECT id FROM professor;"
        );

        while(rsp3.next()){
            System.err.println(rsp3.getInt(1));
        }

        time2 = (int) new Date().getTime();
        System.out.println("Step 9 needs " + (time2 - time1) + " ns");
        stmt.close();

        //ex10
        time1 = (int) new Date().getTime();
        stmt = db.createStatement();
        String updateDep2 = "UPDATE professor " +
                "SET department = 1974 " +
                "WHERE department = 1973;";
        stmt.executeUpdate(updateDep2);

        time2 = (int) new Date().getTime();
        System.out.println("Step 10 needs " + (time2 - time1) + " ns");
        stmt.close();

        //ex11
        time1 = (int) new Date().getTime();
        stmt = db.createStatement();
        ResultSet rsp4 = stmt.executeQuery(
                "SELECT id, address FROM professor " +
                        "WHERE department = 1974;"
        );

        while(rsp4.next()){
            System.err.print("ID: " + rsp4.getInt(1) + ", ");
            System.err.println("Dep: " + rsp4.getString(2));
        }

        time2 = (int) new Date().getTime();
        System.out.println("Step 11 needs " + (time2 - time1) + " ns");
        stmt.close();

        db.close();
    }
}