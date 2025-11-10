package com.zreader.auth.mapper;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zreader.auth.model.ZUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户数据访问
 *
 * @author zy
 * @date 2025/11/9
 */
@Mapper
public interface ZUserMapper extends BaseMapper<ZUser> {

    /**
     * 根据用户名查询用户
     */
    default ZUser selectByUsername(String username) {
        LambdaQueryWrapper<ZUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ZUser::getUsername, username);
        return selectOne(queryWrapper);
    }

}
