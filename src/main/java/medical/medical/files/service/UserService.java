package medical.medical.files.service;

import medical.medical.files.model.enteties.UserEntity;
import medical.medical.files.model.serviceModels.PatientServiceModel;
import medical.medical.files.model.serviceModels.UserServiceRegisterModel;
import medical.medical.files.model.viewModels.UserViewModel;
import medical.medical.files.model.viewModels.UserViewPosition;

import java.util.ArrayList;

public interface UserService {
    long getUsers();

    boolean saveUser(UserServiceRegisterModel user);

    UserEntity findByEmail(String email);



    ArrayList<UserViewModel> findAll();

    UserViewModel findById(Long id);


    boolean userExist(String email,String username);

    void addPatient(PatientServiceModel patientServiceModel, long UserId);

   UserViewPosition findByUsername(String username);
}
