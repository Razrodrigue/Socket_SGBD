package ecriture;

import java.awt.event.*;
import java.io.*;
public class Ecrire {
    File file;
    FileWriter write;
    FileReader read;
    
    public  void createTable (String table,String[]colonne) {
        String dir = table+".txt";
        file = new File(dir);
        
        try {
            write = new FileWriter(file,true);
            read = new FileReader(dir);
            //BufferedReader r_donnee = new BufferedReader(read);
            BufferedWriter w_donnee = new BufferedWriter(write);
             //String verif = r_donnee.readLine();
        
            for(int i=0;i< colonne.length;i++) {
                w_donnee.write(colonne[i]/* .getText() + ";;"*/);
                if(i<colonne.length-1){
                    w_donnee.write( ";;");
                }
            }
            w_donnee.write("//");
            w_donnee.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }


    public void insert(String table,String[]data){
       String dir = table+".txt";
       file = new File(dir);
        try {
            write = new FileWriter(file,true);
            read = new FileReader(dir);
            //BufferedReader r_donnee = new BufferedReader(read);
            BufferedWriter w_donnee = new BufferedWriter(write);
             //String verif = r_donnee.readLine();
        
            for(int i=0;i< data.length;i++) {
                w_donnee.write(data[i]/* .getText() + ";;"*/);
                if(i<data.length-1){
                    w_donnee.write( ";;");
                }
            }
            w_donnee.write("//");
            w_donnee.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}