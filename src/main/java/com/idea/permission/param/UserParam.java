package com.idea.permission.param;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserParam {
    private Integer id;

    @NotBlank(message = "用户名不能为空")
    @Length(min = 2, max = 20, message = "用户名长度为2~20")
    private String username;

    @NotBlank(message = "电话号码的长度不能为空")
    @Length(min = 6, max = 13, message = "电话号码的长度需在6~13个之间")
    private String telephone;

    @NotBlank(message = "邮箱不能为空")
    @Length(min = 6, max = 50, message = "邮箱长度不能超过50个字符")
    private String email;

    @NotNull(message = "必须提供用户所在的部门")
    private Integer deptId;

    @NotNull(message = "必须指定用户的状态")
    @Min(value = 0, message = "用户状态不合法")
    @Max(value = 2, message = "用户状态不合法")
    private Integer status;

    @Length(min = 1, max = 200, message = "备注信息长度不能超过200个字符")
    private String remark;
}
