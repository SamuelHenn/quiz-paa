package br.univates.paa.kickquiz;

import br.univates.paa.kickquiz.DAO.BonusDAO;
import br.univates.paa.kickquiz.model.Bonus;
import br.univates.paa.kickquiz.util.Utils;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class CadastroBonusController implements Initializable {

    private Bonus bonus;

    @FXML
    private Button btnVoltar;

    @FXML
    private ComboBox tvChamada;

    @FXML
    private TextField tvBonus;

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
            if (bonus == null) {
                bonus = new Bonus();
            }

            bonus.setDescricao(tvBonus.getText());
            bonus.setChamada(tvChamada.getItems().get(tvChamada.getSelectionModel().getSelectedIndex()).toString());
            BonusDAO pdao = new BonusDAO();
            pdao.save(bonus);
            btnVoltar(null);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tvChamada.getItems().add("Dica");
        tvChamada.getItems().add("Eliminar resposta");
        tvChamada.getItems().add("Pular");
    }

    public void setBonus(Bonus bonus) {
        this.bonus = bonus;

        if (bonus == null) {
            return;
        }

        tvBonus.setText(bonus.getDescricao());
        tvChamada.getSelectionModel().select(bonus.getChamada());
    }
}
