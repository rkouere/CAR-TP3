/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rmi;

import Q2.*;
import rmi.*;
import java.rmi.RemoteException;

/**
 *
 * @author echallier
 */
public class Main {
    public static void main(String[] args) throws RemoteException {
        
        byte[] data = "bonjour".getBytes();
        SiteImpl noeud1 = new SiteImpl(1);
        SiteImpl noeud2 = new SiteImpl(2);
        SiteImpl noeud3 = new SiteImpl(3);
        SiteImpl noeud4 = new SiteImpl(4);
        SiteImpl noeud5 = new SiteImpl(5);
        SiteImpl noeud6 = new SiteImpl(6);
        
        
        noeud1.addPere(noeud1);
        noeud1.addFils(noeud2);
        noeud1.addFils(noeud5);
        
        noeud2.addPere(noeud1);
        noeud2.addFils(noeud3);
        noeud2.addFils(noeud4);
        
        noeud5.addPere(noeud1);
        noeud5.addFils(noeud6);
        
        noeud1.diffuserMessage(data);
        
    }
}
