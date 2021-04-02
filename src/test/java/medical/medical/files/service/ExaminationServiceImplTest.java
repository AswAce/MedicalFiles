package medical.medical.files.service;


import medical.medical.files.config.currentUser.IAuthenticationFacade;
import medical.medical.files.exeptions.DoctorNotFoundExeption;
import medical.medical.files.exeptions.ExaminationNotFoundException;
import medical.medical.files.model.enteties.*;
import medical.medical.files.model.enums.MedicalBranchesEnum;
import medical.medical.files.model.enums.PartOfTheBodyEnum;
import medical.medical.files.model.enums.ProgressionEnum;
import medical.medical.files.model.viewModels.ExaminationViewModel;
import medical.medical.files.model.viewModels.SetExaminationsForUserView;
import medical.medical.files.repositorie.ExaminationRepository;
import medical.medical.files.service.impl.CloudinaryServiceImpl;
import medical.medical.files.service.impl.DoctorServiceImpl;
import medical.medical.files.service.impl.ExaminationServiceImpl;
import medical.medical.files.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExaminationServiceImplTest {

    private    ExaminationEntity examination1, examination2;
    private    DoctorEntity doctor1;
    private    PatientEntity patient1;
    private    LocationEntity location1;
    private    PrescriptionEntity prescription1;
    private    ExaminationServiceImpl serviceTest;
    private    DoctorServiceImpl doctorService;
    private    UserServiceImpl userService;
    private    CloudinaryServiceImpl cloudinaryService;
    @Mock
    ExaminationRepository examinationRepositoryTest;
    IAuthenticationFacade authenticationFacade;

    @BeforeEach
    public void init() {
        location1 = new LocationEntity();
        location1.setId(1);
        location1.setPartOfTheBody(PartOfTheBodyEnum.BACK);
        prescription1 = new PrescriptionEntity();
        prescription1.setDoctorPrescription("prescription");
        doctor1 = new DoctorEntity();
        doctor1.setId(1);
        patient1 = new PatientEntity();
        patient1.setId(1);
        patient1.setFullName("patient name");

        examination1 = new ExaminationEntity();
        examination1.setProgression(ProgressionEnum.BOOKED);
        examination1.setComplain("complain1");
        examination1.setId(1);
        examination1.setDate(LocalDateTime.now());
        examination1.setTypeOfBranch(MedicalBranchesEnum.DERMATOLOGY);
        examination1.setDoctor(doctor1);
        examination1.setPatient(patient1);
        examination1.setLocation(location1);
        examination1.setPrescription(prescription1);

        examination2 = new ExaminationEntity();
        examination2.setProgression(ProgressionEnum.BOOKED);
        examination2.setComplain("complain2");
        examination2.setId(2);
        examination2.setDate(LocalDateTime.now());
        examination2.setTypeOfBranch(MedicalBranchesEnum.DERMATOLOGY);
        examination2.setDoctor(doctor1);
        examination2.setPatient(patient1);
        examination2.setLocation(location1);
        examination2.setPrescription(prescription1);

        serviceTest = new ExaminationServiceImpl(new ModelMapper(),
                examinationRepositoryTest, doctorService,
                userService, cloudinaryService, authenticationFacade);
    }


    @Test
    public void countExaminationsTest() {
        long count = examinationRepositoryTest.count();
        ;
        Assertions.assertEquals(serviceTest.countAll(), count);

    }

    @Test
    public void getExaminationByDoctorIdTest() {
        when(examinationRepositoryTest.findAllByDoctorId(1)).
                thenReturn(Set.of(examination1, examination2));
        ArrayList<ExaminationViewModel> byDoctorId = serviceTest.findByDoctorId(1);
        Assertions.assertEquals(byDoctorId.size(), 2);

    }

    @Test
    public void findAllByPatientIdAndDepartmentTest() {
        when(examinationRepositoryTest.findAllByPatientIdAndTypeOfBranch(1, MedicalBranchesEnum.DERMATOLOGY)).thenReturn(List.of(examination1, examination2));
        ArrayList<ExaminationViewModel> allByPatientIdAndDepartment = serviceTest.findAllByPatientIdAndDepartment(1, MedicalBranchesEnum.DERMATOLOGY);

        Assertions.assertEquals(allByPatientIdAndDepartment.size(), 2);
        Assertions.assertEquals(allByPatientIdAndDepartment.get(0).getId(), examination1.getId());
        Assertions.assertEquals(allByPatientIdAndDepartment.get(0).getPatientId(), examination1.getPatient().getId());
        Assertions.assertEquals(allByPatientIdAndDepartment.get(0).getDoctorId(), examination1.getDoctor().getId());
        Assertions.assertEquals(allByPatientIdAndDepartment.get(0).getComplain(), examination1.getComplain());
        Assertions.assertEquals(allByPatientIdAndDepartment.get(0).getLocation().getPartOfTheBody(), examination1.getLocation().getPartOfTheBody());
        Assertions.assertEquals(allByPatientIdAndDepartment.get(0).getPrescription().getDoctorPrescription(), examination1.getPrescription().getDoctorPrescription());
        Assertions.assertEquals(allByPatientIdAndDepartment.get(0).getDate(), examination1.getDate());
    }

    @Test
    public void getAllTest() {
        when(examinationRepositoryTest.findAll()).thenReturn(List.of(examination1, examination2));

        ArrayList<SetExaminationsForUserView> all = serviceTest.getAll();

        Assertions.assertEquals(all.size(), 2);

        Assertions.assertEquals(all.get(0).getId(), examination1.getId());
        Assertions.assertEquals(all.get(0).getComplain(), examination1.getComplain());
        Assertions.assertEquals(all.get(0).getPatientName(), examination1.getPatient().getFullName());
        Assertions.assertEquals(all.get(0).getProgressionEnum(), examination1.getProgression());

        Assertions.assertEquals(all.get(1).getId(), examination2.getId());
        Assertions.assertEquals(all.get(1).getComplain(), examination2.getComplain());
        Assertions.assertEquals(all.get(1).getPatientName(), examination2.getPatient().getFullName());
        Assertions.assertEquals(all.get(1).getProgressionEnum(), examination2.getProgression());


    }


}



