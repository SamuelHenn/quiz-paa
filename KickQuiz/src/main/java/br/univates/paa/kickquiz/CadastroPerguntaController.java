package br.univates.paa.kickquiz;

import br.univates.paa.kickquiz.DAO.PerguntaDAO;
import br.univates.paa.kickquiz.model.Pergunta;
import br.univates.paa.kickquiz.model.Resposta;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
    private TextArea taPergunta;

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
    private void removeResposta(ActionEvent event) {
        try {
            int index = listRespostas.getSelectionModel().getSelectedIndex();
            if (index >= 0) {
                listRespostas.getItems().remove(index);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void addResposta(ActionEvent event) {
        try {
            if (listRespostas.getItems().size() == 5) {
                return;
            }

            Resposta resposta = new Resposta();
            resposta.setDescricao(tvResposta.getText());
            if (cbCorreto.isSelected()) {
                for (int i = 0; i < listRespostas.getItems().size(); i++) {
                    Resposta r = (Resposta) listRespostas.getItems().get(i);
                    if (r.isFl_correta()) {
                        return;
                    }
                }

                resposta.setFl_correta(true);
            }

            listRespostas.getItems().add(resposta);
            tvResposta.setText("");
            cbCorreto.setSelected(false);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void save(ActionEvent event) {
        try {
            Pergunta p = new Pergunta();
            p.setDescricao(taPergunta.getText());
            p.setDificuldade(cbBox.getSelectionModel().getSelectedIndex());
            List<Resposta> respostas = new ArrayList<>();
            for (int i = 0; i < listRespostas.getItems().size(); i++) {
                respostas.add((Resposta) listRespostas.getItems().get(i));
            }
            p.setRespostas(respostas);
            PerguntaDAO pdao = new PerguntaDAO();
            pdao.save(p);
            btnVoltar(null);
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
