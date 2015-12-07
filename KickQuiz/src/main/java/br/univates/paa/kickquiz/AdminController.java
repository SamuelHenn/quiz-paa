package br.univates.paa.kickquiz;

import br.univates.paa.kickquiz.DAO.BonusDAO;
import br.univates.paa.kickquiz.DAO.PerguntaDAO;
import br.univates.paa.kickquiz.DAO.PermissoesDAO;
import br.univates.paa.kickquiz.DAO.UsuarioDAO;
import br.univates.paa.kickquiz.model.Bonus;
import br.univates.paa.kickquiz.model.Pergunta;
import br.univates.paa.kickquiz.model.Permissoes;
import br.univates.paa.kickquiz.model.Usuario;
import br.univates.paa.kickquiz.rede.Jogador;
import br.univates.paa.kickquiz.rede.Servidor;
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
    public static final int USUARIO = 3;
    public static final int CLIENTES = 4;

    @FXML
    private MenuBar menuBar;

    @FXML
    private Label tvTitle, tvUser;

    @FXML
    private TableView tvData;

    @FXML
    private Button btnNovo, btnEdit, btnDelete;

    @FXML
    private void btnClientes(ActionEvent event) {
        try {
            List<Jogador> itens = Utils.servidor.getClientes();
            ObservableList data = FXCollections.observableList(itens);
            opcao = CLIENTES;

            tvTitle.setVisible(true);
            tvData.setVisible(true);
            btnNovo.setVisible(true);
            btnNovo.setText("Dar Bonus");
            btnEdit.setVisible(false);
            btnDelete.setVisible(false);
            tvTitle.setText("Jogadores");
            tvData.setItems(data);
            TableColumn desc = new TableColumn("Descrição");
            desc.setCellValueFactory(new PropertyValueFactory("descricao"));
            tvData.getColumns().setAll(desc);
            tvData.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Atenção");
            alert.setHeaderText(e.getMessage());
            alert.setContentText(null);
            alert.show();
        }
    }

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
                case USUARIO:
                    excluirUsuario();
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
                case USUARIO:
                    editarUsuario();
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
            permicaoBotoes(Pergunta.class.getName());
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
            permicaoBotoes(Bonus.class.getName());
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
    private void btnCadastroUsuario(ActionEvent event) {
        try {
            UsuarioDAO bdao = new UsuarioDAO();
            List<Usuario> itens = bdao.listAll();
            ObservableList data = FXCollections.observableList(itens);
            opcao = USUARIO;

            visibilidadeContent(true);
            permicaoBotoes(Usuario.class.getName());
            tvTitle.setText("Usuário");
            tvData.setItems(data);
            TableColumn nome = new TableColumn("Nome");
            nome.setCellValueFactory(new PropertyValueFactory("nome"));
            TableColumn login = new TableColumn("Login");
            login.setCellValueFactory(new PropertyValueFactory("login"));
            tvData.getColumns().setAll(nome, login);
            tvData.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void btnConfiguracoes(ActionEvent event) {
        try {
            Utils.abrirTela(getClass(), menuBar, "configuracao");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void btnAjuda(ActionEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("KickQuiz");
            alert.setHeaderText("É um super jogo de perguntas e respostas!");
            alert.setContentText("Desenvolvido por Miguel e Samuel para cadeira de programação avançada de aplicações.");
            alert.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void permicaoBotoes(String obj) {
        PermissoesDAO pdao = new PermissoesDAO();
        btnNovo.setVisible(pdao.temPermissao(new Permissoes(btnNovo.getId(), obj, Utils.getUserLogado().getPermissao())));
        btnEdit.setVisible(pdao.temPermissao(new Permissoes(btnEdit.getId(), obj, Utils.getUserLogado().getPermissao())));
        btnDelete.setVisible(pdao.temPermissao(new Permissoes(btnDelete.getId(), obj, Utils.getUserLogado().getPermissao())));
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
                case USUARIO:
                    Utils.abrirTela(getClass(), menuBar, "cadastro_usuario");
                    break;
                case CLIENTES:
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Dar bonus");
                    alert.setHeaderText("Um bonus foi oferecido ao jogador");
                    alert.setContentText(null);
                    alert.show();
                    Utils.servidor.enviarMensagem(0, "10", "Bonus");
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

    private void editarUsuario() throws Exception {
        Usuario pergunta = (Usuario) tvData.getItems().get(tvData.getSelectionModel().getSelectedIndex());
        if (pergunta == null) {
            throw new Exception("Nenhum usuário selecionado");
        }

        Stage stage = (Stage) tvData.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/cadastro_usuario.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        CadastroUsuarioController controller = fxmlLoader.<CadastroUsuarioController>getController();
        controller.setUsuario(pergunta);
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
            RunMenssage r = new RunMenssage();
            if (Utils.servidor == null) {
                Utils.servidor = new Servidor(r);
            }

            visibilidadeContent(false);
            Usuario u = Utils.getUserLogado();
            if (u != null) {
                tvUser.setText("Olá " + u.getNome());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public class RunMenssage implements Runnable {

        private String message = null;

        @Override
        public void run() {
            if (message == null) {
                return;
            }

            String[] acao = message.split(";");
            if (acao[0].equals("1")) {
                Utils.servidor.getClientes().get(0).setDescricao(acao[1]);
                btnClientes(null);
            }
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    }

    private void visibilidadeContent(boolean visible) {
        btnNovo.setText("Novo");
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

    private void excluirUsuario() throws Exception {
        Usuario usuario = (Usuario) tvData.getItems().get(tvData.getSelectionModel().getSelectedIndex());
        if (usuario == null) {
            throw new Exception("Nenhum usuário selecionada");
        }

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Excluir usuário");
        alert.setHeaderText("Você quer mesmo excluir esse usuário?");
        ButtonType btnSim = new ButtonType("Sim");
        ButtonType btnNao = new ButtonType("Não", ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(btnSim, btnNao);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == btnSim) {
            UsuarioDAO pdao = new UsuarioDAO();
            pdao.delete(usuario);
            btnCadastroUsuario(null);
        } else {
            alert.close();
        }
    }
}
