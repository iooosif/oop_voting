package main.graficInterface;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Objects;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import main.logic.db.conn_person;
import main.logic.db.conn_cand;
import main.logic.persons.Candidate;
import main.logic.persons.Person;

import static main.Main.blockchain;
import static main.logic.db.conn_person.statmt;
import static main.graficInterface.LoginController.userNum;

/**
 * voting window
 */
public class appController {
    public static String hash_curr = " ";

    public Button voteButton;
    public Image myImage;
    @FXML
    private Button log_button;
    @FXML
    private ResourceBundle resources;
    @FXML
    private TextField count_id;
    @FXML
    private URL location;
    @FXML
    private ComboBox<String> combo_candidates;
    @FXML
    private TextArea hash_id;
    @FXML
    private Button exit_id;
    @FXML
    private Button copy;
    /**
     * copy hash to buffer
     */
    @FXML
    void copy_buf() {
        String textToCopy = hash_id.getText().substring(19);

        // Get the system clipboard
        Clipboard clipboard = Clipboard.getSystemClipboard();

        // Create clipboard content
        ClipboardContent content = new ClipboardContent();
        content.putString(textToCopy);

        // Set the clipboard content
        clipboard.setContent(content);
    }

    /**
     * initialization
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        hash_id.setText("hash of your vote: " + Person.personList.get(userNum).getHash_of_vote());
        ObservableList<String> options = FXCollections.observableArrayList();
        count_id.setText(String.valueOf(Person.personList.get(userNum).getToken()));

        conn_person.Conn("jdbc:sqlite:candidates_db.s3db");
        conn_cand.CreateDB("candidates");
        ResultSet resSet = statmt.executeQuery("SELECT * FROM candidates");

        while (resSet.next()) {
            String family_name = resSet.getString("family_name");
            String name = resSet.getString("name");
            options.add(name + " " + family_name);
        }

        combo_candidates.getItems().clear();
        combo_candidates.getItems().addAll(options);
    }

    @FXML
    void combo_candidates_pam() {
        // No specific implementation
    }

    /**
     * error messages, if person already vote
     * @param message
     */
    public static void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Already Voted");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * button for voting and creating new block
     */
    @FXML
    void vote_button() {
        String selectedValue = combo_candidates.getValue();

        for (int i = 0; i < Candidate.candidateList.size(); i++) {
            if (Objects.equals(selectedValue, Candidate.candidateList.get(i).getName() + ' ' + Candidate.candidateList.get(i).getFamilyName())) {
                if (Person.personList.get(userNum).getToken() > 0) {
                    Person.personList.get(userNum).vote(Candidate.candidateList.get(i), Candidate.candidateList, blockchain);

                    hash_curr = blockchain.getLatestBlock().getHash();
                    Person.personList.get(userNum).setHash_of_vote(hash_curr);
                    hash_id.setText("hash of your vote: " + hash_curr);
                    count_id.setText(String.valueOf(Person.personList.get(userNum).getToken()));
                } else {
                    showErrorAlert("You already voted");
                }
            }
        }
    }
    /**
     * go to result window
     */
    @FXML
    public void goToSecondWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Results.fxml"));
            Parent root = loader.load();
            Scene priv_stage = combo_candidates.getScene();
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
     * go to login window
     */
    @FXML
    public void goToLogin() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent root = loader.load();

        Scene priv_stage = exit_id.getScene();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        stage.setTitle("VoteChain");

        priv_stage.getWindow().hide(); // Close the previous window
    }
}
