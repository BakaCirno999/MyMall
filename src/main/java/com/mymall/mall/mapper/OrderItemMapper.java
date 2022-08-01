package com.mymall.mall.mapper;

import com.mymall.mall.entity.OrderItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ganqian
 * @since 2022-02-18
 */
public interface OrderItemMapper {

    /**
     * 根据订单id获取订单项列表
     *
     * @param orderId
     * @return
     */
    List<OrderItem> selectByOrderId(Long orderId);

    int insertBatch(List<OrderItem> orderItems);

    List<OrderItem> selectByOrderIds(List<Long> orderIds);
}
