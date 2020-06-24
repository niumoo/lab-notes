package net.codingme.algorithm;

import java.util.*;

/**
 * <p>
 * 负载均衡算法 - 加权随机
 *
 * @author niujinpeng
 * @Date 2020/5/26 23:06
 */
public class LoadBalancingByRandomWeight {
    /** 服务器列表 */
    private static HashMap<String, Integer> serverMap = new HashMap<>();
    static {
        serverMap.put("192.168.1.2", 2);
        serverMap.put("192.168.1.3", 2);
        serverMap.put("192.168.1.4", 2);
        serverMap.put("192.168.1.5", 4);
    }
    /**
     * 加权路由算法
     */
    public static String randomWithWeight() {
        List<String> tempList = new ArrayList();
        HashMap<String, Integer> tempMap = new HashMap<>();
        tempMap.putAll(serverMap);
        for (String key : serverMap.keySet()) {
            for (int i = 0; i < serverMap.get(key); i++) {
                tempList.add(key);
            }
        }
        int randomInt = new Random().nextInt(tempList.size());
        return tempList.get(randomInt);
    }

    public static void main(String[] args) {
        HashMap<String, Integer> serverMap = new HashMap<>();
        for (int i = 0; i < 100000; i++) {
            String server = randomWithWeight();
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


}
