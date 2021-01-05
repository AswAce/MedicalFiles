package medical.MedicalProject.Services.Service.DiseaseResultService;

import medical.MedicalProject.Repositories.DiseaseRepository;
import medical.MedicalProject.Services.SeedDto.SeedDisease.DiseaseDto;
import medical.MedicalProject.Services.Service.LocationService.LocationService;
import medical.MedicalProject.Services.Service.MedicineBranchService.MedicineBranchService;
import medical.MedicalProject.Services.Service.NegativeImpactService.NegativeImpactService;
import medical.MedicalProject.entities.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class DiseaseResultServiceImpl implements DiseaseResultService {
    private ModelMapper modelMapper;
    private DiseaseRepository diseaseRepository;
    private LocationService locationService;
    private MedicineBranchService medicineBranchService;
    private NegativeImpactService negativeImpactService;

    @Autowired
    public DiseaseResultServiceImpl(ModelMapper modelMapper, DiseaseRepository diseaseRepository, LocationService locationService, MedicineBranchService medicineBranchService, NegativeImpactService negativeImpactService) {
        this.modelMapper = modelMapper;
        this.diseaseRepository = diseaseRepository;
        this.locationService = locationService;
        this.medicineBranchService = medicineBranchService;


        this.negativeImpactService = negativeImpactService;
    }

    @Override
    public void saveDisease(DiseaseDto diseaseDto) {
        Disease diseaseForDb = this.modelMapper.map(diseaseDto, Disease.class);

        //Set location
        String locations = diseaseDto.getLocations();
        Location locationByName = this.locationService.getLocationByName(locations);
        diseaseForDb.setDiseaseLocation(locationByName);

        //Set medical branch
        String medicineBranch = diseaseDto.getMedicineBranch();
        MedicineBranch medicalBranchByName = this.medicineBranchService.getMedicalBranchByName(medicineBranch);
        diseaseForDb.setMedicineBranch(medicalBranchByName);

        //Set negative impacts from list with their type name
        ;
        Set<NegativeImpact> getByType = new HashSet<>();
        for (String type : diseaseDto.getNegativeImpact()
        ) {
            NegativeImpact negativeImpact = this.negativeImpactService.getNegativeImpacts(type);
            if (negativeImpact != null) {
                getByType.add(negativeImpact);
            } else {
                ///rhrow exepciot
                System.out.println(type + " is not a negative impact");
            }


        }

        diseaseForDb.setNegativeImpacts(getByType);

        //set visual result for deesease
        VisualResult visualResult = this.modelMapper.
                map(diseaseDto.getVisualResultDto(), VisualResult.class);
        diseaseForDb.setVisualResult(visualResult);
        this.diseaseRepository.save(diseaseForDb);
    }

}
