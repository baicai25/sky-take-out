package com.sky.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "996员工登录时传递的数据模型")
// 类比@Api()和@ApiOpration(),这个是@ApiModel()和@ApiModelProperty()
public class EmployeeLoginDTO implements Serializable {

    @ApiModelProperty("用户名1")
    private String username;

    @ApiModelProperty("密码1")
    private String password;

}
