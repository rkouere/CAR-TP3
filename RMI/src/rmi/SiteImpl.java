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
    private List<SiteItf> fils = new ArrayList<SiteItf>();
    private byte[] data = null;
    int id = 0;

    public SiteImpl(int id) throws RemoteException{
        super();
        this.id = id;
    }

    @Override
    public void addFils(SiteItf fils)  throws RemoteException{
        System.out.println("Fils ajout");
        this.fils.add(fils);
    }
    
    @Override
    public List<SiteItf> getFils()  throws RemoteException{
        return this.fils;
    }
    
    @Override
    public void recevoirMessage(byte[] data) throws RemoteException {
        this.data = data;
        if(!this.fils.isEmpty())
            diffuserMessage(this.data);
    }
    @Override
    public void diffuserMessage(byte[] data) throws RemoteException {
        for(SiteItf site:this.fils){
            System.out.println("Sending from node " + this.id + " to node " + site.getId());
            new TransferData(site, this.data).start();
        }
    }
    
    @Override
    public String SayHello() throws RemoteException {
        return "Hello";
    }

    @Override
    public int getId() throws RemoteException {
        return this.id;
    }


}
