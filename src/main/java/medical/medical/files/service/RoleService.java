package medical.medical.files.service;

import medical.medical.files.model.enteties.RoleEntity;
import medical.medical.files.model.enums.RoleEnum;

import java.util.List;

public interface RoleService {
    long countRoles();

    void saveRole(RoleEnum roleEnum);

    RoleEntity findByName(RoleEnum patient);

    List<RoleEntity> getRoles(RoleEnum roleEnum);
}
