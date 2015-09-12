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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CadastroPerguntaController implements Initializable {

    @FXML
    private Button btnVoltar;

    @FXML
    private ComboBox cbBox;

    @FXML
    private CheckBox cbCorreto;

    @FXML
    private ListView listRespostas;

    @FXML
    private TextField tvResposta;

    @FXML
    private TextArea tvPergunta;

    @FXML
    private void btnVoltar(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));

            Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles/Styles.css");

            Stage stage = (Stage) btnVoltar.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void addResposta(ActionEvent event) {
        try {
            String resposta = tvResposta.getText();
            if (cbCorreto.isSelected()) {
                for (int i = 0; i < listRespostas.getItems().size(); i++) {
                    String item = listRespostas.getItems().get(i).toString();
                    if (item.contains(" - Correta")) {
                        return;
                    }
                }

                resposta += " - Correta";
            }

            listRespostas.getItems().add(resposta);
            tvResposta.setText("");
            cbCorreto.setSelected(false);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            cbBox.getItems().add("Fácil");
            cbBox.getItems().add("Média");
            cbBox.getItems().add("Difícil");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
