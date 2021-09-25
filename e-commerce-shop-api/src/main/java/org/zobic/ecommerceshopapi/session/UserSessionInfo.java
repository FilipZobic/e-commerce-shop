package org.zobic.ecommerceshopapi.session;

import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class UserSessionInfo implements Serializable {

  private UUID userId;

  private int numberOfSessions = 0;

  private Set<String> sessionIds = new HashSet<>();

  public void addNewSession(Session newSession) {
    numberOfSessions++;
    sessionIds.add(newSession.getId());
  }

  public boolean hasSession(String id) {
    return sessionIds.contains(id);
  }

  public void removeSession(String id) {
    numberOfSessions--;
    sessionIds.remove(id);
  }

  public boolean sessionsIsEmpty() {
    return sessionIds.isEmpty();
  }
}
