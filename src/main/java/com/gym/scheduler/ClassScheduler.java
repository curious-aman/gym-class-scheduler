package com.gym.scheduler;

import com.gym.scheduler.json.JsonReader;
import com.gym.scheduler.models.ClassDetail;
import com.gym.scheduler.models.ScheduleResponse;
import com.gym.scheduler.services.ReportGenerator;
import com.gym.scheduler.services.Scheduler;
import com.gym.scheduler.utils.IOUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ClassScheduler {
    private static final Logger logger
            = LoggerFactory.getLogger(ClassScheduler.class);
    private String outputFilePath;

    public static void main(String[] args) {
        ClassScheduler classScheduler = new ClassScheduler();
        List<ClassDetail> classDetailList = null;
        String inputFilePath = "src/main/resources/classes.json";
        classScheduler.outputFilePath = "schedule.txt";
        if (args != null && args.length > 0) {
            inputFilePath = args[0];
            if (args.length > 1) {
                classScheduler.outputFilePath = args[1];
            }
        }
        classDetailList = IOUtil.readClassDetails(inputFilePath);
        try {
            classScheduler.scheduleAndGenerateReport(classDetailList);
        } catch (Exception e) {
            logger.error(e.getMessage());
            System.exit(-1);
        }
    }

    public void scheduleAndGenerateReport(List<ClassDetail> classDetailList) throws Exception {
        ScheduleResponse scheduleResponse = new Scheduler().schedule(classDetailList);
        if (scheduleResponse != null) {
            logger.info(scheduleResponse.toString());
            // Generate Schedule Report in output file
            ReportGenerator.generateReport(scheduleResponse.getScheduleList(), outputFilePath);
            // Notify if some classes could not be scheduled because of time shortage (print number of sessions missed)
            if (scheduleResponse.getExcessClassDetailList() != null && scheduleResponse.getExcessClassDetailList().size() > 0) {
                logger.info("Could not schedule following classes due to time limit:");
                scheduleResponse.getExcessClassDetailList().stream().forEach(c ->
                {
                    logger.info("{} - {} - by {} - missed {} times", c.getName(), c.getType().getType(), c.getTrainer(), c.getFrequency());
                });
            }
        }
    }
}
