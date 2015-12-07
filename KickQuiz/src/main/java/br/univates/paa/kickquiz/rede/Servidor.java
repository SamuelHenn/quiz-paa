package br.univates.paa.kickquiz.rede;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor {

    private ArrayList<Jogador> clientes;
    private Runnable runOnConect;
    ServerSocket socketServidor = null;

    public Servidor(Runnable runOnConect) {
        this.clientes = new ArrayList();
        this.runOnConect = runOnConect;

        ServerRun sr = new ServerRun();
        sr.start();
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
                    ObterMensagem obterMensagensCliente = new ObterMensagem(socketNovo);
                    j.setObterMensagem(obterMensagensCliente);
                    j.getObterMensagem().start();
                    EnviarMensagem enviarMensagensCliente = new EnviarMensagem(socketNovo);
                    j.setEnviarMensagem(enviarMensagensCliente);
                    j.getEnviarMensagem().start();
                    clientes.add(j);
                    if (runOnConect != null) {
                        runOnConect.run();
                    }
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
