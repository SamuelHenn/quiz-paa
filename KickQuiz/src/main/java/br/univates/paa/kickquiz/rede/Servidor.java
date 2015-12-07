package br.univates.paa.kickquiz.rede;

import br.univates.paa.kickquiz.AdminController;
import br.univates.paa.kickquiz.util.Utils;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor {

    private ArrayList<Jogador> clientes;
    private AdminController.RunMenssage runOnMenssage;
    ServerSocket socketServidor = null;
    PrintWriter outputSocket = null;

    public Servidor(AdminController.RunMenssage runOnConect) {
        this.clientes = new ArrayList();
        this.runOnMenssage = runOnConect;

        ServerRun sr = new ServerRun();
        sr.start();
    }

    public void enviarMensagem(int indiceJogador, String tipo, String valor) {
        Socket socket = this.clientes.get(indiceJogador).getSocket();
        try {
            outputSocket = new PrintWriter(socket.getOutputStream());
        } catch (IOException ex) {
            Utils.escreveLog("[Erro] enviar Mensagem", "Erro ao criar output do socket", 1);
            Utils.escreveLog("[Erro] enviar Mensagem - Detalhado", ex.getMessage(), 2);
        }
        try {
            outputSocket.println(tipo + ";" + valor);
            outputSocket.flush();
        } catch (NullPointerException e) {
            Utils.escreveLog("[Erro] enviar Mensagem", "Erro ao enviar a menssagem pelo output do socket", 1);
            Utils.escreveLog("[Erro] enviar Mensagem - Detalhado", e.getMessage(), 2);
        }
    }

    public class ServerRun extends Thread {

        public void run() {
            System.out.println("Servidor escutando......");
            Utils.escreveLog("[Mensagem] notificação", "Servidor escutando........", 3);
            try {
                socketServidor = new ServerSocket(4445);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Server error");
            }
            Socket socketNovo = null;
            while (true) {
                try {
                    socketNovo = socketServidor.accept();
                    Jogador j = new Jogador();
                    j.setDescricao("Jogador " + (clientes.size() + 1));
                    j.setSocket(socketNovo);
                    Utils.escreveLog("[Mensagem] notificação", "Nova conexao", 3);
                    ObterMensagem obterMensagensCliente = new ObterMensagem(socketNovo, runOnMenssage, null);
                    j.setObterMensagem(obterMensagensCliente);
                    j.getObterMensagem().start();
                    clientes.add(j);
                } catch (Exception e) {
                    Utils.escreveLog("[Erro] ao criar servidor", "Erro ao criar o socket para servidor", 1);
                    Utils.escreveLog("[Erro] ao criar servidor - Detalhado", e.getMessage(), 2);
                }
            }
        }
    }

    public ArrayList<Jogador> getClientes() {
        return clientes;
    }

    public void setClientes(ArrayList<Jogador> clientes) {
        this.clientes = clientes;
    }
}
