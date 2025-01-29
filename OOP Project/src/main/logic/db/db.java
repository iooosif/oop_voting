package main.logic.db;

import java.sql.SQLException;

/**
 * class for work with db
 */
public class db {
    /**
     * all work with databases, if they are not created
     * @param args
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        conn_person.Conn("jdbc:sqlite:citizens_db.s3db");
        conn_person.CreateDB("citizens");
        conn_person.WriteDB();
        conn_person.ReadDB();
        conn_person.CloseDB();
        conn_cand.Conn("jdbc:sqlite:candidates_db.s3db");
        conn_cand.CreateDB("candidates");
        conn_cand.WriteDB();
        conn_cand.ReadDB();
        conn_cand.CloseDB();
    }
}