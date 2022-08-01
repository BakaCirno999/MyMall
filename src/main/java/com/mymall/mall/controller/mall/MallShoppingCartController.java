package com.mymall.mall.controller.mall;

import com.mymall.mall.common.Constants;
import com.mymall.mall.common.MallException;
import com.mymall.mall.common.ServiceResultEnum;
import com.mymall.mall.config.annotation.TokenToMallUser;
import com.mymall.mall.controller.mall.param.SaveCartItemParam;
import com.mymall.mall.controller.mall.param.UpdateCartItemParam;
import com.mymall.mall.controller.mall.vo.ShoppingCartItemVO;
import com.mymall.mall.entity.ShoppingCartItem;
import com.mymall.mall.entity.User;
import com.mymall.mall.service.ShoppingCartItemService;
import com.mymall.mall.util.PageQueryUtil;
import com.mymall.mall.util.PageResult;
import com.mymall.mall.util.Result;
import com.mymall.mall.util.ResultGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(value = "v1", tags = "5.商城购物车相关接口")
@RequestMapping("/api/v1")
public class MallShoppingCartController {

    @Resource
    private ShoppingCartItemService shoppingCartItemService;

    @GetMapping("/shop-cart/page")
    @ApiOperation(value = "购物车列表(每页默认5条)", notes = "传参为页码")
    public Result<PageResult<List<ShoppingCartItemVO>>> cartItemPageList(Integer pageNumber, @TokenToMallUser User loginMallUser) {
        Map params = new HashMap(8);
        if (pageNumber == null || pageNumber < 1) {
            pageNumber = 1;
        }
        params.put("userId", loginMallUser.getUserId());
        params.put("page", pageNumber);
        params.put("limit", Constants.SHOPPING_CART_PAGE_LIMIT);
        //封装分页请求参数
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(shoppingCartItemService.getMyShoppingCartItems(pageUtil));
    }

    @GetMapping("/shop-cart")
    @ApiOperation(value = "购物车列表(网页移动端不分页)", notes = "")
    public Result<List<ShoppingCartItemVO>> cartItemList(@TokenToMallUser User loginMallUser) {
        return ResultGenerator.genSuccessResult(shoppingCartItemService.getMyShoppingCartItems(loginMallUser.getUserId()));
    }

    @PostMapping("/shop-cart")
    @ApiOperation(value = "添加商品到购物车接口", notes = "传参为商品id、数量")
    public Result saveShoppingCartItem(@RequestBody SaveCartItemParam saveCartItemParam,
                                                 @TokenToMallUser User loginMallUser) {
        String saveResult = shoppingCartItemService.saveCartItem(saveCartItemParam, loginMallUser.getUserId());
        //添加成功
        if (ServiceResultEnum.SUCCESS.getResult().equals(saveResult)) {
            return ResultGenerator.genSuccessResult();
        }
        //添加失败
        return ResultGenerator.genFailResult(saveResult);
    }

    @PutMapping("/shop-cart")
    @ApiOperation(value = "修改购物项数据", notes = "传参为购物项id、数量")
    public Result updateNewBeeMallShoppingCartItem(@RequestBody UpdateCartItemParam updateCartItemParam,
                                                   @TokenToMallUser User loginMallUser) {
        String updateResult = shoppingCartItemService.updateCartItem(updateCartItemParam, loginMallUser.getUserId());
        //修改成功
        if (ServiceResultEnum.SUCCESS.getResult().equals(updateResult)) {
            return ResultGenerator.genSuccessResult();
        }
        //修改失败
        return ResultGenerator.genFailResult(updateResult);
    }

    @DeleteMapping("/shop-cart/{shoppingCartItemId}")
    @ApiOperation(value = "删除购物项", notes = "传参为购物项id")
    public Result updateShoppingCartItem(@PathVariable("shoppingCartItemId") Long shoppingCartItemId,
                                                   @TokenToMallUser User loginMallUser) {
        ShoppingCartItem shoppingCartItem = shoppingCartItemService.getCartItemById(shoppingCartItemId);
        if (!loginMallUser.getUserId().equals(shoppingCartItem.getUserId())) {
            return ResultGenerator.genFailResult(ServiceResultEnum.REQUEST_FORBIDEN_ERROR.getResult());
        }
        Boolean deleteResult = shoppingCartItemService.deleteById(shoppingCartItemId,loginMallUser.getUserId());
        //删除成功
        if (deleteResult) {
            return ResultGenerator.genSuccessResult();
        }
        //删除失败
        return ResultGenerator.genFailResult(ServiceResultEnum.OPERATE_ERROR.getResult());
    }

    @GetMapping("/shop-cart/settle")
    @ApiOperation(value = "根据购物项id数组查询购物项明细", notes = "确认订单页面使用")
    public Result<List<ShoppingCartItemVO>> toSettle(Long[] cartItemIds, @TokenToMallUser User loginMallUser) {
        if (cartItemIds.length < 1) {
            MallException.fail("参数异常");
        }
        int priceTotal = 0;
        List<ShoppingCartItemVO> itemsForSettle = shoppingCartItemService.getCartItemsForSettle(Arrays.asList(cartItemIds), loginMallUser.getUserId());
        if (CollectionUtils.isEmpty(itemsForSettle)) {
            //无数据则抛出异常
            MallException.fail("参数异常");
        } else {
            //总价
            for (ShoppingCartItemVO shoppingCartItemVO : itemsForSettle) {
                priceTotal += shoppingCartItemVO.getGoodsCount() * shoppingCartItemVO.getSellingPrice();
            }
            if (priceTotal < 1) {
                MallException.fail("价格异常");
            }
        }
        return ResultGenerator.genSuccessResult(itemsForSettle);
    }
}
