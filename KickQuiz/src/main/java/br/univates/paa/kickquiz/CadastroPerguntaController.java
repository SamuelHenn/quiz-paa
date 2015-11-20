package br.univates.paa.kickquiz;

import br.univates.paa.kickquiz.DAO.PerguntaDAO;
import br.univates.paa.kickquiz.model.Pergunta;
import br.univates.paa.kickquiz.model.Resposta;
import br.univates.paa.kickquiz.util.Utils;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CadastroPerguntaController implements Initializable {

    private Pergunta pergunta;

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
            Utils.abrirTela(getClass(), cbBox, "admin");
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
            if (listRespostas.getItems().size() == 4) {
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
            if (pergunta == null) {
                pergunta = new Pergunta();
            }

            pergunta.setDescricao(taPergunta.getText());
            pergunta.setDificuldade(cbBox.getSelectionModel().getSelectedIndex());
            pergunta.setRespostas(new ArrayList<Resposta>());
            for (int i = 0; i < listRespostas.getItems().size(); i++) {
                pergunta.addResposta((Resposta) listRespostas.getItems().get(i));
            }
            PerguntaDAO pdao = new PerguntaDAO();
            pdao.save(pergunta);
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

    public Pergunta getPergunta() {
        return pergunta;
    }

    public void setPergunta(Pergunta pergunta) {
        this.pergunta = pergunta;

        if (pergunta == null) {
            return;
        }

        taPergunta.setText(pergunta.getDescricao());
        cbBox.getSelectionModel().select(pergunta.getDificuldade());
        for (Resposta r : pergunta.getRespostas()) {
            listRespostas.getItems().add(r);
        }
    }
}
