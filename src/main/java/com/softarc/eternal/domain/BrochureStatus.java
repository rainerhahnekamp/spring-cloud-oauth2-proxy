package com.softarc.eternal.domain;

import com.softarc.eternal.domain.converter.BrochureStatusConverter;
import java.util.HashMap;
import java.util.Map;

public enum BrochureStatus {
  NONE("none"),
  REQUESTED("requested"),
  CONFIRMED("confirmed"),
  PRINTED("printed"),
  FAILED("failed");

  private final String code;

  BrochureStatus(String code) {
    this.code = code;
  }

  @Override
  public String toString() {
    return this.code;
  }

  public static BrochureStatus fromString(String code) {
    for (BrochureStatus brochureStatus : BrochureStatus.values()) {
      if (brochureStatus.code.equals(code)) {
        return brochureStatus;
      }
    }
    return null;
  }
}
