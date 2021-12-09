package com.gym.scheduler.models;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
@ToString
public class ScheduleResponse {
    List<Schedule> scheduleList;
    List<ClassDetail> excessClassDetailList;
}
