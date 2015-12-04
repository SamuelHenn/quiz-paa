package br.univates.paa.kickquiz.rede;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Cliente {

    public Cliente(String host) throws IOException {
        InetAddress address = InetAddress.getByName(host);
        Socket socket = null;
        String line = null;
        BufferedReader bufferReader = null;
        BufferedReader inputSocket = null;
        PrintWriter outputSocket = null;
        try {
            socket = new Socket(address, 4445); // You can use static final constant PORT_NUM
            bufferReader = new BufferedReader(new InputStreamReader(System.in));
            inputSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outputSocket = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            System.err.print("IO Exception");
        }
        ObterMensagem obterMensagens = new ObterMensagem(socket);
        obterMensagens.start();
        EnviarMensagem enviarMensagens = new EnviarMensagem(socket);
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
}
