package medical.medical.files.service.impl;

import medical.medical.files.model.enteties.DiseaseEntity;
import medical.medical.files.repositorie.DiseaseRepository;
import medical.medical.files.service.DiseaseService;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DiseaseServiceImpl implements DiseaseService {
    private final DiseaseRepository diseaseRepository;

    public DiseaseServiceImpl(DiseaseRepository diseaseRepository) {
        this.diseaseRepository = diseaseRepository;
    }




    @Override
    public Set<DiseaseEntity> findAllByName(String name) {
        return this.diseaseRepository.
                findAllByName(name);
    }



    @Override
    public Set<String> getAllDiseasesNames() {
        return this.diseaseRepository.findAll().stream().map(DiseaseEntity::getName).collect(Collectors.toSet());

    }
}
