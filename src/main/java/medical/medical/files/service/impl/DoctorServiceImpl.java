package medical.medical.files.service.impl;

import medical.medical.files.exeptions.DoctorNotFoundExeption;
import medical.medical.files.model.enteties.*;
import medical.medical.files.model.enums.DayEnum;
import medical.medical.files.model.enums.RoleEnum;
import medical.medical.files.model.serviceModels.AddDoctorProfileServiceModel;
import medical.medical.files.model.viewModels.DoctorSetViewModel;
import medical.medical.files.model.viewModels.SingleDoctorView;
import medical.medical.files.repositorie.DoctorRepository;
import medical.medical.files.repositorie.ExaminationRepository;
import medical.medical.files.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;
    private final CloudinaryService cloudinaryService;
    private final UserService userService;
    private final ScheduleService scheduleService;
    private final MedicalBranchesService medicalBranchesService;
    private final ExaminationRepository examinationRepository;

    public DoctorServiceImpl(DoctorRepository doctorRepository, ModelMapper modelMapper, CloudinaryService cloudinaryService, UserService userService, ScheduleService scheduleService, MedicalBranchesService medicalBranchesService, ExaminationRepository examinationRepository) {
        this.doctorRepository = doctorRepository;
        this.modelMapper = modelMapper;
        this.cloudinaryService = cloudinaryService;
        this.userService = userService;
        this.scheduleService = scheduleService;

        this.medicalBranchesService = medicalBranchesService;
        this.examinationRepository = examinationRepository;
    }


    @Override
    public DoctorEntity findByName(String doctorName) {
        return this.doctorRepository.findByFullName(doctorName);
    }

    @Override
    public void saveDoctorToProfile(AddDoctorProfileServiceModel addDoctorProfileServiceModel) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        UserEntity byUsername = this.userService.findByUserName(userName);
        List<RoleEnum> collect = byUsername.getRoles().stream().map(roleEntity -> roleEntity.getRole()).collect(Collectors.toList());
        if (collect.contains(RoleEnum.DOCTOR)) {
            DoctorEntity doctorEntity = this.modelMapper.
                    map(addDoctorProfileServiceModel, DoctorEntity.class);
            MultipartFile img = addDoctorProfileServiceModel.getImg();
            String originalFilename = img.getOriginalFilename();

            if (!originalFilename.isEmpty()) {
                String imageUrl = cloudinaryService.uploadImage(img);
                doctorEntity.setPhoto(imageUrl);
            } else {
                doctorEntity.setPhoto("https://res.cloudinary.com/aswace/image/upload/v1616319777/medicalApp/docPhoto_rvp0w4.jpg");
            }
            Map<DayEnum, String> createScheduleFromService = addDoctorProfileServiceModel.makeSchedule();
            ScheduleEntity schedule = this.scheduleService.createSchedule(createScheduleFromService);
            doctorEntity.setSchedule(schedule);

            byUsername.setDoctorEntity(doctorEntity);
            this.userService.saveDoctorOrPatientToUser(byUsername);
            MedicalBranchEntity medicalBranchEntity = this.medicalBranchesService.findByMedicalEnum(addDoctorProfileServiceModel.getMedicalBranch());

            Set<DoctorEntity> doctors = medicalBranchEntity.getDoctors();
            doctors.add(this.userService.findByUserName(byUsername.getUsername()).getDoctorEntity());

            this.medicalBranchesService.addDoctorToTheBranch(medicalBranchEntity);
        }

    }

    @Override
    public Set<DoctorSetViewModel> getAll() {
        Set<DoctorSetViewModel> collect = this.doctorRepository.
                findAll().
                stream().
                map(doctorEntity -> {
                    DoctorSetViewModel doctorSetViewModel = this.modelMapper.map(doctorEntity, DoctorSetViewModel.class);
                    if (doctorSetViewModel.getPhoto() == null) {
                        doctorSetViewModel.setPhoto("https://res.cloudinary.com/aswace/image/upload/v1616319777/medicalApp/docPhoto_rvp0w4.jpg");
                    }

                    return doctorSetViewModel;
                }).
                collect(Collectors.toSet());
        return collect;
    }

    @Override
    public SingleDoctorView findById(long id) {
        DoctorEntity doctorEntity = this.doctorRepository.findById(id).orElseThrow(() -> new DoctorNotFoundExeption("Doctor not found"));

        SingleDoctorView singleDoctorView = this.modelMapper.map(doctorEntity, SingleDoctorView.class);
        Map<String, String> workingDaysView = this.scheduleService.getWorkingDaysView(doctorEntity.getSchedule());
        singleDoctorView.setWorkingDays(workingDaysView);
        if (singleDoctorView.getPhoto() == null) {
            singleDoctorView.setPhoto("https://res.cloudinary.com/aswace/image/upload/v1616319777/medicalApp/docPhoto_rvp0w4.jpg");
        }
        return singleDoctorView;
    }

    @Override
    public void deleteDoctor(DoctorEntity doctorEntity) {

        Set<ExaminationEntity> allByDoctorId = this.examinationRepository.findAllByDoctorId(doctorEntity.getId());
        allByDoctorId.forEach(this.examinationRepository::delete);
        this.doctorRepository.delete(doctorEntity);

    }

    @Override
    public long getCount() {

        return this.doctorRepository.count();
    }

    @Override
    public DoctorEntity findByIdSetViewDoctor(int randomNumber) {
        DoctorEntity byId = this.doctorRepository.findById((long) randomNumber).orElse(null);
        return byId;
    }

    @Override
    public DoctorEntity findByIdEntity(Long valueOf) {
        return this.doctorRepository.findById(valueOf).orElseThrow(() -> new DoctorNotFoundExeption("Doctor not found"));
    }

    private DoctorEntity getDoctor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity byUserName = this.userService.findByUserName(authentication.getName());
        DoctorEntity docForDepartment = byUserName.getDoctorEntity();
        return docForDepartment;
    }


}
