package medical.medical.files.web;

import medical.medical.files.model.enteties.DaySchedule;
import medical.medical.files.model.enteties.DoctorEntity;
import medical.medical.files.model.enteties.MedicalBranchEntity;
import medical.medical.files.model.enteties.ScheduleEntity;
import medical.medical.files.model.enums.DayEnum;
import medical.medical.files.model.enums.MedicalBranchesEnum;
import medical.medical.files.repositorie.DoctorRepository;
import medical.medical.files.repositorie.MedicalBranchesRepository;
import medical.medical.files.repositorie.ScheduleRepository;
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

import java.time.LocalTime;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class DepartmentControllerTest {
    @Autowired
    MedicalBranchesRepository medicalBranchesRepository;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ScheduleRepository scheduleRepository;


    private long depId;


    @BeforeEach
    public void init() {

        medicalBranchesRepository.deleteAll();
        scheduleRepository.deleteAll();

        MedicalBranchEntity medicalBranchEntity = new MedicalBranchEntity();
        medicalBranchEntity.setPhoto("photo");
        medicalBranchEntity.setDescription("description");
        medicalBranchEntity.setName(MedicalBranchesEnum.DERMATOLOGY);
        ScheduleEntity scheduleEntity = new ScheduleEntity();
        DaySchedule daySchedule = new DaySchedule();
        daySchedule.setDay(DayEnum.MONDAY);
        daySchedule.setStartTime(LocalTime.NOON);
        daySchedule.setEndTime(LocalTime.MIDNIGHT);
        scheduleEntity.getDays().add(daySchedule);
        medicalBranchEntity.setSchedule(scheduleEntity);
        MedicalBranchEntity save = medicalBranchesRepository.save(medicalBranchEntity);
        depId = save.getId();
    }

    @AfterEach
    public void delete() {
        medicalBranchesRepository.deleteAll();
        scheduleRepository.deleteAll();
    }

    @Test
    @WithMockUser(value = "username", roles = {"USER", "PATIENT"})
    public void shouldReturnCorrectStatusAllDepartmentTest
            () throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.
                get("/departments" +
                        "/all", depId)).
                andExpect(status().isOk()).
                andExpect(view().
                        name("hospital-departments/hospital-departments")).
                andExpect(model().attributeExists("departments"));
    }


}
