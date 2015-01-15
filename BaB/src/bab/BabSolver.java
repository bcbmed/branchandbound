/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bab;

import java.util.ArrayList;

/**
 *Branch and Bound sur le problème de Sac à dos 0-1 à une dimension.
 * 
 * Cette version de branch and bound ne comporte pas de récursivité....
 * Donc pas de dépassement de pile ;-)
 * 
 * Attention également, les poids et profits sont pour l'instant des entiers,
 * pour émettre des doubles, on doit juste changer les int[] par double[] et mettre
 * capSac en double également (et je crois que c'est à peu près tout...)
 * 
 * Autre attention : on ne doit PAS mettre d'objets de poids nul...
 * (au pire on pourrai effectuer un pré-processing en ce disant que tout objet de
 * poids nul rentre forcément dans le sac).
 * 
 * Dernière attention : si on choisi de passer en double, alors il faut se dire
 * que les quantitées doivent êtres positives... (je crois)
 * 
 * @author Guillaume
 */
public class BabSolver {
    
    private ArrayList<double[]> objets;  //liste des objets O_i tels que O_i={profit_i, poids_i}
    private double capSac;             //Capacité totale du sac
    private Solution solution;      //Solution du problème
    
    
    /**
     * Construit une nouvelle instance pour le problème de sac à dos à partir
     * d'une liste d'objets et d'une capacité maximale de sac.
     * <p>
     * Note : la liste d'objets passée en argument peut être triée ou non. Cependant,
     * il est à noter que la liste enregistrée dans BaB par ce constructeur est une nouvelle
     * liste et non un pointeur sur la liste donnée en argument. Comme la liste de BaB est triée
     * selon l'ordre imposé par CompareArray lors de l'appel de la méthode solve(), les deux listes
     * peuvent donc être différentes. En particuliers, la méthode afficheSol() donne les objets
     * que l'on prend ou qu'on ne prends pas, dans l'ordre de la liste de BaB (et donc
     * éventuellement pas dans l'ordre de la liste passée en argument de ce constructeur).
     * 
     * @param objets
     * @param capSac 
     */
    public BabSolver(ArrayList<double[]> objets, double capSac) {
        if (objets==null) this.objets = null;
        else this.objets = new ArrayList<double[]>(objets);
        
        this.capSac = capSac;
        this.solution = null;
    }
    
    /**
     * Tri les données selon l'ordre du comparateur CompareArray.
     * 
     * Note: ne marche qu'à partir de la version java 8
     */
    public void tri(){
        objets.sort(new CompareArray());
    }
    
    public Solution solve(){
        
        objets.sort(new CompareArray());
        
        long start = System.nanoTime();
        
        double[] p = new double[objets.size()];
        double[] w = new double[objets.size()];
        
        for (int i = 0; i < objets.size(); i++) {
            p[i] = objets.get(i)[0];
            w[i] = objets.get(i)[1];
        }
        
        double LB=0.0;
        double BSrest;
        double BS;
        double z=0.0;
        int[] x = new int[p.length];
        int i=0;
        int n=p.length;
        
        while(true){
            BSrest = relaxLin(p, w, capSac, i);
            BS=z+BSrest;
            if(BS>LB){
                while(i<=n-1 && capSac >= w[i]){
                    capSac-=w[i];
                    z+=p[i];
                    x[i]=1;
                    i++;
                }
                if(i==n){
                    if(z>LB){
                        LB=z;
                        solution = new Solution(x, z, capSac);
                        if(BS==z) { //rajout de ma part A RAJOUTER JE PENSE (permet meilleur élagage) En fait faut poser la question....
                            solution.setTimeToSolve(
                                        (System.nanoTime() - start) / 1000000);
                            return solution;
                        }  
                    }
                    //inutile d'aller plus loin dans l'arbre --> bactracking

                    x[n-1]=0;
                    z-=p[n-1];
                    capSac+=w[n-1];
                    i--;
                    
                    while(x[i]==0){
                        i--;
                        if(i==-1){
                            solution.setTimeToSolve(
                                        (System.nanoTime() - start) / 1000000);
                            return solution;
                        }
                    }
                    x[i]=0;
                    z-=p[i];
                    capSac+=w[i];
                    i++; 
                    
                    
                }else if(i<=n-1){  //i<n-1 fixation à zéro
                    x[i]=0;
                    i++;
                }
            }else {
                //inutile d'aller plus loin dans l'arbre --> bactracking
                if(i==n){
                    x[n-1]=0;
                    z-=p[n-1];
                    capSac+=w[n-1];
                    i--;
                }
                while(x[i]==0){
                    i--;
                    if(i==-1){
                        solution.setTimeToSolve(
                                        (System.nanoTime() - start) / 1000000);
                        return solution;
                    }
                }
                x[i]=0;
                z-=p[i];
                capSac+=w[i];
                i++;
            }
        }
    }
    
    /**
     * Résout la relaxation linéaire d'un sous problème de Sac à dos.
     * 
     * Cela correspond à avoir déja choisi (ou non -ie écartés-) certains objets (parmis les premiers
     * car p et w sont triés par ordre de dominance) et de résoudre le problème
     * pour un sac à dos de la taille emputée du poid des objets choisis, et avec
     * pour choix les objets restants (moins ceux qui ont été écartés au début).
     * 
     * @param p la liste des profit des objets
     * @param w la liste des poids des objets
     * @param cap la capacité du sous-sac à dos
     * @param n : pour ne considérer que les objets dont l'indice dans p et w est >=n
     * @return 
     */
    public double relaxLin(double[] p, double[] w, double cap, int n){
        
        double z=0.0;
        
        for (int i = n; i < w.length; i++) {
            if(w[i]<cap){
                cap-=w[i];
                z+=p[i];
            }else if(cap != 0.0){
                z+=cap*p[i]/w[i];
                return z;
            }else{
                return z;
            }
        }
        
        return z;
    }
    
    /**
     * Affiche la série des rapports profit/poids de tous les objets de la liste des objets donnée
     * par la méthode getObjets() (dans l'ordre de celle-ci).
     */
    public void affiche(){
        for (double[] test1 : objets) {
            System.out.print(test1[0] / test1[1] + "\t");
        }
        System.out.println("");
    }
    
    /**
     * Affiche la solution du problème de sac à dos sous la forme d'une suite
     * de 0 et de 1 symbolisant le fait qu'on prenne l'objet (=1) ou qu'on ne le
     * prenne pas (=0), dans l'ordre de la liste des objets donnée par la méthode
     * getObjets().
     */
    public void afficheSol(){
        String strSol = "[";
        for (int i = 0; i < solution.getSol().length; i++) {
            strSol+=solution.getSol()[i]+" ";
        }
        strSol= strSol.substring(0, strSol.length()-1); //on enlève le dernier espace
        strSol+="]";
        
        System.out.println("\nSolution optimale :");
        System.out.println("fopt = " + solution.getFsol());
        System.out.println("xopt = "+strSol);
    }
    
    /**
     * Retourne la liste L des objets de BabSolver.
     * <p>
 Cette liste L peut être différente de celle passée en argument dans le constructeur de BabSolver.
 En effet L peut être dans l'ordre imposé par CompareArray, selon que la méthode
 tri ai été appellée ou non.
 <p>
     * Dans tous les cas, l'appel de la méthode AfficheSol() renvoi les objets que
     * l'on prend ou qu'on ne prend pas, dans l'ordre de la liste L.
     * 
     * @return la liste des objets de BabSolver.
     */
    public ArrayList<double[]> getObjets() {
        return objets;
    }

    public Solution getSolution() {
        return solution;
    }
    
}
