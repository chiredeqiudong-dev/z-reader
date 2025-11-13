package com.zreader.auth.mapper;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zreader.auth.model.UpdateUserInfoDTO;
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

    /**
     * 用户信息更新
     */
    default int updateUserInfo(Integer userId, UpdateUserInfoDTO updateUserInfoDto) {
        LambdaUpdateWrapper<ZUser> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(ZUser::getId, userId)
                .set(updateUserInfoDto.getNickname() != null, ZUser::getUsername, updateUserInfoDto.getNickname())
                .set(updateUserInfoDto.getEmail() != null, ZUser::getEmail, updateUserInfoDto.getEmail())
                .set(updateUserInfoDto.getAvatarUrl() != null, ZUser::getAvatarUrl, updateUserInfoDto.getAvatarUrl())
                .set(updateUserInfoDto.getGender() != null, ZUser::getGender, updateUserInfoDto.getGender())
                .set(updateUserInfoDto.getBio() != null, ZUser::getBio, updateUserInfoDto.getBio());
        return update(updateWrapper);

    }

}
