package org.zobic.ecommerceshopapi.service;

import java.util.List;

public interface RamService {

  boolean isRamValueSupported(int value);
  List<Integer> getAllRamValues();
}
