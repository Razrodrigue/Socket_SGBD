 package lecture;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Vector;

import emp.Table;
import ecriture.*;

public class Lecture {
    String erreur;

    public String getErreur() {
        return erreur;
    }
    public void setErreur(String erreur) {
        this.erreur = erreur;
    }


    Table tab = new Table();
    Table tab2 = new Table();
    Ecrire ecrire= new Ecrire();

    public Table getTableFromFile(String nomTable) throws Exception {
        Table result = new Table();

        File file = new File(nomTable + ".txt");
        try {
            FileReader read = new FileReader(file);
            BufferedReader bf = new BufferedReader(read);
            String f = bf.readLine();
            // On separe par le split (//) le String dans le fichier
            String[] tab1 = f.split("//");
            // On separe par le split (;;) le String indice 0 pour le titre
            String[] tempTitre = tab1[0].split(";;"); // titre

            // On creer un vector pour stocke les valeur du titre
            Vector<String> theTitle = new Vector<>();

            for (int i = 0; i < tempTitre.length; i++) {
                theTitle.add(tempTitre[i]);
            }

            result.setTitre(theTitle); // Azo le titre table

            // On creer un vector pour les autre chaine separer par (//)
            // c est a dire pour les donnes appartir d indice 1 a n
            Vector<Vector<String>> donnes = new Vector<>();

            for (int i = 1; i < tab1.length; i++) {
                Vector<String> tempData = new Vector<>();
                String[] tempDataString = tab1[i].split(";;");

                for (int k = 0; k < tempDataString.length; k++) {
                    tempData.add(tempDataString[k]);
                }
                donnes.add(tempData);
            }
            // On enregistre les donnees
            result.setData(donnes);

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Erreur amn maka fichier => Table");
        }
        return result;
    }



    //requette generaliser
    public Table request(String request) throws Exception {
        Table result = new Table();
        String[] split = request.split(" ");
        int indfrom = indexMot(split, "from");

        try {
            if (split.length <= 6){
                 if (split[0].equals("select") == true  ) {
                    Table tabtemporaire = this.getTableFromRequest(request,split[indfrom+1]);
                    result = tabtemporaire;
                    //this.setErreur(" ");
                }else {
                    //System.out.println("Erreur: sintax erreur: ->"+split[0]);
                    this.setErreur("Erreur: sintax ");
                } 
            }
            if (split[0].equals("create") == true ) {
                this.createTableRequest( request);
                
            }
            if (split[0].equals("insert") == true ) {
                this.insertTableRequest( request);
               
            }

            if (split.length >= 8){
                if (split[0].equals("select") == true && split[4].equals("join") == true ) {
                    //this.insertTableRequest( request);
                    Table tabtemporaire = this.getTableJoinRequest( request);
                    result = tabtemporaire;
                    //System.out.println("rien");
                    this.setErreur(" ");
                } 
                else {
                    //System.out.println("Erreur: sintax erreur: ->"+split[0]);
                    this.setErreur("Erreur: sintax ");
                } 
            }
           
            
        } catch (Exception e) {
            // TODO: handle exception
            throw new Exception("Erreur pour la requette");
        }
        return result;
    }




