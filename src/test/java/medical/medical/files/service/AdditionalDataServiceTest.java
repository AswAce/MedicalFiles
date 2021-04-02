package medical.medical.files.service;

import medical.medical.files.model.enteties.AdditionalDataEntity;
import medical.medical.files.model.enteties.LocationEntity;
import medical.medical.files.model.enums.PartOfTheBodyEnum;
import medical.medical.files.model.enums.SideOfTheBodyEnum;
import medical.medical.files.model.enums.TypeEnum;
import medical.medical.files.repositorie.AdditionalDataRepository;
import medical.medical.files.service.impl.AdditionalDataServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;


import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdditionalDataServiceTest {


    private LocationEntity locationEntityTest;
    private AdditionalDataEntity additionalDataEntityTest;
    private AdditionalDataServiceImpl serviceTest;
    @Mock
    AdditionalDataRepository additionalDataRepository;

    @BeforeEach
    public void init() {
        locationEntityTest = new LocationEntity();
        locationEntityTest.setSideOfTheBody(SideOfTheBodyEnum.LEFT);
        locationEntityTest.setPartOfTheBody(PartOfTheBodyEnum.HAND);
        locationEntityTest.setId(1);
        locationEntityTest.setExactLocation("test");
        additionalDataEntityTest = new AdditionalDataEntity();
        additionalDataEntityTest.setType(TypeEnum.BLOOD_TEST);
        additionalDataEntityTest.setFile("test");
        additionalDataEntityTest.setLocationEntity(locationEntityTest);
        additionalDataEntityTest.setId(1);
        additionalDataEntityTest.setDate(LocalDate.now());
        when(additionalDataRepository.save(additionalDataEntityTest)).thenReturn(additionalDataEntityTest);
        serviceTest = new AdditionalDataServiceImpl(new ModelMapper(), additionalDataRepository);
    }

    @Test
    public void save() {
        AdditionalDataEntity save = serviceTest.save(additionalDataEntityTest);


        Assertions.assertEquals("test", save.getFile());
        Assertions.assertEquals(1, save.getLocationEntity().getId());
        Assertions.assertEquals(TypeEnum.BLOOD_TEST, save.getType());
        Assertions.assertEquals(LocalDate.now(), save.getDate());

        Assertions.assertEquals(PartOfTheBodyEnum.HAND, save.getLocationEntity().getPartOfTheBody());
        Assertions.assertEquals(SideOfTheBodyEnum.LEFT, save.getLocationEntity().getSideOfTheBody());
        Assertions.assertEquals("test", save.getLocationEntity().getExactLocation());
        Assertions.assertEquals(1, save.getLocationEntity().getId());

    }
}
