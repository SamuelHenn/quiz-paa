/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univates.paa.kickquiz.rede;

import java.net.Socket;

/**
 *
 * @author Avell G1310 MAX
 */
public class Jogador {
    
    private String descricao;
    private Socket socket;
    private ObterMensagem obterMensagem;
    private EnviarMensagem enviarMensagem;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ObterMensagem getObterMensagem() {
        return obterMensagem;
    }

    public void setObterMensagem(ObterMensagem obterMensagem) {
        this.obterMensagem = obterMensagem;
    }

    public EnviarMensagem getEnviarMensagem() {
        return enviarMensagem;
    }

    public void setEnviarMensagem(EnviarMensagem enviarMensagem) {
        this.enviarMensagem = enviarMensagem;
    }
    
    
}
