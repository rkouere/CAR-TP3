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
import tests.ExportExpectedResults;

/**
 * Main. Met en route le serveur, lit le fichier de param, créé/exporte les noeuds, envoit le message.
 * @author rkouere
 */
public class Server {
       public static void main(String[] args){
        byte[] data = "bonjour".getBytes();
        int id = 0;
        boolean verbose = false;
        boolean test = false;
        String fichierParam = null;
        String noeudEnvoisMessage = null;
        
        // Sert principalement à imprimer les message en mode verbose
        Globals tools = new Globals();
        // Permet de parser le fichier xml et d'exporter les resultat que l'on doit attendre
        ExportExpectedResults exportRez = null;
        
        String errorMessage =   "Il faut donner au moins deux arguments : \n" +
                                "- le fichier xml de paramétrage\n" + 
                                "- le noeud à partir duquel on envoit le message\n" +
                                "- (optionnel) -test : pour generer les fichiers de test";

        // on verifie que l'on a le bon nombre d'arguments
        if(args.length < 2 || args.length > 3) {
               System.out.println(errorMessage);
               System.exit(-1);
        }
        
        else {
            fichierParam = args[0];
            noeudEnvoisMessage = args[1];            
        }
        
        // si on veut ausis tester le prog, on initialise le boolean qui va bien
        if(args.length == 3) {
            if(args[2].contentEquals("-test")) {
                test = true;
            }
            /// si on a trois arguments et que le troisieme n'est pas test, on arrete tout
            if(test == false) {
                System.out.println(errorMessage);
                System.exit(-1);
            }
        }
 

        
        try {
            /* on demare le registry */
            LocateRegistry.createRegistry(tools.PortServer);
            Registry registre = LocateRegistry.getRegistry(tools.PortServer);
            
            /* reading xml file */
            /* merci http://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/ */
            /* on recupere le fichier de conf */
            File fXmlFile = new File(Globals.pathToParams + fichierParam);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            org.w3c.dom.Document doc = dBuilder.parse(fXmlFile);
            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();
            
            /*
            Si on veut un test, on exporte le fichier correspondant
            */
            if(test) {
                exportRez = new ExportExpectedResults(doc);
                exportRez.export();
                // le noeud à utiliser pour envoyer le message n'est plus celui passé en param (ugly, I know)
                noeudEnvoisMessage = exportRez.getNoeudEnvoisMessage();
            }
            
            /* on commence par generer tous les objets */
            NodeList nList = doc.getElementsByTagName("node");
            SiteItf[] obj = new SiteItf[nList.getLength() + 1];
            
            for (int temp = 0; temp < nList.getLength(); temp++) {
		Node nNode = nList.item(temp);
 
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			Element eElement = (Element) nNode;
                        id = Integer.parseInt(eElement.getAttribute("id"));
                        tools.printVerbose(Integer.toString(id), verbose);
			/* on va creer un objet */
                        obj[temp] = new SiteImpl(id);
 
		}
            }
            tools.printVerbose("Export des noeuds vers le serveur", verbose);
            /* on les exporte dans registre */
            for (int i = 0; i < obj.length-1; i++) {
                tools.printVerbose("==========" + i, verbose);
                tools.printVerbose("Exporting node" + obj[i].getId(), verbose);
                registre.rebind("rmi://localhost:6000/node" + obj[i].getId(), obj[i]);
            }
            
            tools.printVerbose("\n\n==========", verbose);

            // on recupere les stub
            for (int i = 0; i < obj.length - 1; i++) {
                tools.printVerbose("==========" + i, verbose);
                tools.printVerbose("getting stub" + obj[i].getId(), verbose);
                obj[i] = (SiteItf) registre.lookup("rmi://localhost:6000/node" + obj[i].getId());
            }
            
            tools.printVerbose("\n\n==========", verbose);
            // on connecte le node aux autre node avec lesquels il doit communiquer
            for (int temp = 0; temp < nList.getLength() ; temp++) {
		Node nNode = nList.item(temp);
 
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			Element eElement = (Element) nNode;
                        id = Integer.parseInt(eElement.getAttribute("id"));
                        tools.printVerbose("============", verbose);
                        tools.printVerbose("I am noeud " + id + " and I have to connect with " + eElement.getElementsByTagName("connectedNode").getLength() + " nodes", verbose);
                        
                        for(int i = 0; i < eElement.getElementsByTagName("connectedNode").getLength(); i++) {
                            String nodeToConnect = eElement.getElementsByTagName("connectedNode").item(i).getTextContent().replaceAll("\\s","");
                            tools.printVerbose("> connecting to node = " + nodeToConnect, verbose);
                            obj[temp].addNode(obj[Integer.parseInt(nodeToConnect) - 1]);
                        }

		}
            }
        
           tools.printVerbose("\n\n==========", verbose);
           tools.printVerbose("Diffusion du message", verbose);
           
           tools.printVerbose("Envoit a partir du noeud " + obj[0].getId(), verbose);
           tools.printVerbose("Ce node a " + obj[0].getNodes().size() + " nodes.", verbose);
           obj[Integer.parseInt(noeudEnvoisMessage) - 1].diffuserMessage(data);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


}   
 
