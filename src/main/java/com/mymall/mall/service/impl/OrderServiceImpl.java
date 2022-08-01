package com.mymall.mall.service.impl;

import com.mymall.mall.common.*;
import com.mymall.mall.controller.mall.vo.OrderDetailVO;
import com.mymall.mall.controller.mall.vo.OrderItemVO;
import com.mymall.mall.controller.mall.vo.OrderListVO;
import com.mymall.mall.controller.mall.vo.ShoppingCartItemVO;
import com.mymall.mall.entity.*;
import com.mymall.mall.mapper.*;
import com.mymall.mall.service.OrderService;
import com.mymall.mall.util.BeanUtil;
import com.mymall.mall.util.NumberUtil;
import com.mymall.mall.util.PageQueryUtil;
import com.mymall.mall.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ganqian
 * @since 2022-02-18
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private ShoppingCartItemMapper shoppingCartItemMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private OrderAddressMapper orderAddressMapper;
    
    @Override
    public PageResult getOrdersPage(PageQueryUtil pageUtil) {
        List<Order> orders = orderMapper.findOrderList(pageUtil);
        int total = orderMapper.getTotalOrders(pageUtil);
        PageResult pageResult = new PageResult(orders, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public OrderDetailVO getOrderDetailByOrderId(Long orderId) {

        Order order = orderMapper.selectById(orderId);
        if (order == null) {
            MallException.fail(ServiceResultEnum.DATA_NOT_EXIST.getResult());
        }
        List<OrderItem> orderItems = orderItemMapper.selectByOrderId(order.getOrderId());
        //获取订单项数据
        if (!CollectionUtils.isEmpty(orderItems)) {
            List<OrderItemVO> orderItemVOS = BeanUtil.copyList(orderItems, OrderItemVO.class);
            OrderDetailVO orderDetailVO = new OrderDetailVO();
            BeanUtil.copyProperties(order, orderDetailVO);
            orderDetailVO.setOrderStatusString(MallOrderStatusEnum.getOrderStatusEnumByStatus(orderDetailVO.getOrderStatus()).getName());
            orderDetailVO.setPayTypeString(PayTypeEnum.getPayTypeEnumByType(orderDetailVO.getPayType()).getName());
            orderDetailVO.setOrderItemVOS(orderItemVOS);
            return orderDetailVO;
        } else {
            MallException.fail(ServiceResultEnum.ORDER_ITEM_NULL_ERROR.getResult());
            return null;
        }
    }

    @Override
    @Transactional
    public String checkDone(Long[] ids) {
        //查询所有的订单 判断状态 修改状态和更新时间
        List<Order> orders = orderMapper.selectByIds(Arrays.asList(ids));
        String errorOrderNos = "";
        if (!CollectionUtils.isEmpty(orders)) {
            for (Order order : orders) {
                if (order.getIsDeleted() == 1) {
                    errorOrderNos += order.getOrderNo() + " ";
                    continue;
                }
                if (order.getOrderStatus() != 1) {
                    errorOrderNos += order.getOrderNo() + " ";
                }
            }
            if (StringUtils.isEmpty(errorOrderNos)) {
                //订单状态正常 可以执行配货完成操作 修改订单状态和更新时间
                if (orderMapper.checkDone(Arrays.asList(ids)) > 0) {
                    return ServiceResultEnum.SUCCESS.getResult();
                } else {
                    return ServiceResultEnum.DB_ERROR.getResult();
                }
            } else {
                //订单此时不可执行出库操作
                if (errorOrderNos.length() > 0 && errorOrderNos.length() < 100) {
                    return errorOrderNos + "订单的状态不是支付成功无法执行出库操作";
                } else {
                    return "你选择了太多状态不是支付成功的订单，无法执行配货完成操作";
                }
            }
        }
        //未查询到数据 返回错误提示
        return ServiceResultEnum.DATA_NOT_EXIST.getResult();
    }

    @Override
    public String checkOut(Long[] ids) {

        //查询所有的订单 判断状态 修改状态和更新时间
        List<Order> orders = orderMapper.selectByIds(Arrays.asList(ids));
        String errorOrderNos = "";
        if (!CollectionUtils.isEmpty(orders)) {
            for (Order order : orders) {
                if (order.getIsDeleted() == 1) {
                    errorOrderNos += order.getOrderNo() + " ";
                    continue;
                }
                if (order.getOrderStatus() != 1 && order.getOrderStatus() != 2) {
                    errorOrderNos += order.getOrderNo() + " ";
                }
            }
            if (StringUtils.isEmpty(errorOrderNos)) {
                //订单状态正常 可以执行出库操作 修改订单状态和更新时间
                if (orderMapper.checkOut(Arrays.asList(ids)) > 0) {
                    return ServiceResultEnum.SUCCESS.getResult();
                } else {
                    return ServiceResultEnum.DB_ERROR.getResult();
                }
            } else {
                //订单此时不可执行出库操作
                if (errorOrderNos.length() > 0 && errorOrderNos.length() < 100) {
                    return errorOrderNos + "订单的状态不是支付成功或配货完成无法执行出库操作";
                } else {
                    return "你选择了太多状态不是支付成功或配货完成的订单，无法执行出库操作";
                }
            }
        }
        //未查询到数据 返回错误提示
        return ServiceResultEnum.DATA_NOT_EXIST.getResult();
    }

    @Override
    public String closeOrder(Long[] ids) {
        //查询所有的订单 判断状态 修改状态和更新时间
        List<Order> orders = orderMapper.selectByIds(Arrays.asList(ids));
        String errorOrderNos = "";
        if (!CollectionUtils.isEmpty(orders)) {
            for (Order order : orders) {
                // isDeleted=1 一定为已关闭订单
                if (order.getIsDeleted() == 1) {
                    errorOrderNos += order.getOrderNo() + " ";
                    continue;
                }
                //已关闭或者已完成无法关闭订单
                if (order.getOrderStatus() == 4 || order.getOrderStatus() < 0) {
                    errorOrderNos += order.getOrderNo() + " ";
                }
            }
            if (StringUtils.isEmpty(errorOrderNos)) {
                //订单状态正常 可以执行关闭操作 修改订单状态和更新时间
                if (orderMapper.closeOrder(Arrays.asList(ids), MallOrderStatusEnum.ORDER_CLOSED_BY_JUDGE.getOrderStatus()) > 0) {
                    return ServiceResultEnum.SUCCESS.getResult();
                } else {
                    return ServiceResultEnum.DB_ERROR.getResult();
                }
            } else {
                //订单此时不可执行关闭操作
                if (errorOrderNos.length() > 0 && errorOrderNos.length() < 100) {
                    return errorOrderNos + "订单不能执行关闭操作";
                } else {
                    return "你选择的订单不能执行关闭操作";
                }
            }
        }
        //未查询到数据 返回错误提示
        return ServiceResultEnum.DATA_NOT_EXIST.getResult();
    }


    @Override
    @Transactional
    public String saveOrder(User loginMallUser, UserAddress address, List<ShoppingCartItemVO> myShoppingCartItems) {
        List<Long> itemIdList = myShoppingCartItems.stream().map(ShoppingCartItemVO::getCartItemId).collect(Collectors.toList());
        List<Long> goodsIds = myShoppingCartItems.stream().map(ShoppingCartItemVO::getGoodsId).collect(Collectors.toList());
        List<Goods> goods = goodsMapper.selectByIds(goodsIds);
        //检查是否包含已下架商品
        List<Goods> goodsListNotSelling = goods.stream()
                .filter(goodsTemp -> goodsTemp.getGoodsSellStatus() != Constants.SELL_STATUS_UP)
                .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(goodsListNotSelling)) {
            //goodsListNotSelling 对象非空则表示有下架商品
            MallException.fail(goodsListNotSelling.get(0).getGoodsName() + "已下架，无法生成订单");
        }
        Map<Long, Goods> goodsMap = goods.stream().collect(Collectors.toMap(Goods::getGoodsId, Function.identity(), (entity1, entity2) -> entity1));
        //判断商品库存
        for (ShoppingCartItemVO shoppingCartItemVO : myShoppingCartItems) {
            //查出的商品中不存在购物车中的这条关联商品数据，直接返回错误提醒
            if (!goodsMap.containsKey(shoppingCartItemVO.getGoodsId())) {
                MallException.fail(ServiceResultEnum.SHOPPING_ITEM_ERROR.getResult());
            }
            //存在数量大于库存的情况，直接返回错误提醒
            if (shoppingCartItemVO.getGoodsCount() > goodsMap.get(shoppingCartItemVO.getGoodsId()).getStockNum()) {
                MallException.fail(ServiceResultEnum.SHOPPING_ITEM_COUNT_ERROR.getResult());
            }
        }
        //删除购物项
        if (!CollectionUtils.isEmpty(itemIdList) && !CollectionUtils.isEmpty(goodsIds) && !CollectionUtils.isEmpty(goods)) {
            if (shoppingCartItemMapper.deleteBatch(itemIdList) > 0) {
                List<StockNumDTO> stockNumDTOS = BeanUtil.copyList(myShoppingCartItems, StockNumDTO.class);
                int updateStockNumResult = goodsMapper.updateStockNum(stockNumDTOS);
                if (updateStockNumResult < 1) {
                    MallException.fail(ServiceResultEnum.SHOPPING_ITEM_COUNT_ERROR.getResult());
                }
                //生成订单号
                String orderNo = NumberUtil.genOrderNo();
                int priceTotal = 0;
                //保存订单
                Order order = new Order();
                order.setOrderNo(orderNo);
                order.setUserId(loginMallUser.getUserId());
                //总价
                for (ShoppingCartItemVO shoppingCartItemVO : myShoppingCartItems) {
                    priceTotal += shoppingCartItemVO.getGoodsCount() * shoppingCartItemVO.getSellingPrice();
                }
                if (priceTotal < 1) {
                    MallException.fail(ServiceResultEnum.ORDER_PRICE_ERROR.getResult());
                }
                order.setTotalPrice(priceTotal);
                String extraInfo = "";
                order.setExtraInfo(extraInfo);
                //生成订单项并保存订单项纪录
                if (orderMapper.insertSelective(order) > 0) {
                    //生成订单收货地址快照，并保存至数据库
                    OrderAddress orderAddress = new OrderAddress();
                    BeanUtil.copyProperties(address, orderAddress);
                    orderAddress.setOrderId(order.getOrderId());
                    //生成所有的订单项快照，并保存至数据库
                    List<OrderItem> orderItems = new ArrayList<>();
                    for (ShoppingCartItemVO ShoppingCartItemVO : myShoppingCartItems) {
                        OrderItem orderItem = new OrderItem();
                        //使用BeanUtil工具类将ShoppingCartItemVO中的属性复制到orderItem对象中
                        BeanUtil.copyProperties(ShoppingCartItemVO, orderItem);
                        //orderMapper文件insert()方法中使用了useGeneratedKeys因此orderId可以获取到
                        orderItem.setOrderId(order.getOrderId());
                        orderItems.add(orderItem);
                    }
                    //保存至数据库
                    if (orderItemMapper.insertBatch(orderItems) > 0 && orderAddressMapper.insertSelective(orderAddress) > 0) {
                        //所有操作成功后，将订单号返回，以供Controller方法跳转到订单详情
                        return orderNo;
                    }
                    MallException.fail(ServiceResultEnum.ORDER_PRICE_ERROR.getResult());
                }
                MallException.fail(ServiceResultEnum.DB_ERROR.getResult());
            }
            MallException.fail(ServiceResultEnum.DB_ERROR.getResult());
        }
        MallException.fail(ServiceResultEnum.SHOPPING_ITEM_ERROR.getResult());
        return ServiceResultEnum.SHOPPING_ITEM_ERROR.getResult();
    }

    @Override
    public OrderDetailVO getOrderDetailByOrderNo(String orderNo, Long userId) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order == null) {
            MallException.fail(ServiceResultEnum.DATA_NOT_EXIST.getResult());
        }
        if (!userId.equals(order.getUserId())) {
            MallException.fail(ServiceResultEnum.REQUEST_FORBIDEN_ERROR.getResult());
        }
        List<OrderItem> orderItems = orderItemMapper.selectByOrderId(order.getOrderId());
        //获取订单项数据
        if (CollectionUtils.isEmpty(orderItems)) {
            MallException.fail(ServiceResultEnum.ORDER_ITEM_NOT_EXIST_ERROR.getResult());
        }
        List<OrderItemVO> OrderItemVOS = BeanUtil.copyList(orderItems, OrderItemVO.class);
        OrderDetailVO orderDetailVO = new OrderDetailVO();
        BeanUtil.copyProperties(order, orderDetailVO);
        orderDetailVO.setOrderStatusString(MallOrderStatusEnum.getOrderStatusEnumByStatus(orderDetailVO.getOrderStatus()).getName());
        orderDetailVO.setPayTypeString(PayTypeEnum.getPayTypeEnumByType(orderDetailVO.getPayType()).getName());
        orderDetailVO.setOrderItemVOS(OrderItemVOS);
        return orderDetailVO;
    }

    @Override
    public PageResult getMyOrders(PageQueryUtil pageUtil) {
        int total = orderMapper.getTotalOrders(pageUtil);
        List<Order> orders = orderMapper.findOrderList(pageUtil);
        List<OrderListVO> orderListVOS = new ArrayList<>();
        if (total > 0) {
            //数据转换 将实体类转成vo
            orderListVOS = BeanUtil.copyList(orders, OrderListVO.class);
            //设置订单状态中文显示值
            for (OrderListVO orderListVO : orderListVOS) {
                orderListVO.setOrderStatusString(MallOrderStatusEnum.getOrderStatusEnumByStatus(orderListVO.getOrderStatus()).getName());
            }
            List<Long> orderIds = orders.stream().map(Order::getOrderId).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(orderIds)) {
                List<OrderItem> orderItems = orderItemMapper.selectByOrderIds(orderIds);
                Map<Long, List<OrderItem>> itemByOrderIdMap = orderItems.stream().collect(groupingBy(OrderItem::getOrderId));
                for (OrderListVO orderListVO : orderListVOS) {
                    //封装每个订单列表对象的订单项数据
                    if (itemByOrderIdMap.containsKey(orderListVO.getOrderId())) {
                        List<OrderItem> orderItemListTemp = itemByOrderIdMap.get(orderListVO.getOrderId());
                        //将NewBeeMallOrderItem对象列表转换成NewBeeMallOrderItemVO对象列表
                        List<OrderItemVO> orderItemVOS = BeanUtil.copyList(orderItemListTemp, OrderItemVO.class);
                        orderListVO.setOrderItemVOS(orderItemVOS);
                    }
                }
            }
        }
        PageResult pageResult = new PageResult(orderListVOS, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public String cancelOrder(String orderNo, Long userId) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order != null) {
            //验证是否是当前userId下的订单，否则报错
            if (!userId.equals(order.getUserId())) {
                MallException.fail(ServiceResultEnum.NO_PERMISSION_ERROR.getResult());
            }
            //订单状态判断
            if (order.getOrderStatus().intValue() == MallOrderStatusEnum.ORDER_SUCCESS.getOrderStatus()
                    || order.getOrderStatus().intValue() == MallOrderStatusEnum.ORDER_CLOSED_BY_MALLUSER.getOrderStatus()
                    || order.getOrderStatus().intValue() == MallOrderStatusEnum.ORDER_CLOSED_BY_EXPIRED.getOrderStatus()
                    || order.getOrderStatus().intValue() == MallOrderStatusEnum.ORDER_CLOSED_BY_JUDGE.getOrderStatus()) {
                return ServiceResultEnum.ORDER_STATUS_ERROR.getResult();
            }
            if (orderMapper.closeOrder(Collections.singletonList(order.getOrderId()), MallOrderStatusEnum.ORDER_CLOSED_BY_MALLUSER.getOrderStatus()) > 0) {
                return ServiceResultEnum.SUCCESS.getResult();
            } else {
                return ServiceResultEnum.DB_ERROR.getResult();
            }
        }
        return ServiceResultEnum.ORDER_NOT_EXIST_ERROR.getResult();
    }

    @Override
    public String finishOrder(String orderNo, Long userId) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order != null) {
            //验证是否是当前userId下的订单，否则报错
            if (!userId.equals(order.getUserId())) {
                return ServiceResultEnum.NO_PERMISSION_ERROR.getResult();
            }
            //订单状态判断 非出库状态下不进行修改操作
            if (order.getOrderStatus().intValue() != MallOrderStatusEnum.ORDER_EXPRESS.getOrderStatus()) {
                return ServiceResultEnum.ORDER_STATUS_ERROR.getResult();
            }
            order.setOrderStatus((byte) MallOrderStatusEnum.ORDER_SUCCESS.getOrderStatus());
            order.setUpdateTime(new Date());
            if (orderMapper.updateByIdSelective(order) > 0) {
                return ServiceResultEnum.SUCCESS.getResult();
            } else {
                return ServiceResultEnum.DB_ERROR.getResult();
            }
        }
        return ServiceResultEnum.ORDER_NOT_EXIST_ERROR.getResult();
    }

    @Override
    public String paySuccess(String orderNo, int payType) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order != null) {
            //订单状态判断 非待支付状态下不进行修改操作
            if (order.getOrderStatus().intValue() != MallOrderStatusEnum.ORDER_PRE_PAY.getOrderStatus()) {
                return ServiceResultEnum.ORDER_STATUS_ERROR.getResult();
            }
            order.setOrderStatus((byte) MallOrderStatusEnum.ORDER_PAID.getOrderStatus());
            order.setPayType((byte) payType);
            order.setPayStatus((byte) PayStatusEnum.PAY_SUCCESS.getPayStatus());
            order.setPayTime(new Date());
            order.setUpdateTime(new Date());
            if (orderMapper.updateByIdSelective(order) > 0) {
                return ServiceResultEnum.SUCCESS.getResult();
            } else {
                return ServiceResultEnum.DB_ERROR.getResult();
            }
        }
        return ServiceResultEnum.ORDER_NOT_EXIST_ERROR.getResult();
    }

}
