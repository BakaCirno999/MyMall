package com.mymall.mall.mapper;

import com.mymall.mall.entity.AdminUserToken;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ganqian
 * @since 2022-02-18
 */
public interface AdminUserTokenMapper {


    int insert(AdminUserToken record);

    int insertSelective(AdminUserToken record);

    AdminUserToken selectByToken(String token);

    int updateByIdSelective(AdminUserToken adminUserToken);

    AdminUserToken selectById(Long adminUserId);

    int deleteById(Long adminUserId);
}