    // pour faire une requette simple
    public Table getTableFromRequest(String request, String nomTable) throws Exception {
        Table result = new Table();
        // Table tabtemporaire= new Table();
 
        // String req= "select * from emp";
        String[] split = request.split(" ");

        int indetable = indexMot(split, "from");
        try {
            // on verifie la a table a selectionner dans la requette
            if (split[indetable + 1].equals(nomTable) == true) {
                // on verifie si il y le mot select
                if (split[0].equals("select") == true) {
                    // on verifie la caractere * et si il a 4 mot
                    if (split[1].equals("*") == true && split.length == 4) {
                        // on verifie si il y le mot from
                        if (split[2].equals("from") == true && split.length == 4) {
                            // on prend les donnes de la table
                            Table tabtemporaire = this.getTableFromFile(nomTable);
                            result = tabtemporaire;
                            this.setErreur("(reusit)");
                        }
                        else {
                            //System.out.println("Erreur:  sintax erreur");
                            this.setErreur("Erreur: sintax erreur >from");
                        }
                    }
                    // on verifie la caractere * et si il a 4 mot
                    // c est a dire avec where
                    if (split[1].equals("*") == true && split.length == 6) {
                        if (split[2].equals("from") == true && split[4].equals("where") == true && split.length == 6) {
                            String[] colonne = split[5].split("=");
                            String colochercher = colonne[0];
                            String valeur = colonne[1];
                            Table mytable = this.getTableFromFile(nomTable);
                            Table tabtemporaire = this.selection(colochercher, valeur,mytable );
                            result = tabtemporaire;
                            // System.out.println("with where");
                            this.setErreur("(reusit)");
                        }
                        else {
                            //System.out.println("Erreur:  sintax erreur");
                            this.setErreur("Erreur: sintax erreur >from");
                        }
                    }

                    if (split[1].equals("*") == false && split.length == 4) {
                        String[] colonne = split[1].split(",");
                        if (split[2].equals("from") == true) {
                            Table tabtemporaire = this.getTableFromFile(nomTable);
                            result = tabtemporaire.Projection(colonne);
                            this.setErreur("(reusit)");
                        } else {
                            //System.out.println("Erreur: sintax inconue: ->" + split[2]);
                            this.setErreur("Erreur: sintax erreur >from");
                        }
                    }

                    if (split[1].equals("*") == false && split.length == 6) {
                        String[] colonne = split[1].split(",");
                        if (split[2].equals("from") == true && split[4].equals("where") == true) {
                            String[] co = split[5].split("=");
                            String colochercher = co[0];
                            String valeur = co[1];
                            Table mytable = this.getTableFromFile(nomTable);
                            Table tabtemporaire = this.selection(colochercher, valeur, mytable);
                            result = tabtemporaire.Projection(colonne);
                            // System.out.println("with where");
                            this.setErreur("(reusit)");
                        }
                        else {
                            //System.out.println("Erreur: sintax inconue: ->" + split[2]);
                            this.setErreur("Erreur: sintax erreur >from");
                        }
                    }

                } else {
                   // System.out.println("Erreur: sintax erreur: ->"+split[0]);
                    //this.setErreur("Erreur: sintax erreur");
                }
            } else {
                //System.out.println("Erreur:  sintax erreur");
                this.setErreur("Erreur: sintax erreur >nom table");
            }
        } catch (Exception e) {
            // TODO: handle exception
            throw new Exception("Erreur pour la grammaire");
        }

        return result;
    }

    // Pour avaoir l indece du mot a chercher
    int indexMot(String[] tousMots, String aChercher) {
       // int res =0 ;
       int res =-1 ;
        for (int i = 0; i < tousMots.length; i++) {
            if (tousMots[i].equals(aChercher) == true) {
                res = i;
            }
        }

        return res;
    }





