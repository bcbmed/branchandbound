/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bab;

import Utils.GenFichierDonnees;
import Utils.Stats;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Guillaume
 */
public class BaB {
    
    private DecimalFormat f = new DecimalFormat(); //permet de tronquer les chiffres
    
    /**
     * Constructeur servant simplement à initialiser le niveau de tronquature souhaité pour les stats.
     * @param nb nombre de chiffres après la virgule désiré.
     */
    public BaB(int nb){
	f.setMaximumFractionDigits(nb);
    }

    public void launchSimulator(){
        //======================================================================
        //Partie Analyse Sur des données générées aléatoirement 

        BabSolver solver;
        GenFichierDonnees gen;
        ArrayList<Solution> listSols = new ArrayList<Solution>();
        ArrayList<String> nomFichiers = new ArrayList<String>();
        
        final double W = 10000;
        double[] alpha = {0.05, 0.1, 0.15, 0.2, 0.5, 0.8, 0.9};
        int[] nbObjets = {10000, 100000, 1000000};
        int nbSimul = 50; //Donne le nombre de simulations pour chaque
                          //   combinaison de paramètres.

        String[] params = new String[3];
        Stats stat; //Renferme les stats issues  d'une série de nbSimul simulations
        ArrayList<Stats> statList = new ArrayList<Stats>();
        
        long debutCompute = System.nanoTime();
        long totalCompute;

        for (double alph : alpha) {

            for (int nbObj : nbObjets) {

                stat = new Stats();
                params[0] = String.valueOf(alph);
                params[1] = String.valueOf(nbObj);
                params[2] = String.valueOf(W);
                stat.setParams(params);

                for (int j = 0; j < nbSimul; j++) { //nombre de simulations

                    gen = new GenFichierDonnees(alph, nbObj, W);
                    nomFichiers.add(gen.getNomFichierCree());
                    
                    solver = new BabSolver(gen.getListObjets(), W);
                    solver.solve();

                    listSols.add(solver.getSolution());

                    stat.setT(solver.getSolution().getTimeToSolve());
                    stat.setZ(solver.getSolution().getFsol());
                    stat.setC(solver.getSolution().getCapSacRest());
                }

                statList.add(stat);
            }
        }
        
        totalCompute = (System.nanoTime() - debutCompute) / 1000000;

        //=====================================================================
        //On créer un fichier de résultats de toutes les instances et on écrit
        //les résultats dedans.

        PrintWriter fichier = null;
        String nomFichierResultat;
        File currentDir = new File ("");
        int version;

        nomFichierResultat = currentDir.getAbsolutePath() 
                                + "//Fichiers De Donnees//"
                                + "Résultats  ";

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
        } catch (IOException ex) {
            Logger.getLogger(GenFichierDonnees.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        fichier.println("Fichier contenant les résultats de chaque expérience");
        fichier.println("====================================================");
        fichier.println("Ordinateur\t \t : Z97");
        fichier.println("Processeur\t \t : i7 4790k @ 4.4Ghz");
        fichier.println("Mémoire vive\t \t : 16GB");
        fichier.println("OS \t \t \t : VM Ubuntu 14.04 émulée sur Windows 7");
        fichier.println("Temps total calcul \t : " + totalCompute + " ms");
        fichier.println("====================================================");
        fichier.println("");
        fichier.println("");
        for (Solution listSol : listSols) {
            fichier.println(nomFichiers.get(listSols.indexOf(listSol)));
            fichier.println("  Temps : " + f.format(listSol.getTimeToSolve()) + " ms");

            fichier.print("  Xiopt : [ ");
            for(int i=0; i<listSol.getSol().length - 1; i++){
                fichier.print( listSol.getSol()[i] + ", ");
            }
            fichier.println(listSol.getSol()[listSol.getSol().length - 1] + " ]");

            fichier.println("  Z = " + f.format(listSol.getFsol()));
            fichier.println("  Capacité sac restante : " + f.format(listSol.getCapSacRest()));

            fichier.println("");
            fichier.println("");
            fichier.flush();
        }

        fichier.close();


        //=====================================================================
        //On créé un fichier regroupant les statistiques des diverses simulations

        fichier = null;
        currentDir = new File ("");

        nomFichierResultat = currentDir.getAbsolutePath() 
                                + "//Fichiers De Donnees//"
                                + "StatsRésultats  ";

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
        } catch (IOException ex) {
            Logger.getLogger(GenFichierDonnees.class.getName()).log(Level.SEVERE, null, ex);
        }

        fichier.println("Résultats des statistiques obtenues sur des séries de "
                                    + nbSimul
                                    + " simulations");
        fichier.println("======================================================"
                       +"===========================");
        fichier.println("Ordinateur\t \t : Z97");
        fichier.println("Processeur\t \t : i7 4790k @ 4.4Ghz");
        fichier.println("Mémoire vive\t \t : 16GB");
        fichier.println("OS \t \t \t : VM Ubuntu 14.04 émulée sur Windows 7");
        fichier.println("Temps total calcul \t : " + totalCompute + " ms");
        fichier.println("======================================================"
                       +"===========================");
        fichier.println("");
        fichier.println("");
        fichier.println("");

        for (Stats stati : statList) {

            fichier.println("");
            fichier.println("");

            fichier.println("PARAMETRES :  ALPHA = " + stati.getParams()[0]
                                + "   NbOBJETS = " + stati.getParams()[1]
                                + "   CAPACITeSAC = " + stati.getParams()[2]);

            fichier.println("  Temps Moyen: " + f.format(stati.gettMoy()) + " ms"
                                + "   (Min = " + f.format(stati.getTmin()) + " ms, "
                                +     "Max = " + f.format(stati.getTmax()) + " ms)"
                                + "  Ecart type = " + f.format(Math.sqrt(stati.gettVar())));

            fichier.println("  Z Moyen: " + f.format(stati.getzMoy())
                                + "   (Min = " + f.format(stati.getZmin()) + ", "
                                +     "Max = " + f.format(stati.getZmax()) + ")"
                                + "  Ecart type = " + f.format(Math.sqrt(stati.getzVar())));

            fichier.println("  Capacité sac restante moyenne: " 
                                + f.format(stati.getcMoy())
                                + "   (Min = " + f.format(stati.getCmin()) + ", "
                                +     "Max = " + f.format(stati.getCmax()) + ")"
                                + "  Ecart type = " + f.format(Math.sqrt(stati.getcVar())));

            fichier.flush();
        }

        fichier.close();
    }
    
    
        
     /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       BaB bab = new BaB(3);
       bab.launchSimulator();
    }    
}
