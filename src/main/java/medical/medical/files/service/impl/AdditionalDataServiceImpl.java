package medical.medical.files.service.impl;

import medical.medical.files.repositorie.AdditionalDataRepository;
import medical.medical.files.service.AdditionalDataService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class AdditionalDataServiceImpl implements AdditionalDataService {
    private final ModelMapper modelMapper;
    private final AdditionalDataRepository additionalDataRepository;

    public AdditionalDataServiceImpl(ModelMapper modelMapper, AdditionalDataRepository additionalDataRepository) {
        this.modelMapper = modelMapper;
        this.additionalDataRepository = additionalDataRepository;
    }



}
