package com.gym.scheduler.json;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.gym.scheduler.models.ClassDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class JsonReader {
    private static final Logger logger
            = LoggerFactory.getLogger(JsonReader.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    private JsonReader() {
        mapper.findAndRegisterModules();
    }

    public static <T> T readPath(String path) {
        logger.info("Reading json file path: {}", path);
        try {
            File file = new File(path);
            return (T) mapper.readValue(file, ClassDetail[].class);
        } catch (IOException e) {
            logger.error("Unable to read or parse json file: {}", path, e);
            throw new IllegalArgumentException("Unable to parse json file", e);
        }
    }

}
