/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rmi;

import divers.Globals;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/* Ref : http://www.cs.ucsb.edu/~cappello/lectures/rmi/helloworld.shtml 
http://www.javacoffeebreak.com/articles/javarmi/javarmi.html
*/

/**
 *
 * @author echallier
 */
public class SiteImpl extends UnicastRemoteObject implements SiteItf {
    private List<SiteItf> connectedNodes = new ArrayList<SiteItf>();
    private byte[] data = null;
    boolean alreadyVisited = false;
    int id = 0;

    public SiteImpl(int id) throws RemoteException{
        super();
        this.id = id;
    }

    /**
    * @inheritDoc
    *
    */
    @Override
    public void addNode(SiteItf fils)  throws RemoteException{
        System.out.println("Fils ajout");
        this.connectedNodes.add(fils);
    }
    /**
    * @inheritDoc
    *
    */
    @Override
    public List<SiteItf> getNodes()  throws RemoteException{
        return this.connectedNodes;
    }
    
    /**
    * @inheritDoc
    *
    */
    @Override
    public void recevoirMessage(byte[] data) throws RemoteException {
        /* si on a pas déjà envoyé de message, on s'assure que l'on en renverra pas */
        if(this.alreadyVisited == false) {
            synchronized(this) {
                this.alreadyVisited = true;
            }
            System.out.println("I am noeud " + this.id + " et j'ai reçu un message.");
            this.data = data;

            if(!this.connectedNodes.isEmpty())
                diffuserMessage(this.data);
        }
    }
    
    /**
    * @inheritDoc
    *
    */
    @Override
    public void diffuserMessage(byte[] data) throws RemoteException {
        for(SiteItf site:this.connectedNodes){
            System.out.println("Sending from node " + this.id + " to node " + site.getId());
            new TransferData(site, this.data).start();
        }

    }
    
    /**
    * @inheritDoc
    *
    */
    @Override
    public int getId() throws RemoteException {
        return this.id;
    }


}
