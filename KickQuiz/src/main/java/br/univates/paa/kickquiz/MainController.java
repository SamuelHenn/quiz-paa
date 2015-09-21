package br.univates.paa.kickquiz;

import br.univates.paa.kickquiz.DAO.RankingDAO;
import br.univates.paa.kickquiz.DAO.UsuarioDAO;
import br.univates.paa.kickquiz.model.Ranking;
import br.univates.paa.kickquiz.model.Usuario;
import java.net.URL;
import java.util.List;
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
//import javafx.scene.control.Alert;
//import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class MainController implements Initializable {

    @FXML
    private Button btnStart;

    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfSenha;

    @FXML
    private TableView tvRanking;

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
    private void btnLogin(ActionEvent event) {
        try {

            Usuario u = new Usuario();
            u.setNome(tfNome.getText());
            u.setSenha(tfSenha.getText());

            UsuarioDAO udao = new UsuarioDAO();

            if (udao.checkLogin(u)) {
                //Utils.abrirTela(getClass(), btnStart, "admin");
                Stage stage = (Stage) btnStart.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/admin.fxml"));

                Parent root = (Parent) fxmlLoader.load();
                AdminController controller = fxmlLoader.<AdminController>getController();
                controller.initData(u);
                Scene scene = new Scene(root);

                stage.setScene(scene);

                stage.show();
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Erro ao autenticar!");
                alert.setHeaderText("Usuário ou senha não conferem");
                alert.setContentText(null);

                alert.showAndWait();
                System.out.println("Nao tem");
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
            ObservableList data = FXCollections.observableList(itens);

            tvRanking.setItems(data);
            TableColumn desc = new TableColumn("Usuário");
            desc.setCellValueFactory(new PropertyValueFactory("usuario"));
            TableColumn pontos = new TableColumn("Pontos");
            pontos.setCellValueFactory(new PropertyValueFactory("pontos"));
            TableColumn certas = new TableColumn("Perguntas Certas");
            certas.setCellValueFactory(new PropertyValueFactory("perguntas_acertadas"));
            tvRanking.getColumns().setAll(desc, pontos, certas);
            tvRanking.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
