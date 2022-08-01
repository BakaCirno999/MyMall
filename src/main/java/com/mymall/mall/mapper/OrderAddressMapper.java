package com.mymall.mall.mapper;

import com.mymall.mall.entity.OrderAddress;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 订单收货地址关联表 Mapper 接口
 * </p>
 *
 * @author ganqian
 * @since 2022-02-18
 */
public interface OrderAddressMapper {

    int insertSelective(OrderAddress orderAddress);
}
