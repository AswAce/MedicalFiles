package medical.medical.files.service;


import medical.medical.files.model.enteties.DiseaseEntity;
import medical.medical.files.repositorie.DiseaseRepository;
import medical.medical.files.service.impl.DiseaseServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DiseaseServiceImplTest {

    private DiseaseEntity test1, test2, test3;
    private DiseaseServiceImpl serviceToTest;
    @Mock
    DiseaseRepository diseaseRepositoryTest;


    @BeforeEach
    public void setUp() {

        serviceToTest = new DiseaseServiceImpl(diseaseRepositoryTest);
        test1 = new DiseaseEntity();
        test1.setName("name1");
        test1.setType("type1");
        test1.setId(1);
        test1.setChronic(true);
        test1.setCurable(true);
        test1.setDescription("test1");
        test2 = new DiseaseEntity();
        test2.setName("name2");
        test2.setType("type2");
        test2.setId(1);
        test2.setChronic(true);
        test2.setCurable(true);
        test2.setDescription("test2");
        test3 = new DiseaseEntity();
        test3.setName("name1");
        test3.setType("type3");
        test3.setId(3);
        test3.setChronic(true);
        test3.setCurable(true);
        test3.setDescription("test3");




        serviceToTest = new DiseaseServiceImpl(diseaseRepositoryTest);

    }

    @Test
    public void findAllByNameTest() {
        when(diseaseRepositoryTest.findAllByName("name1")).thenReturn(( Set.of(test1,test3)));

        Set<DiseaseEntity> allByName = serviceToTest.findAllByName("name1");

        Assertions.assertEquals(2, allByName.size());
    }

    ;

    @Test
    public void getAllDiseasesNamesTest() {
        when(diseaseRepositoryTest.findAll()).thenReturn(List.of(test1, test2, test3));

        Set<String> allDiseasesNames = serviceToTest.getAllDiseasesNames();

        Assertions.assertTrue(allDiseasesNames.contains("name1"));
        Assertions.assertTrue(allDiseasesNames.contains("name2"));
    }


}
