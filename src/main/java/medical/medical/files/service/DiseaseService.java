package medical.medical.files.service;

import medical.medical.files.model.enteties.DiseaseEntity;

import java.util.Set;

public interface DiseaseService {

    Set<DiseaseEntity> findAllByName(String name);

    Set<String> getAllDiseasesNames();
}