    public Table getTableJoinRequest(String request) throws Exception {
        Table result = new Table();
        // Table tabtemporaire= new Table();
 
        // String req= "select * from emp";
        String[] split = request.split(" ");

        int indetable = indexMot(split, "from");
        try {
            // on verifie la a table a selectionner dans la requette
            if (split[4].equals("join") == true && split[6].equals("on") == true ) {
                //System.out.println("join et on");
                // on verifie si il y le mot select
                if (split[0].equals("select") == true) {
                   // System.out.println("select");

                    // on verifie la caractere * et si il a 4 mot
                    if (split[1].equals("*") == true && split.length == 8) {
                        // System.out.println("*");
                        // on verifie si il y le mot from
                        if (split[2].equals("from") == true && split.length == 8) {
                            //System.out.println("from");
                            // on prend les donnes de la table
                            Table tab1 = this.getTableFromFile(split[3]);
                            Table tab2 = this.getTableFromFile(split[5]);
                            Vector<String> titre1=tab1.getTitre();
                            Vector<String> titre2=tab2.getTitre();
                            //vector to string
                            String[] title1=titre1.toArray(new String[titre1.size()]);
                            String[] title2=titre2.toArray(new String[titre2.size()]);
                            String[] condition = split[7].split("=");

                            int colonne1 = indexMot(title1, condition[0]);
                            int colonne2 = indexMot(title2, condition[1]);
                            //Si les colonnes de la table et celle de la requette sont indentique
                            // si non c est -1
                                if(colonne1 != -1  && colonne2 != -1 ){
                                    Table tabtemporaire = this.jointure( tab1, tab2, colonne1, colonne2);
                                    //System.out.println(tab1.getTitre());
                                    result = tabtemporaire;
                                    this.setErreur("(reusit)");
                                }else{
                                    this.setErreur("Erreur: sintax erreur proche >on");
                                }
                                
                            
                        }
                    }

                    // on verifie la caractere * et si il a 10 mot
                    // c est a dire avec where
                      if (split[1].equals("*") == true && split.length == 10) {
                        if (split[2].equals("from") == true && split[8].equals("where") == true && split.length == 10) {
                            Table tab1 = this.getTableFromFile(split[3]);
                            Table tab2 = this.getTableFromFile(split[5]);
                            Vector<String> titre1=tab1.getTitre();
                            Vector<String> titre2=tab2.getTitre();
                            //vector to string
                            String[] title1=titre1.toArray(new String[titre1.size()]);
                            String[] title2=titre2.toArray(new String[titre2.size()]);
                            String[] condition = split[7].split("=");

                            int colonne1 = indexMot(title1, condition[0]);
                            int colonne2 = indexMot(title2, condition[1]);
                            //Si les colonnes de la table et celle de la requette sont indentique
                            // si non c est -1
                                if(colonne1 != -1  && colonne2 != -1 ){
                                    Table tabtejointure = this.jointure( tab1, tab2, colonne1, colonne2);
                                    //System.out.println(tab1.getTitre());
                                    //result = tabtemporaire;

                                    String[] colonne = split[9].split("=");
                                    String colochercher = colonne[0];
                                    String valeur = colonne[1];
                                    Table tabtemporaire = this.selection(colochercher, valeur, tabtejointure);
                                    result = tabtemporaire;
                                    this.setErreur("(reusit)");
                                    // System.out.println("with where");

                                }
                                else {
                                    //System.out.println("Erreur: sintax erreur: ->"+split[0]);
                                    this.setErreur("Erreur: sintax proche >on ");
                                }
                            


                        }
                    }

                   if (split[1].equals("*") == false && split.length == 8) {
                        String[] colonne = split[1].split(",");
                        if (split[2].equals("from") == true) {
                            //Table tabtemporaire = this.getTableFromFile(nomTable);
                            //result = tabtemporaire.Projection(colonne);

                            // on prend les donnes de la table
                            Table tab1 = this.getTableFromFile(split[3]);
                            Table tab2 = this.getTableFromFile(split[5]);
                            Vector<String> titre1=tab1.getTitre();
                            Vector<String> titre2=tab2.getTitre();
                            //vector to string
                            String[] title1=titre1.toArray(new String[titre1.size()]);
                            String[] title2=titre2.toArray(new String[titre2.size()]);
                            String[] condition = split[7].split("=");

                            int colonne1 = indexMot(title1, condition[0]);
                            int colonne2 = indexMot(title2, condition[1]);
                            //Si les colonnes de la table et celle de la requette sont indentique
                            // si non c est -1
                                if(colonne1 != -1  && colonne2 != -1 ){
                                    Table tabtemporaire = this.jointure( tab1, tab2, colonne1, colonne2);
                                    //System.out.println(tab1.getTitre());
                                    result = tabtemporaire.Projection(colonne);
                                    this.setErreur("(reusit)");
                                }else {
                                    //System.out.println("Erreur: sintax erreur: ->"+split[0]);
                                    this.setErreur("Erreur: sintax proche >on ");
                                }


                        } else {
                            //System.out.println("Erreur: sintax inconue: ->" + split[2]);
                            this.setErreur("Erreur: sintax ");
                        }
                    }

                     if (split[1].equals("*") == false && split.length == 10) {
                        String[] colonne = split[1].split(",");
                        if (split[2].equals("from") == true && split[8].equals("where") == true) {
                            //String[] co = split[5].split("=");
                            //String colochercher = co[0];


                             // on prend les donnes de la table
                             Table tab1 = this.getTableFromFile(split[3]);
                             Table tab2 = this.getTableFromFile(split[5]);
                             Vector<String> titre1=tab1.getTitre();
                             Vector<String> titre2=tab2.getTitre();
                             //vector to string
                             String[] title1=titre1.toArray(new String[titre1.size()]);
                             String[] title2=titre2.toArray(new String[titre2.size()]);
                             String[] condition = split[7].split("=");
 
                             int colonne1 = indexMot(title1, condition[0]);
                             int colonne2 = indexMot(title2, condition[1]);

                             
                             String[] colon = split[9].split("=");
                             String colochercher = colon[0];
                             String valeur = colon[1];
                             //Si les colonnes de la table et celle de la requette sont indentique
                             // si non c est -1
                             
                                 if(colonne1 != -1  && colonne2 != -1 ){
                                     Table tabjoint = this.jointure( tab1, tab2, colonne1, colonne2);
                                     Table tabtemporaire = this.selection(colochercher, valeur, tabjoint);
                                     result = tabtemporaire.Projection(colonne);
                                     this.setErreur("(reusit)");
                                 }else {
                                    //System.out.println("Erreur: sintax erreur: ->"+split[0]);
                                    this.setErreur("Erreur: sintax proche >on ");
                                }


                        }
                    }
                     
                } else {
                   // System.out.println("Erreur: sintax erreur: ->" + split[0]);
                }
            } else {
               // System.out.println("Erreur:  sintax erreur");
                this.setErreur("Erreur: sintax erreur >nom table");
            }
        } catch (Exception e) {
            // TODO: handle exception
            throw new Exception("Erreur pour la grammaire");
        }

        return result;
    }



    

