package org.zobic.ecommerceshopapi.repository;

import org.zobic.ecommerceshopapi.exception.ResourceNotFoundException;
import org.zobic.ecommerceshopapi.model.VerificationToken;

import java.util.UUID;

public interface VerificationTokenRepository {
  VerificationToken save(VerificationToken verificationToken);

  void delete(UUID id);

  VerificationToken findById(UUID id) throws ResourceNotFoundException;

  VerificationToken findByUserId(UUID id);
}
