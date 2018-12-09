package com.idea.permission.service;

import com.google.common.base.Preconditions;
import com.idea.permission.dao.SysUserDao;
import com.idea.permission.exception.ParamException;
import com.idea.permission.model.SysUser;
import com.idea.permission.param.UserParam;
import com.idea.permission.util.BeanValidator;
import com.idea.permission.util.MD5Util;
import com.idea.permission.util.PasswordUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class SysUserService {

    @Resource
    private SysUserDao sysUserDao;

    public void save(UserParam param) {
        BeanValidator.check(param);
        if (checkTelephoneExists(param.getTelephone(), param.getId())) {
            throw new ParamException("该号码已经被占用");
        }
        if (checkEmailExists(param.getEmail(), param.getId())) {
            throw new ParamException("该邮箱已使用");
        }
        //TODO : sendEmail
        //String password = PasswordUtil.generateRandomPassowrd();
        String password = "123456";
        SysUser user = SysUser.builder().username(param.getUsername()).telephone(param.getTelephone()).email(param.getEmail()).password(MD5Util.md5(password))
                .deptId(param.getDeptId()).status(param.getStatus()).remark(param.getRemark()).build();
        user.setOperator("system");
        user.setOperatorIp("127.0.0.1");
        user.setOperateTime(new Date());

        sysUserDao.insertSelective(user);
    }

    public void update(UserParam param) {
        BeanValidator.check(param);
        if (checkTelephoneExists(param.getTelephone(), param.getId())) {
            throw new ParamException("该号码已经被占用");
        }
        if (checkEmailExists(param.getEmail(), param.getId())) {
            throw new ParamException("该邮箱已使用");
        }
        SysUser oldUser = sysUserDao.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(oldUser);
        SysUser newUser = SysUser.builder().id(oldUser.getId()).username(param.getUsername()).telephone(param.getTelephone()).email(param.getEmail())
                .deptId(param.getDeptId()).status(param.getStatus()).remark(param.getRemark()).build();
        newUser.setOperator("system-update");
        newUser.setOperatorIp("127.0.0.1");
        newUser.setOperateTime(new Date());
        sysUserDao.updateByPrimaryKeySelective(newUser);
    }

    public boolean checkEmailExists(String mail, Integer id) {
        return sysUserDao.countByEmail(mail, id) > 0;
    }

    public boolean checkTelephoneExists(String telephone, Integer id) {
        return sysUserDao.countByTelephone(telephone, id) > 0;
    }

    public SysUser findUserByKeyword(String keyword) {
        return sysUserDao.findUserByKeyword(keyword);
    }
}
