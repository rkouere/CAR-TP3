/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package divers;

/**
 * Class contenant des variables globales ainsi que une méthode pour gérer l'affichage en mode verobse.
 * 
 * @author rkouere
 */
public class Globals {
    /**
     * Le port sur lequel le serveur rmi se connect
     */
    public static final int PortServer = 6000;
    
    /**
     * Le chemin vers les fichiers de configuration
     */
    public static final String pathToParams = "";
    
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
