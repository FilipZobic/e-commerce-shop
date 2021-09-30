package org.zobic.ecommerceshopapi.model;

import lombok.Getter;

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
}
