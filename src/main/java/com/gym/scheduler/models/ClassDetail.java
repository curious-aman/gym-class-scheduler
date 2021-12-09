package com.gym.scheduler.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class ClassDetail {
    private String name;
    private ClassType type;
    private int frequency;
    private String trainer;
}
