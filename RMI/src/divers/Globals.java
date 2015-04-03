/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package divers;

/**
 *
 * @author rkouere
 */
public class Globals {
    public static final int PortServer = 6000;
    public void printVerbose(String msg, boolean verbose) {
        if(verbose)
            System.out.println(msg);
    }

}
