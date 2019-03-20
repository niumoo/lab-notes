package net.codingme.box.service.impl;

import net.codingme.box.service.SkillService;
import net.codingme.box.utils.KeyUtil;
import net.codingme.box.utils.RedisLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *
 * @Author niujinpeng
 * @Date 2019/3/20 11:36
 */
@Service
public class SkillServiceImpl implements SkillService {

    @Autowired
    private RedisLock redisLock;

    private static Integer TIME_OUT = 10 * 1000;

    /** 商品和抢购数量 */
    static Map<String, Integer> products;
    /** 库存 */
    static Map<String, Integer> stock;
    /** 订单 */
    static Map<String, String> orders;
    static {
        products = new HashMap<>();
        stock = new HashMap<>();
        orders = new HashMap<>();
        products.put("iphonex", 10000);
        stock.put("iphonex", 10000);
    }

    public String getProductInfo(String product) {
        return " iphonex 抢购活动火爆进行中，限量:" + products.get(product) + "份，目前剩余" + stock.get(product) + "份,下单人数："
            + orders.size() + "人！";
    }

    @Override
    public String querySkillProductInfo(String product) {
        return getProductInfo(product);
    }

    @Override
    public String skillProduct(String product) {
        // 查询库存
        if (stock.get(product) == 0) {
            return "活动已经结束！";
        }
        // 下单
        orders.put(KeyUtil.getUniqueKey(), product);
        // 扣库存
        try {
            // 模拟时间消耗
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stock.put(product, stock.get(product) - 1);
        return "恭喜你强到了 " + product;
    }

    @Override
    public String skillProductByRedis(String product) {
        long value = System.currentTimeMillis() + TIME_OUT;
        if (!redisLock.lock(product, String.valueOf(value))) {
            return "当前人数过于火爆";
        }
        // 查询库存
        if (stock.get(product) == 0) {
            return "活动已经结束！";
        }
        // 下单
        orders.put(KeyUtil.getUniqueKey(), product);
        // 扣库存
        try {
            // 模拟时间消耗
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stock.put(product, stock.get(product) - 1);
        redisLock.unlock(product, String.valueOf(value));
        return "恭喜你强到了 " + product;
    }
}
