package medical.medical.files.service.impl;

import medical.medical.files.model.bindingModels.AddDoctorProfileBindingModel;
import medical.medical.files.model.enteties.DoctorEntity;
import medical.medical.files.model.serviceModels.AddDoctorProfileServiceModel;
import medical.medical.files.repositorie.DoctorRepository;
import medical.medical.files.service.CloudinaryService;
import medical.medical.files.service.DoctorService;
import medical.medical.files.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;
    private final CloudinaryService cloudinaryService;
    private final UserService userService;

    public DoctorServiceImpl(DoctorRepository doctorRepository, ModelMapper modelMapper, CloudinaryService cloudinaryService, UserService userService) {
        this.doctorRepository = doctorRepository;
        this.modelMapper = modelMapper;
        this.cloudinaryService = cloudinaryService;
        this.userService = userService;
    }

    @Override
    public DoctorEntity findByName(String doctorName) {
        return this.doctorRepository.findByFullName(doctorName);
    }

    @Override
    public void saveDoctorToProfile(AddDoctorProfileServiceModel addDoctorProfileServiceModel) throws IOException {
        MultipartFile img = addDoctorProfileServiceModel.getPictureUrl();

        String imageUrl = cloudinaryService.uploadImage(img);
        DoctorEntity doctorEntity = new DoctorEntity();
        doctorEntity.setPictureUrl(imageUrl);
    }
}
