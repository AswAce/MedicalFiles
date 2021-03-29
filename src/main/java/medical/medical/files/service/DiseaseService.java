package medical.medical.files.service;

import medical.medical.files.model.enteties.DiseaseEntity;

import java.util.Set;

public interface DiseaseService {
    void save(DiseaseEntity diseaseEntity);



    Set<DiseaseEntity> findAllByName(String name);

    DiseaseEntity findByNameAndType(String name, String type);

    Set<String> getAllDiseasesNames();
}
