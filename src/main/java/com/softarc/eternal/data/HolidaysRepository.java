package com.softarc.eternal.data;

import com.softarc.eternal.domain.Guide;
import com.softarc.eternal.domain.Holiday;
import com.softarc.eternal.domain.HolidayTrip;

import java.util.List;
import java.util.Optional;

public interface HolidaysRepository {
  List<Holiday> findAll();

  void add(String name, String description);

  void update(Long id, String name, String description);

  Optional<Holiday> find(Long id);

  void remove(Long id);

  void addTrip(Long holidayId, HolidayTrip holidayTrip);

  void assignGuide(Long id, Guide deborah);
}
