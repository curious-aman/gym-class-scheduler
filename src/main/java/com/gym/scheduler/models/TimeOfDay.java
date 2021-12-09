package com.gym.scheduler.models;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalTime;

@ToString
@EqualsAndHashCode
public class TimeOfDay {
    private LocalTime start;
    private LocalTime end;

    public TimeOfDay(LocalTime start, LocalTime end) {
        this.start = start;
        this.end = end;
    }

    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }
}
