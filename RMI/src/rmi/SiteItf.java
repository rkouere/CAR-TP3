/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author echallier
 */
public interface SiteItf extends Remote {
    public void diffuserMessage(byte[] data) throws RemoteException;
    public boolean recevoirMessage(byte[] data) throws RemoteException;
}
