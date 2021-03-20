package medical.medical.files.service.impl;

import medical.medical.files.model.enteties.DiseaseEntity;
import medical.medical.files.repositorie.DiseaseRepository;
import medical.medical.files.service.DiseaseService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DiseaseServiceImpl implements DiseaseService {
    private final DiseaseRepository diseaseRepository;

    public DiseaseServiceImpl(DiseaseRepository diseaseRepository) {
        this.diseaseRepository = diseaseRepository;
    }

    @Override
    public void save(DiseaseEntity diseaseEntity) {

        if (this.findByName(diseaseEntity.getName()) == null) {
            this.diseaseRepository.save(diseaseEntity);
        }

    }

    @Override
    public void deleteDisease(String name) {
        DiseaseEntity byName = this.findByName(name);
        this.diseaseRepository.delete(byName);
    }
    @Override
    public DiseaseEntity findByName(String name) {
        return this.diseaseRepository.
                findByName(name).orElse(null);
    }
}
