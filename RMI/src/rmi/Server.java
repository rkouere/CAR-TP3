/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;

import divers.Globals;
import java.io.File;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author rkouere
 */
public class Server {
       public static void main(String[] args){
        SiteItf obj1, obj2, obj3, obj4, obj5, obj6;
        obj1 = obj2 = obj3 = obj4 = obj5 = obj6 = null;
        byte[] data = "bonjour".getBytes();
        
        if(args.length != 1)
               System.out.println("Il faut donner un fichier xml en parametre");
        
        
        /* reading xml file */
        /* merci http://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/ */
        try {
            File fXmlFile = new File("/home/m1/echallier/fac/m2/car/CAR-TP3/RMI/src/rmi/param1.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            org.w3c.dom.Document doc = dBuilder.parse(fXmlFile);
            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();
 
            /* on veut gerer tous les nodes */
            NodeList nList = doc.getElementsByTagName("node");
            for (int temp = 0; temp < nList.getLength(); temp++) {
		Node nNode = nList.item(temp);
		System.out.println("\nCurrent Element :" + nNode.getNodeName());
 
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
 
			Element eElement = (Element) nNode;
 
			System.out.println("node id : " + eElement.getAttribute("id"));
//			System.out.println("First Name : " + eElement.getElementsByTagName("firstname").item(0).getTextContent());

 
		}
	}
        }
        catch (Exception e) {
	e.printStackTrace();
    }
        
        
        /* FIN reading file */
        
        
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
            obj1.addNode(obj2);
            obj1.addNode(obj5);

            obj2.addNode(obj3);
            obj2.addNode(obj4);

            obj5.addNode(obj6);
            obj4.addNode(obj6);
            
            obj1.diffuserMessage(data);

            
        } catch (Exception ex) {
            System.out.println("SiteImpl err: " + ex.getMessage());
            ex.printStackTrace(); 
        }
    } 
}
