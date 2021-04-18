package medical.medical.files.service.impl;

import medical.medical.files.config.currentUser.IAuthenticationFacade;
import medical.medical.files.exeptions.DoctorNotFoundExeption;
import medical.medical.files.model.enteties.*;
import medical.medical.files.model.enums.DayEnum;
import medical.medical.files.model.enums.MedicalBranchesEnum;
import medical.medical.files.model.enums.RoleEnum;
import medical.medical.files.model.serviceModels.AddDoctorProfileServiceModel;
import medical.medical.files.model.viewModels.DoctorSetViewModel;
import medical.medical.files.model.viewModels.SingleDoctorView;
import medical.medical.files.repositorie.DoctorRepository;

import medical.medical.files.service.*;
import org.modelmapper.ModelMapper;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DoctorServiceImpl implements DoctorService {
    private static final String NOT_FOUND = "Doctor not found";
    private static final String DEFAULT_PHOTO = "https://res.cloudinary.com/aswace/image/upload/v1616319777/medicalApp/docPhoto_rvp0w4.jpg";
    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;
    private final CloudinaryService cloudinaryService;
    private final UserService userService;
    private final ScheduleService scheduleService;
    private final MedicalBranchesService medicalBranchesService;

    private final IAuthenticationFacade authenticationFacade;

    public DoctorServiceImpl(DoctorRepository doctorRepository, ModelMapper modelMapper, CloudinaryService cloudinaryService, UserService userService, ScheduleService scheduleService, MedicalBranchesService medicalBranchesService, IAuthenticationFacade authenticationFacade) {
        this.doctorRepository = doctorRepository;
        this.modelMapper = modelMapper;
        this.cloudinaryService = cloudinaryService;
        this.userService = userService;
        this.scheduleService = scheduleService;

        this.medicalBranchesService = medicalBranchesService;

        this.authenticationFacade = authenticationFacade;
    }


    @Override
    public DoctorEntity findByName(String doctorName, MedicalBranchesEnum medicalBranchesEnum) {
        ArrayList<Optional<DoctorEntity>> byFullName = this.doctorRepository.findByFullName(doctorName);

        byFullName.stream().filter(Optional::isPresent).filter(doctorEntity -> doctorEntity.get().getMedicalBranch() == medicalBranchesEnum);
        return byFullName.get(0).orElseThrow(() -> new DoctorNotFoundExeption(NOT_FOUND));
    }

    @Override
    public void saveDoctorToProfile(AddDoctorProfileServiceModel addDoctorProfileServiceModel) throws IOException {

        String userName = authenticationFacade.getAuthentication().getName();
        UserEntity byUsername = this.userService.findByUserName(userName);
        List<RoleEnum> collect = byUsername.getRoles().stream().map(RoleEntity::getRole).collect(Collectors.toList());
        if (collect.contains(RoleEnum.DOCTOR)) {
            DoctorEntity doctorEntity = this.modelMapper.
                    map(addDoctorProfileServiceModel, DoctorEntity.class);

            if (addDoctorProfileServiceModel.getImg().getOriginalFilename() != null) {
                MultipartFile img = addDoctorProfileServiceModel.getImg();
                String originalFilename = img.getOriginalFilename();
                if (!originalFilename.isEmpty()) {
                    String imageUrl = cloudinaryService.uploadImage(img);
                    doctorEntity.setPhoto(imageUrl);
                } else {
                    doctorEntity.setPhoto(DEFAULT_PHOTO);
                }
            } else {
                doctorEntity.setPhoto(DEFAULT_PHOTO);

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
                        doctorSetViewModel.setPhoto(DEFAULT_PHOTO);
                    }

                    return doctorSetViewModel;
                }).
                collect(Collectors.toSet());
        return collect;
    }

    @Override
    public SingleDoctorView findById(long id) {
        DoctorEntity doctorEntity = this.doctorRepository.findById(id).orElseThrow(() -> new DoctorNotFoundExeption(NOT_FOUND));

        SingleDoctorView singleDoctorView = this.modelMapper.map(doctorEntity, SingleDoctorView.class);
        Map<String, String> workingDaysView = this.scheduleService.getWorkingDaysView(doctorEntity.getSchedule());
        singleDoctorView.setWorkingDays(workingDaysView);
        if (singleDoctorView.getPhoto() == null) {
            singleDoctorView.setPhoto(DEFAULT_PHOTO);
        }
        return singleDoctorView;
    }

    @Override
    public long getCount() {
        try {
            DoctorEntity topByOrderByIdDesc =
                    this.doctorRepository.findTopByOrderByIdDesc().orElseThrow(() -> new DoctorNotFoundExeption(NOT_FOUND));
            return topByOrderByIdDesc.getId();
        } catch (DoctorNotFoundExeption doctorNotFoundExeption) {
            return 0;
        }
    }

    @Override
    public DoctorEntity findByIdSetViewDoctor(int randomNumber) {
        return this.doctorRepository.findById((long) randomNumber).orElse(null);

    }

    @Override
    public DoctorEntity findByIdEntity(Long valueOf) {
        return this.doctorRepository.findById(valueOf).orElseThrow(() -> new DoctorNotFoundExeption(NOT_FOUND));
    }


}
