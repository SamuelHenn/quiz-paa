/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univates.paa.kickquiz.util;

import br.univates.paa.kickquiz.DAO.ConfiguracaoDAO;
import br.univates.paa.kickquiz.DAO.UsuarioDAO;
import br.univates.paa.kickquiz.model.Configuracao;
import br.univates.paa.kickquiz.model.Usuario;
import br.univates.paa.kickquiz.rede.Servidor;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.stage.Stage;

/**
 *
 * @author Avell G1310 MAX
 */
public class Utils {

    private static Usuario userLogado;
    public static Servidor servidor;

    public static void abrirTela(Class classe, Control control, String tela) throws Exception {
        Parent root = FXMLLoader.load(classe.getResource("/fxml/" + tela + ".fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        Stage stage = (Stage) control.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public static Usuario getUserLogado() {
        return userLogado;
    }

    public static void setUserLogado(Usuario userLogado) {
        Utils.userLogado = userLogado;
        UsuarioDAO dao = new UsuarioDAO();
        dao.setUserLogado(userLogado);
    }

    public static void escreveLog(String titulo, String frase) {
        escreveLog(titulo, frase, 1);
    }

    public static void escreveLog(String titulo, String frase, int nivel) {
        ConfiguracaoDAO confNivelLog = new ConfiguracaoDAO();
        Configuracao nivelLog = confNivelLog.getByChave("nivel_log");
        if(Integer.parseInt(nivelLog.getValor()) < nivel){
            System.out.println("Nível inferior ao configurado, não ira gravar este log");
            System.out.println(titulo + " | " + frase);
            return;
        }
        
        ConfiguracaoDAO confDiretorioLog = new ConfiguracaoDAO();
        if(confDiretorioLog == null){
            System.out.println("Diretorio não encontrado nas configurações");
            return;
        }
        Configuracao diretorioLog = confDiretorioLog.getByChave("diretorio_log");
        
        String nome_arquivo;
        String nome_diretorio = diretorioLog.getValor();
        try {
            DateFormat data = new SimpleDateFormat("yyyy\\MM\\");// HH:mm:ss
            nome_diretorio = "C:\\Temp\\quiz\\" + data.format(new Date()) + "\\";
            File diretorio = new File(nome_diretorio);
            if (diretorio.mkdirs()) {
                System.out.println("criou a pasta");
            }

            data = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss
            nome_arquivo = data.format(new Date()) + ".txt";
            File arquivo = new File(nome_diretorio + nome_arquivo); // se já existir, será sobreescrito
            FileWriter fw = new FileWriter(arquivo, true);
            BufferedWriter bw = new BufferedWriter(fw);

            data = new SimpleDateFormat("HH:mm:ss");
            bw.write("===========================================\r\n");
            bw.write("[" + data.format(new Date())+ "] - " + titulo + "\r\n");
            bw.write("Mensagem: " + frase + "\r\n");
            bw.flush();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
