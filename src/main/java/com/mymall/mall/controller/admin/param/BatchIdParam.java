package com.mymall.mall.controller.admin.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("批量删除入参")
public class BatchIdParam implements Serializable {

    @ApiModelProperty("id数组")
    Long[] ids;
}
