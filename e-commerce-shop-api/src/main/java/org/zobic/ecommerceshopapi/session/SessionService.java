package org.zobic.ecommerceshopapi.session;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class SessionService {

  private static final long MINUTES_30_IN_MILLISECONDS = 36000L * 60000L;

  private UserDetailsService userDetailsService;

  private SessionRepository sessionRepository;

  private UserSessionInfoService userSessionInfoService;

  public SessionService(UserDetailsService userDetailsService, SessionRepository sessionRepository, UserSessionInfoService userSessionInfoService) {
    this.userDetailsService = userDetailsService;
    this.sessionRepository = sessionRepository;
    this.userSessionInfoService = userSessionInfoService;
  }

  public boolean sessionExists(String sessionId) {
    return sessionRepository.getSessionById(sessionId) != null;
  }

  public boolean isSessionExpired(Session session) {
    return new Date(System.currentTimeMillis()).after(session.getValidUntil());
  }

  public boolean isSessionValid(Session session) {
    return session != null && isSessionExpired(session);
  }

  private boolean isSessionNull(Session session) {
    return session == null;
  }

  public Date generateSessionTime() {
    return new Date(System.currentTimeMillis() + MINUTES_30_IN_MILLISECONDS);
  }

  public Session generateNewSession(String username, UUID userId) {
    if (!userSessionInfoService.canNumberOfSessionsIncreaseForGivenUser(userId)) {
      deleteOldestSessionForGivenUser(userId);
    }
    String sessionId = UUID.randomUUID().toString();
    Session session = new Session();
    session.setId(sessionId);
    session.setUserId(userId);
    session.setValidUntil(generateSessionTime());
    UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
    session.setUserDetails(userDetails);
    sessionRepository.save(session);
    userSessionInfoService.addNewSession(session);
    return session;
  }

  public Session getSessionById(String sessionId) {
    Session session = sessionRepository.getSessionById(sessionId);
    if (isSessionNull(session)) {
      return null;
    }
    if (isSessionExpired(session)) {
      deleteSession(session);
      return null;
    } else {
      session.setValidUntil(generateSessionTime());
      sessionRepository.save(session);
      return session;
    }
  }

  public Session updateSessionAndUserDetails(String sessionId, String username) { // kada username se promeni tamo gde updatujem security context
    Session session = this.getSessionById(sessionId);
    if (isSessionValid(session)){
      UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
      session.setUserDetails(userDetails);
      session.setValidUntil(generateSessionTime());
      sessionRepository.save(session);
      return session;
    }
    this.deleteSession(session);
    return null;
  }

  public void deleteSession(Session session) {
    userSessionInfoService.removeASession(session);
    sessionRepository.delete(session.getId());
  }

  private void deleteOldestSessionForGivenUser(UUID userId) {
    List<Session> sortedList = this.sessionRepository.getAllSessions().stream()
      .filter(s -> s.getUserId().equals(userId))
      .sorted(Comparator.comparing(Session::getValidUntil))
      .collect(Collectors.toList());
    Session session = sortedList.get(0);
    this.deleteSession(session);
  }
}
