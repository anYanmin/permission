package com.idea.permission.dao;

import com.idea.permission.model.SysUser;
import org.apache.ibatis.annotations.Param;

public interface SysUserDao {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    int countByEmail(@Param("email") String email, @Param("id") Integer id);

    int countByTelephone(@Param("telephone") String telephone, @Param("id") Integer id);

    SysUser findUserByKeyword(@Param("keyword") String keyword);
}