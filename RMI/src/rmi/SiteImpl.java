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
    public static void main(String[] args){
        SiteImpl obj1, obj2, obj3, obj4, obj5, obj6;
        try {
            // Assign a security manager, in the event that dynamic
            // classes are loaded
            if (System.getSecurityManager() == null)
                System.setSecurityManager ( new RMISecurityManager() );
            /* on lance le server */
            Registry registre = LocateRegistry.getRegistry(Globals.PortServer);
            
            /* bin chaque objets au server */
            obj1 = new SiteImpl();
            obj2 = new SiteImpl();
            obj3 = new SiteImpl();
            obj4 = new SiteImpl();
            obj5 = new SiteImpl();
            obj6 = new SiteImpl();
            
            /* exporting the object */
            registre.rebind("node1", obj1);
            registre.rebind("node2", obj2);
            registre.rebind("node3", obj3);
            registre.rebind("node4", obj4);
            registre.rebind("node5", obj5);
            registre.rebind("node6", obj6);
            
            /* we get the reference of the stub */
            obj1 = (SiteImpl) registre.lookup("node1");
            obj2 = (SiteImpl) registre.lookup("node2");
            obj3 = (SiteImpl) registre.lookup("node3");
            obj4 = (SiteImpl) registre.lookup("node4");
            obj5 = (SiteImpl) registre.lookup("node5");
            obj6 = (SiteImpl) registre.lookup("node6");

                       
            /* we add the sons */
            obj1.addFils(obj2);
            obj1.addFils(obj5);

            obj2.addFils(obj3);
            obj2.addFils(obj4);

            obj5.addFils(obj6);
            

            
        } catch (Exception ex) {
            System.out.println("SiteImpl err: " + ex.getMessage());
            ex.printStackTrace(); 
        }
    }
    
    public void addFils(SiteImpl fils) {
        this.fils.add(fils);
    }
    
    public List<SiteImpl> getFils() {
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
