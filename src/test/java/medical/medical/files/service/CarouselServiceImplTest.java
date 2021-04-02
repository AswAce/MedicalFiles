package medical.medical.files.service;

import medical.medical.files.model.enums.MedicalBranchesEnum;
import medical.medical.files.model.viewModels.DoctorSetViewModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;


@ExtendWith(MockitoExtension.class)
public class CarouselServiceImplTest {
    private DoctorSetViewModel modelTest1, modelTest2, modelTest3;
    @Mock
    private List<DoctorSetViewModel> doctorSetViewModelsTest;

    @BeforeEach
    public void init() {
        modelTest1 = new DoctorSetViewModel();
        modelTest1.setFullName("test1");
        modelTest1.setPhoto("photo1");
        modelTest1.setId(1);
        modelTest1.setMedicalBranchesEnum(MedicalBranchesEnum.CARDIOLOGY);
        modelTest2 = new DoctorSetViewModel();
        modelTest2.setFullName("test2");
        modelTest2.setPhoto("photo2");
        modelTest2.setId(2);
        modelTest2.setMedicalBranchesEnum(MedicalBranchesEnum.CARDIOLOGY);
        modelTest3 = new DoctorSetViewModel();
        modelTest3.setFullName("test3");
        modelTest3.setPhoto("photo3");
        modelTest3.setId(3);
        modelTest3.setMedicalBranchesEnum(MedicalBranchesEnum.CARDIOLOGY);

        doctorSetViewModelsTest = new ArrayList<>();
        doctorSetViewModelsTest.add(modelTest1);
        doctorSetViewModelsTest.add(modelTest2);
        doctorSetViewModelsTest.add(modelTest3);



    }


    @Test
    public void getCarouselDoctorsTests() {
        Assertions.assertEquals(3, doctorSetViewModelsTest.size());

        Assertions.assertEquals(1, doctorSetViewModelsTest.get(0).getId());
        Assertions.assertEquals(2, doctorSetViewModelsTest.get(1).getId());
        Assertions.assertEquals(3, doctorSetViewModelsTest.get(2).getId());

        Assertions.assertEquals("test1", doctorSetViewModelsTest.get(0).getFullName());
        Assertions.assertEquals("test2", doctorSetViewModelsTest.get(1).getFullName());
        Assertions.assertEquals("test3", doctorSetViewModelsTest.get(2).getFullName());

        Assertions.assertEquals("photo1", doctorSetViewModelsTest.get(0).getPhoto());
        Assertions.assertEquals("photo2", doctorSetViewModelsTest.get(1).getPhoto());
        Assertions.assertEquals("photo3", doctorSetViewModelsTest.get(2).getPhoto());

        Assertions.assertEquals(MedicalBranchesEnum.CARDIOLOGY, doctorSetViewModelsTest.get(0).getMedicalBranchesEnum());
        Assertions.assertEquals(MedicalBranchesEnum.CARDIOLOGY, doctorSetViewModelsTest.get(1).getMedicalBranchesEnum());
        Assertions.assertEquals(MedicalBranchesEnum.CARDIOLOGY, doctorSetViewModelsTest.get(2).getMedicalBranchesEnum());
    }


}
