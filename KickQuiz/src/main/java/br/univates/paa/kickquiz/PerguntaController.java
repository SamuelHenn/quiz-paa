package br.univates.paa.kickquiz;

import br.univates.paa.kickquiz.DAO.PerguntaDAO;
import br.univates.paa.kickquiz.DAO.TentativaDAO;
import br.univates.paa.kickquiz.DAO.UsuarioDAO;
import br.univates.paa.kickquiz.model.Pergunta;
import br.univates.paa.kickquiz.model.Resposta;
import br.univates.paa.kickquiz.model.Tentativa;
import br.univates.paa.kickquiz.model.Usuario;
import br.univates.paa.kickquiz.util.Utils;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.Pane;

public class PerguntaController implements Initializable {

    private List<Pergunta> perguntas;
    private Pergunta pergunta;
    private Usuario usuario;
    private int num = 0;

    @FXML
    private Label tvPergunta, tvNome, tvNumero;

    @FXML
    private RadioButton rb1, rb2, rb3, rb4, rb5;

    @FXML
    private Pane pBonus;

    @FXML
    private void btnProxima(ActionEvent event) {
        try {
            Resposta r = null;
            if (rb1.isSelected())
                r = pergunta.getRespostas().get(0);
            if (rb2.isSelected())
                r = pergunta.getRespostas().get(1);
            if (rb3.isSelected())
                r = pergunta.getRespostas().get(2);
            if (rb4.isSelected())
                r = pergunta.getRespostas().get(3);
            if (rb5.isSelected())
                r = pergunta.getRespostas().get(4);
            
            if (r == null)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Atenção");
                alert.setHeaderText("Você deve marcar uma resposta");
                alert.setContentText(null);
                alert.show();
                return;
            }
            
            if (pergunta.getRespostas().get(0).isFl_correta() && rb1.isSelected()
                    || pergunta.getRespostas().get(1).isFl_correta() && rb2.isSelected()
                    || pergunta.getRespostas().get(2).isFl_correta() && rb3.isSelected()
                    || pergunta.getRespostas().get(3).isFl_correta() && rb4.isSelected()
                    || pergunta.getRespostas().get(4).isFl_correta() && rb5.isSelected()) {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Atenção");
                alert.setHeaderText("Aeee, você acertou!");
                alert.setContentText(null);
                alert.show();
            }
            
            TentativaDAO tdao = new TentativaDAO();
            Tentativa t = new Tentativa();
            t.setBonus(null);
            t.setResposta(r);
            t.setUsuario(usuario);
            tdao.save(t);
            
            carregarPergunta();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Atenção");
            alert.setHeaderText(e.getMessage());
            alert.setContentText(null);
            alert.show();
        }
    }

    private void carregarPergunta() {
        Random r = new Random();
        pergunta = perguntas.get(r.nextInt(perguntas.size() - 1));

        tvPergunta.setText(pergunta.getDescricao());
        rb1.setText(pergunta.getRespostas().get(0).getDescricao());
        rb2.setText(pergunta.getRespostas().get(1).getDescricao());
        rb3.setText(pergunta.getRespostas().get(2).getDescricao());
        rb4.setText(pergunta.getRespostas().get(3).getDescricao());
        rb5.setText(pergunta.getRespostas().get(4).getDescricao());
        rb1.setSelected(false);
        rb2.setSelected(false);
        rb3.setSelected(false);
        rb4.setSelected(false);
        rb5.setSelected(false);
        atualizaNumero();
    }

    private void atualizaNumero() {
        num++;
        tvNumero.setText("Pergunta número " + num);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            PerguntaDAO pdao = new PerguntaDAO();
            perguntas = pdao.listAll();

            carregarPergunta();

            Button b1 = new Button("Passar");
            Button b2 = new Button("Dica");
            Button b3 = new Button("Resposta");
            pBonus.getChildren().addAll(b1, b2, b3);
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

    public void setUsuario(String nome) {
        UsuarioDAO u = new UsuarioDAO();
        usuario = u.getUsuarioByNome(nome);
        
        if (usuario == null)
        {
            usuario = new Usuario();
            usuario.setNome(nome);
            UsuarioDAO udao = new UsuarioDAO();
            udao.save(usuario);
        }
        
        tvNome.setText(usuario.getNome());
    }
}
