package com.wdbyte.rmi.server;

import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 * RMI Server
 *
 * @author www.wdbyte.com
 * @date 2021/05/08
 */
public interface UserService extends Remote {

    /**
     * 查找用户
     *
     * @param userId
     * @return
     * @throws RemoteException
     */
    User findUser(String userId) throws RemoteException;
}
