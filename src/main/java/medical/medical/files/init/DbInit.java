package medical.medical.files.init;

import medical.medical.files.model.enteties.*;
import medical.medical.files.model.enums.*;
import medical.medical.files.repositorie.UserRepository;
import medical.medical.files.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DbInit implements CommandLineRunner {
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final MedicalBranchesInit medicalBranchesInit;
    private final DiseaseService diseaseService;
    private final UserRepository userRepository;

    public DbInit(RoleService roleService,
                  PasswordEncoder passwordEncoder,
                  UserService userService,
                  MedicalBranchesInit medicalBranchesInit,
                  DiseaseService diseaseService, UserRepository userRepository) {
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.medicalBranchesInit = medicalBranchesInit;
        this.diseaseService = diseaseService;
        this.userRepository = userRepository;


    }


    @Override
    public void run(String... args) throws Exception {
        this.addRoles();
        this.addAdmin();
        this.medicalBranchesInit.addMedicineBranches();

    }


    private void addRoles() {
        if (this.roleService.countRoles() == 0) {
            this.roleService.saveRole(RoleEnum.ADMIN);

            this.roleService.saveRole(RoleEnum.USER);
            this.roleService.saveRole(RoleEnum.PATIENT);
            this.roleService.saveRole(RoleEnum.DOCTOR);
        }
    }

    private void addAdmin() {
        if (this.userService.getUsers() == 0) {


            UserEntity admin = new UserEntity();
            admin.setEmail("admin@abv.bg");
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("onepieceM9"));
            admin.getRoles().add((this.roleService.findByName(RoleEnum.ADMIN)));
            admin.getRoles().add((this.roleService.findByName(RoleEnum.PATIENT)));
            admin.getRoles().add((this.roleService.findByName(RoleEnum.DOCTOR)));

            this.userRepository.saveAndFlush(admin);
        }
    }


}

