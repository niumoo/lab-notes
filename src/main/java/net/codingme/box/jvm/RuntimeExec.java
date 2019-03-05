package net.codingme.box.jvm;

import java.io.*;

/**
 * <p>
 *
 * @Author niujinpeng
 * @Date 2018/11/26 15:17
 */
public class RuntimeExec {

    public static void main(String[] args) throws IOException {
        Process process = Runtime.getRuntime().exec("ipconfig");
        BufferedReader bis = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = null;
        while ((line = bis.readLine()) != null) {
            System.out.println(line);
        }
    }
}
