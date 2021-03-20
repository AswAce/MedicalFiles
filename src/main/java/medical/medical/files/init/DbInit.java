package medical.medical.files.init;

import medical.medical.files.model.enteties.*;
import medical.medical.files.model.enums.*;
import medical.medical.files.model.serviceModels.ExaminationByDoctorServiceModel;
import medical.medical.files.repositorie.ExaminationRepository;
import medical.medical.files.repositorie.PatientRepository;
import medical.medical.files.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DbInit implements CommandLineRunner {
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final MedicalBranchesInit medicalBranchesInit;
    private final DiseaseService diseaseService;
    private final PatientService patientService;
    private final PatientRepository patientRepository;
    private final ExaminationService examinationService;
    private final ExaminationRepository examinationRepository;

    public DbInit(RoleService roleService,
                  PasswordEncoder passwordEncoder,
                  UserService userService,
                  MedicalBranchesInit medicalBranchesInit,
                  DiseaseService diseaseService, PatientService patientService, PatientRepository patientRepository, ExaminationService examinationService, ExaminationRepository examinationRepository) {
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.medicalBranchesInit = medicalBranchesInit;
        this.diseaseService = diseaseService;

        this.patientService = patientService;

        this.patientRepository = patientRepository;
        this.examinationService = examinationService;

        this.examinationRepository = examinationRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        this.addRoles();
        this.addTestUsers();
//        this.medicalBranchesInit.addMedicineBranches();
//        ExaminationByDoctorServiceModel examinationByDoctorServiceModel = new ExaminationByDoctorServiceModel();
//        examinationByDoctorServiceModel.setDoctorId(1);
//        examinationByDoctorServiceModel.setExactLocation("SPine l%");
//        examinationByDoctorServiceModel.setPartOfTheBody(PartOfTheBodyEnum.BACK);

//this.examinationService.addDoctorPart(examinationByDoctorServiceModel,1);
//        this.addDisease();
//        this.deleteDiseaseByName("Diabetes");

//        this.addDiseaseToPatient();
//
    }

//    private void addDiseaseToPatient() {
//        UserEntity userEntity = this.userService.findByEmail("user@abv.bg");
//        PatientEntity patientEntity = userEntity.getPatientEntity();
//        DiseaseEntity diabetes = this.diseaseService.findByName("Diabetes");
//
//        if (patientEntity != null) {
//
//            patientEntity.getDiseases().add(diabetes);
////            userEntity.setPatientEntity(patientEntity);
////            this.userService.saveUser(userEntity);
//            this.patientService.savePatient(patientEntity);
//        }
//
//    }

    private void addRoles() {
        if (this.roleService.countRoles() == 0) {
            this.roleService.saveRole(RoleEnum.ADMIN);

            this.roleService.saveRole(RoleEnum.USER);
            this.roleService.saveRole(RoleEnum.PATIENT);
            this.roleService.saveRole(RoleEnum.DOCTOR);
        }
    }

    private void addTestUsers() {
        if (this.userService.getUsers() != 0) {
//            UserEntity user = new UserEntity();
//            user.setEmail("user@abv.bg");
//
//            user.setUsername("user");
//            user.setPassword(passwordEncoder.encode("asdasd"));
//            user.getRoles().add((this.roleService.findByName(RoleEnum.PATIENT)));
//            PatientEntity patientEntity = new PatientEntity();
//            patientEntity.setAge(22);
//            patientEntity.setDrinking(DrinkingEnum.SOMETIMES);
//            patientEntity.setExercise(ExerciseEnum.MORE_THAN_FOUR_TIMES_A_WEEK);
//            patientEntity.setHeight(186);
//            patientEntity.setWeight(79);
//            patientEntity.setFullName("Asen Rangelov");
//            patientEntity.setSex(SexEnum.MALE);
//            patientEntity.setSmoking(SmokingEnum.NONE);
//
//            user.setPatientEntity(patientEntity);
//            this.userService.saveUser(user);


//            UserEntity admin = new UserEntity();
//            admin.setEmail("admin@abv.bg");
//
//            admin.setUsername("admin");
//            admin.setPassword(passwordEncoder.encode("asdasd"));
//            admin.getRoles().add((this.roleService.findByName(RoleEnum.ADMIN)));
//            admin.getRoles().add((this.roleService.findByName(RoleEnum.PATIENT)));
//            admin.getRoles().add((this.roleService.findByName(RoleEnum.DOCTOR)));
//            this.userService.saveUser(admin);
        }
    }

    private void addDisease() {

        DiseaseEntity diseaseEntity = new DiseaseEntity();
        diseaseEntity.setName("Diabetes");
        diseaseEntity.setChronic(true);
        diseaseEntity.setCurable(false);
        diseaseEntity.setDescription("Diabetes is bad health problem");


        AdditionalDataEntity additionalDataEntity = new AdditionalDataEntity();
        additionalDataEntity.setType(TypeEnum.BLOOD_TEST);
        additionalDataEntity.setFile("blood Rezult");
        LocationEntity locationEntity = new LocationEntity();
        locationEntity.setPartOfTheBody(PartOfTheBodyEnum.BLOOD);
        locationEntity.setExactLocation("Location of the blood test");

        additionalDataEntity.setLocationEntity(locationEntity);

//        diseaseEntity.getAdditionalData().add(additionalDataEntity);

        LocationEntity locationDisease = new LocationEntity();
        locationDisease.setPartOfTheBody(PartOfTheBodyEnum.BLOOD);
        locationDisease.setExactLocation("locatiuon of the disesease is everywhere");

//        diseaseEntity.setLocation(locationDisease);
        this.diseaseService.save(diseaseEntity);
    }

    private void deleteDiseaseByName(String name) {
        this.diseaseService.deleteDisease(name);


    }
}

