package net.codingme.algorithm;

import java.util.*;

/**
 * <p>
 * 负载均衡算法 - IP Hash
 *
 * @author niujinpeng
 * @Date 2020/5/26 23:06
 */
public class LoadBalancingByIpHash {

    private static List<String> serverList = new ArrayList<>();
    static {
        serverList.add("192.168.1.2");
        serverList.add("192.168.1.3");
        serverList.add("192.168.1.4");
        serverList.add("192.168.1.5");
    }

    /**
     * ip hash 路由算法
     */
    public static String ipHash(String ip) {
        // 复制遍历用的集合，防止操作中集合有变更
        List<String> tempList = new ArrayList<>(serverList.size());
        tempList.addAll(serverList);
        int index = ip.hashCode() % serverList.size();
        return tempList.get(Math.abs(index));
    }

    public static void main(String[] args) {
        HashMap<String, Integer> serverMap = new HashMap<>();
        for (int i = 0; i < 100000; i++) {
            String server = ipHash(generateIp());
            Integer count = serverMap.get(server);
            if (count == null) {
                count = 1;
            } else {
                count++;
            }
            // 记录
            serverMap.put(server, count);
        }
        // 路由总体结果
        for (Map.Entry<String, Integer> entry : serverMap.entrySet()) {
            System.out.println("IP:" + entry.getKey() + "，次数：" + entry.getValue());
        }
    }



    public static String generateIp() {
        Random random = new Random();
        int ip1 = random.nextInt(255);
        int ip2 = random.nextInt(255);
        int ip3 = random.nextInt(255);
        int ip4 = random.nextInt(255);
        // 例如：192.111.123.123
        return ip1 + "." + ip2 + "." + ip3 + "." + ip4;
    }
}
