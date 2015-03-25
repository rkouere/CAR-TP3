/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author echallier
 */
public class SiteImpl extends UnicastRemoteObject implements SiteItf {


    private SiteImpl pere = null;
    private List<SiteImpl> fils = new ArrayList<SiteImpl>();
    private byte[] data = null;
    private int id;


    public SiteImpl() throws RemoteException {
    }
    
    public void init(int id) {
        this.id = id;        
    }
    
    
    @Override
    public void diffuserMessage(byte[] data) throws RemoteException {
        synchronized(this.fils) {
            for(SiteImpl site:fils){
                System.out.println("[noeud " + this.id + "] Sending data to " + site.id);
                site.recevoirMessage(data);
            }
        }
    }

    @Override
    public void recevoirMessage(byte[] data) throws RemoteException {
        this.data = data;
        if(!this.fils.isEmpty())
            diffuserMessage(this.data);
    }
    
    /* setters and getter */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void attach(SiteImpl fils) throws RemoteException {
        synchronized(this.fils) {
            this.fils.add(fils);
        }
    }
    
    @Override
    public void detach(SiteImpl fils) throws RemoteException {
        synchronized(this.fils) {
            this.fils.remove(fils);
        }
    }

    @Override
    public void addPere(SiteImpl pere) throws RemoteException {
        this.pere = pere;
    }

 
    
    
}
