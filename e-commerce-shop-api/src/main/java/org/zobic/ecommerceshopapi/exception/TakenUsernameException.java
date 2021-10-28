package org.zobic.ecommerceshopapi.exception;

public class TakenUsernameException extends Exception {
  public TakenUsernameException() {
    super("Username is taken");
  }
}