    public void createTableRequest(String request) throws Exception {
        String[] split = request.split(" ");
        if (split[0].equals("create") == true && split[1].equals("table") == true && split.length == 4) {
            String[] valuer = split[3].split("[(]");
            String [] m=valuer[1].split("[)]");

            String[] colonne = m[0].split(",");
            ecrire.createTable(split[2],colonne);
            this.setErreur("table cree");
        }
        else{
            //System.out.println("erreur pour la creation de la table");
            this.setErreur("erreur pour la creation de la table");
        }

    }


    public void insertTableRequest(String request) throws Exception {
        String[] split = request.split(" ");
        if (split[0].equals("insert") == true && split[1].equals("into") == true && split[3].equals("values") == true && split.length == 5) {
            String[] valuer = split[4].split("[(]");
            String [] m=valuer[1].split("[)]");
           
            String[] data = m[0].split(",");
            ecrire.insert(split[2], data);
            this.setErreur("insertion reussit");
        }
        else{
            //System.out.println("erreur pour l insertion dans table");
            this.setErreur("erreur pour l insertion dans table");
        }

    }



    //public Table selection(String colonne, String valeur, String nomtable) throws Exception {
    public Table selection(String colonne, String valeur, Table table) throws Exception {
        Table result = new Table();
        try {
            //Table table = new Table();
            
            // table = this.getTableFromFile("emp");
           // table = this.getTableFromFile(nomtable);
            Vector<String> titre = table.getTitre();
            Vector<Vector<String>> data = table.getData();
            int indiceColonne = 0;
            boolean verifiercolonne = false;

            // INDICE MISY NY COLONNE
            for (int j = 0; j < titre.size(); j++) {
                if (titre.get(j).equals(colonne) == true) {
                    indiceColonne = j; // Indice anle titre
                    // System.out.println("indice colonne enregistre"+indiceColonne);
                }
            }
            // pour chercher la ligne qui possede la valeur a chercher
            Vector line = new Vector<>();
            Vector<Vector<String>> donne = new Vector<>();
            for (int i = 0; i < data.size(); i++) {
                //on verifie dans la table si le where 
                if (data.get(i).get(indiceColonne).equals(valeur) == true) {
                    line=data.get(i);
                    donne.add(line);
                    verifiercolonne = true;
                }
                
            }
            // verification si la valeur du colonne rechercher existe
            // et si c est le cas on returne les donner selectionner
            if (verifiercolonne == true) {
               // donne.add(line);
                // System.out.println(donne);
                result.setTitre(titre);
                result.setData(donne);
            } else {
                System.out.println("Erreur:  valeur introuvable -> where");
            }

        } catch (Exception e) {
            // TODO: handle exception

            throw new Exception("Erreu sur la methode selection");
        }

        return result;
    }

