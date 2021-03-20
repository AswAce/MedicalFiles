package medical.medical.files.service.impl;

import medical.medical.files.model.enteties.RoleEntity;
import medical.medical.files.model.enums.RoleEnum;
import medical.medical.files.repositorie.RoleRepository;
import medical.medical.files.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public long countRoles() {
        return this.roleRepository.count();
    }

    @Override
    public void saveRole(RoleEnum roleEnum) {
        RoleEntity roleEntity = new RoleEntity(roleEnum);
        this.roleRepository.save(roleEntity);
    }

    @Override
    public RoleEntity findByName(RoleEnum patient) {
        return this.roleRepository.findByRole(patient);
    }

    @Override
    public List<RoleEntity> getRoles(RoleEnum roleEnum) {
        List<RoleEntity> roles = new ArrayList<>();
        if (roleEnum == RoleEnum.ADMIN) {
            roles.add(this.roleRepository.findByRole(RoleEnum.USER));
            roles.add(this.roleRepository.findByRole(RoleEnum.ADMIN));
            return roles;
        } else {
            roles.add(this.roleRepository.findByRole(RoleEnum.USER));
            return roles;
        }

    }
}
