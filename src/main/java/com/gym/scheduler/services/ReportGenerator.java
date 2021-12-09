package com.gym.scheduler.services;

import com.gym.scheduler.models.Schedule;
import com.gym.scheduler.models.TimeOfDay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.DayOfWeek;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportGenerator {
    private static final Logger logger
            = LoggerFactory.getLogger(ReportGenerator.class);

    public static void generateReport(List<Schedule> scheduleList, String filePath) throws Exception {
        Map<DayOfWeek, List<Schedule>> everydaySchedule = scheduleList.stream().collect(Collectors.groupingBy(t -> t.getSlot().getDayOfWeek()));
        Map<DayOfWeek, List<Schedule>> everydayScheduleSorted = everydaySchedule.entrySet().stream().sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2, LinkedHashMap::new));
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath))) {
            bufferedWriter.append("Class Schedule:\n\n");
            everydayScheduleSorted.forEach((day, schedulesByDay) -> {
                Map<TimeOfDay, List<Schedule>> timeSchedule = schedulesByDay.stream().collect(Collectors.groupingBy(t -> t.getSlot().getTimeOfDay()));
                Map<TimeOfDay, List<Schedule>> timeScheduleSorted = timeSchedule.entrySet().stream().sorted(new Comparator<Map.Entry<TimeOfDay, List<Schedule>>>() {
                            @Override
                            public int compare(Map.Entry<TimeOfDay, List<Schedule>> o1, Map.Entry<TimeOfDay, List<Schedule>> o2) {
                                return o1.getKey().getStart().compareTo(o2.getKey().getStart());
                            }
                        })
                        .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue(), (e1, e2) -> e2, LinkedHashMap::new));
                timeScheduleSorted.forEach((timeOfDay, schedulesByTime) -> {
                    Map<Integer, List<Schedule>> roomSchedule = schedulesByDay.stream().collect(Collectors.groupingBy(t -> t.getSlot().getRoomId()));
                    Map<Integer, List<Schedule>> roomScheduleSorted = roomSchedule.entrySet().stream().sorted(Map.Entry.comparingByKey())
                            .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2, LinkedHashMap::new));
                    roomScheduleSorted.forEach((room, schedulesByRoom) -> {
                        try {
                            String roomColour = room == 1 ? "RED" : "BLUE";
                            bufferedWriter.append(day + ",Room " + roomColour + ", " + timeOfDay.getStart() + "-" + timeOfDay.getEnd() + "\n");
                            schedulesByRoom.sort(new Comparator<Schedule>() {
                                @Override
                                public int compare(Schedule o1, Schedule o2) {
                                    return o1.getSlot().getTime().compareTo(o2.getSlot().getTime());
                                }
                            });
                            schedulesByRoom.forEach(a -> {
                                try {
                                    bufferedWriter.append(a.getSlot().getTime() + " - " + a.getClassDetail().getName() + " - " + a.getClassDetail().getType() + " - by " + a.getClassDetail().getTrainer() + "\n");
                                } catch (IOException e) {
                                    logger.error("Error while writing report data" + e.getCause());
                                }
                            });
                        } catch (IOException e) {
                            logger.error("Error while writing report data" + e.getCause());
                        }
                    });
                });
                try {
                    bufferedWriter.append("- - - - -\n");
                } catch (IOException e) {
                    logger.error("Error while writing report data" + e.getCause());
                }
            });
        } catch (Exception e) {
            logger.error("Error while generating report");
            throw new Exception(e.getMessage());
        }
        logger.info("** Generated a Schedule and saved at {}} **", filePath);
    }
}
