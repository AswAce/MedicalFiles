package medical.MedicalProject.Services.Service.UserService;

import medical.MedicalProject.Services.SeedDto.SeedUser.UserDto;
import medical.MedicalProject.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    void createUser(UserDto userDto);

    List<User> getAllUsers();

    boolean matchDbPasswordWithEntered(String username, String enteredPassword);

    User findByEmail(String email);
}
