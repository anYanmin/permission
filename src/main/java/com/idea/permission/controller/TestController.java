package com.idea.permission.controller;

import com.idea.permission.common.ApplicationContextHelper;
import com.idea.permission.common.JsonData;
import com.idea.permission.dao.SysAclModuleDao;
import com.idea.permission.exception.ParamException;
import com.idea.permission.model.SysAclModule;
import com.idea.permission.param.TestVo;
import com.idea.permission.util.BeanValidator;
import com.idea.permission.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/hello.page")
    @ResponseBody
    public JsonData hello() {
        log.info("hello");
        throw new RuntimeException("PermissionException");
        //return JsonData.success("hello permission");
    }

    @RequestMapping("/testVo.json")
    @ResponseBody
    public JsonData validate(TestVo vo) throws ParamException{
        log.info("validate");
        BeanValidator.check(vo);
        return JsonData.success("validate");
    }

    @RequestMapping("/test.json")
    @ResponseBody
    public JsonData test() throws ParamException{
        log.info("test");
        SysAclModuleDao dao = ApplicationContextHelper.popBean(SysAclModuleDao.class);
        SysAclModule module = dao.selectByPrimaryKey(1);
        log.info(JsonMapper.objectToString(module));
        return JsonData.success("validate");
    }
}
