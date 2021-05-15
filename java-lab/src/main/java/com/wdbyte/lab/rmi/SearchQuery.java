package com.wdbyte.lab.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author github.com/niumoo
 * @date 2021/04/04
 */
public class SearchQuery extends UnicastRemoteObject implements Search {
    public SearchQuery() throws RemoteException {
        super();
    }

    @Override
    public User query(String userId) throws RemoteException {
        if ("1".equals(userId)) {
            return new User("金庸", 100);
        }
        return new User("好汉", 20);
    }
}
