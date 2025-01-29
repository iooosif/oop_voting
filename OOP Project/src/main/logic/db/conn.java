package main.logic.db;

import java.sql.SQLException;
/**
 * abstract class for operations
 */

public abstract class conn {
    /**
     * for creating db
     * @param name
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static void CreateDB(String name) throws ClassNotFoundException, SQLException {

    }

    /**
     * for writing in db
     * @throws SQLException
     */
    public static void WriteDB() throws SQLException {

    }

    /**
     * For reading from db
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static void ReadDB() throws ClassNotFoundException, SQLException {

    }

}
