/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rmi;

import Q2.*;
import rmi.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author echallier
 */
public class SiteImpl implements SiteItf {

    public SiteImpl() throws RemoteException{
        super();
    }
    public static void main(String[] args) throws Exception{
        
    }

    @Override
    public void diffuserMessage(byte[] data, Nodes[] fils) throws RemoteException {
        for(Nodes site:fils){
            site.recevoirMessage(data);
        }
    }

}
