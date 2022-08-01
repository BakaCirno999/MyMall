package com.mymall.mall.controller.admin.param;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@ApiModel("用户重命名入参")
public class UpdateAdminNameParam {

    @NotEmpty(message = "loginUserName不能为空")
    private String loginUserName;

    @NotEmpty(message = "nickName不能为空")
    private String nickName;
}
