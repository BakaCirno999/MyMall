package com.mymall.mall.service.impl;

import com.mymall.mall.common.Constants;
import com.mymall.mall.common.MallException;
import com.mymall.mall.common.ServiceResultEnum;
import com.mymall.mall.controller.mall.param.SaveCartItemParam;
import com.mymall.mall.controller.mall.param.UpdateCartItemParam;
import com.mymall.mall.controller.mall.vo.ShoppingCartItemVO;
import com.mymall.mall.entity.Goods;
import com.mymall.mall.entity.ShoppingCartItem;
import com.mymall.mall.mapper.GoodsMapper;
import com.mymall.mall.mapper.ShoppingCartItemMapper;
import com.mymall.mall.service.ShoppingCartItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mymall.mall.util.BeanUtil;
import com.mymall.mall.util.PageQueryUtil;
import com.mymall.mall.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ganqian
 * @since 2022-02-18
 */
@Service
public class ShoppingCartItemServiceImpl implements ShoppingCartItemService {

    @Autowired
    private ShoppingCartItemMapper shoppingCartItemMapper;

    @Autowired
    private GoodsMapper goodsMapper;


    /**
     * 数据转换
     *
     * @param shoppingCartItemVOS
     * @param shoppingCartItems
     * @return
     */
    private List<ShoppingCartItemVO> getShoppingCartItemVOS(List<ShoppingCartItemVO> shoppingCartItemVOS, List<ShoppingCartItem> shoppingCartItems) {
        if (!CollectionUtils.isEmpty(shoppingCartItems)) {
            //查询商品信息并做数据转换
            List<Long> goodsIds = shoppingCartItems.stream().map(ShoppingCartItem::getGoodsId).collect(Collectors.toList());
            List<Goods> goods = goodsMapper.selectByIds(goodsIds);
            Map<Long, Goods> goodsMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(goods)) {
                goodsMap = goods.stream().collect(Collectors.toMap(Goods::getGoodsId, Function.identity(), (entity1, entity2) -> entity1));
            }
            for (ShoppingCartItem shoppingCartItem : shoppingCartItems) {
                ShoppingCartItemVO shoppingCartItemVO = new ShoppingCartItemVO();
                BeanUtil.copyProperties(shoppingCartItem, shoppingCartItemVO);
                if (goodsMap.containsKey(shoppingCartItem.getGoodsId())) {
                    Goods goodsTemp = goodsMap.get(shoppingCartItem.getGoodsId());
                    shoppingCartItemVO.setGoodsCoverImg(goodsTemp.getGoodsCoverImg());
                    String goodsName = goodsTemp.getGoodsName();
                    // 字符串过长导致文字超出的问题
                    if (goodsName.length() > 28) {
                        goodsName = goodsName.substring(0, 28) + "...";
                    }
                    shoppingCartItemVO.setGoodsName(goodsName);
                    shoppingCartItemVO.setSellingPrice(goodsTemp.getSellingPrice());
                    shoppingCartItemVOS.add(shoppingCartItemVO);
                }
            }
        }
        return shoppingCartItemVOS;
    }

    @Override
    public List<ShoppingCartItemVO> getCartItemsForSettle(List<Long> cartItemIds, Long userId) {
        List<ShoppingCartItemVO> shoppingCartItemVOS = new ArrayList<>();
        if (CollectionUtils.isEmpty(cartItemIds)) {
            MallException.fail("购物项不能为空");
        }
        List<ShoppingCartItem> shoppingCartItems = shoppingCartItemMapper.selectByUserIdAndCartItemIds(userId, cartItemIds);
        if (CollectionUtils.isEmpty(shoppingCartItems)) {
            MallException.fail("购物项不能为空");
        }
        if (shoppingCartItems.size() != cartItemIds.size()) {
            MallException.fail("参数异常");
        }
        return getShoppingCartItemVOS(shoppingCartItemVOS, shoppingCartItems);
    }

    @Override
    public PageResult getMyShoppingCartItems(PageQueryUtil pageUtil) {
        List<ShoppingCartItemVO> shoppingCartItemVOS = new ArrayList<>();
        List<ShoppingCartItem> shoppingCartItems = shoppingCartItemMapper.findMyCartItems(pageUtil);
        int total = shoppingCartItemMapper.getTotalMyCartItems(pageUtil);
        PageResult pageResult = new PageResult(getShoppingCartItemVOS(shoppingCartItemVOS, shoppingCartItems), total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public List<ShoppingCartItemVO> getMyShoppingCartItems(Long userId) {
        List<ShoppingCartItemVO> shoppingCartItemVOS = new ArrayList<>();
        List<ShoppingCartItem> shoppingCartItems = shoppingCartItemMapper.selectByUserId(userId, Constants.SHOPPING_CART_ITEM_TOTAL_NUMBER);
        return getShoppingCartItemVOS(shoppingCartItemVOS, shoppingCartItems);
    }

    @Override
    public String saveCartItem(SaveCartItemParam saveCartItemParam, Long userId) {
        ShoppingCartItem temp = shoppingCartItemMapper.selectByUserIdAndGoodsId(userId, saveCartItemParam.getGoodsId());
        if (temp != null) {
            //已存在则修改该记录
            MallException.fail(ServiceResultEnum.SHOPPING_CART_ITEM_EXIST_ERROR.getResult());
        }
        Goods goods = goodsMapper.selectById(saveCartItemParam.getGoodsId());
        //商品为空
        if (goods == null) {
            return ServiceResultEnum.GOODS_NOT_EXIST.getResult();
        }
        int totalItem = shoppingCartItemMapper.selectCountByUserId(userId);
        //超出单个商品的最大数量
        if (saveCartItemParam.getGoodsCount() < 1) {
            return ServiceResultEnum.SHOPPING_CART_ITEM_NUMBER_ERROR.getResult();
        }
        //超出单个商品的最大数量
        if (saveCartItemParam.getGoodsCount() > Constants.SHOPPING_CART_ITEM_LIMIT_NUMBER) {
            return ServiceResultEnum.SHOPPING_CART_ITEM_LIMIT_NUMBER_ERROR.getResult();
        }
        //超出最大数量
        if (totalItem > Constants.SHOPPING_CART_ITEM_TOTAL_NUMBER) {
            return ServiceResultEnum.SHOPPING_CART_ITEM_TOTAL_NUMBER_ERROR.getResult();
        }
        ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
        BeanUtil.copyProperties(saveCartItemParam, shoppingCartItem);
        shoppingCartItem.setUserId(userId);
        //保存记录
        if (shoppingCartItemMapper.insertSelective(shoppingCartItem) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public String updateCartItem(UpdateCartItemParam updateCartItemParam, Long userId) {
        ShoppingCartItem shoppingCartItemUpdate = shoppingCartItemMapper.selectById(updateCartItemParam.getCartItemId());
        if (shoppingCartItemUpdate == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        if (!shoppingCartItemUpdate.getUserId().equals(userId)) {
            MallException.fail(ServiceResultEnum.REQUEST_FORBIDEN_ERROR.getResult());
        }
        //超出单个商品的最大数量
        if (updateCartItemParam.getGoodsCount() > Constants.SHOPPING_CART_ITEM_LIMIT_NUMBER) {
            return ServiceResultEnum.SHOPPING_CART_ITEM_LIMIT_NUMBER_ERROR.getResult();
        }
        //当前登录账号的userId与待修改的cartItem中userId不同，返回错误
        if (!shoppingCartItemUpdate.getUserId().equals(userId)) {
            return ServiceResultEnum.NO_PERMISSION_ERROR.getResult();
        }
        //数值相同，则不执行数据操作
        if (updateCartItemParam.getGoodsCount().equals(shoppingCartItemUpdate.getGoodsCount())) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        shoppingCartItemUpdate.setGoodsCount(updateCartItemParam.getGoodsCount());
        shoppingCartItemUpdate.setUpdateTime(new Date());
        //修改记录
        if (shoppingCartItemMapper.updateByIdSelective(shoppingCartItemUpdate) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public ShoppingCartItem getCartItemById(Long shoppingCartItemId) {
        ShoppingCartItem shoppingCartItem = shoppingCartItemMapper.selectById(shoppingCartItemId);
        if (shoppingCartItem == null) {
            MallException.fail(ServiceResultEnum.DATA_NOT_EXIST.getResult());
        }
        return shoppingCartItem;
    }

    @Override
    public Boolean deleteById(Long shoppingCartItemId, Long userId) {
        ShoppingCartItem shoppingCartItem = shoppingCartItemMapper.selectById(shoppingCartItemId);
        if (shoppingCartItem == null) {
            return false;
        }
        //userId不同不能删除
        if (!userId.equals(shoppingCartItem.getUserId())) {
            return false;
        }
        return shoppingCartItemMapper.deleteById(shoppingCartItemId) > 0;
    }
}
