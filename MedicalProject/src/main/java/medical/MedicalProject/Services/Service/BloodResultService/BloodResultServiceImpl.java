package medical.MedicalProject.Services.Service.BloodResultService;

import medical.MedicalProject.Repositories.BloodResultRepository;
import medical.MedicalProject.Services.SeedDto.SeetBloodResult.BloodResultDto;
import medical.MedicalProject.Services.ViewDto.BloodResultView.BloodResultDtoView;
import medical.MedicalProject.entities.BloodResult;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BloodResultServiceImpl implements BloodResultService {
    private BloodResultRepository bloodResultRepository;
    private ModelMapper modelMapper;

    @Autowired
    public BloodResultServiceImpl(BloodResultRepository bloodResultRepository, ModelMapper modelMapper) {
        this.bloodResultRepository = bloodResultRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void saveBloodResult(BloodResultDto bloodResultDto) {
        this.bloodResultRepository.saveAndFlush(this.modelMapper.map(bloodResultDto, BloodResult.class));
    }

    @Override
    public ArrayList<BloodResultDtoView> getAllBloodServiceForHealthProfile(int id) {
        ArrayList<BloodResult> byHealthProfileId = this.bloodResultRepository.findByHealthProfileIdEquals(id);

        List<BloodResultDtoView> collect = byHealthProfileId.stream().map(b -> this.modelMapper.map(b, BloodResultDtoView.class)).collect(Collectors.toList());
        return (ArrayList<BloodResultDtoView>) collect;
    }
}
