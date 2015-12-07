package br.univates.paa.kickquiz.rede;

import br.univates.paa.kickquiz.AdminController;
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
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            outputSocket.println(tipo + ";" + valor);
            outputSocket.flush();
        } catch (NullPointerException e) {
            System.out.println("Client Closed");
        }
    }

    public class ServerRun extends Thread {

        public void run() {
            System.out.println("Servidor escutando......");
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
                    System.out.println("Nova conexão");
                    ObterMensagem obterMensagensCliente = new ObterMensagem(socketNovo, runOnMenssage, null);
                    j.setObterMensagem(obterMensagensCliente);
                    j.getObterMensagem().start();
                    EnviarMensagem enviarMensagensCliente = new EnviarMensagem(socketNovo);
                    j.setEnviarMensagem(enviarMensagensCliente);
                    j.getEnviarMensagem().start();
                    clientes.add(j);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Connection Error");
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