    public Table produitcartesienne(String nomtab1, String nomtab2) throws Exception {
        Table result = new Table();
        try {
            Table tab1 = this.getTableFromFile(nomtab1);
            Table tab2 = this.getTableFromFile(nomtab2);
            Vector<String> newTitle = new Vector<>();
            Vector<Vector<String>> newdata = new Vector<>();
            Vector<String> newline = new Vector<>();
            Vector<String> tab1Title = tab1.getTitre();
            Vector<String> tab2Title = tab2.getTitre();
            Vector<Vector<String>> data1 = tab1.getData();
            Vector<Vector<String>> data2 = tab2.getData();

            // ajout du nouveau titre1
            for (int i = 0; i < tab1Title.size(); i++) {
                newTitle.add(tab1Title.get(i));
            }
            // ajout du nouveau titre2
            for (int i = 0; i < tab2Title.size(); i++) {
                newTitle.add(tab2Title.get(i));
            }

            result.setTitre(newTitle);
            // System.out.println(newTitle);

            // ajout donne dans le nouveau table par projection
            /********************************************************************** */
            for (int i = 0; i < data2.size(); i++) {
                for (int j = 0; j < data2.size(); j++) {
                    newline = lineparProduit(data1.get(i), data2.get(j));
                    // System.out.println(newline);
                    newdata.add(newline);
                }

            }
            result.setData(newdata);
            /********************************************************************** */

        } catch (Exception e) {
            // TODO: handle exception
            throw new Exception("Erreur du produit cartesienne");
        }
        return result;
    }

    Vector lineparProduit(Vector linetab1, Vector linetab2) {
        Vector newline = new Vector();
        for (int i = 0; i < linetab1.size(); i++) {
            newline.add(linetab1.get(i));
        }
        for (int i = 0; i < linetab2.size(); i++) {
            newline.add(linetab2.get(i));
        }
        return newline;
    }

    public Table difference(String nomtab1, String nomtab2) throws Exception {
        Table result = new Table();

        try {
            Table tab1 = this.getTableFromFile(nomtab1);
            Table tab2 = this.getTableFromFile(nomtab2);
            Vector<String> newTitle = tab1.getTitre();
            Vector<Vector<String>> data1 = tab1.getData();
            Vector<Vector<String>> data2 = tab2.getData();

            result.setTitre(newTitle);
            for (int i = 0; i < data2.size(); i++) {
                for (int j = 0; j < data1.size(); j++) {
                    if (data1.get(j).equals(data2.get(i))) {
                        data1.remove(j);
                    }

                }
            }
            result.setData(data1);
            // System.out.println(data1);

        } catch (Exception e) {
            // TODO: handle exception
            throw new Exception("erreur de la division");
        }

        return result;
    }

    public Table intersection(String nomtab1, String nomtab2) throws Exception {
        Table result = new Table();

        try {
            Table tab1 = this.getTableFromFile(nomtab1);
            Table tab2 = this.getTableFromFile(nomtab2);
            Vector<String> newTitle = tab1.getTitre();
            Vector<Vector<String>> data1 = tab1.getData();
            Vector<Vector<String>> data2 = tab2.getData();
            Vector<Vector<String>> newdata = new Vector<>();

            result.setTitre(newTitle);
            for (int i = 0; i < data1.size(); i++) {
                for (int j = 0; j < data2.size(); j++) {
                    if (data1.get(i).equals(data2.get(j))) {
                        // System.out.println(data1.get(i));
                        newdata.add(data1.get(i));
                    }

                }
            }
            // System.out.println(newdata);
            result.setData(newdata);

        } catch (Exception e) {
            // TODO: handle exception
            throw new Exception("erreur intersection");
        }

        return result;
    }

    public Table union(String nomtab1, String nomtab2) throws Exception {
        Table result = new Table();

        try {
            Table tab1 = this.getTableFromFile(nomtab1);
            Table tab2 = this.getTableFromFile(nomtab2);
            Vector<String> newTitle = tab1.getTitre();
            Vector<Vector<String>> data1 = tab1.getData();
            Vector<Vector<String>> data2 = tab2.getData();
            Vector<Vector<String>> newdata = new Vector<>();

            result.setTitre(newTitle);
            for (int i = 0; i < data1.size(); i++) {
                for (int j = 0; j < data2.size(); j++) {
                    if (data1.get(i).equals(data2.get(j))) {
                        data2.remove(j);
                        // System.out.println(data2.get(j));
                    }

                }
            }

            for (int i = 0; i < data2.size(); i++) {
                data1.add(data2.get(i));
            }

            // System.out.println(data1);
            result.setData(data1);

        } catch (Exception e) {
            // TODO: handle exception
            throw new Exception("erreur union");
        }

        return result;
    }


