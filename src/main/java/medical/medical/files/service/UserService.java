package medical.medical.files.service;

import medical.medical.files.model.enteties.UserEntity;

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



   UserViewPosition findByUserNameForDoctorPatientFields(String username);
   UserEntity findByUserName(String username);

    void saveDoctorOrPatientToUser(UserEntity byUsername);


    UserViewModel findByUserNameView(String username);

    void deletePatient(long id);

    void deleteDoctor(long id);

    boolean checkPassword(String name, String oldPassword);

    void editUser(long id, String email, String username, String password);

}
