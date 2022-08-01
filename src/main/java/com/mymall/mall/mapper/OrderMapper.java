package com.mymall.mall.mapper;

import com.mymall.mall.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mymall.mall.util.PageQueryUtil;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ganqian
 * @since 2022-02-18
 */
public interface OrderMapper {

    List<Order> findOrderList(PageQueryUtil pageUtil);

    int getTotalOrders(PageQueryUtil pageUtil);

    Order selectById(Long orderId);

    List<Order> selectByIds(@Param("orderIds") List<Long> orderIds);

    int checkDone(@Param("orderIds") List<Long> orderIds);

    int checkOut(@Param("orderIds") List<Long> orderIds);

    int closeOrder(@Param("orderIds") List<Long> orderIds, @Param("orderStatus") int orderStatus);

    int insertSelective(Order order);

    Order selectByOrderNo(String orderNo);

    int updateByIdSelective(Order order);
}
