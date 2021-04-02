package medical.medical.files.service;

import medical.medical.files.model.enteties.DoctorEntity;
import medical.medical.files.model.enteties.PatientEntity;
import medical.medical.files.model.enteties.RoleEntity;
import medical.medical.files.model.enteties.UserEntity;
import medical.medical.files.model.enums.RoleEnum;
import medical.medical.files.model.viewModels.UserViewModel;
import medical.medical.files.repositorie.UserRepository;
import medical.medical.files.service.impl.MedicalBranchesServiceImpl;
import medical.medical.files.service.impl.RoleServiceImpl;
import medical.medical.files.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    private UserEntity patient, doctor, admin;
    private UserServiceImpl serviceTest;

    private PasswordEncoder passwordEncoder;
    private RoleServiceImpl roleService;
    private MedicalBranchesServiceImpl medicalBranchesService;

    @Mock
    UserRepository repositoryTest;

    @BeforeEach
    public void init() {
        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setFullName("testName");
        patientEntity.setId(1);
        DoctorEntity doctorEntity = new DoctorEntity();
        doctorEntity.setFullName("doctorTest");
        doctorEntity.setId(1);

        RoleEntity roleUser = new RoleEntity();
        roleUser.setRole(RoleEnum.USER);
        roleUser.setId(1);
        RoleEntity rolePatient = new RoleEntity();

        rolePatient.setRole(RoleEnum.PATIENT);
        rolePatient.setId(2);
        RoleEntity roleDoctor = new RoleEntity();

        roleDoctor.setRole(RoleEnum.DOCTOR);
        roleDoctor.setId(3);

        RoleEntity roleAdmin = new RoleEntity();
        roleAdmin.setRole(RoleEnum.ADMIN);
        roleAdmin.setId(4);


        patient = new UserEntity();
        patient.getRoles().add(rolePatient);
        patient.getRoles().add(roleUser);

        patient.setPassword("password");
        patient.setEmail("email@");
        patient.setUsername("patient");
        patient.setId(1);
        patient.setPatientEntity(patientEntity);

        doctor = new UserEntity();
        doctor.getRoles().add(roleDoctor);
        doctor.getRoles().add(roleUser);
        doctor.setPassword("passwordDoc");
        doctor.setEmail("email@doc");
        doctor.setUsername("doc");
        doctor.setId(2);
        doctor.setDoctorEntity(doctorEntity);

        admin = new UserEntity();
        admin.setId(3);
        admin.setUsername("admin");
        admin.setPassword("adminpassword");
        admin.setEmail("admin@");
        admin.getRoles().add(roleAdmin);
        admin.getRoles().add(rolePatient);
        admin.getRoles().add(roleUser);
        admin.getRoles().add(roleDoctor);

        serviceTest = new UserServiceImpl(repositoryTest, new ModelMapper(),
                passwordEncoder, roleService, medicalBranchesService);
    }


    @Test
    void findByEmailTest() {
        when(repositoryTest.findByEmail("email@")).thenReturn(Optional.ofNullable(patient));
        Assertions.assertEquals(serviceTest.findByEmail("email@"), patient);
    }

    @Test
    void findByEmailTestError() {

        Assertions.assertNull(serviceTest.findByEmail("fakeEmail@.com"));
    }

    @Test
    public void findAllTest() {
        when(repositoryTest.findAll()).thenReturn(List.of(patient,doctor,admin));

        ArrayList<UserViewModel> all = serviceTest.findAll();
        Assertions.assertEquals(all.size(),3);
        Assertions.assertEquals(all.get(0).getId(),patient.getId());
        Assertions.assertEquals(all.get(1).getId(),doctor.getId());
        Assertions.assertEquals(all.get(2).getId(),admin.getId());
    }


}
