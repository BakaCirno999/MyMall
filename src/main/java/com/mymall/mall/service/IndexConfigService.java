package com.mymall.mall.service;

import com.mymall.mall.controller.mall.vo.IndexConfigGoodsVO;
import com.mymall.mall.entity.IndexConfig;
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
public interface IndexConfigService {

    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getConfigsPage(PageQueryUtil pageUtil);

    String saveIndexConfig(IndexConfig indexConfig);

    String updateIndexConfig(IndexConfig indexConfig);

    IndexConfig getIndexConfigById(Long id);

    Boolean deleteBatch(Long[] ids);

    /**
     * 返回固定数量的首页配置商品对象(首页调用)
     *
     * @param number
     * @return
     */
    List<IndexConfigGoodsVO> getConfigGoodsesForIndex(int configType, int number);
}
