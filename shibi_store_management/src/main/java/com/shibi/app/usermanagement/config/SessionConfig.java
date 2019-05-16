package com.shibi.app.usermanagement.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.RedisFlushMode;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

/**
 * Created by User on 07/06/2018.
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 600, redisNamespace = "mysession", redisFlushMode = RedisFlushMode.IMMEDIATE)
public class SessionConfig extends AbstractHttpSessionApplicationInitializer {
}
