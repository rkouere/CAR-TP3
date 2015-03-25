/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rmi;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author echallier
 */
public class Main {
    public static void main(String[] args) {
        
        byte[] data = "bonjour".getBytes();
        SiteImpl noeud1 = new SiteImpl();
        SiteImpl noeud2 = new SiteImpl();
        SiteImpl noeud3 = new SiteImpl();
        SiteImpl noeud4 = new SiteImpl();
        SiteImpl noeud5 = new SiteImpl();
        SiteImpl noeud6 = new SiteImpl();
        
        
        try {
            /* initialisaiton des id de chaques noeud */
            noeud1.init(1);
            noeud2.init(2);
            noeud3.init(3);
            noeud4.init(4);
            noeud5.init(5);
            noeud6.init(6);

            /* initialisation des pere/fils de chaque noeud */
            noeud1.addPere(noeud1);
            noeud1.attach(noeud2);
            noeud1.attach(noeud5);

            noeud2.addPere(noeud1);
            noeud2.attach(noeud3);
            noeud2.attach(noeud4);

            noeud5.addPere(noeud1);
            noeud5.attach(noeud6);

            /* fin init */
            
            /* let's talk ! */
            noeud1.diffuserMessage(data);
            
        } catch (RemoteException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
}
