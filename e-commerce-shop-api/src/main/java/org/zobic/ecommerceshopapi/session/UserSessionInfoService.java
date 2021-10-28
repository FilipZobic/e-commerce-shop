package org.zobic.ecommerceshopapi.session;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserSessionInfoService {

  private final int MAX_NUMBER_OF_SESSIONS_PER_USER = 3;

  private UserSessionsInfoRepository userSessionsInfoRepository;

  public UserSessionInfoService(UserSessionsInfoRepository userSessionsInfoRepository) {
    this.userSessionsInfoRepository = userSessionsInfoRepository;
  }

  private boolean validateUserSessionInfo(UserSessionInfo userSessionInfo) {
    return userSessionInfo != null;
  }

  private boolean canNumberOfSessionsIncrease(UserSessionInfo userSessionInfo) {
    return userSessionInfo.getNumberOfSessions() < 3;
  }

  public boolean canNumberOfSessionsIncreaseForGivenUser(UUID userId) {
    UserSessionInfo userSessionInfo = userSessionsInfoRepository.getUserSessionsInfo(userId);
    return userSessionInfo == null || canNumberOfSessionsIncrease(userSessionInfo);
  }

  public UserSessionInfo addNewSession(Session newSession) {
    UserSessionInfo userSessionInfo = userSessionsInfoRepository.getUserSessionsInfo(newSession.getUserId());
    if (validateUserSessionInfo(userSessionInfo)) {
      if (canNumberOfSessionsIncrease(userSessionInfo)) {
        userSessionInfo.addNewSession(newSession);
        userSessionsInfoRepository.save(userSessionInfo);
        return userSessionInfo;
      } else {
        throw new RuntimeException(String.format("Maximum number of sessions: %s for user: %s has been reached",MAX_NUMBER_OF_SESSIONS_PER_USER ,newSession.getUserId()));
      }
    } else {
      UserSessionInfo newUserSessionInfo = new UserSessionInfo();
      newUserSessionInfo.setUserId(newSession.getUserId());
      newUserSessionInfo.addNewSession(newSession);
      userSessionsInfoRepository.save(newUserSessionInfo);
      return newUserSessionInfo;
    }
  }

  public void removeASession(Session sessionToRemove) {
    UserSessionInfo userSessionInfo = userSessionsInfoRepository.getUserSessionsInfo(sessionToRemove.getUserId());
    if (validateUserSessionInfo(userSessionInfo)) {
      if (userSessionInfo.hasSession(sessionToRemove.getId())) {
        userSessionInfo.removeSession(sessionToRemove.getId());
        if (userSessionInfo.sessionsIsEmpty()) {
          userSessionsInfoRepository.delete(sessionToRemove.getUserId());
        } else {
          userSessionsInfoRepository.save(userSessionInfo);
        }
      } else {
        throw new RuntimeException(String.format("User: %s does not have any session with given id: %s",sessionToRemove.getUserId(), sessionToRemove.getId()));
      }
    } else {
      throw new RuntimeException(String.format("User: %s does not have any active sessions",sessionToRemove.getUserId()));
    }
  }
}
