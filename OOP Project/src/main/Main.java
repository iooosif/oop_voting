package main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import main.logic.blockchain.Block;
import main.logic.blockchain.Blockchain;
import main.logic.db.conn_person;
import main.graficInterface.LoginController;
import main.logic.persons.Candidate;
import main.logic.persons.Person;

import java.sql.ResultSet;
import java.sql.SQLException;


import static main.logic.db.conn_person.statmt;

/**
 * Main class
 */
public class Main extends Application {
    public static Blockchain blockchain;

    /**
     * to create all candidates
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void createAllCandidates() throws SQLException, ClassNotFoundException {
        conn_person.Conn("jdbc:sqlite:candidates_db.s3db");
        conn_person.CreateDB("candidates");
        blockchain = Blockchain.getInstance();
        blockchain.addBlock(new Block(0, "", "first block", ""));
        ResultSet resSet;
        resSet = statmt.executeQuery("SELECT * FROM candidates");
        while (resSet.next()) {

            String pasNum = resSet.getString("pasNum");
            String name = resSet.getString("name");
            String family_name = resSet.getString("family_name");
            int day = resSet.getInt("day");
            int month = resSet.getInt("month");
            int year = resSet.getInt("year");
            Candidate candidate = new Candidate(name, family_name, new int[]{day, month, year}, pasNum);
        }
    }

    /**
     * to create all persons
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void createAllPersons() throws SQLException, ClassNotFoundException {
        conn_person.Conn("jdbc:sqlite:citizens_db.s3db");
        conn_person.CreateDB("citizens");

        ResultSet resSet;
        resSet = statmt.executeQuery("SELECT * FROM citizens");
        while (resSet.next()) {

            String pasNum = resSet.getString("pasNum");
            String name = resSet.getString("name");
            String family_name = resSet.getString("family_name");
            int day = resSet.getInt("day");
            int month = resSet.getInt("month");
            int year = resSet.getInt("year");
            Person person = new Person(name, family_name, new int[]{day, month, year}, pasNum);
        }
    }

    /**
     * start of the program
     * @param args
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        createAllPersons();
        createAllCandidates();


        Person Person_at_the_moment;
        launch(args);


    }

    /**
     * starting graphic interface
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("graficInterface/Login.fxml"));

        Parent root = loader.load();
        LoginController controller = loader.getController();
        controller.setPrimaryStage(primaryStage);
        primaryStage.setTitle("VoteChain");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();


    }
}