package br.univates.paa.kickquiz.rede;

import br.univates.paa.kickquiz.PerguntaController;
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
            e.printStackTrace();
            System.err.print("IO Exception");
        }
        ObterMensagem obterMensagens = new ObterMensagem(this.socket, null, runOnMenssage);
        obterMensagens.start();
        EnviarMensagem enviarMensagens = new EnviarMensagem(this.socket);
        enviarMensagens.start();

        System.out.println("Client Address : " + address);
        System.out.println("Enter Data to echo Server ( Enter QUIT to end):");

//        String response = null;
//        try {
//            line = bufferReader.readLine();
//            while (line.compareTo("QUIT") != 0) {
//                System.out.println();
//                outputSocket.println("[cliente fala] - " + line);
//                outputSocket.flush();
//                response = inputSocket.readLine();
//                System.out.println("Mensagem enviada: " + response);
//                line = bufferReader.readLine();
//
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.out.println("Socket read Error");
//        } finally {
//            inputSocket.close();
//            outputSocket.close();
//            bufferReader.close();
//            socket.close();
//            System.out.println("Connection Closed");
//        }
    }

    public void enviaMensagem(String tipo, String valor) {
        try {
            outputSocket = new PrintWriter(this.socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("IO error in server thread");
        }
        try {
            outputSocket.println(tipo + ";" + valor);
            outputSocket.flush();
        } catch (NullPointerException e) {
            System.out.println("Client " + line + " Closed");
        }
    }

    public void fechar() {
        try {
            System.out.println("Connection Closing..");
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
            System.out.println("Socket Close Error");
        }
    }
}
