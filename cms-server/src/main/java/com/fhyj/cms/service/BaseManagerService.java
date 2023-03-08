package com.fhyj.cms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fhyj.cms.bean.ManagerCustomBean;
import com.fhyj.cms.bean.ManagerManageSearchBean;
import com.fhyj.cms.entity.BaseManager;

import java.util.List;

/**
* @author djdgt
* @description 针对表【base_manager(用户表)】的数据库操作Service
* @createDate 2023-03-07 15:09:45
*/
public interface BaseManagerService extends IService<BaseManager> {

    BaseManager login(BaseManager baseManager);

    List<ManagerCustomBean> managers(ManagerManageSearchBean managers);

    boolean checkUniqueness(ManagerCustomBean managers);

    int saveManagerRole(Integer id, List<String> roleList);

    void updateBatch(String managerIds, Integer status);
}
