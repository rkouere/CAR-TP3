/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;

import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;

/**
 *
 * @author rkouere
 */
public class Client {
    public static void main(String[] args)  throws Exception {
        byte[] data = "bonjour".getBytes();

//         // Assign security manager
//        if (System.getSecurityManager() == null)
//        {
//            System.setSecurityManager   (new RMISecurityManager());
//        }

        // Call registry for the first node
        SiteImpl service = (SiteImpl) Naming.lookup("rmi://" + InetAddress.getLocalHost().getHostName() + "/node1");
        service.diffuserMessage(data);
    }
}
