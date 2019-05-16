package com.shibi.app.interceptor;

import com.shibi.app.caches.SessionManager;
import com.shibi.app.caches.UserTokenCacheService;
import com.shibi.app.config.RedisConfiguration;
import com.shibi.app.dto.UserDetail;
import com.shibi.app.enums.Errors;
import com.shibi.app.enums.SecurityConstants;
import com.shibi.app.services.impl.UserRoleService;
import com.shibi.app.services.impl.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by User on 27/06/2018.
 */
@Component
public class InterceptorConfig extends HandlerInterceptorAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(InterceptorConfig.class);

    @Autowired
    private RedisConfiguration redisToken;

    @Autowired
    private UserTokenCacheService userTokenCacheService;

    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private UserService userService;

//    @Autowired
//    private LoginRequest loginRequest;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object Handler) throws Exception {

//        Optional<User> userObject = userService.findByUsername(loginRequest.getUsername());

//        User user = userObject.get();
        if("OPTIONS".equals(request.getMethod())){
            return true;
        }

        UserDetail userDetail = redisToken.decodeToken(request.getHeader(SecurityConstants.HEADER_STRING.getValue()));

        if(userDetail == null){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, Errors.UNKNOWN_USER.getValue());
            return false;
        }

        if(!redisToken.isValidUserSession(userDetail.getSessionId())){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, Errors.EXPIRED_SESSION.getValue());
            return false;
        }

        if(!redisToken.isValidUserToken(request.getHeader(SecurityConstants.HEADER_STRING.getValue()), userDetail.getSessionId())) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,Errors.EXPIRED_TOKEN.getValue());
        }

//        Role roles = new Role();
//        List<String> task = userTokenCacheService.getTask(userDetail.getSessionId(),request.getHeader(SecurityConstants.HEADER_STRING.getValue()));
//        List<String> role = user.getUserRoles().stream().map(r->r.getRole()).map(r->r.getName()).collect(Collectors.toList());
//        role.add("ADMIN");
        //confirm that the this user have the right to visit this url
//        if ((role === true)) {
//            return false;
//        }
//        if (!isUserAuthorized(task,request)){
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, Errors.NOT_PERMITTED.getValue());
//            return false;
//        }

        //update Session
        sessionManager.updateTimeout(userDetail.getSessionId());

        return true;
    }
}
