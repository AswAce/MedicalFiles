package medical.medical.files.service;

import medical.medical.files.config.currentUser.IAuthenticationFacade;
import medical.medical.files.exeptions.DoctorNotFoundExeption;
import medical.medical.files.model.enteties.DaySchedule;
import medical.medical.files.model.enteties.DoctorEntity;
import medical.medical.files.model.enteties.ScheduleEntity;
import medical.medical.files.model.enums.DayEnum;
import medical.medical.files.model.enums.MedicalBranchesEnum;
import medical.medical.files.model.viewModels.DoctorSetViewModel;
import medical.medical.files.model.viewModels.SingleDoctorView;
import medical.medical.files.repositorie.DoctorRepository;
import medical.medical.files.service.impl.DoctorServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;


import java.time.LocalTime;

import java.util.Optional;


import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DoctorServiceImplTest {

    private DoctorEntity doctorTest, doctorTest2;

    private ScheduleEntity scheduleTest;
    private DaySchedule dayTest;
    private DoctorServiceImpl doctorServiceTest;
    @Autowired
    private CloudinaryService cloudinaryServiceTest;
    @Autowired
    private UserService userServiceTest;
    @Autowired
    private ScheduleService scheduleServiceTest;
    @Autowired
    private MedicalBranchesService medicalBranchesServiceTest;
    @Autowired
    private IAuthenticationFacade authenticationFacadeTest;
    @Mock
    DoctorRepository doctorRepositoryTest;

    @BeforeEach
    public void init() {

        dayTest = new DaySchedule();
        dayTest.setId(1);
        dayTest.setDay(DayEnum.MONDAY);
        dayTest.setStartTime(LocalTime.now());
        dayTest.setEndTime(LocalTime.now());

        scheduleTest = new ScheduleEntity();
        scheduleTest.setId(1);
        scheduleTest.getDays().add(dayTest);

        doctorTest = new DoctorEntity();
        doctorTest.setFullName("name1");
        doctorTest.setPhoto("photo");
        doctorTest.setBio("bio");
        doctorTest.setRoomNumber("a1");
        doctorTest.setMedicalBranch(MedicalBranchesEnum.DERMATOLOGY);
        doctorTest.setId(1);
        doctorTest.setExperience(1);
        doctorTest.setSchedule(scheduleTest);

        doctorTest2 = new DoctorEntity();
        doctorTest2.setFullName("name2");
        doctorTest2.setPhoto("photo2");
        doctorTest2.setBio("bio2");
        doctorTest2.setRoomNumber("a2");
        doctorTest2.setMedicalBranch(MedicalBranchesEnum.DERMATOLOGY);
        doctorTest2.setId(2);
        doctorTest2.setExperience(2);
        doctorTest2.setSchedule(scheduleTest);

        doctorServiceTest = new DoctorServiceImpl(doctorRepositoryTest,
                new ModelMapper(), cloudinaryServiceTest, userServiceTest, scheduleServiceTest,
                medicalBranchesServiceTest, authenticationFacadeTest

        );
    }

    @Test
    public void findByNameTest() {

        Mockito.when(doctorRepositoryTest.findByFullName("name1")).
                thenReturn(Optional.ofNullable((doctorTest)));

        DoctorEntity name1 = doctorServiceTest.findByName("name1");
        Assertions.assertEquals(name1.getFullName(), doctorTest.getFullName());
        Assertions.assertEquals(doctorTest.getMedicalBranch(), name1.getMedicalBranch());
        Assertions.assertEquals(doctorTest.getPhoto(), name1.getPhoto());
        Assertions.assertEquals(doctorTest.getBio(), name1.getBio());
        Assertions.assertEquals(doctorTest.getRoomNumber(), name1.getRoomNumber());
        Assertions.assertEquals(doctorTest.getId(), name1.getId());
        Assertions.assertEquals(doctorTest.getSchedule().getId(), name1.getSchedule().getId());
        Assertions.assertEquals(doctorTest.getSchedule().getDays().size(), name1.getSchedule().getDays().size());
        Assertions.assertEquals(doctorTest.getExperience(), name1.getExperience());


    }

    @Test
    public void findByNameNotFoundDoctorTest() {
        Assertions.assertThrows(DoctorNotFoundExeption.class, () -> {
            doctorServiceTest.findByName("Doctor fake name ");
        });
    }

    @Test
    public void saveDoctorToProfileTest() {
    }

//    @Test
//    public void getAllTest() {
//        Set<DoctorSetViewModel> all = doctorServiceTest.getAll();
//        Mockito.when(doctorRepositoryTest.findAll()).thenReturn(List.of(doctorTest, doctorTest2));
//
////        Assertions.assertTrue(all.contains(doctorTest));
////        Assertions.assertTrue(all.contains(doctorTest2));
//        Assertions.assertEquals(all.size(),2);
//
//
//    }

    @Test
    public void getCountTest() {
        when(doctorRepositoryTest.findTopByOrderByIdDesc()).thenReturn(Optional.ofNullable(doctorTest));
        long count = doctorServiceTest.getCount();
        Assertions.assertEquals(count, doctorTest.getId());
    }

    @Test
    public void findByIdSetViewDoctorTestNotFound() {
        Assertions.assertThrows(DoctorNotFoundExeption.class, () -> {
            doctorServiceTest.findById(133333);
        });
    }

    @Test
    public void findByIdSetViewDoctorTest() {
        when(doctorRepositoryTest.findById(1l)).thenReturn(Optional.ofNullable(doctorTest));
        SingleDoctorView byId = doctorServiceTest.findById(1);
        Assertions.assertEquals(byId.getFullName(), doctorTest.getFullName());

        Assertions.assertEquals(doctorTest.getPhoto(), byId.getPhoto());
        Assertions.assertEquals(doctorTest.getBio(), byId.getBio());

        Assertions.assertEquals(doctorTest.getId(), byId.getId());

        Assertions.assertEquals(doctorTest.getExperience(), byId.getExperience());


    }

    @Test
    public void findByIdEntityTest() {
        when(doctorRepositoryTest.findById(1l)).thenReturn(Optional.ofNullable(doctorTest));
        DoctorEntity byIdEntity = doctorServiceTest.findByIdEntity(1L);
        Assertions.assertEquals(byIdEntity.getFullName(), doctorTest.getFullName());
        Assertions.assertEquals(doctorTest.getMedicalBranch(), byIdEntity.getMedicalBranch());
        Assertions.assertEquals(doctorTest.getPhoto(), byIdEntity.getPhoto());
        Assertions.assertEquals(doctorTest.getBio(), byIdEntity.getBio());
        Assertions.assertEquals(doctorTest.getRoomNumber(), byIdEntity.getRoomNumber());
        Assertions.assertEquals(doctorTest.getId(), byIdEntity.getId());
        Assertions.assertEquals(doctorTest.getSchedule().getId(), byIdEntity.getSchedule().getId());
        Assertions.assertEquals(doctorTest.getSchedule().getDays().size(), byIdEntity.getSchedule().getDays().size());
        Assertions.assertEquals(doctorTest.getExperience(), byIdEntity.getExperience());
    }

    @Test
    public void findByIdEntityTestNotFound() {
        Assertions.assertThrows(DoctorNotFoundExeption.class, () -> doctorServiceTest.findByIdEntity(12345654321L));
    }

}
