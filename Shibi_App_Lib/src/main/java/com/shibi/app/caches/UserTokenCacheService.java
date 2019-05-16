package com.shibi.app.caches;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by User on 06/06/2018.
 */
@Repository
public class UserTokenCacheService {

    private static final String KEY = "userToken";
    private static final Logger LOG = LoggerFactory.getLogger(UserTokenCacheService.class);

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    private HashOperations hashOperations;

    @Value("${token-timeout}")
    private int tokenTimeout;

    @PostConstruct
    private void init(){
        hashOperations = redisTemplate.opsForHash();
    }


    public Object findUserToken(String userToken, String sessionId) {
        return hashOperations.get(KEY+sessionId, userToken);
    }


    public boolean saveUserToken(String userToken, String sessionId) {

        if (userToken == null){
            LOG.error("userToken is NULL...unable to save... returning false");
            return false;
        }
        List<String> tasks = new ArrayList<>();
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>() {}.getType();
        String json = gson.toJson(tasks, type);
        hashOperations.put(KEY+sessionId, userToken , json);
        redisTemplate.expire(KEY+sessionId, tokenTimeout, TimeUnit.DAYS);
        return true;
    }


    public boolean saveUserTokenAndTask(String sessionId, String userToken, List<String> tasks) {

        if (userToken == null){
            LOG.error("userToken is NULL...unable to save... returning false");
            return false;
        }

        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>() {}.getType();
        String json = gson.toJson(tasks, type);

        hashOperations.put(KEY+sessionId, userToken , json);
        redisTemplate.expire(KEY+sessionId, tokenTimeout, TimeUnit.DAYS);
        return true;

    }


    public List<String> getTask(String sessionId, String userToken) {

        Object task = hashOperations.get(KEY + sessionId, userToken);

        if (task != null){
            Gson gson = new Gson();
            Type type = new TypeToken<List<String>>() {}.getType();
            List<String> fromJson = gson.fromJson(String.valueOf(task), type);
            return fromJson;
        }

        return new ArrayList<>();
    }




    public void deleteUserToken(String userToken, String sessionId) {
        hashOperations.delete(KEY+sessionId, userToken);
    }



}
