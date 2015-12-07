package br.univates.paa.kickquiz.rede;

import br.univates.paa.kickquiz.PerguntaController;
import br.univates.paa.kickquiz.util.Utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Cliente {

    private Socket socket = null;
    private String line = null;
    private BufferedReader inputSocket = null;
    private PrintWriter outputSocket = null;
    private BufferedReader bufferReader = null;

    public Cliente(String host, PerguntaController.RunMenssage runOnMenssage) throws IOException {
        InetAddress address = InetAddress.getByName(host);
        String line = null;
        BufferedReader bufferReader = null;
        BufferedReader inputSocket = null;
        PrintWriter outputSocket = null;
        try {
            this.socket = new Socket(address, 4445); // You can use static final constant PORT_NUM
            bufferReader = new BufferedReader(new InputStreamReader(System.in));
            inputSocket = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            outputSocket = new PrintWriter(this.socket.getOutputStream());
        } catch (IOException e) {
            Utils.escreveLog("[Erro] ao criar cliente", "Erro ao criar o socket para clientes", 1);
            Utils.escreveLog("[Erro] ao criar cliente - Detalhado", e.getMessage(), 2);
        }
        ObterMensagem obterMensagens = new ObterMensagem(this.socket, null, runOnMenssage);
        obterMensagens.start();

        Utils.escreveLog("[Mensagem] notificação", "Endereço do servidor" + address, 3);
    }

    public void enviaMensagem(String tipo, String valor) {
        try {
            outputSocket = new PrintWriter(this.socket.getOutputStream());
        } catch (IOException e) {
            Utils.escreveLog("[Erro] enviar Mensagem", "Erro ao criar output do socket", 1);
            Utils.escreveLog("[Erro] enviar Mensagem - Detalhado", e.getMessage(), 2);
        }
        try {
            outputSocket.println(tipo + ";" + valor);
            outputSocket.flush();
        } catch (NullPointerException e) {
            Utils.escreveLog("[Erro] enviar Mensagem", "Erro ao enviar a menssagem pelo output do socket", 1);
            Utils.escreveLog("[Erro] enviar Mensagem - Detalhado", e.getMessage(), 2);
        }
    }

    public void fechar() {
        try {
            Utils.escreveLog("[Mensagem] notificação", "Fechando socket", 3);
            if (inputSocket != null) {
                inputSocket.close();
            }
            if (outputSocket != null) {
                outputSocket.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException ie) {
            Utils.escreveLog("[Erro] fechar socket", "Erro ao fechar a conexao do socket", 1);
            Utils.escreveLog("[Erro] enviar Mensagem - Detalhado", ie.getMessage(), 2);
        }
    }
}
