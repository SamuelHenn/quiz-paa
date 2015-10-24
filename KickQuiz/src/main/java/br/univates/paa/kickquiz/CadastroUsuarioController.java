package br.univates.paa.kickquiz;

import br.univates.paa.kickquiz.DAO.UsuarioDAO;
import br.univates.paa.kickquiz.model.Usuario;
import br.univates.paa.kickquiz.util.Utils;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class CadastroUsuarioController implements Initializable {

    private Usuario usuario;

    @FXML
    private Button btnVoltar;

    @FXML
    private TextField tvNome;

    @FXML
    private TextField tvLogin;
    
    @FXML
    private PasswordField tvSenha;

    @FXML
    private void btnVoltar(ActionEvent event) {
        try {
            Utils.abrirTela(getClass(), btnVoltar, "admin");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void save(ActionEvent event) {
        try {
            if (usuario == null) {
                usuario = new Usuario();
            }

            usuario.setNome(tvNome.getText());
            usuario.setLogin(tvLogin.getText());
            usuario.setSenha(tvSenha.getText());
            UsuarioDAO pdao = new UsuarioDAO();
            pdao.save(usuario);
            btnVoltar(null);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;

        if (usuario == null) {
            return;
        }

        tvNome.setText(usuario.getNome());
        tvLogin.setText(usuario.getLogin());
        tvSenha.setText(usuario.getSenha());
    }
}
