package medical.medical.files.service.impl;

import medical.medical.files.model.enteties.DaySchedule;
import medical.medical.files.model.enteties.MedicalBranchEntity;
import medical.medical.files.model.enteties.ScheduleEntity;
import medical.medical.files.model.enums.DayEnum;
import medical.medical.files.model.enums.MedicalBranchesEnum;
import medical.medical.files.repositorie.MedicalBranchesRepository;
import medical.medical.files.service.MedicalBranchesService;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MedicalBranchesServiceImpl implements MedicalBranchesService {
    private final MedicalBranchesRepository medicalBranchesRepository;


    public MedicalBranchesServiceImpl(MedicalBranchesRepository medicalBranchesRepository) {
        this.medicalBranchesRepository = medicalBranchesRepository;
    }

    @Override
    public void saveBranch(String name, String description) {
        if (MedicalBranchEnumExist(name) != null && this.findByName(name) == null) {
            MedicalBranchEntity medicalBranchEntity = new MedicalBranchEntity();
            medicalBranchEntity.setName(MedicalBranchesEnum.valueOf(name));
            medicalBranchEntity.setDescription(description);
            medicalBranchEntity.setPhoto(this.addPhoto(MedicalBranchesEnum.valueOf(name)));
            ScheduleEntity scheduleEntity = createSchedule(MedicalBranchesEnum.valueOf(name));
            medicalBranchEntity.setSchedule(scheduleEntity);
            medicalBranchesRepository.saveAndFlush(medicalBranchEntity);

        }

    }

    public ScheduleEntity createSchedule(MedicalBranchesEnum valueOf) {
        ScheduleEntity scheduleEntity = new ScheduleEntity();
        DaySchedule daySchedule1;
        DaySchedule daySchedule2;
        DaySchedule daySchedule3;
        DaySchedule daySchedule4;
        DaySchedule daySchedule5;
        switch (valueOf) {
            case GENERAL_PRACTICE:
                daySchedule1 = createWorkingDay(DayEnum.MONDAY, "9:00", "15:00");
                daySchedule2 = createWorkingDay(DayEnum.THURSDAY, "8:00", "18:00");
                daySchedule3 = createWorkingDay(DayEnum.WEDNESDAY, "8:00", "18:00");
                daySchedule4 = createWorkingDay(DayEnum.THURSDAY, "8:00", "18:00");
                daySchedule5 = createWorkingDay(DayEnum.FRIDAY, "8:00", "18:00");
                scheduleEntity.getDays().addAll(List.of(daySchedule1, daySchedule2, daySchedule3, daySchedule4, daySchedule5));
                return scheduleEntity;
            case NEUROLOGY:
                daySchedule1 = createWorkingDay(DayEnum.MONDAY, "9:00", "15:00");
                daySchedule2 = createWorkingDay(DayEnum.THURSDAY, "8:00", "18:00");
                daySchedule3 = createWorkingDay(DayEnum.WEDNESDAY, "8:00", "18:00");
                daySchedule4 = createWorkingDay(DayEnum.THURSDAY, "8:00", "18:00");
                daySchedule5 = createWorkingDay(DayEnum.FRIDAY, "8:00", "18:00");
                scheduleEntity.getDays().addAll(List.of(daySchedule1, daySchedule2, daySchedule3, daySchedule4, daySchedule5));
                return scheduleEntity;
            case ORTHOPAEDICS:
                daySchedule1 = createWorkingDay(DayEnum.MONDAY, "9:00", "3:00");
                daySchedule2 = createWorkingDay(DayEnum.THURSDAY, "8:00", "18:00");
                daySchedule3 = createWorkingDay(DayEnum.WEDNESDAY, "8:00", "18:00");
                daySchedule4 = createWorkingDay(DayEnum.THURSDAY, "8:00", "18:00");
                daySchedule5 = createWorkingDay(DayEnum.FRIDAY, "8:00", "18:00");
                scheduleEntity.getDays().addAll(List.of(daySchedule1, daySchedule2, daySchedule3, daySchedule4, daySchedule5));
                return scheduleEntity;
            case DERMATOLOGY:
                daySchedule1 = createWorkingDay(DayEnum.MONDAY, "9:00", "15:00");
                daySchedule2 = createWorkingDay(DayEnum.THURSDAY, "8:00", "18:00");
                daySchedule3 = createWorkingDay(DayEnum.WEDNESDAY, "8:00", "18:00");
                daySchedule4 = createWorkingDay(DayEnum.THURSDAY, "8:00", "18:00");
                daySchedule5 = createWorkingDay(DayEnum.FRIDAY, "8:00", "18:00");
                scheduleEntity.getDays().addAll(List.of(daySchedule1, daySchedule2, daySchedule3, daySchedule4, daySchedule5));
                return scheduleEntity;
            case CARDIOLOGY:
                daySchedule1 = createWorkingDay(DayEnum.MONDAY, "9:00", "15:00");
                daySchedule2 = createWorkingDay(DayEnum.THURSDAY, "8:00", "18:00");
                daySchedule3 = createWorkingDay(DayEnum.WEDNESDAY, "8:00", "18:00");
                daySchedule4 = createWorkingDay(DayEnum.THURSDAY, "8:00", "18:00");
                daySchedule5 = createWorkingDay(DayEnum.FRIDAY, "8:00", "18:00");
                scheduleEntity.getDays().addAll(List.of(daySchedule1, daySchedule2, daySchedule3, daySchedule4, daySchedule5));
                return scheduleEntity;
            case PULMONOLOGY:
                daySchedule1 = createWorkingDay(DayEnum.MONDAY, "9:00", "15:00");
                daySchedule2 = createWorkingDay(DayEnum.THURSDAY, "8:00", "18:00");
                daySchedule3 = createWorkingDay(DayEnum.WEDNESDAY, "8:00", "18:00");
                daySchedule4 = createWorkingDay(DayEnum.THURSDAY, "8:00", "18:00");
                daySchedule5 = createWorkingDay(DayEnum.FRIDAY, "8:00", "18:00");
                scheduleEntity.getDays().addAll(List.of(daySchedule1, daySchedule2, daySchedule3, daySchedule4, daySchedule5));
                return scheduleEntity;
            case SURGERY:
                daySchedule1 = createWorkingDay(DayEnum.MONDAY, "9:00", "15:00");
                daySchedule2 = createWorkingDay(DayEnum.THURSDAY, "8:00", "18:00");
                daySchedule3 = createWorkingDay(DayEnum.WEDNESDAY, "8:00", "18:00");
                daySchedule4 = createWorkingDay(DayEnum.THURSDAY, "8:00", "18:00");
                daySchedule5 = createWorkingDay(DayEnum.FRIDAY, "8:00", "18:00");
                scheduleEntity.getDays().addAll(List.of(daySchedule1, daySchedule2, daySchedule3, daySchedule4, daySchedule5));
                return scheduleEntity;
        }
        return null;
    }

    private DaySchedule createWorkingDay(DayEnum day, String start, String end) {
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
    public MedicalBranchEntity findByName(String name) {
        MedicalBranchEntity byName = this.medicalBranchesRepository.
                findByName(MedicalBranchEnumExist(name)).orElse(null);
        return byName;
    }

    @Override
    public String addPhoto(MedicalBranchesEnum valueOf) {
        switch (valueOf) {
            case SURGERY:
                return "https://res.cloudinary.com/aswace/image/upload/v1615881451/medicalApp/Branches/SURGERY_fbzky6.jpg";
            case CARDIOLOGY:
                return "https://res.cloudinary.com/aswace/image/upload/v1615881058/medicalApp/Branches/CARDIOLOGY_i0sxwh.jpg";
            case DERMATOLOGY:
                return "https://res.cloudinary.com/aswace/image/upload/v1615881122/medicalApp/Branches/DERMATOLOGY_rusa2x.jpg";
            case PULMONOLOGY:
                return "https://res.cloudinary.com/aswace/image/upload/v1615881357/medicalApp/Branches/PULMONOLOGY_pq8hdo.jpg";
            case ORTHOPAEDICS:
                return "https://res.cloudinary.com/aswace/image/upload/v1615881400/medicalApp/Branches/ORTHOPAEDICS_ssqr4g.jpg";
            case NEUROLOGY:
                return "https://res.cloudinary.com/aswace/image/upload/v1615881241/medicalApp/Branches/NEUROLOGY_pjq2tx.jpg";
            case GENERAL_PRACTICE:
                return "https://res.cloudinary.com/aswace/image/upload/v1615881174/medicalApp/Branches/GENERAL_PRACTICE_jtfc6q.jpg";
        }
        return null;
    }


    private MedicalBranchesEnum MedicalBranchEnumExist(String name) {
        List<MedicalBranchesEnum> collect = Arrays.stream(MedicalBranchesEnum.values()).collect(Collectors.toList());
        try {
            if (collect.contains(MedicalBranchesEnum.valueOf(name))) {
                return MedicalBranchesEnum.valueOf(name);
            }
            return null;
        } catch (IllegalArgumentException e) {
            return null;
        }

    }


}
