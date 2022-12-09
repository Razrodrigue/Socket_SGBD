package serv;
import lecture.*;
import emp.*;
import java.io.*;
import java.net.*;

public class Server extends Thread{

    int nbClients;

    public void run() {
        try {
            ServerSocket ss = new ServerSocket(3214);
            // a chaque fois un client se connct on l accept
            System.out.println("attente de connection du client");
            while (true) {
                Socket s = ss.accept();
                ++nbClients;
                new Conversation(s, nbClients).start();  
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server().start();
    }



}








    