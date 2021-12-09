package com.gym.scheduler.utils;

import com.gym.scheduler.json.JsonReader;
import com.gym.scheduler.models.ClassDetail;
import com.gym.scheduler.models.ClassDetails;

import java.util.Arrays;
import java.util.List;

public class IOUtil {
    public static List<ClassDetail> readClassDetails(String file){
        ClassDetail[] classDetailArray  = JsonReader.readPath(file);
        return Arrays.asList(classDetailArray);
    }
}
