package medical.medical.files.web;


import medical.medical.files.model.enteties.PatientEntity;
import medical.medical.files.model.enteties.RoleEntity;
import medical.medical.files.model.enteties.UserEntity;
import medical.medical.files.model.enums.*;
import medical.medical.files.repositorie.ExaminationRepository;
import medical.medical.files.repositorie.PatientRepository;

import medical.medical.files.repositorie.RoleRepository;
import medical.medical.files.repositorie.UserRepository;
import medical.medical.files.service.impl.CloudinaryServiceImpl;
import medical.medical.files.service.impl.PatientServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class PatientControllerTest {
    private static final String PATIENTS_CONTROLLER_PREFIX = "/patients";

    private long patientId;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ExaminationRepository examinationRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    CloudinaryServiceImpl cloudinaryService;

    @Autowired
    private PatientRepository patientRepository;

    @BeforeEach
    public void setUp() {
        this.init();


    }

    private void init() {
        userRepository.deleteAll();
        patientRepository.deleteAll();
        roleRepository.deleteAll();
        RoleEntity user = new RoleEntity();
        user.setRole(RoleEnum.USER);
        roleRepository.save(user);
        RoleEntity patient = new RoleEntity();
        patient.setRole(RoleEnum.PATIENT);
        roleRepository.save(patient);


        PatientEntity p1 = new PatientEntity();
        p1.setImageUrl("patientURL");
        p1.setFullName("patient1");
        p1.setSmoking(SmokingEnum.NONE);
        p1.setSex(SexEnum.MALE);
        p1.setWeight(100);
        p1.setHeight(100);
        p1.setExercise(ExerciseEnum.NONE);
        p1.setDrinking(DrinkingEnum.NONE);
        p1.setAge(20);
        p1.setPhone("0877-11-22-33");
        PatientEntity patientEntity = patientRepository.save(p1);

        UserEntity user1 = new UserEntity();
        user1.setUsername("username");
        user1.setEmail("user1@.com");
        user1.setPassword("1234");
        user1.getRoles().add(patient);
        user1.getRoles().add(user);

        user1.setPatientEntity(p1);

        patientId = patientEntity.getId();
    }

    @Test
    @WithMockUser(value = "username", roles = {"USER", "PATIENT"})
    public void shouldReturnCorrectStatus() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.
                get(PATIENTS_CONTROLLER_PREFIX +
                        "/patient/profile", patientId)).
                andExpect(status().isOk()).
                andExpect(view().
                        name("patient-profile/patient-profile")).
                andExpect(model().attributeExists("patient")).
                andExpect(model().attributeExists("user"));

    }


}
