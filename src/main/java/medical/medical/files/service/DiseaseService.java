package medical.medical.files.service;

import medical.medical.files.model.enteties.DiseaseEntity;

public interface DiseaseService {
    void save(DiseaseEntity diseaseEntity);

    void deleteDisease(String name);

    DiseaseEntity findByName(String name);
}
