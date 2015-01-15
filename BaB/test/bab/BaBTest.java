/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bab;

import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Guillaume
 */
public class BaBTest {
    
    public BaBTest() {
    }

    /**
     * Test of tri method, of class BabSolver.
     */
    @Test
    public void testTri() {
        System.out.println("tri");
        
        ArrayList<double[]> objets = new ArrayList<>();
        
        double[] c = {30,2}; //Objets classés dans l'ordre décroissant profit/poids
        double[] e = {28,2};
        double[] a = {27,3};
        double[] d = {34,4};
        double[] b = {7,1};
        
        objets.add(a);
        objets.add(b);
        objets.add(c);
        objets.add(e);
        objets.add(d);
        
        double[][] triee = {c,e,a,d,b};
        
        BabSolver bab = new BabSolver(objets,6);
        
        for (int i = 0; i < objets.size(); i++) {
            assertFalse(objets.get(i)==triee[i]); //les objets ne sont pas triés dans la liste initiale
            assertFalse(bab.getObjets().get(i)==triee[i]); //les objets ne sont pas triés dans la liste de bab
        }
        
        bab.tri();
        
        for (int i = 0; i < objets.size(); i++) {
            assertFalse(objets.get(i)==triee[i]); //les objets ne sont pas triés dans la liste initiale
            assertTrue(bab.getObjets().get(i)==triee[i]); //les objets sont triés dans la liste de bab
        }
    }

    /**
     * Test of solve method, of class BabSolver.
     */
    @Test
    public void testSolve1() {
        System.out.println("solve1");
        
        ArrayList<double[]> objets = new ArrayList<>();
        
        double[] c = {30,2};
        double[] e = {28,2};
        double[] a = {27,3};
        double[] d = {34,4};
        double[] b = {7,1};
        
        objets.add(a);
        objets.add(b);
        objets.add(c);
        objets.add(e);
        objets.add(d);
        
        BabSolver bab = new BabSolver(objets, 6);
        int[] prisOuPas= {1, 1, 0, 0, 1};
        Solution expResult = new Solution(prisOuPas, 65.0, 5.0);
        Solution result = bab.solve();
        
        for (int i = 0; i < prisOuPas.length; i++) {
            assertTrue(expResult.getSol()[i]==result.getSol()[i]);
        }
        assertTrue(expResult.getFsol()==result.getFsol());
        
    }
    
    /**
     * Test of solve method, of class BabSolver.
     */
    @Test
    public void testSolve2() {
        System.out.println("solve2");
        
        ArrayList<double[]>objets = new ArrayList<>();
        
        double[] o1 = {2, 6};
        double[] o2 = {1, 1};
        double[] o3 = {1, 1};
        double[] o4 = {1, 1};
        double[] o5 = {1, 1};
        double[] o6 = {1, 1};
        double[] o7 = {1, 1};
        
        objets.add(o1);
        objets.add(o2);
        objets.add(o3);
        objets.add(o4);
        objets.add(o5);
        objets.add(o6);
        objets.add(o7);
        
        BabSolver bab = new BabSolver(objets, 6);
        int[] prisOuPas= {1, 1, 1, 1, 1, 1, 0};
        Solution expResult = new Solution(prisOuPas, 6.0, 5.0);
        Solution result = bab.solve();
        
        for (int i = 0; i < prisOuPas.length; i++) {
            assertTrue(expResult.getSol()[i]==result.getSol()[i]);
        }
        assertTrue(expResult.getFsol()==result.getFsol());
        
    }
    
    /**
     * Test of solve method, of class BabSolver.
     */
    @Test
    public void testSolve3() {
        System.out.println("solve3");
        
        ArrayList<double[]>objets = new ArrayList<>();
        
        double[] o8 = {1, 1};
        double[] o9 = {5, 5};
        
        objets.add(o8);
        objets.add(o9);
        
        BabSolver bab = new BabSolver(objets, 5);
        int[] prisOuPas= {1, 0};
        Solution expResult = new Solution(prisOuPas, 5.0, 5.0);
        Solution result = bab.solve();
        
        for (int i = 0; i < prisOuPas.length; i++) {
            assertTrue(expResult.getSol()[i]==result.getSol()[i]);
        }
        assertTrue(expResult.getFsol()==result.getFsol());
        
    }

    /**
     * Test of relaxLin method, of class BabSolver.
     */
    @Test
    public void testRelaxLin() {
        System.out.println("relaxLin");
        double[] p = {30,28,27,34,7};
        double[] w = {2, 2, 3, 4, 1};
        double cap = 4;
        int n = 2;
        BabSolver bab = new BabSolver(null,0);
        double expResult = 35.5;
        double result = bab.relaxLin(p, w, cap, n);
        
        assertTrue(expResult==result);
        
    }
}
