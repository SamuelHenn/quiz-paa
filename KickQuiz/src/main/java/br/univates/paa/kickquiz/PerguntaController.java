package br.univates.paa.kickquiz;

import br.univates.paa.kickquiz.DAO.PerguntaDAO;
import br.univates.paa.kickquiz.model.Pergunta;
import br.univates.paa.kickquiz.util.Utils;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;

public class PerguntaController implements Initializable {

    @FXML
    private Label tvPergunta;
    @FXML
    private RadioButton rb1, rb2, rb3, rb4, rb5;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            PerguntaDAO pdao = new PerguntaDAO();
            Pergunta p = pdao.getById(1);
            tvPergunta.setText(p.getDescricao());
            rb1.setText(p.getRespostas().get(0).getDescricao());
            rb2.setText(p.getRespostas().get(1).getDescricao());
            rb3.setText(p.getRespostas().get(2).getDescricao());
            rb4.setText(p.getRespostas().get(3).getDescricao());
            rb5.setText(p.getRespostas().get(4).getDescricao());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void btnSair(ActionEvent event) {
        try {
            Utils.abrirTela(getClass(), tvPergunta, "main");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
