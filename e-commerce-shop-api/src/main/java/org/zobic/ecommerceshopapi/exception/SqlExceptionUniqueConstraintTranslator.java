package org.zobic.ecommerceshopapi.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.support.SQLExceptionTranslator;

import java.sql.SQLException;

public class SqlExceptionUniqueConstraintTranslator implements SQLExceptionTranslator {

  @Override
  public DataAccessException translate(String s, String s1, SQLException e) {
    if (e.getErrorCode() == 23505) {
      System.out.println("DOING SOME STUFF BOI");
    }
    return new DuplicateKeyException(
      "Custom Exception translator - Integrity constraint violation.", e);
  }
}
