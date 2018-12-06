package com.idea.permission.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class DeptParam {

    private Integer id;

    @NotNull(message = "部门名称不能为空")
    @Length(min = 2, max = 15, message = "部门名称的长度应该为2~15个字符串")
    private String name;

    private Integer parentId = 0; //避免parentId为空

    @NotNull(message = "部门的展示顺序不可以为空")
    private Integer seq;

    @Length(max = 150, message = "备注的长度不能超过150个字符")
    private String remark;

}
