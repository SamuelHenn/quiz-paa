package br.univates.paa.kickquiz;

import br.univates.paa.kickquiz.DAO.PerguntaDAO;
import br.univates.paa.kickquiz.model.Pergunta;
import br.univates.paa.kickquiz.model.Usuario;
import br.univates.paa.kickquiz.util.Utils;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AdminController implements Initializable {

    private Usuario user;

    @FXML
    private MenuBar menuBar;

    @FXML
    private Label tvTitle, tvUser;

    @FXML
    private TableView tvData;

    @FXML
    private Button btnNovo, btnEdit, btnDelete;

    @FXML
    private void btnSair(ActionEvent event) {
        try {
            Utils.abrirTela(getClass(), menuBar, "main");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void btnCadastroPergunta(ActionEvent event) {
        try {
            PerguntaDAO pdao = new PerguntaDAO();
            List<Pergunta> itens = pdao.listAll();
            ObservableList data = FXCollections.observableList(itens);

            visibilidadeContent(true);
            tvTitle.setText("Perguntas");
            tvData.setItems(data);
            TableColumn desc = new TableColumn("Descrição");
            desc.setCellValueFactory(new PropertyValueFactory("descricao"));
            TableColumn dificuldade = new TableColumn("Dificuldade");
            dificuldade.setCellValueFactory(new PropertyValueFactory("dificuldade"));
            tvData.getColumns().setAll(desc, dificuldade);
            tvData.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void btnNovaPergunta(ActionEvent event) {
        try {
            Utils.abrirTela(getClass(), menuBar, "cadastro_pergunta");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            visibilidadeContent(false);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void setUser(Usuario user) {
        try {
            this.user = user;
            if (user != null) {
                tvUser.setText("Olá " + user.getNome());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void visibilidadeContent(boolean visible) {
        tvTitle.setVisible(visible);
        tvData.setVisible(visible);
        btnNovo.setVisible(visible);
        btnEdit.setVisible(visible);
        btnDelete.setVisible(visible);
    }
}
