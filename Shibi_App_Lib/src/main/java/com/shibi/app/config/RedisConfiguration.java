package com.shibi.app.config;

import com.google.gson.Gson;
import com.shibi.app.caches.SessionManager;
import com.shibi.app.caches.UserTokenCacheService;
import com.shibi.app.dto.UserDetail;
import com.shibi.app.enums.SecurityConstants;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by User on 05/06/2018.
 */
@Service
public class RedisConfiguration {

    @Autowired
    private UserTokenCacheService userTokenCacheService;

    @Autowired
    private SessionManager sessionManager;


    private static final Logger LOG = LoggerFactory.getLogger(UserTokenCacheService.class);

    public String generateToken(Object object){

        //generate token
        String token = Jwts.builder()
                .setPayload(new Gson().toJson(object))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET.getValue())
                .compact();

        return SecurityConstants.TOKEN_PREFIX.getValue()+" "+ token;
    }

    public UserDetail decodeToken(String token){

        if (token != null) {

            // parse the token.
            Claims userDetailString = null;
            try {
                userDetailString = Jwts.parser()
                        .setSigningKey(SecurityConstants.SECRET.getValue())
                        .parseClaimsJws(token.trim().replace(" ","").replace(SecurityConstants.TOKEN_PREFIX.getValue(), ""))
                        .getBody();
            } catch (ExpiredJwtException e) {
                LOG.error("Unable to parse token", e);
                return null;
            } catch (UnsupportedJwtException e) {
                LOG.error("Unable to parse token", e);
                return null;
            } catch (MalformedJwtException e) {
                LOG.error("Unable to parse token", e);
                return null;
            } catch (SignatureException e) {
                LOG.error("Unable to parse token", e);
                return null;
            } catch (IllegalArgumentException e) {
                LOG.error("Unable to parse token", e);
                return null;
            } catch (Exception e){
                LOG.error("Unable to parse token", e);
                return null;
            }

            UserDetail userDetail = new UserDetail();
            userDetail.setId(Long.valueOf(String.valueOf(userDetailString.get("id"))));
            userDetail.setUsername(userDetailString.get("username", String.class));
            userDetail.setEmail(userDetailString.get("email", String.class));
            userDetail.setFirstName(userDetailString.get("firstName", String.class));
            userDetail.setLastName(userDetailString.get("lastName", String.class));
            userDetail.setPhone(userDetailString.get("phoneNumber", String.class));
//            userDetail.setGeneratedPassword(userDetailString.get("generatedPassword", Boolean.class));
            userDetail.setEnabled(userDetailString.get("enabled", Boolean.class));
            userDetail.setSessionId(userDetailString.get("sessionId", String.class));
            userDetail.setRoles((List<String>) userDetailString.get("roles"));

            if (userDetail.getId() != null) {
                return userDetail;
            }

            return null;
        }

        return null;
    }

    public boolean isValidUserToken(String userToken, String sessionId){

        Object token = userTokenCacheService.findUserToken(userToken, sessionId);
        return token != null;
    }

    public boolean isValidUserSession(String sessionId){
        return sessionManager.isValidateSession(sessionId);
    }

    public String generateAndSaveTokenInRedis(UserDetail userDetail){

        //generate token
        String token = Jwts.builder()
                .setPayload(new Gson().toJson(userDetail))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET.getValue())
                .compact();

        //save in redis
        if (userTokenCacheService.saveUserToken(SecurityConstants.TOKEN_PREFIX.getValue() +" "+ token, userDetail.getSessionId())){
//            logger.info("User token saved : {}", token);
        }

        return SecurityConstants.TOKEN_PREFIX.getValue()+" "+ token;
    }

}
