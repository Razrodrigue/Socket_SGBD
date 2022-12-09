package client;
import emp.*;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.Vector;

public class Client extends Thread{
    Socket s;

    public Client(Socket socket){
    super();
    this.s=socket;
    }

    public void run(){
        //
        try {
             
            
           /* */  //pour envoyer une chaine de carractere
             OutputStream os= s.getOutputStream();
             PrintWriter pw= new PrintWriter(os,true);

               //pour lire un objet envoyer par le serveur
           InputStream is = s.getInputStream(); 
           ObjectInputStream ois= new ObjectInputStream(is);

           //chaine de caractere
           InputStreamReader isr= new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

           //InputStreamReader isr= new InputStreamReader(is);
           //BufferedReader br = new BufferedReader(isr);
            /* */
            String mes=br.readLine();
            System.out.println(mes);
            System.out.println("\n");
            

            while(true){
            Scanner scanner=new Scanner(System.in);
            System.out.print("SuperSQL >#");
            String req=scanner.nextLine();
            /* */
            pw.println(req);
            /* */
            Vector <String> t1=(Vector <String>)ois.readObject();
            Vector<Vector<String>>d1=(Vector<Vector<String>>)ois.readObject();
            //System.out.println("titre :"+t1);
            //System.out.println("donne :"+d1);
            if(t1 != null && d1!=null){
                Table table=new Table();
                try {
                    table.setTitre(t1);
                    table.setData(d1);
                    table.viewTable();
                    System.out.println("(row :"+d1.size()+")");
                    String messag=br.readLine();
                    System.out.println(messag);
                    System.out.println("\n");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            }
            else{
                String messag=br.readLine();
                System.out.println(messag);
                System.out.println("\n");
            }




            }
           

       } catch (Exception e) {
           // TODO: handle exception
       }
   }

    

    /**
     * @param args
     * @param scanner2
     */
    public static void main(String[] args) {

        try {
            InetAddress addr = InetAddress.getByName(null); 
            Socket s= new Socket(addr,3214);
            new Client(s).start();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        
    }
}