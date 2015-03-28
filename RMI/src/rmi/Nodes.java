/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rkouere
 */
public class Nodes {
    private List<Nodes> fils = new ArrayList<Nodes>();
    private int id;
    public Nodes(int id) {
        this.id = id;
    }
    
    public void recevoirMessage(byte[] data) {
        
    }

    public int getId() {
        return id;
    }
    
    public void addFils(Nodes fils) {
        this.fils.add(fils);
    }
    
    public List<Nodes> getFils() {
        return this.fils;
    }
    
}
