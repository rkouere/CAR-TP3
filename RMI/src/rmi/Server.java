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
        byte[] data = "bonjour".getBytes();
        int id = 0;
        
        if(args.length != 2) {
               System.out.println("Il faut donner deux arguments : \n" +
                       "- le noeud à partir duquel on envoit le message\n" +
                       "- le fichier xml ede paramétrage");
               System.exit(-1);
        }
        
 
        try {
            /* on demare le registry */
            LocateRegistry.createRegistry(Globals.PortServer);
            Registry registre = LocateRegistry.getRegistry(Globals.PortServer);
            
            /* reading xml file */
            /* merci http://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/ */
            /* on recupere le fichier de conf */
            File fXmlFile = new File("src/rmi/" + args[1]);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            org.w3c.dom.Document doc = dBuilder.parse(fXmlFile);
            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();
 
            /* on veut gerer tous les nodes */
            NodeList nList = doc.getElementsByTagName("node");
            /* on commence par generer tous les objets */
            
            SiteItf[] obj = new SiteItf[nList.getLength() + 1];

            for (int temp = 0; temp < nList.getLength(); temp++) {
		Node nNode = nList.item(temp);
 
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			Element eElement = (Element) nNode;
                        id = Integer.parseInt(eElement.getAttribute("id"));
                        System.out.println(id);
			/* on va creer un objet */
                        obj[temp] = new SiteImpl(id);
 
		}
            }
            System.out.println("Export des noeuds vers le serveur");
            /* on les exporte dans registre */
            for (int i = 0; i < obj.length-1; i++) {
                System.out.println("==========" + i);
                System.out.println("Exporting node" + obj[i].getId());
                registre.rebind("rmi://localhost:6000/node" + obj[i].getId(), obj[i]);
            }
            
            System.out.println("\n\n==========");

            // on recupere les stub
            for (int i = 0; i < obj.length - 1; i++) {
                System.out.println("==========" + i);
                System.out.println("getting stub" + obj[i].getId());
                obj[i] = (SiteItf) registre.lookup("rmi://localhost:6000/node" + obj[i].getId());
            }
            
            System.out.println("\n\n==========");
            // on ajoute pour chaque noeud les nodes avec lesquels il peut se connecter
            for (int temp = 0; temp < nList.getLength() ; temp++) {
		Node nNode = nList.item(temp);
 
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			Element eElement = (Element) nNode;
                        id = Integer.parseInt(eElement.getAttribute("id"));
                        System.out.println("============");
                        System.out.println("I am noeud " + id + " and I have to connect with " + eElement.getElementsByTagName("connectedNode").getLength() + " nodes");
			/* on va creer un objet */
                        
                        for(int i = 0; i < eElement.getElementsByTagName("connectedNode").getLength(); i++) {
                            String nodeToConnect = eElement.getElementsByTagName("connectedNode").item(i).getTextContent().replaceAll("\\s","");
                            System.out.println("> connecting to node = " + nodeToConnect);
                            obj[temp].addNode(obj[Integer.parseInt(nodeToConnect) - 1]);
                        }

		}
            }
        
           System.out.println("\n\n==========");
           System.out.println("Diffusion du message");
           
            System.out.println("Envoit a partir du noeud " + obj[0].getId());
            System.out.println("Ce node a " + obj[0].getNodes().size() + " nodes.");
           obj[Integer.parseInt(args[0]) - 1].diffuserMessage(data);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }  
}   
        /* FIN reading file */
        
        
//        try {
//            /* bin chaque objets au server */
//            obj1 = new SiteImpl(1);
//            obj2 = new SiteImpl(2);
//            obj3 = new SiteImpl(3);
//            obj4 = new SiteImpl(4);
//            obj5 = new SiteImpl(5);
//            obj6 = new SiteImpl(6);
//
//            // Assign a security manager, in the event that dynamic
//            // classes are loaded
//            

//            /* exporting the object */
//            registre.rebind("rmi://localhost:6000/node1", obj1);
//            registre.rebind("rmi://localhost:6000/node2", obj2);
//            registre.rebind("rmi://localhost:6000/node3", obj3);
//            registre.rebind("rmi://localhost:6000/node4", obj4);
//            registre.rebind("rmi://localhost:6000/node5", obj5);
//            registre.rebind("rmi://localhost:6000/node6", obj6);
//            
//            /* we get the reference of the stub */
//            obj1 = (SiteItf) registre.lookup("rmi://localhost:6000/node1");
//            obj2 = (SiteItf) registre.lookup("rmi://localhost:6000/node2");
//            obj3 = (SiteItf) registre.lookup("rmi://localhost:6000/node3");
//            obj4 = (SiteItf) registre.lookup("rmi://localhost:6000/node4");
//            obj5 = (SiteItf) registre.lookup("rmi://localhost:6000/node5");
//            obj6 = (SiteItf) registre.lookup("rmi://localhost:6000/node6");
//           
//
//
//            /* we add the sons */
//            obj1.addNode(obj2);
//            obj1.addNode(obj5);
//
//            obj2.addNode(obj3);
//            obj2.addNode(obj4);
//
//            obj5.addNode(obj6);
//            obj4.addNode(obj6);
//            
//            obj1.diffuserMessage(data);
//
//            
//        } catch (Exception ex) {
//            System.out.println("SiteImpl err: " + ex.getMessage());
//            ex.printStackTrace(); 
//        }
//    } 

