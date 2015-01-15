/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bab;

import java.util.ArrayList;

/**
 *
 * @author Guillaume
 */
public class Solution {
    private int[] sol;
    private double fsol;
    private double capRest;
    private long timeToSolve;

    public Solution(int[] sol, double fsol, double capRest) {
        this.sol = sol.clone();
        this.fsol = fsol;
        this.capRest = capRest;
        this.timeToSolve = 0;
    }

    public void setTimeToSolve(long timeToSolve) {
        this.timeToSolve = timeToSolve;
    }
    
    /**
     * Retourne la durée qu'il a fallu pour trouver la solution.
     * @return la durée qu'il a fallu pour trouver la solution.
     */
    public long getTimeToSolve() {
        return timeToSolve;
    }
    
    /**
     * Retourne un tableau de 0 et de 1 où 
     * (1=on prend l'objet, 0=on ne prend pas l'objet).
     * 
     * @return une solution au problème de sac à dos.
     */
    public int[] getSol(){
        return sol;
    }
    
    /**
     * Retourne la valeur de la fonction objectif.
     * @return la valeur de la fonction objectif. 
     */
    public double getFsol(){
        return fsol;
    }
    
    /**
     * Retourne la capacité restante du sac.
     * @return la capacité restante du sac.
     */
    public double getCapSacRest() {
        return capRest;
    }
}
