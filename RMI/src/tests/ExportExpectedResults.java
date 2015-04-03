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
import org.w3c.dom.NodeList;

/**
 *
 * @author rkouere
 */
public class ExportExpectedResults {
    NodeList nList = null;
    BufferedWriter out = null;
    
    
    public ExportExpectedResults(NodeList nList) {
        this.nList = nList;
    }
    
    public void export() throws IOException {
        System.out.println("Exporting");

        out = new BufferedWriter(new FileWriter(Globals.pathToParams + "expectedRes.txt"));
        
        out.close();
        
    }
    
}
