package com.fhyj.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fhyj.cms.bean.ActionResult;
import com.fhyj.cms.bean.ManagerCustomBean;
import com.fhyj.cms.bean.ManagerManageSearchBean;
import com.fhyj.cms.entity.BaseManager;
import com.fhyj.cms.service.BaseManagerService;
import com.fhyj.cms.mapper.BaseManagerMapper;
import com.fhyj.cms.util.MD5Util;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author djdgt
 * @description 针对表【base_manager(用户表)】的数据库操作Service实现
 * @createDate 2023-03-07 15:09:45
 */
@Service
@AllArgsConstructor
public class BaseManagerServiceImpl extends ServiceImpl<BaseManagerMapper, BaseManager>
        implements BaseManagerService {

    private final BaseManagerMapper managerMapper;

    @Override
    public BaseManager login(BaseManager baseManager) {
        String password = MD5Util.MD5ToDepth(baseManager.getPassword()).toUpperCase();
        return managerMapper.selectOne(new LambdaQueryWrapper<BaseManager>().eq(BaseManager::getUsername, baseManager.getUsername())
                .eq(BaseManager::getPassword, password));
    }

    @Override
    public List<ManagerCustomBean> managers(ManagerManageSearchBean managers) {
        return null;
    }

    @Override
    public boolean checkUniqueness(ManagerCustomBean managers) {
        return baseMapper.selectCount(getWrapper(managers)) > 0;
    }

    private Wrapper<BaseManager> getWrapper(ManagerCustomBean managers) {
        LambdaQueryWrapper<BaseManager> wrapper = new LambdaQueryWrapper<>();
        if (managers.getId() != null) {
            wrapper.eq(BaseManager::getId, managers.getId());
        }
        if (StringUtils.hasLength(managers.getUsername())) {
            wrapper.eq(BaseManager::getUsername, managers.getUsername());
        }
        if (managers.getStatus() != null) {
            wrapper.eq(BaseManager::getStatus, managers.getStatus());
        }
        return wrapper;
    }

    @Override
    public int saveManagerRole(Integer id, List<String> roleList) {
        return 0;
    }

    @Override
    public void updateBatch(String managerIds, Integer status) {
        String[] ids = managerIds.split(",");
        List<BaseManager> managerList = managerMapper.selectBatchIds(Arrays.asList(ids));
        for (BaseManager baseManager : managerList) {
            if (baseManager.getStatus() == null) {
                baseManager.setPassword("689EE787E0EA220E6D5A72163EB8C437");//默认123456
            } else {
                baseManager.setStatus(status);//锁定解锁
            }
            managerMapper.updateById(baseManager);
        }
    }
}




