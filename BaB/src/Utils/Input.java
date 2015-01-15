/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

/**
 *
 * @author Guillaume
 */
public class Input {
    private Scanner scanner;
    
    private static final String CHARSET = "UTF-8";
    private static final Locale LOCALE = Locale.US;
            
    
    public Input(File file) {
        try {
            scanner = new Scanner(file, CHARSET);
            scanner.useLocale(LOCALE);  
        }
        catch (IOException ioe) {
            System.err.println("Could not open " + file);
        }
        
    }
    
    public double readDouble(){
        return scanner.nextDouble();
    }
    
    public boolean hasNext() {
        return scanner.hasNext();
    }
    
    public static void main(String[] args) {
        Input in = new Input(new File("C:\\Users\\Guillaume\\Documents\\NetBeansProjects\\"
                        + "BranchAndBound_SacADos\\Fichiers De Donnees\\"
                        + "UnitTest.txt"));
        
        System.out.println(in.hasNext());
        double d = in.readDouble();
        System.out.println(d);
        System.out.println(in.readDouble());
        System.out.println(in.hasNext());
    }
    
}
