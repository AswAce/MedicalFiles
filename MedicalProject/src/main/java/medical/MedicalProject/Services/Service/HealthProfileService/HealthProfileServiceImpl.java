package medical.MedicalProject.Services.Service.HealthProfileService;

import com.fasterxml.jackson.annotation.JsonIgnore;
import medical.MedicalProject.Repositories.BloodResultRepository;
import medical.MedicalProject.Repositories.HealthProfileRepository;
import medical.MedicalProject.Repositories.UserRepository;
import medical.MedicalProject.Services.SeedDto.SeedHealthProfile.HealthProfileDto;
import medical.MedicalProject.Services.SeedDto.SeedUser.UserDto;
import medical.MedicalProject.Services.SeedDto.SeetBloodResult.BloodResultDto;
import medical.MedicalProject.Services.Service.BloodResultService.BloodResultService;
import medical.MedicalProject.Services.Service.UserService.UserService;
import medical.MedicalProject.Services.ViewDto.BloodResultView.BloodResultDtoView;
import medical.MedicalProject.Services.ViewDto.HealthProfileView.HealthProfileViewDto;
import medical.MedicalProject.entities.BloodResult;
import medical.MedicalProject.entities.HealthProfile;
import medical.MedicalProject.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

@Service
public class HealthProfileServiceImpl implements HealthProfileService {
    private ModelMapper modelMapper;
    private HealthProfileRepository healthProfileRepository;
    private UserRepository userRepository;
    private UserService userService;
    private BloodResultService bloodResultService;
    private BloodResultRepository bloodResultRepository;

    public HealthProfileServiceImpl(ModelMapper modelMapper, HealthProfileRepository healthProfileRepository, UserRepository userRepository, UserService userService, BloodResultService bloodResultService, BloodResultRepository bloodResultRepository) {
        this.modelMapper = modelMapper;
        this.healthProfileRepository = healthProfileRepository;
        this.userRepository = userRepository;

        this.userService = userService;
        this.bloodResultService = bloodResultService;
        this.bloodResultRepository = bloodResultRepository;
    }

    @Override
    public void createHealthProfile(HealthProfileDto healthProfileDto) {

        String userEmail = healthProfileDto.getUserEmail();
        User byEmail = this.userService.findByEmail(userEmail);
        HealthProfile healthProfile = this.modelMapper.map(healthProfileDto, HealthProfile.class);
        healthProfile.setUser(byEmail);

        this.healthProfileRepository.save(healthProfile);
        byEmail.setHealthProfile(healthProfile);
        this.userRepository.saveAndFlush(byEmail);

    }

    @Override
    public void addBloodResult(BloodResultDto bloodResultDto) {
        User byEmail = this.userService.findByEmail(bloodResultDto.getEmail());


        BloodResult bloodResult = this.modelMapper.map(bloodResultDto, BloodResult.class);
        bloodResult.setHealthProfile(byEmail.getHealthProfile());
        this.bloodResultRepository.saveAndFlush(bloodResult);
//        byEmail.getHealthProfile().getBloodResults().add(bloodResult);
//        this.userRepository.saveAndFlush(byEmail);

    }

    @Override
    public HealthProfileViewDto getHealthProfile(int id) {

        HealthProfile one = getHealthProfileById(id);

        User user = one.getUser();
        ArrayList<BloodResultDtoView> allBloodServiceForHealthProfile = this.bloodResultService.getAllBloodServiceForHealthProfile((int) 1);
        System.out.println();
        HealthProfileViewDto map = this.modelMapper.map(one, HealthProfileViewDto.class);
        map.setUserDto(this.modelMapper.map(user, UserDto.class));
        map.setBloodResultDtos(allBloodServiceForHealthProfile);

        return map;
    }

    private HealthProfile getHealthProfileById(long id) {
        return this.healthProfileRepository.findById(id).orElse(null);
    }

}
