package com.rongdu.loans.sys.interceptor;

import com.alibaba.dubbo.common.json.JSON;
import com.google.common.collect.Maps;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.JedisUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.cust.vo.CustUserVO;
import com.rongdu.loans.sys.annotation.LockAndSyncRequest;
import com.rongdu.loans.sys.web.ApiResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

/**
 * Created by lee on 2018/5/4.
 */
public class LockRequestInterceptor implements HandlerInterceptor {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        try {
            HttpSession session = httpServletRequest.getSession();
            boolean lockedRequest = lockedRequest(httpServletRequest, session, o);
            if (!lockedRequest) {
                ApiResult result = new ApiResult();
                result.setCode("FAIL");
                result.setMsg("请勿重复点击");
                httpServletResponse.setContentType("application/json");
                httpServletResponse.setCharacterEncoding("UTF-8");
                httpServletResponse.getWriter().write(JSON.json(result));
                return false;
            }
        } catch (Exception e) {
            logger.error("【LockRequestInterceptor.preHandle】异常");
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception exception) throws Exception {
        try {
            HttpSession session = httpServletRequest.getSession();
            Map<String, Object> map = getLockRedisKeyAndSeconds(httpServletRequest, session, o);
            if (map != null && !map.isEmpty()) {
                String lockRedisKey = (String) map.get("redisKey");
                if (StringUtils.isNotEmpty(lockRedisKey)) {
                    JedisUtils.del(lockRedisKey);
                }
            }
        } catch (Exception e) {
            logger.error("【LockRequestInterceptor.afterCompletion】异常");
        }
    }

    private boolean lockedRequest(HttpServletRequest request, HttpSession session, Object handler) {
        boolean lockRequest = true;
        Map<String, Object> map = getLockRedisKeyAndSeconds(request, session, handler);
        if (map != null && !map.isEmpty()) {
            String lockRedisKey = (String) map.get("redisKey");
            Integer seconds = (Integer) map.get("seconds");
            if (StringUtils.isNotEmpty(lockRedisKey)) {
                Long lockKeyResult = JedisUtils.setNxAndEx(lockRedisKey,
                        DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"), seconds);
                if (lockKeyResult == null || lockKeyResult <= 0L) {
                    lockRequest = false;
                }
            }
        }
        return lockRequest;
    }

    private Map<String, Object> getLockRedisKeyAndSeconds(HttpServletRequest request, HttpSession session,
                                                          Object handler) {
        Map<String, Object> map = Maps.newHashMap();
        String redisKey = null;
        Method method = null;
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            method = handlerMethod.getMethod();
        }
        if (method != null) {
            LockAndSyncRequest lockAnnotation = method.getAnnotation(LockAndSyncRequest.class);
            if (lockAnnotation != null && lockAnnotation.locked()) {
                String redisKeyPre = lockAnnotation.redisKeyPre();
                String redisKeyAfterByRequestName = lockAnnotation.redisKeyAfterByRequestName();
                int seconds = lockAnnotation.seconds();
                StringBuilder redisKeyAfterSB = new StringBuilder();
                if (StringUtils.isNotEmpty(redisKeyAfterByRequestName)) {
                    redisKeyAfterSB.append(request.getParameter(redisKeyAfterByRequestName));
                }
                if (StringUtils.isEmpty(redisKeyAfterSB)) {
                    Subject subject = SecurityUtils.getSubject();
                    CustUserVO shiroUser = (CustUserVO) subject.getPrincipal();
                    String userId = shiroUser.getId();
                    if (userId != null) {
                        redisKeyAfterSB.append("b").append(userId);
                    }
                }
                if (StringUtils.isEmpty(redisKeyAfterSB)) {
                    redisKeyAfterSB.append("s").append(session.getId());
                }
                String redisKeyAfter = redisKeyAfterSB.toString();
                if (StringUtils.isNotEmpty(redisKeyAfter)) {
                    redisKey = redisKeyPre + redisKeyAfter;
                }
                map.put("seconds", seconds);
                map.put("redisKey", redisKey);
            }
        }
        return map;
    }
}
