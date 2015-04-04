/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import divers.Globals;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author rkouere
 */
public class ExportExpectedResults {
    org.w3c.dom.Document doc = null;
    BufferedWriter out = null;
    NodeList nList = null;
    String sent = null;
    String received = null;
    String nodeOrigin = null;
    
    public ExportExpectedResults(org.w3c.dom.Document doc) {
        this.doc = doc;
    }
    /**
     * Export le nombre de message qui doivent être envoyé et ceux qui doinvent être recu.
     * @throws IOException Si le fichier contenant ces informations n'a pas pu être exporté
     */
    public void export() throws IOException {
        System.out.println("Exporting");
        // on recupere le node qui contient les paramètres que l'on peut attendre.
        nList = doc.getElementsByTagName("testResults");
        
        // on verifie qu'il existe
        if(nList.getLength() == 0) {
            System.out.println("Le fichier de parametre ne contient pas d'information concernant les tests.");
            System.exit(-1);
        }
        
        // on recupere les informations
        Node nNode = nList.item(0);
        Element eElement = (Element) nNode;
        
        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
            this.sent = eElement.getElementsByTagName("sent").item(0).getTextContent();
            this.received = eElement.getElementsByTagName("received").item(0).getTextContent();
            this.nodeOrigin = eElement.getElementsByTagName("nodeOrigin").item(0).getTextContent();
        }
        
        // on les ecrit dans un fichier
        out = new BufferedWriter(new FileWriter(Globals.pathToParams + "expectedRes.txt"));
        out.write(this.sent + "\n");
        out.write(this.received + "\n");
        out.close();
        
    }
    public String getNoeudEnvoisMessage() {
        return this.nodeOrigin;
    }
    
}
