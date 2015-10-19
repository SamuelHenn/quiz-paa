package br.univates.paa.kickquiz;

import br.univates.paa.kickquiz.DAO.BonusDAO;
import br.univates.paa.kickquiz.DAO.PerguntaDAO;
import br.univates.paa.kickquiz.model.Bonus;
import br.univates.paa.kickquiz.model.Pergunta;
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
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class AdminController implements Initializable {

    private int opcao = 0;
    public static final int PERGUNTA = 1;
    public static final int BONUS = 2;

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
    private void btnExcluir(ActionEvent event) {
        try {
            switch (opcao) {
                case PERGUNTA:
                    excluirPergunta();
                    break;
                case BONUS:
                    excluirBonus();
                    break;
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Atenção");
            alert.setHeaderText(e.getMessage());
            alert.setContentText(null);
            alert.show();
        }
    }

    @FXML
    private void btnEdit(ActionEvent event) {
        try {
            switch (opcao) {
                case PERGUNTA:
                    editarPergunta();
                    break;
                case BONUS:
                    editarBonus();
                    break;
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Atenção");
            alert.setHeaderText(e.getMessage());
            alert.setContentText(null);
            alert.show();
        }
    }

    @FXML
    private void btnCadastroPergunta(ActionEvent event) {
        try {
            PerguntaDAO pdao = new PerguntaDAO();
            List<Pergunta> itens = pdao.listAll();
            ObservableList data = FXCollections.observableList(itens);
            opcao = PERGUNTA;

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
    private void btnCadastroBonus(ActionEvent event) {
        try {
            BonusDAO bdao = new BonusDAO();
            List<Bonus> itens = bdao.listAll();
            ObservableList data = FXCollections.observableList(itens);
            opcao = BONUS;

            visibilidadeContent(true);
            tvTitle.setText("Bônus");
            tvData.setItems(data);
            TableColumn desc = new TableColumn("Descrição");
            desc.setCellValueFactory(new PropertyValueFactory("descricao"));
            TableColumn chamada = new TableColumn("Chamada");
            chamada.setCellValueFactory(new PropertyValueFactory("Chamada"));
            tvData.getColumns().setAll(desc, chamada);
            tvData.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void btnNovo(ActionEvent event) {
        try {
            switch (opcao) {
                case PERGUNTA:
                    Utils.abrirTela(getClass(), menuBar, "cadastro_pergunta");
                    break;
                case BONUS:
                    Utils.abrirTela(getClass(), menuBar, "cadastro_bonus");
                    break;
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Atenção");
            alert.setHeaderText(e.getMessage());
            alert.setContentText(null);
            alert.show();
        }
    }

    private void editarPergunta() throws Exception {
        Pergunta pergunta = (Pergunta) tvData.getItems().get(tvData.getSelectionModel().getSelectedIndex());
        if (pergunta == null) {
            throw new Exception("Nenhuma pergunta selecionada");
        }

        Stage stage = (Stage) tvData.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/cadastro_pergunta.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        CadastroPerguntaController controller = fxmlLoader.<CadastroPerguntaController>getController();
        controller.setPergunta(pergunta);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    private void editarBonus() throws Exception {
        Bonus bonus = (Bonus) tvData.getItems().get(tvData.getSelectionModel().getSelectedIndex());
        if (bonus == null) {
            throw new Exception("Nenhum bonus selecionada");
        }

        Stage stage = (Stage) tvData.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/cadastro_bonus.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        CadastroBonusController controller = fxmlLoader.<CadastroBonusController>getController();
        controller.setBonus(bonus);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            visibilidadeContent(false);
            Usuario u = Utils.getUserLogado();
            if (u != null) {
                tvUser.setText("Olá " + u.getNome());
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

    private void excluirPergunta() throws Exception {
        Pergunta pergunta = (Pergunta) tvData.getItems().get(tvData.getSelectionModel().getSelectedIndex());
        if (pergunta == null) {
            throw new Exception("Nenhuma pergunta selecionada");
        }

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Excluir pergunta");
        alert.setHeaderText("Você quer mesmo excluir essa pergunta?");
        ButtonType btnSim = new ButtonType("Sim");
        ButtonType btnNao = new ButtonType("Não", ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(btnSim, btnNao);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == btnSim) {
            PerguntaDAO pdao = new PerguntaDAO();
            pdao.delete(pergunta);
            btnCadastroPergunta(null);
        } else {
            alert.close();
        }
    }
    
    private void excluirBonus() throws Exception {
        Bonus bonus = (Bonus) tvData.getItems().get(tvData.getSelectionModel().getSelectedIndex());
        if (bonus == null) {
            throw new Exception("Nenhum bonus selecionada");
        }

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Excluir bonus");
        alert.setHeaderText("Você quer mesmo excluir esse bonus?");
        ButtonType btnSim = new ButtonType("Sim");
        ButtonType btnNao = new ButtonType("Não", ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(btnSim, btnNao);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == btnSim) {
            BonusDAO pdao = new BonusDAO();
            pdao.delete(bonus);
            btnCadastroBonus(null);
        } else {
            alert.close();
        }
    }
}
