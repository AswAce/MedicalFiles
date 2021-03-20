package medical.medical.files.repositorie;

import medical.medical.files.model.enteties.RoleEntity;

import medical.medical.files.model.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByRole(RoleEnum patient);
}
