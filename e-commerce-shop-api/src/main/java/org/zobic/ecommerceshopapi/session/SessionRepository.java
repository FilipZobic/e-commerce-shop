package org.zobic.ecommerceshopapi.session;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SessionRepository {

  private final String REDIS_DB_NAME = "SESSION";

  private HashOperations hashOperations;

  private RedisTemplate redisTemplate;

  public SessionRepository(RedisTemplate redisTemplate) {
    this.hashOperations = redisTemplate.opsForHash();
    this.redisTemplate = redisTemplate;
  }

  public Session getSessionById(String sessionId) {
    return  (Session) hashOperations.get(REDIS_DB_NAME, sessionId);
  }

  public void save(Session session) {
    hashOperations.put(REDIS_DB_NAME, session.getId(), session);
  }

  public List<Session> getAllSessions() {
    return hashOperations.values(REDIS_DB_NAME); // add validation check
  }

  public void delete(String sessionId) {
    hashOperations.delete(REDIS_DB_NAME, sessionId);
  }
}
