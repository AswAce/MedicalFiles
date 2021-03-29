package medical.medical.files.service.impl;

import medical.medical.files.exeptions.DepartmentNotFoundException;
import medical.medical.files.model.enteties.DaySchedule;
import medical.medical.files.model.enteties.DoctorEntity;
import medical.medical.files.model.enteties.MedicalBranchEntity;
import medical.medical.files.model.enteties.ScheduleEntity;
import medical.medical.files.model.enums.DayEnum;
import medical.medical.files.model.enums.MedicalBranchesEnum;
import medical.medical.files.model.viewModels.DepartmentView;
import medical.medical.files.model.viewModels.SingleDepartmentView;
import medical.medical.files.repositorie.MedicalBranchesRepository;
import medical.medical.files.service.ReviewService;
import medical.medical.files.service.MedicalBranchesService;
import medical.medical.files.service.ScheduleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MedicalBranchesServiceImpl implements MedicalBranchesService {
    private final MedicalBranchesRepository medicalBranchesRepository;
    private final ModelMapper modelMapper;
    private final ScheduleService scheduleService;



    public MedicalBranchesServiceImpl(MedicalBranchesRepository medicalBranchesRepository, ModelMapper modelMapper, ScheduleService scheduleService ) {
        this.medicalBranchesRepository = medicalBranchesRepository;
        this.modelMapper = modelMapper;

        this.scheduleService = scheduleService;

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


    @Override
    public MedicalBranchEntity findByMedicalEnum(MedicalBranchesEnum medicalBranch) {
        MedicalBranchEntity medicalBranchEntity = this.medicalBranchesRepository.findByName(medicalBranch).
                orElseThrow(() -> new IllegalArgumentException("Branch not found"));
        return medicalBranchEntity;
    }

    @Override
    public void addDoctorToTheBranch(MedicalBranchEntity medicalBranchEntity) {
        this.medicalBranchesRepository.saveAndFlush(medicalBranchEntity);
    }

    @Override
    public Set<DepartmentView> getAllDepartments() {
        Set<DepartmentView> collect = this.medicalBranchesRepository.findAll().
                stream()
                .map(medicalBranchEntity -> {
                    DepartmentView map = this.modelMapper.map(medicalBranchEntity, DepartmentView.class);
                    map.setMedicalBranchesEnum(medicalBranchEntity.getName());
                    List<String> docOne = getFirstDoctor(medicalBranchEntity);
                    if (!docOne.isEmpty()) {
                        map.setFirstDoctorName(docOne.get(0));
                    }
                    return map;
                }).collect(Collectors.toSet());


        return collect;
    }


    @Override
    public SingleDepartmentView findById(long id) {
        MedicalBranchEntity medicalBranchEntity = this.medicalBranchesRepository.findById(id).
                orElseThrow(() -> new DepartmentNotFoundException("There is no department with this id"));
        SingleDepartmentView singleDepartmentView = this.modelMapper.map(medicalBranchEntity, SingleDepartmentView.class);

        List<String> firstDoctor = getFirstDoctor(medicalBranchEntity);
        if (!firstDoctor.isEmpty()) {
            singleDepartmentView.setHeadDoctor(firstDoctor.get(0));
        } else {
            singleDepartmentView.setHeadDoctor("We don't have");
        }

        Map<String, String> workingDays = this.scheduleService.getWorkingDaysView(medicalBranchEntity.getSchedule());
        singleDepartmentView.setMonday(workingDays.get("MONDAY"));
        singleDepartmentView.setTuesday(workingDays.get("TUESDAY"));
        singleDepartmentView.setWednesday(workingDays.get("WEDNESDAY"));
        singleDepartmentView.setThursday(workingDays.get("THURSDAY"));
        singleDepartmentView.setFriday(workingDays.get("FRIDAY"));
        return singleDepartmentView;
    }

    @Override
    public void deleteDoctorFromBrnach(DoctorEntity doctorEntity) {
//        this.medicalBranchesRepository.removeDoctor(doctorEntity.getId());
    }

    @Override
    public void removeDoctorFromBranch(MedicalBranchesEnum medicalBranch, DoctorEntity doctorEntity) {
        MedicalBranchEntity byMedicalEnum = this.findByMedicalEnum(medicalBranch);
        byMedicalEnum.getDoctors().remove(doctorEntity);
        this.medicalBranchesRepository.save(byMedicalEnum);
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

    private List<String> getFirstDoctor(MedicalBranchEntity medicalBranchEntity) {
        return medicalBranchEntity.
                getDoctors().stream().
                map(DoctorEntity::getFullName).
                limit(1).
                collect(Collectors.toList());
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

    private ScheduleEntity createSchedule(MedicalBranchesEnum valueOf) {
        ScheduleEntity scheduleEntity = new ScheduleEntity();
        DaySchedule daySchedule1;
        DaySchedule daySchedule2;
        DaySchedule daySchedule3;
        DaySchedule daySchedule4;
        DaySchedule daySchedule5;
        switch (valueOf) {
            case GENERAL_PRACTICE:
                daySchedule1 = createWorkingDay(DayEnum.MONDAY, "9:00", "15:00");
                daySchedule2 = createWorkingDay(DayEnum.TUESDAY, "8:00", "18:00");
                daySchedule3 = createWorkingDay(DayEnum.WEDNESDAY, "9:00", "16:00");
                daySchedule4 = createWorkingDay(DayEnum.THURSDAY, "8:00", "17:00");
                daySchedule5 = createWorkingDay(DayEnum.FRIDAY, "8:00", "18:00");
                scheduleEntity.getDays().addAll(List.of(daySchedule1, daySchedule2, daySchedule3, daySchedule4, daySchedule5));
                return scheduleEntity;
            case NEUROLOGY:
                daySchedule1 = createWorkingDay(DayEnum.MONDAY, "9:00", "19:00");
                daySchedule2 = createWorkingDay(DayEnum.TUESDAY, "8:00", "16:00");
                daySchedule3 = createWorkingDay(DayEnum.WEDNESDAY, "8:00", "15:00");
                daySchedule4 = createWorkingDay(DayEnum.THURSDAY, "8:00", "18:00");
                daySchedule5 = createWorkingDay(DayEnum.FRIDAY, "8:00", "17:00");
                scheduleEntity.getDays().addAll(List.of(daySchedule1, daySchedule2, daySchedule3, daySchedule4, daySchedule5));
                return scheduleEntity;
            case ORTHOPAEDICS:
                daySchedule1 = createWorkingDay(DayEnum.MONDAY, "9:00", "17:00");
                daySchedule2 = createWorkingDay(DayEnum.TUESDAY, "13:00", "19:00");
                daySchedule3 = createWorkingDay(DayEnum.WEDNESDAY, "8:00", "17:00");
                daySchedule4 = createWorkingDay(DayEnum.THURSDAY, "9:00", "20:00");
                daySchedule5 = createWorkingDay(DayEnum.FRIDAY, "8:00", "18:00");
                scheduleEntity.getDays().addAll(List.of(daySchedule1, daySchedule2, daySchedule3, daySchedule4, daySchedule5));
                return scheduleEntity;
            case DERMATOLOGY:
                daySchedule1 = createWorkingDay(DayEnum.MONDAY, "7:00", "12:00");
                daySchedule2 = createWorkingDay(DayEnum.TUESDAY, "8:00", "13:00");
                daySchedule3 = createWorkingDay(DayEnum.WEDNESDAY, "13:00", "19:00");
                daySchedule4 = createWorkingDay(DayEnum.THURSDAY, "8:00", "14:00");
                daySchedule5 = createWorkingDay(DayEnum.FRIDAY, "8:00", "14:00");
                scheduleEntity.getDays().addAll(List.of(daySchedule1, daySchedule2, daySchedule3, daySchedule4, daySchedule5));
                return scheduleEntity;
            case CARDIOLOGY:
                daySchedule1 = createWorkingDay(DayEnum.MONDAY, "9:00", "16:00");
                daySchedule2 = createWorkingDay(DayEnum.TUESDAY, "8:00", "13:00");
                daySchedule3 = createWorkingDay(DayEnum.WEDNESDAY, "13:00", "18:00");
                daySchedule4 = createWorkingDay(DayEnum.THURSDAY, "14:00", "19:00");
                daySchedule5 = createWorkingDay(DayEnum.FRIDAY, "8:00", "12:00");
                scheduleEntity.getDays().addAll(List.of(daySchedule1, daySchedule2, daySchedule3, daySchedule4, daySchedule5));
                return scheduleEntity;
            case PULMONOLOGY:
                daySchedule1 = createWorkingDay(DayEnum.MONDAY, "9:00", "18:00");
                daySchedule2 = createWorkingDay(DayEnum.TUESDAY, "8:00", "13:00");
                daySchedule3 = createWorkingDay(DayEnum.WEDNESDAY, "8:00", "20:00");
                daySchedule4 = createWorkingDay(DayEnum.THURSDAY, "8:00", "18:00");
                daySchedule5 = createWorkingDay(DayEnum.FRIDAY, "7:00", "18:00");
                scheduleEntity.getDays().addAll(List.of(daySchedule1, daySchedule2, daySchedule3, daySchedule4, daySchedule5));
                return scheduleEntity;
            case SURGERY:
                daySchedule1 = createWorkingDay(DayEnum.MONDAY, "6:00", "22:00");
                daySchedule2 = createWorkingDay(DayEnum.TUESDAY, "6:00", "22:00");
                daySchedule3 = createWorkingDay(DayEnum.WEDNESDAY, "6:00", "22:00");
                daySchedule4 = createWorkingDay(DayEnum.THURSDAY, "6:00", "22:00");
                daySchedule5 = createWorkingDay(DayEnum.FRIDAY, "6:00", "22:00");
                scheduleEntity.getDays().addAll(List.of(daySchedule1, daySchedule2, daySchedule3, daySchedule4, daySchedule5));
                return scheduleEntity;
        }
        return null;
    }
}
