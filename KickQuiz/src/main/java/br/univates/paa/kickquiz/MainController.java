package br.univates.paa.kickquiz;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainController implements Initializable {

    @FXML
    private Button btnStart;

    @FXML
    private void btnStart(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/pergunta.fxml"));

            Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles/Styles.css");

            Stage stage = (Stage) btnStart.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    @FXML
    private void btnCadastroPergunta(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/cadastro_pergunta.fxml"));

            Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles/Styles.css");

            Stage stage = (Stage) btnStart.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
}
