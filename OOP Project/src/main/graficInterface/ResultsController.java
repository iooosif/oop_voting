package main.graficInterface;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.logic.persons.Candidate;
import main.logic.persons.Person;
import main.logic.work.Results;

import static main.graficInterface.LoginController.userNum;
import static main.graficInterface.appController.hash_curr;

/**
 * result window
 */
public class ResultsController {

    public Image myImage;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private TextArea results_id;
    @FXML
    private TextArea percentage_of_voters;
    @FXML
    private TextArea winner;
    @FXML
    private TextArea hash_id;
    private String previousText = "";
    @FXML
    private ProgressBar progress;

    /**
     * initialization of result window
     */
    @FXML
    void initialize() {
        DecimalFormat df = new DecimalFormat("#.##");

        Results res = Results.getInstance(Candidate.candidateList, Person.personList);
        StringBuilder builder = new StringBuilder(); // Create a StringBuilder to accumulate result texts
        Collections.sort(Candidate.candidateList, Collections.reverseOrder());
        for (int i = 0; i < Candidate.candidateList.size(); i++) {

            double res_for_i = res.results_for_concrete(Candidate.candidateList.get(i).getPasNumber());
            String percent_cand = df.format(100 * res_for_i);
            String text_res = "Candidate " + Candidate.candidateList.get(i).getName() + " " + Candidate.candidateList.get(i).getFamilyName() + " received " + percent_cand + "%";
            builder.append(text_res); // Add the current result to the StringBuilder
            builder.append("\n"); // Add a newline for result separation
        }

        // Add the previous text value before the new value
        results_id.setText(previousText + builder.toString());
        // Save the current text value as the previous one for next time
        previousText = results_id.getText();

        String percentage = df.format(res.percentage_of_voters());
        percentage_of_voters.setText("Percentage of citizens who have already voted: " + percentage + "%");
        progress.setProgress(res.percentage_of_voters() / 100);

        if (res.percentage_of_voters() == 100) {
            StringBuilder res_builder = new StringBuilder();
            results_id.setText(res.winner());
            Candidate.candidateList.sort(Collections.reverseOrder());
            String text_res = res.winner() + "\n";
            for (int i = 1; i < Candidate.candidateList.size(); i++) {
                res_builder.append(text_res); // Add the current result to the StringBuilder
                res_builder.append("\n");
                double res_for_i = res.results_for_concrete(Candidate.candidateList.get(i).getPasNumber());
                String percent_cand = df.format(100 * res_for_i);
                text_res = "Candidate " + Candidate.candidateList.get(i).getName() + " " + Candidate.candidateList.get(i).getFamilyName() + " received " + percent_cand + "%";
                // Add a newline for result separation
            }
            res_builder.append(text_res); // Add the current result to the StringBuilder
            results_id.setText(String.valueOf(res_builder));
        }

        hash_id.setText("Hash of your vote: " + Person.personList.get(userNum).getHash_of_vote());
    }

    /**
     * go to login window
     */
    @FXML
    public void goToSecondWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = loader.load();
            Scene priv_stage = results_id.getScene();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            stage.setTitle("VoteChain");
            hash_curr = " ";

            priv_stage.getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
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
            Scene priv_stage = results_id.getScene();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            stage.setTitle("VoteChain");

            priv_stage.getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
