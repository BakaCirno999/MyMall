package com.mymall.mall.mapper;

import com.mymall.mall.entity.Carousel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mymall.mall.util.PageQueryUtil;
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
public interface CarouselMapper {

    int deleteById(Integer carouselId);

    int insert(Carousel record);

    int insertSelective(Carousel record);

    Carousel selectById(Integer carouselId);

    int updateByIdSelective(Carousel record);

    int updateById(Carousel record);

    List<Carousel> findCarouselList(PageQueryUtil pageUtil);

    int getTotalCarousels(PageQueryUtil pageUtil);

    int deleteBatch(Long[] ids);

    List<Carousel> findCarouselsByNum(@Param("number") int number);
}
