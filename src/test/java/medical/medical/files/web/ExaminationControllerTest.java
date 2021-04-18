package medical.medical.files.web;

import medical.medical.files.model.enteties.DoctorEntity;
import medical.medical.files.model.enteties.ExaminationEntity;
import medical.medical.files.model.enteties.PatientEntity;
import medical.medical.files.model.enteties.ReviewsEntity;
import medical.medical.files.model.enums.*;
import medical.medical.files.repositorie.DoctorRepository;
import medical.medical.files.repositorie.ExaminationRepository;
import medical.medical.files.repositorie.PatientRepository;
import medical.medical.files.repositorie.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class ExaminationControllerTest {

    private static final String PATIENTS_CONTROLLER_PREFIX = "/examinations";
    @Autowired
    private ExaminationRepository examinationRepository;

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private MockMvc mockMvc;

    private long examinationId;


    @BeforeEach
    public void build() {
        doctorRepository.deleteAll();
        patientRepository.deleteAll();
        examinationRepository.deleteAll();
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

        DoctorEntity d1 = new DoctorEntity();
        d1.setFullName("doctor");
        d1.setExperience(1);
        d1.setMedicalBranch(MedicalBranchesEnum.DERMATOLOGY);
        d1.setRoomNumber("A1");
        d1.setBio("bio");
        d1.setPhoto("photo");
        d1.setPhone("0877-11-22-33");
        DoctorEntity doc = doctorRepository.save(d1);

        ExaminationEntity examinationEntity = new ExaminationEntity();
        examinationEntity.setProgression(ProgressionEnum.BOOKED);
        examinationEntity.setDoctor(d1);
        examinationEntity.setPatient(p1);
        examinationEntity.setDate(LocalDateTime.now());
        examinationEntity.setComplain("complain");
        ExaminationEntity entity = examinationRepository.save(examinationEntity);

        examinationId = entity.getId();
    }

//    @Test
//    @WithMockUser(value = "username", roles = {"USER", "PATIENT"})
//    public void shouldReturnCorrectStatusProfileTest
//            () throws Exception {
//
//        mockMvc.perform(MockMvcRequestBuilders.
//                get(PATIENTS_CONTROLLER_PREFIX +
//                        "/examination/{id}", examinationId)).
//                andExpect(status().isOk()).
//                andExpect(view().
//                        name("examination/single-examination-view-doctor-part")).
//                andExpect(model().attributeExists("examinationDetails"));
//    }

    @Test
    @WithMockUser(value = "username", roles = {"USER", "DOCTOR"})

    public void shouldReturnCorrectStatusForAddData() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.
                get(PATIENTS_CONTROLLER_PREFIX + "/examination/{id}/add-additional-data", examinationId)).
                andExpect(status().isOk()).
                andExpect(view().name("examination/add-additional-data")).
                andExpect(model().attributeExists("doctorId"));
//
    }

//    @Test
//    @WithMockUser(value = "username", roles = {"USER", "DOCTOR"})
//    public void shouldReturnCorrectStatusForDisease() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.
//                get(PATIENTS_CONTROLLER_PREFIX + "/examination/{id}/add-disease", examinationId)).
//                andExpect(status().isOk()).
//                andExpect(view().name("examination/add-disease")).
//
//
//                andExpect(model().attributeExists("examinationId"));
//    }
}

