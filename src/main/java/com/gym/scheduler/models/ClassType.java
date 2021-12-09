package com.gym.scheduler.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.gym.scheduler.json.CustomTypeEnumDeserializer;

@JsonDeserialize(using = CustomTypeEnumDeserializer.class)
public enum ClassType {
    STRENGTH_AND_CONDITIONING("Strength and Conditioning",45),
    CARDIO("Cardio", 60),
    MIND_AND_BODY("Mind and Body",30),
    DANCE("Dance", 60);

    private String type;
    private int duration;

    ClassType(String type, int duration) {
        this.type=type;
        this.duration = duration;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
