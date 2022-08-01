package com.mymall.mall.mapper;

import com.mymall.mall.entity.IndexConfig;
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
public interface IndexConfigMapper {

    int deleteById(Long configId);

    int insert(IndexConfig record);

    int insertSelective(IndexConfig record);

    IndexConfig selectByTypeAndGoodsId(@Param("configType") int configType, @Param("goodsId") Long goodsId);

    int updateById(IndexConfig record);

    List<IndexConfig> findIndexConfigList(PageQueryUtil pageUtil);

    int getTotalIndexConfigs(PageQueryUtil pageUtil);

    int deleteBatch(Long[] ids);

    List<IndexConfig> findIndexConfigsByTypeAndNum(@Param("configType") int configType, @Param("number") int number);

    IndexConfig selectById(Long configId);

    int updateByIdSelective(IndexConfig indexConfig);
}
