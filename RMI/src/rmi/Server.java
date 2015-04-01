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
        byte[] data = "bonjour".getBytes();
        try {
            /* bin chaque objets au server */
            obj1 = new SiteImpl(1);
            obj2 = new SiteImpl(2);
            obj3 = new SiteImpl(3);
            obj4 = new SiteImpl(4);
            obj5 = new SiteImpl(5);
            obj6 = new SiteImpl(6);

            // Assign a security manager, in the event that dynamic
            // classes are loaded
            
            /* starts the registry */
            LocateRegistry.createRegistry(Globals.PortServer);

            Registry registre = LocateRegistry.getRegistry(Globals.PortServer);
           
            /* exporting the object */
            registre.rebind("rmi://localhost:6000/node1", obj1);
            registre.rebind("rmi://localhost:6000/node2", obj2);
            registre.rebind("rmi://localhost:6000/node3", obj3);
            registre.rebind("rmi://localhost:6000/node4", obj4);
            registre.rebind("rmi://localhost:6000/node5", obj5);
            registre.rebind("rmi://localhost:6000/node6", obj6);
            
            /* we get the reference of the stub */
            obj1 = (SiteItf) registre.lookup("rmi://localhost:6000/node1");
            obj2 = (SiteItf) registre.lookup("rmi://localhost:6000/node2");
            obj3 = (SiteItf) registre.lookup("rmi://localhost:6000/node3");
            obj4 = (SiteItf) registre.lookup("rmi://localhost:6000/node4");
            obj5 = (SiteItf) registre.lookup("rmi://localhost:6000/node5");
            obj6 = (SiteItf) registre.lookup("rmi://localhost:6000/node6");
           

                       
            /* we add the sons */
            obj1.addFils(obj2);
            obj1.addFils(obj5);

            obj2.addFils(obj3);
            obj2.addFils(obj4);

            obj5.addFils(obj6);
            
            obj1.diffuserMessage(data);

            
        } catch (Exception ex) {
            System.out.println("SiteImpl err: " + ex.getMessage());
            ex.printStackTrace(); 
        }
    } 
}
