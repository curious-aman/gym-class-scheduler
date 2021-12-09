package com.gym.scheduler.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {
    private Slot slot;
    private ClassDetail classDetail;
}
