package com.idea.permission.service;

import com.google.common.base.Preconditions;
import com.idea.permission.dao.SysDeptDao;
import com.idea.permission.exception.ParamException;
import com.idea.permission.model.SysDept;
import com.idea.permission.param.DeptParam;
import com.idea.permission.util.BeanValidator;
import com.idea.permission.util.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SysDeptService {

    @Resource
    private SysDeptDao sysDeptDao;

    public void save(DeptParam param) {
        BeanValidator.check(param);
        if (checkExists(param.getParentId(), param.getName(), param.getId())) {
            throw new ParamException("同一层级下不能存在相同名称的部门");
        }
        SysDept dept = SysDept.builder().name(param.getName()).parentId(param.getParentId()).seq(param.getSeq()).remark(param.getRemark()).build();
        dept.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));
        dept.setOperator("system");
        dept.setOperatorIp("127.0.0.1");
        dept.setOperateTime(new Date());
        sysDeptDao.insertSelective(dept);
    }

    public void update(DeptParam param) {
        BeanValidator.check(param);
        if (checkExists(param.getParentId(), param.getName(), param.getId())) {
            throw new ParamException("同一层级下不能存在相同名称的部门");
        }
        SysDept oldDept = sysDeptDao.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(oldDept, "不存在该部门,无法更新");
        SysDept newDept = SysDept.builder().id(param.getId()).name(param.getName()).parentId(param.getParentId()).seq(param.getSeq()).remark(param.getRemark()).build();
        newDept.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));
        newDept.setOperator("system");
        newDept.setOperatorIp("127.0.0.1");
        newDept.setOperateTime(new Date());
        updateWithChild(oldDept, newDept);
    }

    @Transactional
    public void updateWithChild(SysDept oldDept, SysDept newDept) {
        String oldLevelPrefix = oldDept.getLevel();
        String newLevelPrefix = newDept.getLevel();
        if (!oldLevelPrefix.equals(newLevelPrefix)) {
            List<SysDept> deptList = sysDeptDao.getChildDeptListByLevel(oldLevelPrefix);
            if (CollectionUtils.isNotEmpty(deptList)) {
                for (SysDept dept : deptList) {
                    String level = dept.getLevel();
                    if (level.indexOf(oldLevelPrefix) == 0) {
                        level = newLevelPrefix + level.substring(oldLevelPrefix.length());
                        dept.setLevel(level);
                    }
                }
                sysDeptDao.batchUpdateLevel(deptList);
            }
        }
        sysDeptDao.updateByPrimaryKeySelective(newDept);
    }

    private boolean checkExists(Integer parentId, String deptName, Integer deptId) {
        return sysDeptDao.countByNameAndParentId(parentId, deptName, deptId) > 0;
    }

    private String getLevel(Integer deptId) {
        SysDept dept = sysDeptDao.selectByPrimaryKey(deptId);
        if (dept == null) {
            return null;
        }
        return dept.getLevel();
    }
}
