package com.idea.permission.param;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class TestVo {

    @NotBlank
    private String msg;

    @NotNull
    @Max(value = 10, message = "id值不能大于10")
    private Integer id;
}
