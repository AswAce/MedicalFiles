package medical.medical.files.model.serviceModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import medical.medical.files.model.enums.PartOfTheBodyEnum;
import medical.medical.files.model.enums.SideOfTheBodyEnum;
import medical.medical.files.model.enums.TypeEnum;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdditionalDataServiceModel {


    private long examinationId;
    private LocalDate date;


    private TypeEnum type;


    private MultipartFile file;


    private PartOfTheBodyEnum partOfTheBody;

    private SideOfTheBodyEnum sideOfTheBody;


    private String exactLocation;


}
