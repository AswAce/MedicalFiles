package medical.MedicalProject.Services.Service.UserService;

import medical.MedicalProject.Repositories.UserRepository;
import medical.MedicalProject.Services.SeedDto.SeedUser.UserDto;
import medical.MedicalProject.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private ModelMapper modelMapper;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;


    @Autowired
    public UserServiceImpl(ModelMapper modelMapper, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;

        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createUser(UserDto userDto) {
        User newUser = this.modelMapper.map(userDto, User.class);
        String encode = passwordEncoder.encode(newUser.getPassword());
        newUser.setPassword(encode);
        System.out.println();
        for (User u : this.getAllUsers()
        ) {
            if (u.getEmail().equals(newUser.getEmail())) {
                //Throw exxeption
                System.out.println("User exist");
                return;

            }
        }

        this.userRepository.saveAndFlush(newUser);

    }

    @Override
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public boolean matchDbPasswordWithEntered(String email, String enteredPassword) {

        User user = userRepository.findByEmail(email);
        if (user == null)
            throw new UsernameNotFoundException("user not found");
        if (passwordEncoder.matches(enteredPassword, user.getPassword())) {
            System.out.println("Password Match");
//			can change the passwrod as well and store in db
            return true;
        } else {
//			can throw the exception
            System.out.println("Password not matched");
            return false;
        }
    }

    @Override
    public User findByEmail(String email) {
        User byEmail = this.userRepository.findByEmail(email);
        if (byEmail == null) {
            throw new UsernameNotFoundException("user not found");
        }
        return byEmail;
    }


}

