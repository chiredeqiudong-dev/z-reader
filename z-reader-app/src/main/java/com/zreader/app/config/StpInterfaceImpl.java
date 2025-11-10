package com.zreader.app.config;


import cn.dev33.satoken.stp.StpInterface;
import com.zreader.auth.mapper.ZUserMapper;
import com.zreader.auth.model.ZUser;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Sa-Token 权限认证接口实现
 * 用于获取用户的角色和权限列表
 *
 * @author zy
 * @date 2025/11/10
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    private final ZUserMapper zUserMapper;

    public StpInterfaceImpl(ZUserMapper zUserMapper) {
        this.zUserMapper = zUserMapper;
    }

    /**
     * 返回指定账号id所拥有的权限码集合
     * 本系统暂不使用细粒度权限，返回空列表
     *
     * @param loginId   登录的用户ID
     * @param loginType 登录类型
     * @return 权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return new ArrayList<>();
    }

    /**
     * 返回指定账号id所拥有的角色标识集合
     *
     * @param loginId   登录的用户ID
     * @param loginType 登录类型
     * @return 角色标识集合
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        List<String> roles = new ArrayList<>();
        // 根据用户ID查询用户角色
        ZUser user = zUserMapper.selectById((String) loginId);
        if (user != null) {
            // 将角色ID转为字符串加入列表
            roles.add(String.valueOf(user.getRole()));
        }
        return roles;
    }
}

