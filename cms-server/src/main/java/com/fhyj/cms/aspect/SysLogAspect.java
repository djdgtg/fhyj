package com.fhyj.cms.aspect;

import com.fhyj.cms.entity.BaseLog;
import com.fhyj.cms.entity.BaseManager;
import com.fhyj.cms.service.BaseLogService;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


/**
 * @author qinc
 * @description
 * @date 2018/12/11
 */
@Aspect
@Component
public class SysLogAspect {

    @Autowired
    private BaseLogService logService;

    @Autowired
    private HttpServletRequest request; //自动注入request

    private static final Logger logger = LoggerFactory.getLogger(SysLogAspect.class);

    @Pointcut("@annotation(com.fhyj.cms.aspect.LogAnnotation)")
    public void actionAspect() {
    }

    @AfterReturning("actionAspect() && @annotation(logAnnotation)")
    public void afterReturn(LogAnnotation logAnnotation) {
        try {
            BaseManager loginUser = (BaseManager) request.getSession().getAttribute("USER_VALUE_OBJECT");
            String logType = logAnnotation.type();
            String logDetail = logAnnotation.detail();
            if (loginUser != null) {
                BaseLog log = new BaseLog();
                log.setType(Integer.valueOf(logType));
                log.setDetail(logDetail);
                log.setCreateTime(new Date());
                log.setCreator(Integer.valueOf(loginUser.getId()));
                logService.save(log);
            }
        } catch (Exception e) {
            logger.error("==后置通知异常==");
            logger.error("异常信息:{}", e.getMessage());
            e.printStackTrace();
        }
    }
}
