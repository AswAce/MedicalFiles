package medical.medical.files.service.impl;

import medical.medical.files.model.enteties.AdditionalDataEntity;
import medical.medical.files.repositorie.AdditionalDataRepository;
import medical.medical.files.service.AdditionalDataService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class AdditionalDataServiceImpl implements AdditionalDataService {

    private final AdditionalDataRepository additionalDataRepository;

    public AdditionalDataServiceImpl(ModelMapper modelMapper, AdditionalDataRepository additionalDataRepository) {

        this.additionalDataRepository = additionalDataRepository;
    }


    @Override
    public AdditionalDataEntity save(AdditionalDataEntity additionalDataEntity) {
        return this.additionalDataRepository.save(additionalDataEntity);
    }
}
