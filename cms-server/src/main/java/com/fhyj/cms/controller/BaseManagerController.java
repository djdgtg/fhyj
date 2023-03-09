package com.fhyj.cms.controller;

import com.fhyj.cms.aspect.LogType;
import com.fhyj.cms.bean.ActionResult;
import com.fhyj.cms.bean.ManagerCustomBean;
import com.fhyj.cms.bean.ManagerManageSearchBean;
import com.fhyj.cms.entity.BaseLog;
import com.fhyj.cms.entity.BaseManager;
import com.fhyj.cms.service.BaseLogService;
import com.fhyj.cms.service.BaseManagerService;
import com.fhyj.cms.util.MD5Util;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * @author qinc
 * @description
 * @date 2018/12/11
 */
@Controller
@RequestMapping("/manager")
public class BaseManagerController {

    @Autowired
    private BaseManagerService managerService;

    @Autowired
    private BaseLogService logService;

    @PostMapping("checkLogin")
    @ResponseBody
    public ActionResult login(BaseManager baseManager, HttpServletRequest request) {
        BaseManager result = managerService.login(baseManager);
        if (result == null) {
            return ActionResult.build(-1, "用户名或密码错误，请重新登录！");
        }
        // 正常初始化数据
        BaseManager loginUser = new BaseManager();
        loginUser.setId(result.getId());
        loginUser.setLastLoginTime(result.getLastLoginTime());
        request.getSession().setAttribute("USER_VALUE_OBJECT", loginUser);
        // 更新用户登录信息
        result.setLastLoginTime(new Date());
        managerService.updateById(result);
        //重定向到主页面的跳转方法
        return ActionResult.ok();
    }

    @RequestMapping("/loadLoginUser")
    @ResponseBody
    public BaseManager loadLoginUser(HttpServletRequest request) {
        BaseManager loginUser = (BaseManager) request.getSession().getAttribute("USER_VALUE_OBJECT");
        BaseManager baseManagers = managerService.getById(loginUser.getId());
        if (baseManagers != null) {
            baseManagers.setLastLoginTime(loginUser.getLastLoginTime());
        }
        return baseManagers;
    }

    @RequestMapping("/list")
    @ResponseBody
    public List<ManagerCustomBean> list(ManagerManageSearchBean managers) {
        return managerService.managers(managers);
    }

    @RequestMapping("/add")
    @ResponseBody
    public ActionResult add(ManagerCustomBean managers, HttpServletRequest request) {
        if (getManager(managers, request)) {
            return ActionResult.build(400, "该管理员已存在,请输入其他管理员用户名!");
        }
        boolean save = managerService.save(managers);
        return getActionResult(managers, save);

    }

    private boolean getManager(ManagerCustomBean managers, HttpServletRequest request) {
        if (managerService.checkUniqueness(managers)) {
            return true;
        }
        if (!StringUtils.hasLength(managers.getPassword())) {
            managers.setPassword("689EE787E0EA220E6D5A72163EB8C437");//默认123456
        } else {
            managers.setPassword(MD5Util.MD5ToDepth(managers.getPassword()));
        }
        Date now = new Date();
        managers.setCreateTime(now);
        managers.setModifyTime(now);
        BaseManager loginUser = (BaseManager) request.getSession().getAttribute("USER_VALUE_OBJECT");

        managers.setCreator(loginUser.getId());
        managers.setModifier(loginUser.getId());
        managers.setStatus(1);
        return false;
    }

    @RequestMapping("/update")
    @ResponseBody
    public ActionResult update(ManagerCustomBean managers, HttpServletRequest request) {
        if (getManager(managers, request)) {
            return ActionResult.build(400, "该管理员已存在,请输入其他管理员用户名!");
        }
        boolean update = managerService.updateById(managers);
        return getActionResult(managers, update);
    }

    @NotNull
    private ActionResult getActionResult(ManagerCustomBean managers, boolean update) {
        if (update) {
            String roleIds = managers.getRoleIdsStr();
            List<String> roleList = Arrays.asList(roleIds.split(","));
            // 插入管理员角色信息
            int res = managerService.saveManagerRole(managers.getId(), roleList);
            if (res > 0) {
                return ActionResult.ok();
            } else {
                return ActionResult.build(400, "操作失败，请重试！");
            }
        } else {
            return ActionResult.build(400, "操作失败，请重试！");
        }
    }

    @RequestMapping("/delBatch")
    @ResponseBody
    public ActionResult delBatch(String managerIds) {
        List<String> ids = Arrays.asList(managerIds.split(","));
        managerService.removeBatchByIds(ids);
        return ActionResult.ok();
    }

    @RequestMapping("/updateBatch")
    @ResponseBody
    public ActionResult updateBatch(String managerIds, Integer status) {
        managerService.updateBatch(managerIds, status);
        return ActionResult.ok();
    }

    @RequestMapping("/logout")
    @ResponseBody
    public ActionResult logout(HttpServletRequest request) {
        BaseManager loginUser = (BaseManager) request.getSession().getAttribute("USER_VALUE_OBJECT");
        if (loginUser != null) {
            BaseLog logs = new BaseLog();
            logs.setCreateTime(new Date());
            logs.setDetail("用户退出");
            logs.setCreator(loginUser.getId());
            logs.setType(Integer.valueOf(LogType.OPERATION_1));
            logService.save(logs);
        }
        request.getSession().invalidate();
        return ActionResult.ok();
    }

    @RequestMapping("/updatePassword")
    @ResponseBody
    public ActionResult updatePassword(ManagerManageSearchBean managersManageSearchBean, HttpServletRequest request) {
        BaseManager baseManager = managerService.getById(managersManageSearchBean.getUserId());
        if (baseManager == null) {
            return ActionResult.build(-1, "当前用户不存在!");
        } else {
            String oldPassword = managersManageSearchBean.getOldPassword();
            String mdOldPassword = MD5Util.MD5ToDepth(oldPassword).toUpperCase();
            if (mdOldPassword.equals(baseManager.getPassword())) {
                BaseManager newManager = new BaseManager();
                Date now = new Date();
                newManager.setModifyTime(now);
                BaseManager loginUser = (BaseManager) request.getSession().getAttribute("USER_VALUE_OBJECT");
                newManager.setModifier(loginUser.getId());
                String md5Password = MD5Util.MD5ToDepth(managersManageSearchBean.getPassword()).toUpperCase();
                newManager.setPassword(md5Password);
                newManager.setId(baseManager.getId());
                managerService.updateById(newManager);
            } else {
                return ActionResult.build(-1, "当前密码错误，请重新输入!");
            }
        }
        return ActionResult.ok();
    }

    @RequestMapping("/updateProfile")
    @ResponseBody
    public ActionResult updateProfile(BaseManager baseManager, HttpServletRequest request) {
        Date now = new Date();
        baseManager.setLastLoginTime(now);
        BaseManager loginUser = (BaseManager) request.getSession().getAttribute("USER_VALUE_OBJECT");
        baseManager.setModifier(loginUser.getId());
        managerService.updateById(baseManager);
        return ActionResult.ok();
    }

}
