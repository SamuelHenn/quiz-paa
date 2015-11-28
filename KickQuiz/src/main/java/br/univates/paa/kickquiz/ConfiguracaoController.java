package br.univates.paa.kickquiz;

import br.univates.paa.kickquiz.DAO.BonusDAO;
import br.univates.paa.kickquiz.DAO.ConfiguracaoDAO;
import br.univates.paa.kickquiz.model.Bonus;
import br.univates.paa.kickquiz.model.Configuracao;
import br.univates.paa.kickquiz.util.Utils;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

public class ConfiguracaoController implements Initializable {

    @FXML
    private Button btnVoltar;

    @FXML
    private ComboBox cbNivel;

    @FXML
    private CheckBox cbAtivo;

    @FXML
    private TextField tvQuantidade, tvDiretorio;

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
            ConfiguracaoDAO cdao = new ConfiguracaoDAO();
            Configuracao dir = cdao.getByChave("diretorio_log");
            dir.setValor(tvDiretorio.getText());
            cdao.save(dir);
            Configuracao nivel = cdao.getByChave("nivel_log");
            nivel.setValor((String) cbNivel.getSelectionModel().getSelectedItem());
            cdao.save(nivel);
            Configuracao quant = cdao.getByChave("quantidade-limpeza-auditoria");
            quant.setValor(tvQuantidade.getText());
            cdao.save(quant);
            Configuracao ativo = cdao.getByChave("auditoria-ativa");
            ativo.setValor(Boolean.toString(cbAtivo.isSelected()));
            cdao.save(ativo);

            btnVoltar(null);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            cbNivel.getItems().add("1");
            cbNivel.getItems().add("2");
            cbNivel.getItems().add("3");

            ConfiguracaoDAO cdao = new ConfiguracaoDAO();
            Configuracao dir = cdao.getByChave("diretorio_log");
            tvDiretorio.setText(dir.getValor());
            Configuracao nivel = cdao.getByChave("nivel_log");
            cbNivel.getSelectionModel().select(nivel.getValor());
            Configuracao quant = cdao.getByChave("quantidade-limpeza-auditoria");
            tvQuantidade.setText(quant.getValor());
            Configuracao ativo = cdao.getByChave("auditoria-ativa");
            cbAtivo.setSelected(Boolean.valueOf(ativo.getValor()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void escolherDir(ActionEvent event) {
        try {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory = directoryChooser.showDialog(tvDiretorio.getScene().getWindow());

            if (selectedDirectory == null) {
                tvDiretorio.setText("No Directory selected");
            } else {
                tvDiretorio.setText(selectedDirectory.getAbsolutePath());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
