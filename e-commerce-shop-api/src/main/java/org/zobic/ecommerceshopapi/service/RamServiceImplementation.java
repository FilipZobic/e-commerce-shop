package org.zobic.ecommerceshopapi.service;

import org.springframework.stereotype.Service;
import org.zobic.ecommerceshopapi.model.Ram;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class RamServiceImplementation implements RamService {

  public List<Integer> ramMemoryList = new ArrayList<>();

  @PostConstruct
  public void postConstruct() {
    Arrays.stream(Ram.values()).forEach(value -> {
      ramMemoryList.add(value.getSize());
    });
    Collections.sort(ramMemoryList);
  }

  @Override
  public boolean isRamValueSupported(int value) {
    return this.ramMemoryList.contains(value);
  }

  @Override
  public List<Integer> getAllRamValues() {
    return ramMemoryList;
  }
}
