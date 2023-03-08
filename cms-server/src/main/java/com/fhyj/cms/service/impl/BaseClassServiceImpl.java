package com.fhyj.cms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fhyj.cms.bean.ActionResult;
import com.fhyj.cms.bean.ClassCustomBean;
import com.fhyj.cms.bean.ListInfo;
import com.fhyj.cms.bean.TreeBean;
import com.fhyj.cms.entity.BaseClass;
import com.fhyj.cms.mapper.BaseClassMapper;
import com.fhyj.cms.service.BaseClassService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author djdgt
 * @description 针对表【base_class(分类表)】的数据库操作Service实现
 * @createDate 2023-03-07 15:09:45
 */
@Service
@AllArgsConstructor
public class BaseClassServiceImpl extends ServiceImpl<BaseClassMapper, BaseClass>
        implements BaseClassService {

    private final BaseClassMapper classMapper;

    @Override
    public ActionResult listCustom() {
        List<ClassCustomBean> list = classMapper.listCustom();
        List<ClassCustomBean> levelOneClsList = iterateClassTree(list, 0);
        return ActionResult.ok(levelOneClsList);
    }

    private List<ClassCustomBean> iterateClassTree(List<ClassCustomBean> clsVoList, int pid) {
        List<ClassCustomBean> result = new ArrayList<>();
        for (ClassCustomBean clsVo : clsVoList) {
            int clsId = clsVo.getId();//获取菜单的id
            int parentId = clsVo.getParentId();//获取菜单的父id
            if (parentId == pid) {
                List<ClassCustomBean> iterateCls = iterateClassTree(clsVoList, clsId);
                if (iterateCls.size() > 0) {
                    clsVo.setChildren(iterateCls);
                }
                result.add(clsVo);
            }
        }
        return result;
    }

    @Override
    public boolean checkUniqueness(BaseClass baseClass) {
        return classMapper.checkUniqueness(baseClass) > 0;
    }

    @Override
    public ActionResult treeList() {
        List<ClassCustomBean> list = classMapper.listCustom();
        List<TreeBean> parentClassList = new ArrayList<>();
        List<TreeBean> childClassList = new ArrayList<>();
        for (ClassCustomBean baseClasses : list) {
            TreeBean treeBean = new TreeBean();
            treeBean.setId(baseClasses.getId());
            treeBean.setText(baseClasses.getName());
            treeBean.setParentId(baseClasses.getParentId());
            if (baseClasses.getParentId() == 0) {
                parentClassList.add(treeBean);
            } else {
                childClassList.add(treeBean);
            }

        }
        List<TreeBean> classesTreeList = new ArrayList<>();

        TreeBean cNode = new TreeBean();
        cNode.setId(0);
        cNode.setText("--顶级分类--");
        cNode.setChildren(parentClassList);
        cNode.setParentId(-1);
        cNode.setNodes(parentClassList);
        classesTreeList.add(cNode);
        TreeBean.iteraterMenus(parentClassList, childClassList);
        ListInfo<TreeBean> listInfo = new ListInfo<>();
        listInfo.setLength(classesTreeList.size());
        listInfo.setList(classesTreeList);
        return ActionResult.ok(listInfo);
    }
}




