package main.graficInterface;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.logic.db.conn_person;
import main.logic.persons.Person;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.io.IOException;
import java.util.Objects;

import static main.logic.db.conn_person.statmt;

/**
 * login window
 */
public class LoginController {
    public static String userLogin;
    public static int userNum;
    private Stage primaryStage; // Reference to the main window

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private Button log_button;
    @FXML
    private Button result_id;
    @FXML
    public TextField log_id;

    @FXML
    private PasswordField log_pass;

    @FXML
    public void result_action() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Results.fxml"));
            Parent root = loader.load();

            Scene priv_stage = log_id.getScene();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            priv_stage.getWindow().hide();

        } catch (IOException e) {
            System.out.println("error");
        }
    }

    /**
     * initialization
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @FXML
    public void onClickMethod() throws SQLException, ClassNotFoundException {

        conn_person.Conn("jdbc:sqlite:citizens_db.s3db");
        conn_person.CreateDB("citizens");
        userLogin = log_id.getText();
        String userPassword = log_pass.getText();
        ResultSet resSet;
        resSet = statmt.executeQuery("SELECT * FROM citizens");
        int flag = 0;
        while (resSet.next()) {
            String pasNum = resSet.getString("pasNum");
            System.out.println(pasNum);
            String password = resSet.getString("password");
            if (Objects.equals(userLogin, pasNum) && Objects.equals(userPassword, password)) {
                for (int i = 0; i < Person.personList.size(); i++) {
                    if (Objects.equals(pasNum, Person.personList.get(i).getPasNumber())) {
                        userNum = i;
                        break;
                    }
                }
                goToSecondWindow(); // Move to the next window
                flag += 1;
                break;
            }
        }

        if (flag == 0) { // If no matching login and password are found
            showErrorAlert("wrong password or login");
        }
    }

    /**
     * if wrong password entered
     * @param message
     */
    public static void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Wrong login or password");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    /**
     * go to voting window
     */
    @FXML
    public void goToSecondWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("app.fxml"));
            Parent root = loader.load();
            Scene priv_stage = log_id.getScene();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            stage.setTitle("VoteChain");
            priv_stage.getWindow().hide(); // Close the previous window
        } catch (IOException e) {
            System.out.println("pam");
        }
    }
    /**
     * go to blockchain window
     */
    @FXML
    public void goToBlockchainWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Blockchain.fxml"));
            Parent root = loader.load();
            Scene priv_stage = log_pass.getScene();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            stage.setTitle("VoteChain");
            priv_stage.getWindow().hide(); // Close the previous window
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
