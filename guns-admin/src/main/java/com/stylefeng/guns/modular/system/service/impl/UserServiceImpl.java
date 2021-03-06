package com.stylefeng.guns.modular.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.core.datascope.DataScope;
import com.stylefeng.guns.modular.system.dao.UserMapper;
import com.stylefeng.guns.modular.system.model.User;
import com.stylefeng.guns.modular.system.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 管理员表 服务实现类
 * </p>
 *
 * @author stylefeng123
 * @since 2018-02-22
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {


    @Override
    public int setStatus(String userId, String status) {
        return this.baseMapper.setStatus(userId, status);
    }

    @Override
    public int changePwd(String userId, String pwd) {
        return this.baseMapper.changePwd(userId, pwd);
    }

    @Override
    public List<Map<String, Object>> selectUsers(DataScope dataScope, String name, String beginTime, String endTime, String deptid) {
        return this.baseMapper.selectUsers(dataScope, name, beginTime, endTime, deptid);
    }

    @Override
    public int setRoles(String userId, String roleIds) {
        return this.baseMapper.setRoles(userId, roleIds);
    }

    @Override
    public User getByAccount(String account) {
        return this.baseMapper.getByAccount(account);
    }

    @Override
    public void registerUser(User user) {
        //添加用户
        this.baseMapper.registerUser(user);
        //获取用户
        User newUser = this.baseMapper.getByAccount(user.getAccount());
        //添加权限
        this.baseMapper.addUserPower(newUser.getId(),81,3);
        this.baseMapper.addUserPower(newUser.getId(),81,5);
        this.baseMapper.addUserPower(newUser.getId(),81,6);
        /**
         * 复制权限给用户
         */
        this.baseMapper.insertTbbcopuserpower(newUser);
    }

    @Override
    public void createUser(User user) {
        //添加用户
        this.baseMapper.registerUser(user);
    }
}
