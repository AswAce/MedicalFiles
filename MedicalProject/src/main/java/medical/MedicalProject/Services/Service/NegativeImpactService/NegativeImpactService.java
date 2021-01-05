package medical.MedicalProject.Services.Service.NegativeImpactService;

import medical.MedicalProject.Services.SeedDto.SeedNegativeImpact.NegativeImpactDto;
import medical.MedicalProject.entities.NegativeImpact;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Set;


@Service
public interface NegativeImpactService {

    void addNegativeImpact(NegativeImpactDto negativeIm);

    ArrayList<NegativeImpact> getAllNegativeImpacts();

    NegativeImpact  getNegativeImpacts(String negativeImpactName);
}
