package com.mymall.mall.service;

import com.mymall.mall.controller.mall.vo.IndexCarouselVO;
import com.mymall.mall.entity.Carousel;
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
public interface CarouselService {

    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getCarouselPage(PageQueryUtil pageUtil);

    String saveCarousel(Carousel carousel);

    String updateCarousel(Carousel carousel);

    Carousel getCarouselById(Integer id);

    Boolean deleteBatch(Long[] ids);

    /**
     * 返回固定数量的轮播图对象(首页调用)
     *
     * @param number
     * @return
     */
    List<IndexCarouselVO> getCarouselsForIndex(int number);
}
