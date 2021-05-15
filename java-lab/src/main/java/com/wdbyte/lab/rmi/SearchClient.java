package com.wdbyte.lab.rmi;

import java.rmi.Naming;

/**
 * @author darcy
 * @date 2021/04/04
 */
public class SearchClient {
    public static void main(String args[]) {
        User answer;
        String value = "1";
        try {
            // lookup method to find reference of remote object
            Search access = (Search)Naming.lookup("rmi://localhost:1900/search");
            answer = access.query(value);
            System.out.println("query:" + value);
            System.out.println("result:" + answer);
        } catch (Exception ae) {
            System.out.println(ae);
        }
    }
}
