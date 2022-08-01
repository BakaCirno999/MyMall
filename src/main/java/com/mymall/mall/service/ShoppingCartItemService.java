package com.mymall.mall.service;

import com.mymall.mall.controller.mall.param.SaveCartItemParam;
import com.mymall.mall.controller.mall.param.UpdateCartItemParam;
import com.mymall.mall.controller.mall.vo.ShoppingCartItemVO;
import com.mymall.mall.entity.ShoppingCartItem;
import com.baomidou.mybatisplus.extension.service.IService;
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
public interface ShoppingCartItemService {

    /**
     * 根据userId和cartItemIds获取对应的购物项记录
     *
     * @param cartItemIds
     * @param userId
     * @return
     */
    List<ShoppingCartItemVO> getCartItemsForSettle(List<Long> cartItemIds, Long userId);

    /**
     * 我的购物车(分页数据)
     *
     * @param pageUtil
     * @return
     */
    PageResult getMyShoppingCartItems(PageQueryUtil pageUtil);

    /**
     * 获取我的购物车中的列表数据
     *
     * @param userId
     * @return
     */
    List<ShoppingCartItemVO> getMyShoppingCartItems(Long userId);

    /**
     * 保存商品至购物车中
     *
     * @param saveCartItemParam
     * @param userId
     * @return
     */
    String saveCartItem(SaveCartItemParam saveCartItemParam, Long userId);

    /**
     * 修改购物车中的属性
     *
     * @param updateCartItemParam
     * @param userId
     * @return
     */
    String updateCartItem(UpdateCartItemParam updateCartItemParam, Long userId);

    /**
     * 获取购物项详情
     *
     * @param shoppingCartItemId
     * @return
     */
    ShoppingCartItem getCartItemById(Long shoppingCartItemId);

    /**
     * 删除购物车中的商品
     *
     *
     * @param shoppingCartItemId
     * @param userId
     * @return
     */
    Boolean deleteById(Long shoppingCartItemId, Long userId);
}
