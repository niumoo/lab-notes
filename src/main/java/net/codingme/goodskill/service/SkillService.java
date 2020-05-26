package net.codingme.goodskill.service;

/**
 * <p>
 *
 * @Author niujinpeng
 * @Date 2019/3/2011:36
 */
public interface SkillService {

    public String querySkillProductInfo(String productId);

    public String skillProduct(String productId);
    public String skillProductByRedis(String productId);
}
