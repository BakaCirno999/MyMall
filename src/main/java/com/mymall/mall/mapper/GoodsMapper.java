package com.mymall.mall.mapper;

import com.mymall.mall.entity.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mymall.mall.entity.StockNumDTO;
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
public interface GoodsMapper {

    int deleteByPrimaryKey(Long goodsId);

    int insert(Goods record);

    int insertSelective(Goods record);

    Goods selectByPrimaryKey(Long goodsId);

    Goods selectByCategoryIdAndName(@Param("goodsName") String goodsName, @Param("goodsCategoryId") Long goodsCategoryId);

    int updateByIdSelective(Goods record);

    int updateByPrimaryKeyWithBLOBs(Goods record);

    int updateByPrimaryKey(Goods record);

    List<Goods> selectByPrimaryKeys(List<Long> goodsIds);

//    List<Goods> findGoodsListBySearch(PageQueryUtil pageUtil);

//    int getTotalGoodsBySearch(PageQueryUtil pageUtil);

    int batchInsert(@Param("GoodsList") List<Goods> GoodsList);

    int updateStockNum(@Param("stockNumDTOS") List<StockNumDTO> stockNumDTOS);

    int batchUpdateSellStatus(@Param("orderIds")Long[] orderIds,@Param("sellStatus") int sellStatus);

    List<Goods> findGoodsList(PageQueryUtil pageUtil);

    int getTotalGoods(PageQueryUtil pageUtil);

    Goods selectById(Long goodsId);

    List<Goods> selectByIds(List<Long> goodsIds);
}
