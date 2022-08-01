package com.mymall.mall.service;

import com.mymall.mall.controller.mall.vo.OrderDetailVO;
import com.mymall.mall.controller.mall.vo.ShoppingCartItemVO;
import com.mymall.mall.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mymall.mall.entity.User;
import com.mymall.mall.entity.UserAddress;
import com.mymall.mall.util.PageQueryUtil;
import com.mymall.mall.util.PageResult;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ganqian
 * @since 2022-02-18
 */
public interface OrderService {

    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getOrdersPage(PageQueryUtil pageUtil);

    /**
     * 获取订单详情
     *
     * @param orderId
     * @return
     */
    OrderDetailVO getOrderDetailByOrderId(Long orderId);

    /**
     * 配货
     *
     * @param ids
     * @return
     */
    String checkDone(Long[] ids);

    /**
     * 出库
     *
     * @param ids
     * @return
     */
    String checkOut(Long[] ids);

    /**
     * 关闭订单
     *
     * @param ids
     * @return
     */
    String closeOrder(Long[] ids);

    String saveOrder(User loginMallUser, UserAddress address, List<ShoppingCartItemVO> itemsForSave);

    /**
     * 获取订单详情
     *
     * @param orderNo
     * @param userId
     * @return
     */
    OrderDetailVO getOrderDetailByOrderNo(String orderNo, Long userId);

    /**
     * 我的订单列表
     *
     * @param pageUtil
     * @return
     */
    PageResult getMyOrders(PageQueryUtil pageUtil);

    /**
     * 手动取消订单
     *
     * @param orderNo
     * @param userId
     * @return
     */
    String cancelOrder(String orderNo, Long userId);

    /**
     * 确认收货
     *
     * @param orderNo
     * @param userId
     * @return
     */
    String finishOrder(String orderNo, Long userId);

    String paySuccess(String orderNo, int payType);
}
