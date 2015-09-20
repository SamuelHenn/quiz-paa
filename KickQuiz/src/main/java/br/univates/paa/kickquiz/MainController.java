package br.univates.paa.kickquiz;

import br.univates.paa.kickquiz.DAO.UsuarioDAO;
import br.univates.paa.kickquiz.model.Usuario;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
            
            if (udao.checkLogin(u))
            {
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/admin.fxml"));
                Scene scene = new Scene(root);
                scene.getStylesheets().add("/styles/Styles.css");
                Stage stage = (Stage) btnStart.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }
            else
            {
                System.out.println("Nao tem");
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
