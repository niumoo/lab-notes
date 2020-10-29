package com.wdbyte.other;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 负载均衡算法 - 轮训
 *
 * @author niujinpeng
 * @Date 2020/5/26 23:06
 */
public class LoadBalancingByOneByOne {

    /** 服务器列表 */
    private static List<String> serverList = new ArrayList<>();
    static {
        serverList.add("192.168.1.2");
        serverList.add("192.168.1.3");
        serverList.add("192.168.1.4");
        serverList.add("192.168.1.5");
    }
    private static Integer index = 0;

    /**
     * 随机路由算法
     */
    public static String randomOneByOne() {
        // 复制遍历用的集合，防止操作中集合有变更
        List<String> tempList = new ArrayList<>(serverList.size());
        tempList.addAll(serverList);
        String server = "";
        synchronized (index) {
            index++;
            if (index == tempList.size()) {
                index = 0;
            }
            server = tempList.get(index);;
        }
        return server;
    }

    public static void main(String[] args) {
        HashMap<String, Integer> serverMap = new HashMap<>();
        for (int i = 0; i < 100000; i++) {
            String server = randomOneByOne();
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
