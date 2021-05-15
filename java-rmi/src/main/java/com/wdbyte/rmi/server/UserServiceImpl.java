package com.wdbyte.rmi.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author www.wdbyte.com
 * @date 2021/05/08
 */
public class UserServiceImpl extends UnicastRemoteObject implements UserService {

    protected UserServiceImpl() throws RemoteException {
    }

    @Override
    public User findUser(String userId) throws RemoteException {
        // 加载在查询
        if ("00001".equals(userId)) {
            User user = new User();
            user.setName("金庸");
            user.setAge(100);
            user.setSkill("写作");
            return user;
        }
        throw new RemoteException("查无此人");
    }
}
