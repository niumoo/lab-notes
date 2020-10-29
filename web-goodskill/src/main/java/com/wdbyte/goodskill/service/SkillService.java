package com.wdbyte.goodskill.service;

/**
 * <p>
 *
 * @Author <a href="https://www.wdbyte.com">https://www.wdbyte.com</a>
 * @Date 2019/3/2011:36
 */
public interface SkillService {

    String querySkillProductInfo(String productId);

    String skillProduct(String productId);

    String skillProductByRedis(String productId);
}
