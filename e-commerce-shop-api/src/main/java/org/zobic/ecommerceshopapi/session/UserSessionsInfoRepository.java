package org.zobic.ecommerceshopapi.session;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class UserSessionsInfoRepository {

  private final String USER_DB_SESSION_INFO = "USER_SESSION_INFO";

  private HashOperations hashOperations;

  private RedisTemplate redisTemplate;

  public UserSessionsInfoRepository(RedisTemplate redisTemplate) {
    this.hashOperations = redisTemplate.opsForHash();
    this.redisTemplate = redisTemplate;
  }


  public UserSessionInfo getUserSessionsInfo(UUID userId) {
    return  (UserSessionInfo) hashOperations.get(USER_DB_SESSION_INFO, userId);
  }

  public void save(UserSessionInfo userSessionInfo) {
    hashOperations.put(USER_DB_SESSION_INFO, userSessionInfo.getUserId(), userSessionInfo);
  }

  public List<Session> getAllUserSessionInfo() {
    return hashOperations.values(USER_DB_SESSION_INFO);
  }

  public void delete(UUID userId) {
    hashOperations.delete(USER_DB_SESSION_INFO, userId);
  }
}
