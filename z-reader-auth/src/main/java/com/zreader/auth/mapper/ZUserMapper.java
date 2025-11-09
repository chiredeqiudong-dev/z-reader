package com.zreader.auth.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zreader.auth.domain.ZUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户数据访问
 * @author zy
 * @date 2025/11/9
 */
@Mapper
public interface ZUserMapper extends BaseMapper<ZUser> {

}
