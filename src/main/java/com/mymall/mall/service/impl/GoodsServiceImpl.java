package com.mymall.mall.service.impl;

import com.mymall.mall.common.MallCategoryLevelEnum;
import com.mymall.mall.common.MallException;
import com.mymall.mall.common.ServiceResultEnum;
import com.mymall.mall.controller.mall.vo.SearchGoodsVO;
import com.mymall.mall.entity.GoodsCategory;
import com.mymall.mall.entity.Goods;
import com.mymall.mall.mapper.GoodsCategoryMapper;
import com.mymall.mall.mapper.GoodsMapper;
import com.mymall.mall.service.GoodsService;
import com.mymall.mall.util.BeanUtil;
import com.mymall.mall.util.PageQueryUtil;
import com.mymall.mall.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ganqian
 * @since 2022-02-18
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsCategoryMapper goodsCategoryMapper;

    @Override
    public PageResult getGoodsPage(PageQueryUtil pageUtil) {
        List<Goods> goodsList = goodsMapper.findGoodsList(pageUtil);
        int total = goodsMapper.getTotalGoods(pageUtil);
        PageResult pageResult = new PageResult(goodsList, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    @Transactional
    public String saveGoods(Goods Goods) {
        GoodsCategory goodsCategory = goodsCategoryMapper.selectById(Goods.getGoodsCategoryId());
        // 分类不存在或者不是三级分类，则该参数字段异常
        if (goodsCategory == null || goodsCategory.getCategoryLevel().intValue() != MallCategoryLevelEnum.LEVEL_THREE.getLevel()) {
            return ServiceResultEnum.GOODS_CATEGORY_ERROR.getResult();
        }
        if (goodsMapper.selectByCategoryIdAndName(Goods.getGoodsName(), Goods.getGoodsCategoryId()) != null) {
            return ServiceResultEnum.SAME_GOODS_EXIST.getResult();
        }
        if (goodsMapper.insertSelective(Goods) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    @Transactional
    public String updateGoods(Goods goods) {

        GoodsCategory goodsCategory = goodsCategoryMapper.selectById(goods.getGoodsCategoryId());
        // 分类不存在或者不是三级分类，则该参数字段异常
        if (goodsCategory == null || goodsCategory.getCategoryLevel().intValue() != MallCategoryLevelEnum.LEVEL_THREE.getLevel()) {
            return ServiceResultEnum.GOODS_CATEGORY_ERROR.getResult();
        }
        Goods temp = goodsMapper.selectById(goods.getGoodsId());
        if (temp == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        Goods temp2 = goodsMapper.selectByCategoryIdAndName(goods.getGoodsName(), goods.getGoodsCategoryId());
        if (temp2 != null && !temp2.getGoodsId().equals(goods.getGoodsId())) {
            //name和分类id相同且不同id 不能继续修改
            return ServiceResultEnum.SAME_GOODS_EXIST.getResult();
        }
        goods.setUpdateTime(new Date());
        if (goodsMapper.updateByIdSelective(goods) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public Goods getGoodsById(Long id) {
        Goods goods = goodsMapper.selectById(id);
        if (goods == null) {
            MallException.fail(ServiceResultEnum.GOODS_NOT_EXIST.getResult());
        }
        return goods;
    }

    @Override
    public Boolean batchUpdateSellStatus(Long[] ids, int sellStatus) {
        return goodsMapper.batchUpdateSellStatus(ids, sellStatus) > 0;
    }

    @Override
    public PageResult searchGoods(PageQueryUtil pageUtil) {
        List<Goods> goodsList = goodsMapper.findGoodsList(pageUtil);
        int total = goodsMapper.getTotalGoods(pageUtil);
        List<SearchGoodsVO> searchGoodsVOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(goodsList)) {
            searchGoodsVOS = BeanUtil.copyList(goodsList, SearchGoodsVO.class);
            for (SearchGoodsVO searchGoodsVO : searchGoodsVOS) {
                String goodsName = searchGoodsVO.getGoodsName();
                String goodsIntro = searchGoodsVO.getGoodsIntro();
                // 字符串过长导致文字超出的问题
                if (goodsName.length() > 28) {
                    goodsName = goodsName.substring(0, 28) + "...";
                    searchGoodsVO.setGoodsName(goodsName);
                }
                if (goodsIntro.length() > 30) {
                    goodsIntro = goodsIntro.substring(0, 30) + "...";
                    searchGoodsVO.setGoodsIntro(goodsIntro);
                }
            }
        }
        PageResult pageResult = new PageResult(searchGoodsVOS, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }
}
