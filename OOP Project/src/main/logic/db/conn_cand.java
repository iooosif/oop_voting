package main.logic.db;

import java.sql.SQLException;

/**
 * operations with candidates db
 */
public class conn_cand extends conn_person {
    /**
     * create table
     * @param name
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static void CreateDB(String name) throws ClassNotFoundException, SQLException {
        statmt = conn.createStatement();
        String str = String.format("CREATE TABLE if not exists '%s' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'name' text, 'family_name'  text, 'day' INTEGER, 'month' INTEGER, 'year' INTEGER, 'pasNum' text);", name);
        statmt.execute(str);

        System.out.println("data base created.");
    }

    /**
     * fulfill table
     * @throws SQLException
     */
    public static void WriteDB() throws SQLException {
        statmt.execute("INSERT INTO 'candidates' ('name', 'family_name','day', 'month','year','pasNum') VALUES ('Alexei', 'Navalny','12','5','1978', '1001230'); ");
        statmt.execute("INSERT INTO 'candidates' ('name', 'family_name','day', 'month','year','pasNum') VALUES ('Andrey', 'Saharov','15','8','1921', '1000231'); ");
        statmt.execute("INSERT INTO 'candidates' ('name', 'family_name','day', 'month','year','pasNum') VALUES ('Egor', 'Letov','12','2','1971', '1011235'); ");

        System.out.println("table is fulled");
    }

    /**
     *  printing table
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    // printing table
    public static void ReadDB() throws ClassNotFoundException, SQLException {
        resSet = statmt.executeQuery("SELECT * FROM candidates");

        while (resSet.next()) {
            int id = resSet.getInt("id");
            String name = resSet.getString("name");
            String family_name = resSet.getString("family_name");
            System.out.println("ID = " + id);
            System.out.println("name = " + name);
            System.out.println("family_name = " + family_name);
            System.out.println();
        }

        System.out.println("table printed");
    }

}
