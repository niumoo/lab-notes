package com.wdbyte.lab.classloader;

/**
 * <p>
 *
 * 后台启动一条线程，不断检测是否要刷新重新加载，实现了热加载的类
 * 
 * @Author niujinpeng
 * @Date 2019/10/24 23:53
 */
public class MsgHandle implements Runnable {
    @Override
    public void run() {
        while (true) {
            BaseManager manager = ManagerFactory.getManager(ManagerFactory.MY_MANAGER);
            manager.logic();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
