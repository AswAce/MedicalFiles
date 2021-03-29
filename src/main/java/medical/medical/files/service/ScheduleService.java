package medical.medical.files.service;

import medical.medical.files.model.enteties.DaySchedule;
import medical.medical.files.model.enteties.ScheduleEntity;
import medical.medical.files.model.enums.DayEnum;

import java.util.Map;

public interface ScheduleService {

    ScheduleEntity createSchedule(Map<DayEnum,String> schedule);
    DaySchedule createWorkingDay(DayEnum day, String start, String end);
    Map<String, String> getWorkingDaysView(ScheduleEntity schedule);
}