    public Table division(String nomtab1, String nomtab2) throws Exception {
        Table result = new Table();

        try {
            Table tab1 = this.getTableFromFile(nomtab1);
            Table tab2 = this.getTableFromFile(nomtab2);
            Vector<String> Title1 = tab1.getTitre();
            Vector<String> Title2 = tab2.getTitre();
            Vector<Vector<String>> data1 = tab1.getData();
            Vector<Vector<String>> data2 = tab2.getData();
            Vector<Vector<String>> newdata = new Vector<>();

            Vector <Integer>indices= indiceTitremitovy(Title1,Title2);
            //System.out.println(indices);
            //suprimer les colonnes du titre qui sont egaux

            for (int i = 0; i < Title2.size(); i++) {
                for (int j = 0; j < Title1.size(); j++) {
                    if (Title1.get(j).equals(Title2.get(i))) {
                       Title1.remove(j);
                    }

                }
            }
            //System.out.println(Title1);
            result.setTitre(Title1);

            //Pour chercher les lignes qui sont egaux
              for (int i = 0; i < data1.size(); i++) {
                for (int j = 0; j < indices.size(); j++) {
                    if (data1.get(i).get(indices.get(j)).equals(data2.get(j))) {
                        // System.out.println(data1.get(i));
                        //newdata.add(data1.get(i));
                    } 

                }
            }

            // System.out.println(newdata);
           // result.setData(newdata);

        } catch (Exception e) {
            // TODO: handle exception
            throw new Exception("erreur de la division");
        }

        return result;
    }

    Vector <Integer> indiceTitremitovy(Vector<String> Title1,Vector<String> Title2){
        Vector <Integer> result=new Vector<>();
        for (int i = 0; i < Title1.size(); i++) {
            for (int j = 0; j < Title2.size(); j++) {
                if (Title1.get(i).equals(Title2.get(j))) {
                    result.add(i);
                }

            }
        }

        return result;
    }




   // public Table jointure(String nomtab1, String nomtab2,int id1,int id2) throws Exception {
    public Table jointure(Table tab1, Table tab2,int id1,int id2) throws Exception {
        Table result = new Table();
        try {
            //Table tab1 = this.getTableFromFile(nomtab1);
           // Table tab2 = this.getTableFromFile(nomtab2);
            Vector<String> newTitle = new Vector<>();
            Vector<Vector<String>> newdata = new Vector<>();
            Vector<String> newline = new Vector<>();
            Vector<String> tab1Title = tab1.getTitre();
            Vector<String> tab2Title = tab2.getTitre();
            Vector<Vector<String>> data1 = tab1.getData();
            Vector<Vector<String>> data2 = tab2.getData();

            // ajout du nouveau titre1
            for (int i = 0; i < tab1Title.size(); i++) {
                newTitle.add(tab1Title.get(i));
            }
            // ajout du nouveau titre2
            for (int i = 0; i < tab2Title.size(); i++) {
                newTitle.add(tab2Title.get(i));
            }

            result.setTitre(newTitle);
            // System.out.println(newTitle);

            // ajout donne dans le nouveau table par projection
            /********************************************************************** */
            for (int i = 0; i < data2.size(); i++) {
                for (int j = 0; j < data2.size(); j++) {
                    if(data1.get(i).get(id1).equals(data2.get(j).get(id2))){
                        //System.out.println("dddddddd");
                        newline = lineparProduit(data1.get(i), data2.get(j));
                         //System.out.println(newline);
                        newdata.add(newline);
                    }
                    
                }

            }
            result.setData(newdata);
           //System.out.println(newdata);
            /********************************************************************** */

        } catch (Exception e) {
            // TODO: handle exception
            throw new Exception("Erreur du produit cartesienne");
        }
        return result;
    }


















}