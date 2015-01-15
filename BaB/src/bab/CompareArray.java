/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bab;

import java.util.ArrayList;
import java.util.Comparator;

/**
 *Comparator of 1-dimensionnal array of length 2 in order to sort.
 * <p>
 * <p>
 * Exemple for t0=[p0,w0] and t1=[p1,w1] (knapsack problem):
 * <p>
 * t0 <b>&lsaquo</b> t1 if {p0/w0 > p1/w1 or (p0/w0 = p1/w1 and p0 > p1)}<p>
 * t0 = t1 if {p0/w0 = p1/w1 and p0 = p1}<p>
 * t0 > t1 if {p0/w0 &lsaquo p1/w1 or (p0/w0 = p1/w1 and p0 &lsaquo p1}
 * <p>
 * naturally, t0 <b>&lsaquo</b> t1 significates that t0 will be placed before t1
 * <p>
 * what's happen if t0 = t1 ? is t0 is exchanged with t1 ? we don't know...
 * <p>
 * <b>Note:</b> this comparator imposes orderings that are inconsistent with equals.
 * 
 * @author Guillaume
 */
public class CompareArray implements Comparator<double[]>{

    @Override
    public int compare(double[] a, double[] b) {
        if (a[0]/a[1] > b[0]/b[1] ||
            a[0]/a[1] == b[0]/b[1] && a[0]>b[0]){
            return -1;
        }else if (a[0]/a[1] == b[0]/b[1] && a[0]==b[0]){
            return 0;
        }else{
            return 1;
        }
    }
    
}
