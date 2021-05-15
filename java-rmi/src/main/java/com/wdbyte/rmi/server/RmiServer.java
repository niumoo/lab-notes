package com.wdbyte.rmi.server;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

/**
 * RMI Server 端
 *
 * @author https://www.wdbyte.com
 * @date 2021/05/08
 */
public class RmiServer {

    public static void main(String[] args) {
        try {
            UserService userService = new UserServiceImpl();
            LocateRegistry.createRegistry(1900);
            Naming.rebind("rmi://localhost:1900/user", userService);
            System.out.println("start server,port is 1900");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
