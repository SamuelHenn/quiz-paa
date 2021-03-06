package br.univates.paa.kickquiz;

import br.univates.paa.kickquiz.DAO.RankingDAO;
import br.univates.paa.kickquiz.DAO.UsuarioDAO;
import br.univates.paa.kickquiz.model.Ranking;
import br.univates.paa.kickquiz.model.Usuario;
import br.univates.paa.kickquiz.util.Utils;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class MainController implements Initializable {

    @FXML
    private Button btnStart;

    @FXML
    private TextField tfNome;

    @FXML
    private PasswordField tfSenha;

    @FXML
    private TableView tvRanking;

    @FXML
    private void btnStart(ActionEvent event) {
        pedirNome();
    }

    private void pedirNome() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Iniciar jogo");
        dialog.setHeaderText("Olá, bem vindo ao KickQuiz");
        dialog.setContentText("Por favor, digite seu nome:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            abrirPerguntas(result.get());
        }
    }

    private void abrirPerguntas(String nome) {
        try {
            Stage stage = (Stage) btnStart.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/pergunta.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            PerguntaController controller = fxmlLoader.<PerguntaController>getController();
            controller.setUsuario(nome);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void btnLogin(ActionEvent event) {
        try {

            Usuario u = new Usuario();
            u.setLogin(tfNome.getText());
            u.setSenha(tfSenha.getText());

            UsuarioDAO udao = new UsuarioDAO();
            u = udao.checkLogin(u);

            if (u != null) {
                Utils.setUserLogado(u);
                Utils.abrirTela(getClass(), btnStart, "admin");
            } else {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Erro ao autenticar!");
                Utils.escreveLog("teste", "Tentativa de login com usuário \"" + tfNome.getText() + "\"", 2);
                alert.setHeaderText("Usuário ou senha não conferem");
                alert.setContentText(null);
                alert.show();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            RankingDAO rdao = new RankingDAO();
            List<Ranking> itens = rdao.listAll();
            for (int i = 0; i < itens.size(); i++) {
                itens.get(i).setPosicao((i + 1) + "°");
            }
            ObservableList data = FXCollections.observableList(itens);

            tvRanking.setItems(data);
            TableColumn pos = new TableColumn("Posição");
            pos.setCellValueFactory(new PropertyValueFactory("posicao"));
            TableColumn desc = new TableColumn("Usuário");
            desc.setCellValueFactory(new PropertyValueFactory("usuario"));
            TableColumn pontos = new TableColumn("Pontos");
            pontos.setCellValueFactory(new PropertyValueFactory("pontos"));
            TableColumn certas = new TableColumn("Perguntas Certas");
            certas.setCellValueFactory(new PropertyValueFactory("perguntas_acertadas"));
            tvRanking.getColumns().setAll(pos, desc, pontos, certas);
            tvRanking.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
