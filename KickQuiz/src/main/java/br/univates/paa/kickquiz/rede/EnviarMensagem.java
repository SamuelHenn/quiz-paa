package br.univates.paa.kickquiz.rede;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


class EnviarMensagem extends Thread {

    String line = null;
    BufferedReader inputSocket = null;
    PrintWriter outputSocket = null;
    Socket cliente = null;
    BufferedReader bufferReader = null;

    public EnviarMensagem(Socket cliente) {
        this.cliente = cliente;
        
    }

    public void run() {
        try {
            outputSocket = new PrintWriter(cliente.getOutputStream());
            bufferReader = new BufferedReader(new InputStreamReader(System.in));
        } catch (IOException e) {
            System.out.println("IO error in server thread");
        }
        try {
            line = bufferReader.readLine();
            while (line.compareTo("QUIT") != 0) {
                outputSocket.println("[servidor fala] - "+line);
                outputSocket.flush();
                System.out.println("Mensagem enviada");
                line = bufferReader.readLine();
            }
        } catch (IOException e) {
            line = this.getName(); //reused String line for getting thread name
            System.out.println("IO Error/ Client " + line + " terminated abruptly");
        } catch (NullPointerException e) {
            line = this.getName(); //reused String line for getting thread name
            System.out.println("Client " + line + " Closed");
        } finally {
            try {
                System.out.println("Connection Closing..");
                if (inputSocket != null) {
                    inputSocket.close();
                    System.out.println(" Socket Input Stream Closed");
                }
                if (outputSocket != null) {
                    outputSocket.close();
                    System.out.println("Socket Out Closed");
                }
                if (cliente != null) {
                    cliente.close();
                    System.out.println("Socket Closed");
                }
            } catch (IOException ie) {
                System.out.println("Socket Close Error");
            }
        }
    }
}
