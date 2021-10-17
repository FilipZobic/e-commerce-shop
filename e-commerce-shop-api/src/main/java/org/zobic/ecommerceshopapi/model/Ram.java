package org.zobic.ecommerceshopapi.model;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum Ram {
  GB1(1),
  GB2(2),
  GB4(4),
  GB6(6),
  GB8(8),
  GB12(12),
  GB16(16),
  GB24(24),
  GB32(32),
  GB64(64),
  GB128(128);

  @Getter
  private final int size;

  Ram(int size) {
    this.size = size;
  }

  private static final Map<Integer, Ram> map = new HashMap<>(values().length, 1);

  static {
    for (Ram r : values()) map.put(r.size, r);
  }

  public static Ram of(Integer value) {
    Ram result = map.get(value);
    if (result == null) {
      throw new IllegalArgumentException("Invalid ram value: " + value);
    }
    return result;
  }
}
