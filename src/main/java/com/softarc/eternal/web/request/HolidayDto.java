package com.softarc.eternal.web.request;

import jakarta.validation.constraints.NotNull;

public record HolidayDto(
  @NotNull Long id,
  @NotNull String name,
  @NotNull String description
) {}
