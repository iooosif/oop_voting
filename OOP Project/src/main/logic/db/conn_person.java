package main.logic.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * operations with person db
 */
public class conn_person extends conn {
    public static Connection conn;
    public static Statement statmt;
    public static ResultSet resSet;

    /**
     * connecting data base
     * @param name
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    //connecting data base
    public static void Conn(String name) throws ClassNotFoundException, SQLException {
        conn = null;

        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection(name);

        System.out.println("Table connected!");
    }

    /**
     * creating table
     * @param name
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    // creating table
    public static void CreateDB(String name) throws ClassNotFoundException, SQLException {
        statmt = conn.createStatement();
        String str = String.format("CREATE TABLE if not exists '%s' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'name' text, 'family_name'  text, 'day' INTEGER, 'month' INTEGER, 'year' INTEGER, 'pasNum' text, 'password' text);", name);
        statmt.execute(str);

        System.out.println("table created or already exist.");
    }

    /**
     * fulling table
     * @throws SQLException
     */
    // fulling table
    public static void WriteDB() throws SQLException {
        statmt.execute("INSERT INTO 'citizens' ('name', 'family_name','day', 'month','year','pasNum','password') VALUES ('Petya', 'pam','12','5','2000', '1201230', '123'); ");
        statmt.execute("INSERT INTO 'citizens' ('name', 'family_name','day', 'month','year','pasNum', 'password') VALUES ('Vasya', 'pam1','12','5','2000', '1201231', '123'); ");
        statmt.execute("INSERT INTO 'citizens' ('name', 'family_name', 'day', 'month','year','pasNum','password') VALUES ('Masha', 'pam2','12','5','2000', '1201232', '123'); ");

        System.out.println("table is fulled");
    }

    /**
     * printing table
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    // printing table
    public static void ReadDB() throws ClassNotFoundException, SQLException {
        resSet = statmt.executeQuery("SELECT * FROM citizens");

        while (resSet.next()) {
            int id = resSet.getInt("id");
            String name = resSet.getString("name");
            String family_name = resSet.getString("family_name");
            System.out.println("ID = " + id);
            System.out.println("name = " + name);
            System.out.println("family_name = " + family_name);
            System.out.println();
        }

        System.out.println("Table printed");
    }

    /**
     * closing
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    // closing
    public static void CloseDB() throws ClassNotFoundException, SQLException {
        conn.close();
        statmt.close();
        resSet.close();

        System.out.println("connection closed");
    }

}