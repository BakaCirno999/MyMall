package com.mymall.mall.controller.mall;

import com.mymall.mall.common.MallException;
import com.mymall.mall.common.ServiceResultEnum;
import com.mymall.mall.controller.mall.vo.IndexCategoryVO;
import com.mymall.mall.service.GoodsCategoryService;
import com.mymall.mall.util.Result;
import com.mymall.mall.util.ResultGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api(value = "v1", tags = "3.商城分类页面接口")
@RequestMapping("/api/v1")
public class GoodsCategoryController {

    @Resource
    private GoodsCategoryService goodsCategoryService;

    @GetMapping("/categories")
    @ApiOperation(value = "获取分类数据", notes = "分类页面使用")
    public Result<List<IndexCategoryVO>> getCategories() {
        List<IndexCategoryVO> categories = goodsCategoryService.getCategoriesForIndex();
        if (CollectionUtils.isEmpty(categories)) {
            MallException.fail(ServiceResultEnum.DATA_NOT_EXIST.getResult());
        }
        return ResultGenerator.genSuccessResult(categories);
    }
}
