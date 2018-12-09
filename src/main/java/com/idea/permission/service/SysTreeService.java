package com.idea.permission.service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.idea.permission.dao.SysDeptDao;
import com.idea.permission.dto.DeptLevelDto;
import com.idea.permission.model.SysDept;
import com.idea.permission.util.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;

@Service
public class SysTreeService {

    @Resource
    private SysDeptDao sysDeptDao;

    public List<DeptLevelDto> deptTree() {
        List<DeptLevelDto> dtoList = Lists.newArrayList();
        List<SysDept> deptList = sysDeptDao.getAllDepts();
        for (SysDept dept : deptList) {
            dtoList.add(DeptLevelDto.adapt(dept));
        }
        return deptListToTree(dtoList);
    }

    private List<DeptLevelDto> deptListToTree(List<DeptLevelDto> deptLevelDtoList) {
        if (CollectionUtils.isEmpty(deptLevelDtoList)) {
            return Lists.newArrayList();
        }
        //以level为key,以deptList为value
        Multimap<String, DeptLevelDto> levelDeptMap = ArrayListMultimap.create();
        List<DeptLevelDto> rootList = Lists.newArrayList();
        for (DeptLevelDto dto : deptLevelDtoList) {
            levelDeptMap.put(dto.getLevel(), dto);
            if (LevelUtil.ROOT.equals(dto.getLevel())) {
                rootList.add(dto);
            }
        }
        //按照seq从小到大排序
        rootList.sort(Comparator.comparingInt(DeptLevelDto::getSeq));
        transformDeptTree(rootList, LevelUtil.ROOT, levelDeptMap);
        return rootList;
    }

    private void transformDeptTree(List<DeptLevelDto> deptLevelDtoList, String level, Multimap<String, DeptLevelDto> levelDeptMap){
        for (DeptLevelDto deptLevelDto : deptLevelDtoList) {
            //处理当前层级的数据
            String nextLevel = LevelUtil.calculateLevel(level, deptLevelDto.getId());
            //处理下一层
            List<DeptLevelDto> tmpDeptList = (List<DeptLevelDto>) levelDeptMap.get(nextLevel);
            if (CollectionUtils.isNotEmpty(tmpDeptList)) {
                //排序
                tmpDeptList.sort(Comparator.comparingInt(DeptLevelDto::getSeq));
                //设置下一层部门
                deptLevelDto.setDeptList(tmpDeptList);
                //进入下一层处理
                transformDeptTree(tmpDeptList, nextLevel, levelDeptMap);
            }
        }
    }

}
