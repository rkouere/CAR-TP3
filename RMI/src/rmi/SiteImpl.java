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
    private List<SiteImpl> fils = new ArrayList<SiteImpl>();
    private byte[] data = null;

    public SiteImpl() throws RemoteException{
        super();
    }

    @Override
    public void addFils(SiteImpl fils)  throws RemoteException{
        this.fils.add(fils);
    }
    
    @Override
    public List<SiteImpl> getFils()  throws RemoteException{
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
        for(SiteImpl site:this.fils){
            site.recevoirMessage(data);
        }
    }

}
