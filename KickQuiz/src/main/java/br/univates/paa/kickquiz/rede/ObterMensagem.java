package br.univates.paa.kickquiz.rede;

import br.univates.paa.kickquiz.AdminController;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ObterMensagem extends Thread {

    String line = null;
    BufferedReader inputSocket = null;
    Socket cliente = null;
    private AdminController.RunMenssage runOnMenssage;

    public ObterMensagem(Socket cliente, AdminController.RunMenssage runOnConect) {
        this.cliente = cliente;
        this.runOnMenssage = runOnConect;
    }

    public void run() {
        try {
            inputSocket = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
        } catch (IOException e) {
            System.out.println("IO error in server thread");
        }
        try {
            line = inputSocket.readLine();
            while (line.compareTo("QUIT") != 0) {
                System.out.println("Mensagem do cliente :  " + line);
                if (line != null && line.length() > 0 && runOnMenssage != null) {
                    runOnMenssage.setMessage(line);
                    runOnMenssage.run();
                }
                line = inputSocket.readLine();
            }
        } catch (IOException e) {
            line = this.getName();
            System.out.println("IO Error/ Client " + line + " terminated abruptly");
        } catch (NullPointerException e) {
            line = this.getName();
            System.out.println("Client " + line + " Closed");
        } finally {
            try {
                System.out.println("Connection Closing..");
                if (inputSocket != null) {
                    inputSocket.close();
                    System.out.println(" Socket Input Stream Closed");
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
