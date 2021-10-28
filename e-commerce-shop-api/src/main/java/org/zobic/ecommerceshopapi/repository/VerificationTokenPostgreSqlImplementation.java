package org.zobic.ecommerceshopapi.repository;

import org.springframework.stereotype.Repository;
import org.zobic.ecommerceshopapi.exception.ResourceNotFoundException;
import org.zobic.ecommerceshopapi.model.VerificationToken;
import org.zobic.ecommerceshopapi.repository.VerificationTokenPostgreSql;
import org.zobic.ecommerceshopapi.repository.VerificationTokenRepository;

import java.util.UUID;

@Repository
public class VerificationTokenPostgreSqlImplementation implements VerificationTokenRepository {

  private VerificationTokenPostgreSql verificationTokenPostgreSql;

  public VerificationTokenPostgreSqlImplementation(VerificationTokenPostgreSql verificationTokenPostgreSql) {
    this.verificationTokenPostgreSql = verificationTokenPostgreSql;
  }

  @Override
  public VerificationToken save(VerificationToken verificationToken) {
    return this.verificationTokenPostgreSql.save(verificationToken);
  }

  @Override
  public void delete(UUID id) {
    this.verificationTokenPostgreSql.deleteById(id);
  }

  @Override
  public VerificationToken findById(UUID id) throws ResourceNotFoundException {
    return this.verificationTokenPostgreSql.findById(id).orElseThrow(() -> new ResourceNotFoundException("Token does not exist"));
  }

  @Override
  public VerificationToken findByUserId(UUID id) {
    return this.verificationTokenPostgreSql.findByUserId(id);
  }
}
