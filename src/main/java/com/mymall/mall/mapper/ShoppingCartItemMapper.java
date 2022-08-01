package com.mymall.mall.mapper;

import com.mymall.mall.entity.ShoppingCartItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mymall.mall.util.PageQueryUtil;
import com.mymall.mall.util.PageResult;
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
public interface ShoppingCartItemMapper {

    List<ShoppingCartItem> selectByUserIdAndCartItemIds(@Param("userId") Long userId, @Param("cartItemIds") List<Long> cartItemIds);

    int deleteBatch(List<Long> ids);

    List<ShoppingCartItem> findMyCartItems(PageQueryUtil pageUtil);

    int getTotalMyCartItems(PageQueryUtil pageUtil);

    List<ShoppingCartItem> selectByUserId(@Param("userId") Long userId, @Param("number") int number);

    ShoppingCartItem selectByUserIdAndGoodsId(@Param("userId") Long userId, @Param("goodsId") Long goodsId);

    int selectCountByUserId(Long userId);

    int insertSelective(ShoppingCartItem shoppingCartItem);

    ShoppingCartItem selectById(Long cartItemId);

    int updateByIdSelective(ShoppingCartItem shoppingCartItemUpdate);

    int deleteById(Long cartItemId);
}
