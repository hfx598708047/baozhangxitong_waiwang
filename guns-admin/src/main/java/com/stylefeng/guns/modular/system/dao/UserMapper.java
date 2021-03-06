package com.stylefeng.guns.modular.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.stylefeng.guns.core.datascope.DataScope;
import com.stylefeng.guns.modular.system.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 管理员表 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2017-07-11
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 修改用户状态
     */
    int setStatus(@Param("userId") String userId, @Param("status") String status);

    /**
     * 修改密码
     */
    int changePwd(@Param("userId") String userId, @Param("pwd") String pwd);

    /**
     * 根据条件查询用户列表
     */
    List<Map<String, Object>> selectUsers(@Param("dataScope") DataScope dataScope, @Param("name") String name, @Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("deptid") String deptid);

    /**
     * 设置用户的角色
     */
    int setRoles(@Param("userId") String userId, @Param("roleIds") String roleIds);

    /**
     * 通过账号获取用户
     */
    User getByAccount(@Param("account") String account);

    /**
     * 注册用户
     */
    void registerUser(User user);

    void addUserPower(@Param("USERNUM") long USERNUM,@Param("OPTYPENUM") int OPTYPENUM,@Param("OPPOWERCODE") int OPPOWERCODE);

    /**
     * 创建用户
     */
    void createUser(User user);

    /**
     * 复制权限给用户
     */
    void insertTbbcopuserpower(User user);

}