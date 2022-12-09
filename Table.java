package emp;
import java.util.Vector;

public class Table{
 Vector<String> titre;
 Vector<Vector<String>> data;



 public Vector<String> getTitre() {
    return titre;
}
public void setTitre(Vector<String> titre) {
    this.titre = titre;
}
 
public Vector<Vector<String>> getData() {
    return data;
}
public void setData(Vector<Vector<String>> data) {
    this.data = data;
}


        public Table Projection(String[]colonne) throws Exception{
            Table result = new Table();
            Vector<String> titre=getTitre();
            String[] tabTitre= titre.toArray(new String[titre.size()]);
            Vector<Integer> indiceColonne = new Vector<>();

            // INDICE MISY NY COLONNE
            for(int i=0;i<colonne.length;i++){
                for(int j=0;j<tabTitre.length; j++){
                    if(tabTitre[j].equals(colonne[i]) == true){
                        indiceColonne.add(j);     // Indice anle titre
                        break;
                    } 
                }
            }
            //  Vrification Colonne
            if(indiceColonne.size() != colonne.length) throw new Exception("Misy colonne tsy mifanaraka");

            /// Setena ny titre anle result
            // Colonne[] => Titre anle table result
            Vector<String> forTitle = new Vector<>();
            // Algo
                for(int i = 0; i < colonne.length; i++){
                    forTitle.add(colonne[i]);
                }
            result.setTitre(forTitle);

            /// DATAA
            Vector <Vector<String>> data = this.getData();
            Vector <Vector<String>> fordata= new Vector<>();

            for(int i = 0; i < data.size(); i++){
                Vector line= new Vector<>();
                for(int k = 0; k < indiceColonne.size(); k++){
                    line.add(data.get(i).get(indiceColonne.get(k)));
                }
                fordata.add(line);
            }

            result.setData(fordata);
         
            // for(int i = 0; i < colonne.length; i++){        // Verification colonne nangatahana => Titre table
            //     if(titre.contains(colonne[i]) == false) throw new Exception("Misy colonne tsy mifanaraka");
            // }


            return result;
        }



        // Only affichage
    public void viewTable(){

        if(this.getData().size() == 0){
            System.out.println("Empty set");
        }else{
            for(int i = 0; i < this.getTitre().size(); i++){
                System.out.print("|______"+this.getTitre().get(i) + "______|");
            }
            System.out.println("");
            for (int i = 0; i < this.getData().size(); i++) {
                for(int j = 0; j < this.getData().get(i).size(); j++){
                    System.out.print("|      "+this.getData().get(i).get(j) + "      |");
                }
                System.out.println("");
            } 
        }        
    }










}