package com.gym.scheduler.services;

import com.gym.scheduler.models.*;
import io.vavr.control.Either;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Scheduler {
    private ScheduleResponse scheduleResponse;
    private static final int NUM_OF_DAYS = 5;
    private static final TimeOfDay lunch = new TimeOfDay(LocalTime.of(11, 0), LocalTime.of(13, 0));
    private static final TimeOfDay postOffice = new TimeOfDay(LocalTime.of(18, 0), LocalTime.of(20, 0));

    public ScheduleResponse schedule(List<ClassDetail> classes) {
        List<Slot> allSlots = new ArrayList<>();
        for (int i = 1; i <= NUM_OF_DAYS; i++) {
            int finalI = i;
            Stream.of(Room.RED, Room.BLUE).forEach(room -> Stream.of(lunch, postOffice).forEach(timeOfDay -> allSlots.add(new Slot(DayOfWeek.of(finalI), room, timeOfDay, timeOfDay.getStart()))));
        }
        generateRandomSchedule(classes, allSlots, null, null);
        return scheduleResponse;
    }

    public void generateRandomSchedule(List<ClassDetail> classDetailList,
                                                   List<Slot> availableSlots,
                                                   List<Schedule> schedules,
                                                   List<ClassDetail> excessClasses
    ) {
        if (excessClasses == null) {
            excessClasses = new ArrayList<>();
        }
        if (schedules == null) {
            schedules = new ArrayList<>();
        }
        if (classDetailList == null || classDetailList.size()==0) {
            scheduleResponse = new ScheduleResponse(schedules, excessClasses);
        } else {
            List<ClassDetail> head = classDetailList.subList(0, 1);
            List<ClassDetail> tail = null;
            if(classDetailList.size()>1) {
                tail = classDetailList.subList(1, classDetailList.size());
            }
            Either<Boolean, Slot> resultE = randomSlotForClass(head.get(0).getType().getDuration(), availableSlots);

            if (resultE.isLeft() && !resultE.getLeft()) {
                excessClasses.addAll(head);
                generateRandomSchedule(tail, availableSlots, schedules, excessClasses);
            } else {
                List<ClassDetail> leftOverList;
                int frequency = head.get(0).getFrequency();
                if (frequency == 1) {
                    leftOverList = tail;
                } else {
                    leftOverList = new ArrayList<>();
                    List<ClassDetail> copy = new ArrayList<>(head);
                    copy.get(0).setFrequency(frequency - 1);
                    if(tail !=null)
                        leftOverList.addAll(tail);

                    leftOverList.addAll(copy);
                }

                Slot slot = resultE.get();
                Slot updatedSlot = new Slot(slot.getDayOfWeek(),
                        slot.getRoomId(),
                        slot.getTimeOfDay(),
                        slot.getTime().plus(head.get(0).getType().getDuration(), ChronoUnit.MINUTES)
                );
                List<Slot> updatedSlots = availableSlots.stream().filter(t -> !t.equals(slot)).collect(Collectors.toList());
                updatedSlots.add(updatedSlot);
                schedules.add(new Schedule(slot, head.get(0)));
                generateRandomSchedule(leftOverList, updatedSlots, schedules, excessClasses);

            }
        }
    }

    private Either<Boolean, Slot> randomSlotForClass(int classDuration, List<Slot> availableSlots) {
        List<Slot> validSlots = availableSlots.stream().filter(s -> {
            if (lunch.equals(s.getTimeOfDay())) {
                return Duration.between(s.getTime(), lunch.getEnd()).toMinutes() >= classDuration;
            } else if (postOffice.equals(s.getTimeOfDay())) {
                return Duration.between(s.getTime(), postOffice.getEnd()).toMinutes() >= classDuration;
            } else {
                return false;
            }
        }).collect(Collectors.toList());

        if (validSlots.size() == 0) {
            return Either.left(false);
        } else {
            Random ran = new Random();
            return Either.right(validSlots.get(ran.nextInt(validSlots.size())));
        }
    }
}
