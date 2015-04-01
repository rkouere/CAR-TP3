/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rmi;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author echallier
 */
public class TransferData extends Thread {
    SiteItf target = null;
    private byte[] data = null;
    public TransferData (SiteItf target,  byte[] data) {
        this.target = target;
        this.data = data;
    }
    public void run() {
        try {
            this.target.recevoirMessage(this.data);
        } catch (RemoteException ex) {
            Logger.getLogger(TransferData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
