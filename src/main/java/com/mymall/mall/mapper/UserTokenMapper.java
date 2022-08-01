package com.mymall.mall.mapper;

import com.mymall.mall.entity.UserToken;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ganqian
 * @since 2022-02-18
 */
public interface UserTokenMapper {

    int insert(UserToken record);

    int insertSelective(UserToken record);

    UserToken selectById(Long userId);

    UserToken selectByToken(String token);

    int updateById(UserToken record);

    int updateByIdSelective(UserToken userToken);

    int deleteById(Long userId);

}
