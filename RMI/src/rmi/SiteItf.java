/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rmi;

import rmi.*;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Interface regroupant les méthodes utilisé par chaque noeud
 * @author echallier
 */
public interface SiteItf extends Remote {
    /**
     * Diffuse le message a tous les noeuds connectés.
     * En fait, il appelle la méthode recevoir message de la machine cible.
     * @param data un tableau de byte
     * @throws RemoteException 
     */
    public void diffuserMessage(byte[] data) throws RemoteException;
    
    /**
     * Stock le message
     * @param data un tableau de byte
     * @throws RemoteException 
     */
    public void recevoirMessage(byte[] data) throws RemoteException;
    
    /**
     * Rajoute un connectedNodes à un noeud
     * @param connectedNodes Un noeud de type SiteItf
     * @throws RemoteException 
     */
    public void addNode(SiteItf connectedNodes) throws RemoteException;
    
    /**
     * Renvois la liste noeuds connectés
     * @return La liste des noeuds connectés
     * @throws RemoteException 
     */
    public List<SiteItf> getNodes() throws RemoteException;

    /**
     * Renvois l'id d'un noeud
     * @return le numéro du noeud
     * @throws RemoteException 
     */
    public int getId() throws RemoteException;

}
