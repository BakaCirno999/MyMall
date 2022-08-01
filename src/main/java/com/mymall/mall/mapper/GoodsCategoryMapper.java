package com.mymall.mall.mapper;

import com.mymall.mall.entity.GoodsCategory;
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
public interface GoodsCategoryMapper {

    int deleteById(Long categoryId);

    int insert(GoodsCategory record);

    int insertSelective(GoodsCategory record);

    GoodsCategory selectById(Long categoryId);

    GoodsCategory selectByLevelAndName(@Param("categoryLevel") Byte categoryLevel, @Param("categoryName") String categoryName);

    int updateByIdSelective(GoodsCategory record);

    int updateById(GoodsCategory record);

    List<GoodsCategory> findGoodsCategoryList(PageQueryUtil pageUtil);

    int getTotalGoodsCategories(PageQueryUtil pageUtil);

    int deleteBatch(Long[] ids);

    List<GoodsCategory> selectByLevelAndParentIdsAndNumber(@Param("parentIds") List<Long> parentIds, @Param("categoryLevel") int categoryLevel, @Param("number") int number);

}
