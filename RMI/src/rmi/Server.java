/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;

import divers.Globals;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author rkouere
 */
public class Server {
       public static void main(String[] args){
        SiteItf obj1, obj2, obj3, obj4, obj5, obj6;
        try {
            /* bin chaque objets au server */
            obj1 = new SiteImpl();
            obj2 = new SiteImpl();
            obj3 = new SiteImpl();
            obj4 = new SiteImpl();
            obj5 = new SiteImpl();
            obj6 = new SiteImpl();
            
            

            // Assign a security manager, in the event that dynamic
            // classes are loaded
            
            /* starts the registry */
            LocateRegistry.createRegistry(Globals.PortServer);

            Registry registre = LocateRegistry.getRegistry(Globals.PortServer);
            

            
            /* exporting the object */
            registre.rebind("rmi://localhost:6000/node1", obj1);
            registre.rebind("rmi://localhost:6000/node2", obj2);
//            registre.rebind("node3", obj3);
//            registre.rebind("node4", obj4);
//            registre.rebind("node5", obj5);
//            registre.rebind("node6", obj6);
            
            /* we get the reference of the stub */
            obj1 = (SiteItf) registre.lookup("rmi://localhost:6000/node1");
            obj2 = (SiteItf) registre.lookup("rmi://localhost:6000/node2");
//            obj3 = (SiteImpl) registre.lookup("node3");
//            obj4 = (SiteImpl) registre.lookup("node4");
//            obj5 = (SiteImpl) registre.lookup("node5");
//            obj6 = (SiteImpl) registre.lookup("node6");

                       
            /* we add the sons */
           obj1.addFils(obj2);
//            obj1.addFils(obj5);
//
//            obj2.addFils(obj3);
//            obj2.addFils(obj4);
//
//            obj5.addFils(obj6);
            

            
        } catch (Exception ex) {
            System.out.println("SiteImpl err: " + ex.getMessage());
            ex.printStackTrace(); 
        }
    } 
}
