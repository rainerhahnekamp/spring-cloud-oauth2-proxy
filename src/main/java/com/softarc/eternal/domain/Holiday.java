package com.softarc.eternal.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Holiday {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @NotBlank
  @Size(min = 3)
  @Pattern(regexp = "\\w*")
  private String name;

  @NotBlank
  private String description;

  @Column(name = "COVERPATH")
  private String coverPath;

  @Builder.Default
  @Transient
  private Set<HolidayTrip> trips = new HashSet<>();
}
