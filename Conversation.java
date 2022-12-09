package serv;
import lecture.*;
import emp.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.net.*;
import java.util.Vector;



public class Conversation extends Thread {
    Socket socket;
    int nbr;

    public Conversation(Socket socket, int nbr){
        super();
        this.socket= socket;
        this.nbr=nbr;
    }

    public void run(){
        //code de la conversation
        
        try {
            /******** */
            InputStream is = socket.getInputStream(); 
            InputStreamReader isr= new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);


            //Pour serialiser un objet vers client
            OutputStream os= socket.getOutputStream();
            ObjectOutputStream oss= new ObjectOutputStream(os);


            //envoyer les donnees 
            //OutputStream os1= socket.getOutputStream();
            PrintWriter pw= new PrintWriter(os,true);
            Lecture lec=new Lecture();


                     //il permet de returner les informations du client
            String IP= socket.getRemoteSocketAddress().toString();
            System.out.println("Connection du client numero :"+nbr+" IP ="+IP);
            pw.println("Bienvienue, vous etes le clients numero :"+nbr);
            /************************* */
            

            //pour faire tourner la conversation 
            while(true){
                String req=br.readLine();
                System.out.println(" La requette :"+req);
                if(req!=null){
                    Vector<String>titre=new Vector<>();
                    Vector<Vector<String>> data=new Vector<Vector<String>>();
                    Table table=lec.request(req);
                    String erreur=lec.getErreur();
                    //System.out.println(erreur);
                        
                        if(table!=null){
                            titre=table.getTitre();
                            data=table.getData();
                            oss.writeObject(titre);
                            oss.writeObject(data);
                        }
                        try {
                            if (erreur!=null){
                                pw.println(erreur);
                               //System.out.println(erreur);
                            }
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        
                    
                }
            }
           
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    
}