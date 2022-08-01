package com.mymall.mall.service;

import com.mymall.mall.controller.mall.vo.IndexCategoryVO;
import com.mymall.mall.entity.GoodsCategory;
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
public interface GoodsCategoryService {

    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getCategorisPage(PageQueryUtil pageUtil);

    GoodsCategory getGoodsCategoryById(Long id);

    List<GoodsCategory> selectByLevelAndParentIdsAndNumber(List<Long> parentIds, int categoryLevel);

    String saveCategory(GoodsCategory goodsCategory);

    String updateGoodsCategory(GoodsCategory goodsCategory);

    boolean deleteBatch(Long[] ids);

    /**
     * 返回分类数据(首页调用)
     *
     * @return
     */
    List<IndexCategoryVO> getCategoriesForIndex();
}
