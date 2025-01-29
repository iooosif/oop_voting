package main.graficInterface;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import main.logic.persons.Candidate;

import static main.Main.blockchain;
import static main.graficInterface.appController.hash_curr;

/**
 * blockhain window
 */
public class BlockchainController {

    public Image myImage;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private TextArea text;
    private String previousText = "";

    /**
     * initialization
     */
    @FXML
    void initialize() {
        StringBuilder builder = new StringBuilder(); // Create a StringBuilder to accumulate text results

        for (int i = 0; i < blockchain.getBlockchain().size(); i++) {
            if (i == 0) {
                String text_res = "Transaction id " + i + " " + blockchain.getBlockchain().get(i).getHash() + "\nWith data: " + blockchain.getBlockchain().get(i).getData();
                builder.append(text_res); // Add the current result to the StringBuilder
                builder.append("\n"); // Add a newline to separate results

            } else {
                for (int j = 0; j < Candidate.candidateList.size(); j++) {
                    if (Objects.equals(blockchain.getBlockchain().get(i).getCandidate_pas(), Candidate.candidateList.get(j).getPasNumber())) {

                        String text_res = "Transaction id " + i + " " + blockchain.getBlockchain().get(i).getHash() + "\nFor: " + Candidate.candidateList.get(j).getName() + " " + Candidate.candidateList.get(j).getFamilyName();
                        builder.append(text_res); // Add the current result to the StringBuilder
                        builder.append("\n"); // Add a newline to separate results

                        break;
                    }
                }
            }
        }

        // Add the previous text value before the new one
        text.setText(previousText + builder.toString());
        // Save the current text value as the previous one for the next time
        previousText = text.getText();
    }

    @FXML
    private Button reg_id;
    @FXML
    private Button find;
    @FXML
    private TextArea field_result;
    @FXML
    private TextField field_enter;
    /**
     * go to login window
     */
    @FXML
    public void goToRegWindow() {
        try {
            hash_curr = " ";
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = loader.load();

            Scene priv_stage = reg_id.getScene();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            stage.setTitle("VoteChain");

            priv_stage.getWindow().hide(); // Hide the previous window
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * go to result window
     */
    @FXML
    public void goToResultWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Results.fxml"));
            Parent root = loader.load();
            Scene priv_stage = reg_id.getScene();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            stage.setTitle("VoteChain");

            priv_stage.getWindow().hide(); // Hide the previous window
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * search for transaction
     */
    @FXML
    public void onAction() {
        String entered = field_enter.getText();

        String res = "Not found"; // Default result if no match is found
        for (int i = 0; i < blockchain.getBlockchain().size(); i++) {
            if (Objects.equals(entered, blockchain.getBlockchain().get(i).getHash())) {
                for (int j = 0; j < Candidate.candidateList.size(); j++) {
                    if (Objects.equals(blockchain.getBlockchain().get(0).getHash(), blockchain.getBlockchain().get(i).getHash())) {
                        res = "This is the first block of our blockchain, it contains nothing";
                    }
                    if (Objects.equals(blockchain.getBlockchain().get(i).getCandidate_pas(), Candidate.candidateList.get(j).getPasNumber())) {
                        Date date = new Date(blockchain.getBlockchain().get(i).getTimestamp());
                        res = "Vote entered for: " + Candidate.candidateList.get(j).getName() + " " + Candidate.candidateList.get(j).getFamilyName() + " at " + date + "\nHash of the previous block is:\n" + blockchain.getBlockchain().get(i).getPreviousHash();
                        break;
                    }
                }
                break;
            }
        }
        field_result.setText(res);
    }
}
