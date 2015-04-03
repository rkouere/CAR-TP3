/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package divers;

/**
 * Stockaqe du numero de port
 * Fonction permettant d'imprimer des message en mode verbose
 * @author rkouere
 */
public class Globals {
    public static final int PortServer = 6000;
    public static final String pathToParams = new String("src/params/");
    
    /**
     * Imprime les message passé en paramètre  si verbose est vrai 
     * @param msg Une string
     * @param verbose Un bolleen
     */
    public void printVerbose(String msg, boolean verbose) {
        if(verbose)
            System.out.println(msg);
    }

}
