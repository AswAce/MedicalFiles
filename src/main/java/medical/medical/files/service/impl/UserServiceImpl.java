package medical.medical.files.service.impl;

import jdk.jshell.spi.ExecutionControl;
import medical.medical.files.model.bindingModels.UserRegisterBindingModel;
import medical.medical.files.model.enteties.PatientEntity;
import medical.medical.files.model.enteties.UserEntity;
import medical.medical.files.model.enums.RoleEnum;
import medical.medical.files.model.serviceModels.PatientServiceModel;
import medical.medical.files.model.serviceModels.UserServiceRegisterModel;
import medical.medical.files.model.viewModels.UserViewModel;
import medical.medical.files.model.viewModels.UserViewPosition;
import medical.medical.files.repositorie.UserRepository;
import medical.medical.files.service.RoleService;
import medical.medical.files.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    public long getUsers() {
        return this.userRepository.count();
    }

    @Override
    public boolean saveUser(UserServiceRegisterModel userServiceModel) {
        if (this.userExist(userServiceModel.getEmail(), userServiceModel.getUsername())) {
            return true;
        }
        UserEntity userEntity = this.modelMapper.map(userServiceModel, UserEntity.class);
        userEntity.setPassword(passwordEncoder.encode(userServiceModel.getPassword()));
        switch (userServiceModel.getRole()) {
            case DOCTOR:
                userEntity.getRoles().add(this.roleService.findByName(RoleEnum.USER));
                userEntity.getRoles().add(this.roleService.findByName(RoleEnum.DOCTOR));
                break;
            case PATIENT:
                userEntity.getRoles().add(this.roleService.findByName(RoleEnum.USER));
                userEntity.getRoles().add(this.roleService.findByName(RoleEnum.PATIENT));
                break;
            case ADMIN:
                userEntity.getRoles().add(this.roleService.findByName(RoleEnum.USER));
                userEntity.getRoles().add(this.roleService.findByName(RoleEnum.PATIENT));
                userEntity.getRoles().add(this.roleService.findByName(RoleEnum.DOCTOR));
                userEntity.getRoles().add(this.roleService.findByName(RoleEnum.ADMIN));
                break;
        }


        UserEntity save = this.userRepository.save(userEntity);
        return false;
    }


    @Override
    public UserEntity findByEmail(String email) {


        return this.userRepository.findByEmail(email).orElse(null);
    }


    @Override
    public ArrayList<UserViewModel> findAll() {
        List<UserViewModel> userViewModels = this.userRepository.
                findAll().
                stream().
                map(userEntity -> {
                    UserViewModel userViewModel = this.modelMapper.map(userEntity, UserViewModel.class);
                    userViewModel.setId(userEntity.getId());

                    ;
                    String collect = userEntity.
                            getRoles().
                            stream().
                            map(roleEntity -> roleEntity.getRole().name()).collect(Collectors.joining(" "));

                    userViewModel.setRole(collect);

                    return userViewModel;
                }).
                collect(Collectors.toList());

        return (ArrayList<UserViewModel>) userViewModels;
    }

    @Override
    public UserViewModel findById(Long id) {
        Optional<UserEntity> byId = this.userRepository.findById(id);
        UserViewModel userViewModel = this.modelMapper.map(byId, UserViewModel.class);
        return userViewModel;
    }


    @Override
    public void addPatient(PatientServiceModel patientServiceModel, long userId) {
        UserEntity userEntity = this.userRepository.findById(userId);

        if (userEntity.getRoles().contains(RoleEnum.USER)) {
            PatientEntity patientEntity = this.modelMapper.map(patientServiceModel, PatientEntity.class);

            userEntity.setPatientEntity(patientEntity);
            this.userRepository.saveAndFlush(userEntity);
        }
    }

    @Override
    public UserViewPosition findByUsername(String username) {
        UserEntity byUsername = this.userRepository.findByUsername(username).orElse(null);
        UserViewPosition userViewPosition = new UserViewPosition();
        if (byUsername == null) {
            return null;
        }
        if (byUsername.getDoctorEntity() == null) {
            userViewPosition.setDoctor(false);
        } else {
            userViewPosition.setDoctor(true);
        }
        if (byUsername.getPatientEntity() == null) {
            userViewPosition.setPatient(false);
        } else {
            userViewPosition.setPatient(true);
        }

        return userViewPosition;
    }

    public boolean userExist(String email, String username) {
        UserEntity userEntity = this.userRepository.findByEmail(email).orElse(null);
        UserEntity userEntity1 = this.userRepository.findByUsername(username).orElse(null);
        if (userEntity == null || userEntity1 == null) {
            return false;
        }
        return true;
    }
}
