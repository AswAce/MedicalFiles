package medical.medical.files.service.impl;

import medical.medical.files.model.enteties.DaySchedule;
import medical.medical.files.model.enteties.ScheduleEntity;
import medical.medical.files.model.enums.DayEnum;
import medical.medical.files.service.ScheduleService;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ScheduleServiceImpl implements ScheduleService {


    @Override
    public ScheduleEntity createSchedule(Map<DayEnum, String> schedule) {
        ScheduleEntity scheduleEntity = new ScheduleEntity();
        schedule.entrySet().stream().forEach(dayEnumStringEntry ->
                {
                    String[] startingEndingTime = dayEnumStringEntry.getValue().split("-");
                    scheduleEntity.getDays().add(createWorkingDay(dayEnumStringEntry.getKey(),
                            startingEndingTime[0], startingEndingTime[1]));
                }
        );
        return scheduleEntity;

    }

    @Override
    public DaySchedule createWorkingDay(DayEnum day, String start, String end) {
        DaySchedule daySchedule = new DaySchedule();
        daySchedule.setDay(day);
        String pattern = "H:mm";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
        LocalTime.parse(start, dtf);
        daySchedule.setStartTime(LocalTime.parse(start, dtf));
        daySchedule.setEndTime(LocalTime.parse(end, dtf));

        return daySchedule;
    }

    @Override
    public Map<String, String> getWorkingDaysView(ScheduleEntity schedule) {
        Map<String, String> workingDays = new LinkedHashMap<>();
        schedule.getDays().stream().forEach(daySchedule -> {
            String time = daySchedule.getStartTime().toString() + "-" + daySchedule.getEndTime().toString();
            workingDays.put(daySchedule.getDay().name(), time);
        });
        return workingDays;
    }
}
