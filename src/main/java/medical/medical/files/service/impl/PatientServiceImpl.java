package medical.medical.files.service.impl;

import medical.medical.files.config.currentUser.IAuthenticationFacade;
import medical.medical.files.exeptions.ExaminationNotFoundException;
import medical.medical.files.model.enteties.*;
import medical.medical.files.model.enums.BmiResultsEnum;
import medical.medical.files.model.enums.MedicalBranchesEnum;
import medical.medical.files.model.enums.RoleEnum;
import medical.medical.files.model.serviceModels.AddDiseaseServiceModel;
import medical.medical.files.model.serviceModels.AddExaminationServiceModel;
import medical.medical.files.model.serviceModels.PatientServiceModel;
import medical.medical.files.model.viewModels.*;
import medical.medical.files.repositorie.PatientRepository;
import medical.medical.files.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;

    private final ExaminationService examinationService;
    private final UserService userService;
    private final CloudinaryService cloudinaryService;
    private final IAuthenticationFacade authenticationFacade;


    public PatientServiceImpl(PatientRepository patientRepository, ModelMapper modelMapper, ExaminationService examinationService, UserService userService, CloudinaryService cloudinaryService, IAuthenticationFacade authenticationFacade) {
        this.patientRepository = patientRepository;
        this.modelMapper = modelMapper;

        this.examinationService = examinationService;
        this.userService = userService;
        this.cloudinaryService = cloudinaryService;


        this.authenticationFacade = authenticationFacade;
    }

    @Override
    public void savePatient(PatientServiceModel patientServiceModel) throws IOException {

        UserEntity userEntity = this.userService.findByUserName(authenticationFacade.getAuthentication().getName());

        List<RoleEnum> collect = userEntity.getRoles().stream().map(RoleEntity::getRole).collect(Collectors.toList());
        if (collect.contains(RoleEnum.PATIENT)) {
            PatientEntity patientEntity = this.modelMapper.map(patientServiceModel, PatientEntity.class);
            if (!patientServiceModel.getImg().getOriginalFilename().isEmpty()) {
                String uploadImage = this.cloudinaryService.uploadImage(patientServiceModel.getImg());
                patientEntity.setImageUrl(uploadImage);
            } else {
                patientEntity.setImageUrl("https://res.cloudinary.com/aswace/image/upload/v1616342430/medicalApp/default-avatar-profile-icon-vector-social-media-user-photo-183042379_s17edi.jpg");
            }


            userEntity.setPatientEntity(patientEntity);
            this.userService.saveDoctorOrPatientToUser(userEntity);
        }


    }

    @Override
    public void addExaminationToThePatient(AddExaminationServiceModel examinationServiceModel) {


        PatientEntity patientEntity = this.userService.findByUserName(this.authenticationFacade.getAuthentication().getName()).getPatientEntity();


        this.patientRepository.save(patientEntity);
    }

    @Override
    public PatientViewModel findById(long patientId) {
        PatientEntity patientEntityById = this.patientRepository.findById(patientId);

        PatientViewModel patientViewModel = this.mapPatientEntityToPatientViewModel(patientEntityById);
        patientViewModel.setPhoto(patientEntityById.getImageUrl());
        if (patientViewModel.getPhoto() == null) {
            patientViewModel.setPhoto("https://res.cloudinary.com/aswace/image/upload/v1616342430/medicalApp/default-avatar-profile-icon-vector-social-media-user-photo-183042379_s17edi.jpg");
        }
        List<DiseaseViewModel> diseaseViewModels = patientEntityById.getDiseases().
                stream().
                map(diseaseEntity ->
                        this.modelMapper.map(diseaseEntity, DiseaseViewModel.class)).collect(Collectors.toList());
        patientViewModel.setDiseaseViewModels((ArrayList<DiseaseViewModel>) diseaseViewModels);


        return patientViewModel;
    }


    @Override
    public void deletePatient(PatientEntity patientEntity) {
        this.patientRepository.delete(patientEntity);
    }

    @Override
    public void addDiseaseToThePatient(AddDiseaseServiceModel addDiseaseServiceModel) throws ExaminationNotFoundException {


        UserEntity byUserName = this.userService.findByUserName(this.authenticationFacade.getAuthentication().getName());
        if (byUserName.getDoctorEntity() != null) {
            long examinationId = addDiseaseServiceModel.getExaminationId();

            long doctorId = this.examinationService.findByExaminationId(examinationId).getDoctor().getId();
            if (doctorId == byUserName.getDoctorEntity().getId()) {


                PatientEntity byId = this.patientRepository.findById(addDiseaseServiceModel.getPatientId());
                DiseaseEntity diseaseEntity = this.modelMapper.map(addDiseaseServiceModel, DiseaseEntity.class);
                diseaseEntity.setId(0);
                byId.getDiseases().add(diseaseEntity);
                this.patientRepository.save(byId);
            }


        }


    }

    @Override
    public boolean patientHasExaminationsInThisDepartment(MedicalBranchesEnum name) {

        if (getIfUserIsPatient() != null) {
            long id = getIfUserIsPatient().getId();
            ArrayList<ExaminationViewModel> allByPatientIdAndDepartment = this.examinationService.findAllByPatientIdAndDepartment(id, name);
            return allByPatientIdAndDepartment.size() > 0;
        }


        return false;
    }

    @Override
    public Set<String> findPatientDoctorsForThisDepartment(MedicalBranchesEnum name) {
        if (getIfUserIsPatient() != null) {
            getIfUserIsPatient().getId();
            ArrayList<ExaminationViewModel> allByPatientIdAndDepartment = this.examinationService.findAllByPatientIdAndDepartment(getIfUserIsPatient().getId(), name);
            Set<String> doctors = allByPatientIdAndDepartment.
                    stream().
                    map(ExaminationViewModel::getDoctorFullName).
                    collect(Collectors.toSet());
            return doctors;
        }
        return null;
    }

    @Override
    public int countAll() {
        return (int) this.patientRepository.count();
    }


    private PatientViewModel mapPatientEntityToPatientViewModel(PatientEntity patientEntity) {

        PatientViewModel patientViewModel = this.modelMapper.map(patientEntity, PatientViewModel.class);

        patientViewModel.setBmi(bmiIndex(patientEntity.getWeight(), patientEntity.getHeight()));


        return patientViewModel;
    }

    private String bmiIndex(double weight, double height) {

        double bmi = weight / (height * height);
        String result = " Your Body Mass Index is  " + String.valueOf(bmi) + ". This is considered: ";
        if (bmi < 15) {
            result += BmiResultsEnum.VERY_SEVERELY_UNDERWEIGHT.name();
        } else if (bmi >= 15 && bmi < 16) {
            result += BmiResultsEnum.SEVERELY_UNDERWEIGHT.name();
        } else if (bmi >= 16 && bmi < 18.5) {
            result += BmiResultsEnum.UNDERWEIGHT.name();
        } else if (bmi >= 18.5 && bmi < 25) {
            result += BmiResultsEnum.NORMAL_HEALTHY_WEIGHT.name();
        } else if (bmi >= 25 && bmi < 30) {
            result += BmiResultsEnum.OVERWEIGHT.name();
        } else if (bmi >= 30 && bmi < 35) {
            result += BmiResultsEnum.MODERATELY_OBESE.name();
        } else if (bmi >= 35 && bmi < 40) {
            result += BmiResultsEnum.SEVERELY_OBESE.name();
        } else if (bmi >= 40) {
            result += BmiResultsEnum.VERY_SEVERELY_OBESE.toString();
        }

        return result;
    }

    private PatientEntity getIfUserIsPatient() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity byUserName = this.userService.findByUserName(authentication.getName());
        if (byUserName.getPatientEntity() != null) {

            return byUserName.getPatientEntity();
        }
        return null;
    }
}
