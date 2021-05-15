package com.wdbyte.lab.rmi;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

/**
 * @author github.com/niumoo
 * @date 2021/04/04
 */
public class SearchServer {
    public static void main(String[] args) {
        try {
            Search obj = new SearchQuery();
            LocateRegistry.createRegistry(1900);
            Naming.rebind("rmi://localhost:1900/search", obj);
            System.out.println("start server,port is 1900");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
