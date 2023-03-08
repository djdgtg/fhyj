package com.fhyj.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fhyj.cms.bean.ActionResult;
import com.fhyj.cms.bean.CustomSearchBean;
import com.fhyj.cms.bean.LogManageSearchBean;
import com.fhyj.cms.bean.PageInfo;
import com.fhyj.cms.entity.BaseLog;
import com.fhyj.cms.service.BaseLogService;
import com.fhyj.cms.mapper.BaseLogMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @author djdgt
 * @description 针对表【base_log(日志表)】的数据库操作Service实现
 * @createDate 2023-03-07 15:09:45
 */
@Service
@AllArgsConstructor
public class BaseLogServiceImpl extends ServiceImpl<BaseLogMapper, BaseLog>
        implements BaseLogService {

    private final BaseLogMapper logMapper;

    @Override
    public ActionResult logs(LogManageSearchBean logManageSearchBean, CustomSearchBean customSearchBean) {
        PageInfo<BaseLog> pageInfo = new PageInfo<>();
        LambdaQueryWrapper<BaseLog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BaseLog::getType, logManageSearchBean.getLogType())
                .eq(BaseLog::getCreator, logManageSearchBean.getUserName())
                .between(BaseLog::getCreateTime, logManageSearchBean.getStartTime(), logManageSearchBean.getEndTime());
        Page<BaseLog> page = logMapper.selectPage(new Page<>(customSearchBean.getStart(), customSearchBean.getLength()), queryWrapper);
        pageInfo.setRows(page.getRecords());
        pageInfo.setTotal(page.getTotal());
        return ActionResult.ok(pageInfo);
    }
}




