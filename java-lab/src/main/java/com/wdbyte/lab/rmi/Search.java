package com.wdbyte.lab.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 定义一个远程接口
 *
 * @author github.com/niumoo
 * @date 2021/04/04
 */
public interface Search extends Remote {
    User query(String userId) throws RemoteException;
}
