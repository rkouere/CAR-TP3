/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rmi;

import Q2.*;
import rmi.*;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author echallier
 */
public interface SiteItf extends Remote {
    public void diffuserMessage(byte[] data) throws RemoteException;
    public void recevoirMessage(byte[] data) throws RemoteException;
    public void addFils(SiteItf fils) throws RemoteException;
    public List<SiteItf> getFils() throws RemoteException;
    
    public String SayHello() throws RemoteException;
}
