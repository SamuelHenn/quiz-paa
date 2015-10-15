/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univates.paa.kickquiz.util;

import br.univates.paa.kickquiz.model.Usuario;
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
    }
}
