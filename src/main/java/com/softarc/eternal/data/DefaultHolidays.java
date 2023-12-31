package com.softarc.eternal.data;

import com.softarc.eternal.domain.BrochureStatus;
import com.softarc.eternal.domain.Guide;
import com.softarc.eternal.domain.Holiday;
import com.softarc.eternal.domain.HolidayTrip;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class DefaultHolidays implements Holidays {

  private final List<Holiday> holidays = new ArrayList<>();
  private final OverlappingCalculator overlappingCalculator;
  private Long currentId = 3L;

  public DefaultHolidays(
    List<Holiday> holidays,
    OverlappingCalculator overlappingCalculator
  ) {
    this.holidays.addAll(holidays);
    this.overlappingCalculator = overlappingCalculator;
  }

  @Override
  public List<Holiday> findAll() {
    return this.holidays;
  }

  @Override
  public void add(String name, String description, Optional<String> optCover) {
    var holiday = new Holiday(
      this.currentId++,
      name,
      description,
      BrochureStatus.REQUESTED,
      optCover.orElse(""),
      new ArrayList<>()
    );
    this.holidays.add(holiday);
  }

  @Override
  public void update(
    Long id,
    String name,
    String description,
    Optional<String> optCover
  ) {
    var holiday = this.find(id).orElseThrow();
    holiday.setName(name);
    holiday.setDescription(description);
    holiday.setCoverPath(optCover.orElse(""));
  }

  @Override
  public Optional<Holiday> find(Long id) {
    for (Holiday holiday : this.holidays) {
      if (holiday.getId().equals(id)) {
        return Optional.of(holiday);
      }
    }

    return Optional.empty();
  }

  @Override
  public void remove(Long id) {
    this.holidays.removeIf(holiday -> holiday.getId().equals(id));
  }

  @Override
  public void addTrip(Long holidayId, HolidayTrip holidayTrip) {
    var holiday = this.find(holidayId).orElseThrow();
    holiday.getTrips().add(holidayTrip);
  }

  @Override
  public void assignGuide(Long holidayTripId, Guide guide) {
    var holidayTrip =
      this.findTripId(holidayTripId)
        .orElseThrow(() ->
          new RuntimeException(
            String.format("Cannot find Trip %s", guide.toString())
          )
        );

    this.findAll()
      .stream()
      .flatMap(holiday -> holiday.getTrips().stream())
      .filter(filterOverlappingTrip(holidayTripId, guide, holidayTrip))
      .findFirst()
      .ifPresent(trip -> this.throwAlreadyAssignedException(trip, guide));

    holidayTrip.setGuide(guide);
  }

  private Predicate<HolidayTrip> filterOverlappingTrip(
    Long holidayTripId,
    Guide guide,
    HolidayTrip holidayTrip
  ) {
    return trip ->
      trip.getGuide() != null &&
      !trip.getId().equals(holidayTripId) &&
      trip.getGuide().getId().equals(guide.getId()) &&
      this.isTripOverlapping(trip, holidayTrip);
  }

  private Optional<HolidayTrip> findTripId(Long holidayTripId) {
    return this.holidays.stream()
      .flatMap(holiday -> holiday.getTrips().stream())
      .filter(holidayTrip -> holidayTrip.getId().equals(holidayTripId))
      .findFirst();
  }

  private void throwAlreadyAssignedException(HolidayTrip trip, Guide guide) {
    throw new RuntimeException(
      String.format(
        "Guide %d already assigned to trip %d",
        guide.getId(),
        trip.getId()
      )
    );
  }

  private boolean isTripOverlapping(HolidayTrip trip1, HolidayTrip trip2) {
    return this.overlappingCalculator.isOverlapping(
        trip1.getFromDate(),
        trip1.getToDate(),
        trip2.getFromDate(),
        trip2.getToDate()
      );
  }
}
