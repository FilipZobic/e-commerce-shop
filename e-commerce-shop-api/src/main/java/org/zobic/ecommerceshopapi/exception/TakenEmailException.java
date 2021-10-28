package org.zobic.ecommerceshopapi.exception;

public class TakenEmailException extends Exception{
  public TakenEmailException() {
    super("Email is taken");
  }
}
