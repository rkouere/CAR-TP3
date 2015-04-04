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
 * Implémentation de l'interface SiteItf
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
        synchronized(this) {
            if(this.alreadyVisited == false) {
                    this.alreadyVisited = true;
                this.data = data;
                System.out.println((char)27 + "[34mNode " + this.id + " has received a message and is sending it to its friends. " + (char)27 + "[30m");

                // si il y a des nodes a qui envoyer des messages
                if(!this.connectedNodes.isEmpty())
                    diffuserMessage(this.data);

            }
        }
    }
    
    /**
    * @inheritDoc
    *
    */
    @Override
    public void diffuserMessage(byte[] data) throws RemoteException {
        for(SiteItf site:this.connectedNodes){
            System.out.println((char)27 + "[32mSending from node " + this.id + " to node " + site.getId() + (char)27 + "[30m");
            new TransferData(site, this.data).start();
            
            if(Globals.test)
                Globals.nbrNoeudDejaEnvoye++;

        }
 
        // si il n'y a plus de nodes à envoyer
        if(Globals.test)
            if(Globals.nbrNoeudAEnvoyer == Globals.nbrNoeudDejaEnvoye)
                System.exit(0);
    }
    
    /**
    * @inheritDoc
    *
    */
    @Override
    public int getId() throws RemoteException {
        return this.id;
    }
    
    /**
     * Permet d'éviter au node étant le point de depart d'envoyer de re-envoyer des messages
     */
    public void setSenderId()  throws RemoteException{
        this.alreadyVisited = true;
    }


}
