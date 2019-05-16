package com.shibi.app.usermanagement.controller;

import com.shibi.app.caches.SessionManager;
import com.shibi.app.caches.UserLoginCacheService;
import com.shibi.app.caches.UserTokenCacheService;
import com.shibi.app.config.RedisConfiguration;
import com.shibi.app.dto.Response;
import com.shibi.app.dto.UserDetail;
import com.shibi.app.enums.Constants;
import com.shibi.app.enums.SecurityConstants;
import com.shibi.app.models.User;
import com.shibi.app.services.impl.RoleService;
import com.shibi.app.services.impl.UserService;
import com.shibi.app.utils.SecurityUtility;
import com.shibi.app.usermanagement.dto.LoginRequest;
import com.shibi.app.usermanagement.dto.LoginResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by User on 13/06/2018.
 */

@RestController
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserTokenCacheService userTokenCacheService;

    @Autowired
    private UserLoginCacheService userLoginCacheService;

    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private RedisConfiguration redisConfiguration;

    @Value("${encryption.salt}")
    private String salt;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest, HttpSession httpSession) throws Exception{
        Optional<User> userObject = userService.findByUsername(loginRequest.getUsername());

        if(!userObject.isPresent()) {
            return new ResponseEntity(new Response("invalid credentials"), HttpStatus.BAD_REQUEST);
        }

        User user = userObject.get();
        BCryptPasswordEncoder passwordEncoder = SecurityUtility.passwordEncoder();
        if(passwordEncoder.matches(loginRequest.getPassword(),user.getPassword())){

            List<String> roles = user.getUserRoles().stream().map(r->r.getRole()).map(r->r.getName()).collect(Collectors.toList());

            LoginResponse response = new LoginResponse();

            UserDetail userDetail = new UserDetail();
            userDetail.setId(user.getId());
            userDetail.setUsername(user.getUsername());
            userDetail.setFirstName(user.getFirstName());
            userDetail.setLastName(user.getLastName());
            userDetail.setEmail(user.getEmail());
            userDetail.setPhone(user.getPhone());
//            userDetail.setUserType(user.getUserType().getValue());
            userDetail.setSessionId(httpSession.getId());
            userDetail.setEnabled(user.isEnabled());
            userDetail.setRoles(roles);



            //create a token using jwt and redis
            String token = redisConfiguration.generateAndSaveTokenInRedis(userDetail);

            //update SessionId
            user.setSessionId(userDetail.getSessionId());
            setAsLoggedIn(user,userDetail.getSessionId());
            userService.save(user);

            response.setToken(token);

//            System.out.println(response.getToken());

            return new ResponseEntity<>(response,HttpStatus.OK);
        }

        return new ResponseEntity(new Response("invalid credentials"),HttpStatus.BAD_REQUEST);
    }

//    @RequestMapping(value = "/user/logout", method = RequestMethod.POST)
//    public ResponseEntity logout(){
//
//        SecurityContextHolder.clearContext();
//        return new ResponseEntity(new Response("Logout successful"), HttpStatus.OK);
//    }


