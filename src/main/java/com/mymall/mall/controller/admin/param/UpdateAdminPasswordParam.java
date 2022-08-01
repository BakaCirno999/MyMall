package com.mymall.mall.controller.admin.param;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@ApiModel("密码修改入参")
public class UpdateAdminPasswordParam {

    @NotEmpty(message = "originalPassword不能为空")
    private String originalPassword;

    @NotEmpty(message = "newPassword不能为空")
    private String newPassword;
}
