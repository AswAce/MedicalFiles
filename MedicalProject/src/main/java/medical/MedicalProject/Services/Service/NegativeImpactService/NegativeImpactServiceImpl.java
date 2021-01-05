package medical.MedicalProject.Services.Service.NegativeImpactService;

import medical.MedicalProject.Repositories.NegativeImpactRepository;
import medical.MedicalProject.Services.SeedDto.SeedNegativeImpact.NegativeImpactDto;
import medical.MedicalProject.entities.NegativeImpact;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NegativeImpactServiceImpl implements NegativeImpactService {
    private NegativeImpactRepository negativeImpactRepository;
    private ModelMapper modelMapper;

    @Autowired
    public NegativeImpactServiceImpl(NegativeImpactRepository negativeImpactRepository, ModelMapper modelMapper) {
        this.negativeImpactRepository = negativeImpactRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addNegativeImpact(NegativeImpactDto negativeIm) {

        for (NegativeImpact negativeImpact : getAllNegativeImpacts()
        ) {
            if (negativeImpact.getType().equals(negativeIm.getType())
                    && negativeImpact.getPainLevel().equals(negativeIm.getPainLevel()) &&
                    negativeImpact.getDangerLevel() == negativeIm.getDangerLevel()
            ) {

                //Trhrow Error same impacts
                System.out.println("Negative impact already exist ");
                return;
            }
        }
        NegativeImpact negativeImpactForDb = this.modelMapper.map(negativeIm, NegativeImpact.class);
        this.negativeImpactRepository.saveAndFlush(negativeImpactForDb);

    }

    @Override
    public ArrayList<NegativeImpact> getAllNegativeImpacts() {
        return (ArrayList<NegativeImpact>) this.negativeImpactRepository.findAll();
    }

    @Override
    public NegativeImpact getNegativeImpacts(String negativeImpactName) {
        NegativeImpact negativeImpact = this.negativeImpactRepository.findByType(negativeImpactName);

        return negativeImpact;
    }
}
