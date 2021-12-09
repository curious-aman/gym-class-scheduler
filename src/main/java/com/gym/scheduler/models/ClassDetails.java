package com.gym.scheduler.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class ClassDetails {
    List<ClassDetail> classDetailList;
}