//    @PostMapping(value = "/login")
//    public ResponseEntity login(@Valid @RequestBody LoginRequest loginRequest, HttpSession httpSession){
//
//        if(userLoginCacheService.isUserLogged(loginRequest.getUsername())){
//
//            //get the old user detail.
//            String userSession = userLoginCacheService.getLoggedUserSession(loginRequest.getUsername());
//
//            //update the login cache
//            userLoginCacheService.setUserAsNotLogged(loginRequest.getUsername());
//
//            //remove from redis
//            userTokenCacheService.deleteUserToken(userSession);
//
//            //delete from session cache
//            sessionManager.deleteSession(userSession);
//        }
//        LoginResponse response = new LoginResponse();
//
//
//        User userObject = userService.getByUsername(loginRequest.getUsername());
//        if(userObject == null){
//            return new ResponseEntity(new Response("Invalid Credentials"), HttpStatus.BAD_REQUEST);
//        }
//
//
//
//
//        BCryptPasswordEncoder passwordEncoder = SecurityUtility.passwordEncoder();
//        if(passwordEncoder.matches(loginRequest.getPassword(),userObject.getPassword())){
//
//            UserDetail userDetail = new UserDetail();
//            userDetail.setId(userObject.getId());
//            userDetail.setUsername(userObject.getUsername());
//            userDetail.setRole(userObject.getRole().getRoleName());
//            userDetail.setSessionId(httpSession.getId());
//            userDetail.setUserType(userObject.getUserType().getValue());
//
//            String token = "";
//
//
//            User user1 = userService.getByUserId(userObject.getId());
//            if(user1 == null){
//                return new ResponseEntity(new Response("This account has been disabled please contact Administrator"), HttpStatus.BAD_REQUEST);
//            }
//
//            token = redisConfiguration.generateToken(userDetail);
//            userTokenCacheService.saveUserToken(userDetail.getSessionId(),token);
//
//            //update SessionId
//            userObject.setSessionId(userDetail.getSessionId());
//            setAsLoggedIn(userObject,userDetail.getSessionId());
//            userService.save(userObject);
//
//
//
//            return ResponseEntity.ok(new LoginResponse(user1,token));
//
//        }
//
//        return new ResponseEntity (new Response("Invalid Credentials"), HttpStatus.BAD_REQUEST);
//
//    }
//
//    @RequestMapping(value = "/user/logout", method = RequestMethod.POST)
//    public ResponseEntity logout(){
//        SecurityContextHolder.clearContext();
//        return new ResponseEntity(new Response("Logout successful"), HttpStatus.OK);
//    }
//
////    @PostMapping(value = "/user/logout")
////    public ResponseEntity logOut(@RequestHeader("Authorization") String authorization, HttpSession httpSession ) {
////
////        LogOutResponse logOutResponse = new LogOutResponse();
////
////        if(id ==  null) {
////            logOutResponse.setStatus(false);
////            logOutResponse.setMessage(AuthError.INVALID_REQUEST.getValue());
////
////            return new ResponseEntity<>(logOutResponse, HttpStatus.BAD_REQUEST);
////        }
////
////        if(authorization == null) {
////            logger.error("authorization not found");
////            logOutResponse.setStatus(false);
////            logOutResponse.setMessage(AuthError.MISSING_TOKEN.getValue());
////
////            return new ResponseEntity<>(logOutResponse,HttpStatus.BAD_REQUEST);
////        }
////
////        UserDetail userDetail = redisConfiguration.decodeToken(authorization);
////
////        Object userToken = userTokenCacheService.findUserToken(authorization, userDetail.getSessionId());
////
////        if(userToken == null) {
////            logOutResponse.setStatus(false);
////            logOutResponse.setMessage(AuthError.EXPIRED_TOKEN.getValue());
////
////            return new ResponseEntity<>(logOutResponse,HttpStatus.NOT_ACCEPTABLE);
////        }
////
////        //logOut the user by removing the session id from redis
////        userTokenCacheService.deleteUserToken(authorization, userDetail.getSessionId());
////
////        httpSession.invalidate();
////        logOutResponse.setStatus(true);
////        logOutResponse.setMessage("You are successfully logged out");
////
////        return new ResponseEntity<>(logOutResponse, HttpStatus.OK);
////    }

    @RequestMapping(value = "/user/logout", method = RequestMethod.POST)
    public ResponseEntity logout(){
        SecurityContextHolder.clearContext();
        return new ResponseEntity(new Response("Logout successful"), HttpStatus.OK);
    }

//   @RequestMapping(value = "/user/logout", method = RequestMethod.GET)
//   public ResponseEntity logout(@ApiIgnore @RequestAttribute(Constants.USER_DETAIL) UserDetail userDetail, @ApiIgnore HttpServletRequest request){
//
//      //update login cache
//        userLoginCacheService.setUserAsNotLogged(userDetail.getUsername());
//
//      //remove from redis
//       System.out.println(SecurityConstants.HEADER_STRING.getValue());
//      userTokenCacheService.deleteUserToken(request.getHeader(SecurityConstants.HEADER_STRING.getValue()), userDetail.getSessionId());
//
//
//       return new ResponseEntity(new Response("Successfully logged out"), HttpStatus.OK);
//  }

//    @RequestMapping(value = "/logout", method = RequestMethod.GET)
//    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if(authentication != null) {
//            new SecurityContextLogoutHandler().logout(request,response,authentication);
//        }
//
//        return "redirect: /login?logout";
//    }
    private void setAsLoggedIn(User user, String sessionId){
        //set logged in to true
        userLoginCacheService.setUserAsLogged(user.getUsername(),sessionId);
    }
//}
}