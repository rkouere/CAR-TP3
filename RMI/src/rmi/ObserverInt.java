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
public interface ObserverInt extends Remote {
    public void attach(SiteImpl fils) throws RemoteException;
    public void detach(SiteImpl fils) throws RemoteException;
}
