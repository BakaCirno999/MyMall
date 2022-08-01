package com.mymall.mall.service;

import com.mymall.mall.entity.Goods;
import com.mymall.mall.util.PageQueryUtil;
import com.mymall.mall.util.PageResult;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ganqian
 * @since 2022-02-18
 */
public interface GoodsService {

    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getGoodsPage(PageQueryUtil pageUtil);

    /**
     * 添加商品
     *
     * @param goodsInfo
     * @return
     */
    String saveGoods(Goods goodsInfo);

    /**
     * 修改商品信息
     *
     * @param goods
     * @return
     */
    String updateGoods(Goods goods);

    /**
     * 获取商品详情
     *
     * @param id
     * @return
     */
    Goods getGoodsById(Long id);

    /**
     * 批量修改销售状态(上架下架)
     *
     * @param ids
     * @return
     */
    Boolean batchUpdateSellStatus(Long[] ids, int sellStatus);

    /**
     * 商品搜索
     *
     * @param pageUtil
     * @return
     */
    PageResult searchGoods(PageQueryUtil pageUtil);
}
