package com.gym.scheduler.models;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class Slot {
    private DayOfWeek dayOfWeek;
    private Integer roomId;
    private TimeOfDay timeOfDay;
    private LocalTime time;

    public Slot(DayOfWeek dayOfWeek, Integer roomId, TimeOfDay timeOfDay, LocalTime time) {
        this.dayOfWeek = dayOfWeek;
        this.roomId = roomId;
        this.timeOfDay = timeOfDay;
        this.time = time;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public TimeOfDay getTimeOfDay() {
        return timeOfDay;
    }

    public void setTimeOfDay(TimeOfDay timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
