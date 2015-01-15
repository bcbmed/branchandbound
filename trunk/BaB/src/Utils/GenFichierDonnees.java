/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import bab.CompareArray;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Guillaume
 */
public class GenFichierDonnees {
    
    private int nbObjets;
    private double alpha;
    private double capaciteSac;
    private PrintWriter fichier;
    private ArrayList<double[]> listObjets;
    private String nomDuFichierCree;
    
    private static final double C1 = 40.0;
    private static final double C2 = 2.0;
    
    /**
     * Génère un fichier de données où chaque ligne représente un objet, et 
     * conserve une Arraylist des objets généré.
     * <p>
     * Une ligne se compose de deux nombres réels : l'utilité et le poids
     * de l'objet correspondant.
     * <p>
     * Le poids <em>wi</em> de l'objet n°i est définit aléatoirement selon une 
     * loi uniforme sur [W/C1, W/C2[ où C1 et C2 sont des constantes.
     * <p>
     * L'utilité <em>pi</em> de l'objet n°i est définit aléatoirement selon une 
     * loi uniforme sur [ (1-<em>alpha</em>)*<em>wi</em> ,
     *                    (1+<em>alpha</em>)*<em>wi</em> [
     * 
     * @param alpha réel dans [0,1] représentant la variance dans la génération
     * de <em>pi</em> autour de <em>wi</em>
     * .... <em>alpha</em>=0 => variance nulle, donc <em>pi</em>=<em>wi</em>.
     *      <em>alpha</em>=1 => forte variance : <em>pi</em> est dans [0,2*<em>wi</em> [
     * @param nbObjets nombre total d'objets que l'on veut générer
     * @param capaciteSac capacité totale du sac (W)
     * 
     */
    public GenFichierDonnees(double alpha, int nbObjets, double capaciteSac) {
        this.alpha = alpha;
        this.nbObjets = nbObjets;
        this.capaciteSac = capaciteSac;
        this.listObjets = new ArrayList<>();
        this.nomDuFichierCree = "";
                
        if( nbObjets != 0){
            initFichier();
            remplirFichier();
        }
    }
    /**
     * Initialise un fichier vide dont le nom comporte les valeurs des 
     * paramètres : alpha, nombre d'objets disponibles, capacité totale du sac.
     * <p>
     * Le nom de fichier possède également un numéro de version, cela permet de
     * créer plusieurs fichiers de données différents avec les mêmes paramètres.
     */
    private void initFichier() {
        String nomFichierResultat;
        File currentDir = new File ("");
        int version;
        
        nomFichierResultat = currentDir.getAbsolutePath() + "//Fichiers De Donnees//"
                        + "alpha_" + this.alpha 
                        + " - nbObjet_" + nbObjets 
                        + " - W_" + capaciteSac 
                        + " -  v";
        
        //Creer le dossier parents des fichiers de résultats s'il n'existe pas.
        if(!new File(currentDir.getAbsolutePath()
                    + "//Fichiers De Donnees//").exists()){
            new File(currentDir.getAbsolutePath() 
                    + "//Fichiers De Donnees//").mkdirs();
        }
        
        version = 0;
        while(new File(nomFichierResultat + version + ".txt").exists()){
            version++;
        }
        try {
            fichier = new PrintWriter(new FileWriter(nomFichierResultat + version + ".txt"));
            nomDuFichierCree = nomFichierResultat + version + ".txt";
        } catch (IOException ex) {
            Logger.getLogger(GenFichierDonnees.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Remplit <em>fichier</em> créé précédemment en créant une ligne pour
     * chaque objet.
     * <p>
     * Une ligne comprend deux nombres réels. Le premier définit l'utilité de
     * l'objet, tandis que le second définit le poids de l'objet.
     */
    private void remplirFichier() {
        double wi;
        double pi;
        
        for (int i = 0; i < nbObjets; i++) {
            wi = unif(  capaciteSac / C1,
                        capaciteSac / C2
                    );
            pi = unif(  wi * (1-alpha),
                        wi * (1+alpha)
                    );
            
            double[] objet = {pi, wi};
            listObjets.add(objet);
        }
        
        listObjets.sort(new CompareArray());
        
        for (int i = 0; i < nbObjets; i++) {
            fichier.println(listObjets.get(i)[0] + " " + listObjets.get(i)[1]);
        }
                
        fichier.flush();
        fichier.close();
    }
    
    /**
     * Générateur de nombre aléatoire suivant la loi uniforme sur [a,b[
     * @param a borne inférieure de [a,b[
     * @param b borne supérieure de [a,b[
     * @return un nombre réel dans l'intervalle [a,b[
     */
    private double unif(double a, double b) {
        return a + (b-a)*Math.random();
    }
    
    public ArrayList<double[]> getListObjets(){
        return listObjets;
    }
    
    public String getNomFichierCree(){
        return nomDuFichierCree;
    }
    
    //On génère les fichiers de données voulus
    public static void main(String[] args) {
        GenFichierDonnees generate = new GenFichierDonnees(0.1, 100, 10000);
    }
}
